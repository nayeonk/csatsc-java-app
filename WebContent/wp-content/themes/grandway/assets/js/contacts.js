jQuery(function($) {

    /**
     @TODO: the Attachment section 
    */

    "use strict";

    var Forms = {
        selector: $('[data-form = "contactForm"]'),
        cssClass: {'warning' : 'warning','error' : 'error', 'success' : 'success'},
        status: {'error' : 'contact-form-fail', 'success': 'contact-form-success'},

        hideBox: function(el) {
            if (el && typeof el !== 'undefined')
                $(el).slideUp('slow');
        },

        showBox: function(el) {
            el.slideDown('slow');
        }
    };

    var submitterText = Forms.selector.find('.contact-form-send').val();

    Forms.selector.each(function()
    {
        var that = $(this);
        
        that.submit(function(e) {

            var selector = e.target;

            e.preventDefault();

            // hide all boxes if some exists
            Forms.hideBox( $(selector).find('.' + Forms.cssClass['error']) );
            Forms.hideBox( $(selector).find('.' + Forms.cssClass['success']) );

            var fieldZZ = {},
                submitter = that.find('.contact-form-send');

            fieldZZ.name = that.find('[name = "name"]').val();
            fieldZZ.email = that.find('[name = "email"]').val();
            fieldZZ.subject = that.find('[name = "subject"]').val();
            fieldZZ.message = that.find('[name = "message"]').val();

            if (!fieldZZ.name && !fieldZZ.email && !fieldZZ.subject && !fieldZZ.message) {
                submitter.val(Phoenix.fillAllFields).prop("disabled", true);
                setTimeout(function() {
                    submitter.val(submitterText).prop("disabled", false);
                }, 1500);
                return false;
            }

            var serialiZZer = that.serialize();

            submitter.val(Phoenix.Sending).prop("disabled", true);

            // Ajax request
            $.post( that.attr('action'), "action="+ Phoenix.THEME_SLUG +"_contact_form_ajax_handler&submitted=true&security="+ Phoenix.nonce +"&" + serialiZZer, function(data) {

                var $return = data;            

                if (typeof $return.emailSent != 'undefined' && $return.emailSent === true) {
                    var box = that.find('.' + Forms.status['success']);
                    Forms.showBox( box );
                    submitter.slideUp().remove();
                } else if (typeof $return.emailSent != 'undefined' && $return.emailSent === false) {
                    var box = that.find('.' + Forms.status['error']);
                    Forms.showBox( box );
                    submitter.slideUp().remove();
                } else {
                    for (var i in $return) {
                        var box = $(selector).find('.' + $return[i]);
                        Forms.showBox( box );
                    }
                    submitter.prop("disabled", true);
                    setTimeout(function() {
                        submitter.val(submitterText).prop("disabled", false);
                    }, 1500);
                    return false;
                }
            });

        });
    });

})
