(function ($, window, document, undefined) {

    var gridContainer = $('#grid-container-portfolio'),
        filtersContainer = $('#filters-container-portfolio');

    // init cubeportfolio
    gridContainer.cubeportfolio({
        animationType: 'skew',
        gapHorizontal: 30,
        gapVertical: 30,
        gridAdjustment: 'responsive',
        caption: 'overlayBottomReveal',
        displayType: 'lazyLoading',
        displayTypeSpeed: 100,
        // lightbox
        lightboxDelegate: '.cbp-lightbox',
        lightboxGallery: true,
        lightboxTitleSrc: 'data-title',
        lightboxShowCounter: true,
        // singlePage popup
        singlePageDelegate: '.cbp-singlePage',
        singlePageDeeplinking: true,
        singlePageStickyNavigation: true,
        singlePageShowCounter: true,
        singlePageCallback: function (url, element) {
            // to update singlePage content use the following method: this.updateSinglePage(yourContent)
        },
        // singlePageInline
        singlePageInlineDelegate: '.cbp-singlePageInline',
        singlePageInlinePosition: 'below',
        singlePageInlineShowCounter: true,
        singlePageInlineCallback: function(url, element) {
            // to update singlePageInline content use the following method: this.updateSinglePageInline(yourContent)
            var t = this;

            var data = {
                'action': PhoenixTeam.THEME_SLUG + '_get_inline_portfolio',
                'security': PhoenixTeam.nonce,
                'url' : url
            };

            $.ajax({
                url: PhoenixTeam.ajaxUrl,
                data: data,
                type: 'GET',
                timeout: 5000
            })
            .done(function(result) {
                t.updateSinglePageInline(result);
            })
            .fail(function() {
                t.updateSinglePageInline(portSetts.inlineError);
            });
        }
    });

    // add listener for filters click
    filtersContainer.on('click', '.cbp-filter-item', function (e) {
        var me = $(this), wrap;

        // get cubeportfolio data and check if is still animating (reposition) the items.
        if ( !$.data(gridContainer[0], 'cubeportfolio').isAnimating ) {
            me.addClass('cbp-filter-item-active').siblings().removeClass('cbp-filter-item-active');
        }

        // filter the items
        gridContainer.cubeportfolio('filter', me.data('filter'), function () {});
    });

    // activate counter for filters
    gridContainer.cubeportfolio('showCounter', filtersContainer.find('.cbp-filter-item'));

    // add listener for load more click
    $('.cbp-l-loadMore-button-link').on('click', function(e) {

        e.preventDefault();

        var clicks, me = $(this), oMsg;

        if (me.hasClass('cbp-l-loadMore-button-stop')) return;

        // get the number of times the loadMore link has been clicked
        clicks = $.data(this, 'numberOfClicks');
        clicks = (clicks)? ++clicks : 1;
        $.data(this, 'numberOfClicks', clicks);

        // set loading status
        oMsg = me.text();
        me.text(portSetts.moreLoading);

        var data = {
            'action': PhoenixTeam.THEME_SLUG + '_get_more_portfolio',
            'security': PhoenixTeam.nonce,
            'query': PhoenixTeam.queryVars,
            'page' : PhoenixTeam.currentPage
        };

        // perform ajax request
        $.ajax({
            url: PhoenixTeam.ajaxUrl,
            data: data,
            type: 'POST'
        })
        .done( function (result) {
            var items;

            if( result ) {
                gridContainer.find('ul').append(result);

                // put the original message back
                setTimeout(function() {
                    me.text(oMsg);
                }, 1000);

                PhoenixTeam.currentPage++;
            } else {
                me.text(portSetts.moreNoMore);
                me.addClass('cbp-l-loadMore-button-stop');
            }

            // find current container
            items = $(result).filter( function () {
                return $(this).is('div' + '.cbp-loadMore-block' + clicks);
            });

            gridContainer.cubeportfolio('appendItems', items.html());

        })
        .fail(function() {
            // error
        });

    });

})(jQuery, window, document);
