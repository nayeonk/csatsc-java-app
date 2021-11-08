/*
-------------------------------------------
  STICKY NAVIGATION
-------------------------------------------
*/
jQuery(function($) {

    "use strict";

    jQuery(document).scroll(function () {

        var STICKY_APPEARS = 129;

        var y = $(document).scrollTop(),
            wpAdminBar = $('#wpadminbar'),
            nav_container = $("#nav-container"),
            nav = nav_container.find("nav"),
            top_spacing = 0,
            offset = 0;

            wpAdminBar = (typeof wpAdminBar.length != 'undefined') ? wpAdminBar.height() : 0;
            top_spacing = top_spacing + wpAdminBar;

        if (y >= STICKY_APPEARS) {
            if (!nav.hasClass("sticky")) {
                nav_container.css({ 'height' : nav.outerHeight() });
                nav.stop().addClass("sticky").css("top", -nav.outerHeight()).animate({"top" : top_spacing});
            }
        } else {
            nav_container.css({ 'height':'auto' });
            nav.stop().removeClass("sticky").css("top",nav.outerHeight() + offset).animate({"top" : ""}, 0);
        }
    });

});
