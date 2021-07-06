<%@tag description="Generic page template with header and footer" %>
<%@attribute name="name" description="The logged in user's name to be displayed at the top in format: Welcome {name}" %>
<%@attribute name="styles" fragment="true" description="Add additional styles within the <head/>" %>
<%@attribute name="scripts" fragment="true" description="Add additional scripts at the bottom of the document" %>

<html lang="en-US">
<head>
    <title>USC - CS@SC Summer Camps : USC &#8211; CS@SC Summer Camps</title>

    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1" name="viewport"/>

    <%-- Raw CSS --%>
    <style>
        img.wp-smiley,
        img.emoji {
            display: inline !important;
            border: none !important;
            box-shadow: none !important;
            height: 1em !important;
            width: 1em !important;
            margin: 0 .07em !important;
            vertical-align: -0.1em !important;
            background: none !important;
            padding: 0 !important;
        }

        .menu li a:hover {
            color: #9A1E20;
        }
    </style>

    <%-- CSS Imports --%>
    <link rel="stylesheet" href="css/admin-control-panel.css"/>
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="css/forms.css" media="screen"/>
    <link rel="stylesheet" href="lib/fancybox/source/jquery.fancybox.css"/>

    <link rel='stylesheet' id='contact-form-7-css'
          href='/wp-content/plugins/contact-form-7/includes/css/styles.css?ver=4.3'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='bwg_frontend-css'
          href='/wp-content/plugins/photo-gallery/css/bwg_frontend.css?ver=1.2.70'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='bwg_font-awesome-css'
          href='/wp-content/plugins/photo-gallery/css/font-awesome/font-awesome.css?ver=4.2.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='bwg_mCustomScrollbar-css'
          href='/wp-content/plugins/photo-gallery/css/jquery.mCustomScrollbar.css?ver=1.2.70'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='rs-plugin-settings-css'
          href='/wp-content/plugins/revslider/public/assets/css/settings.css?ver=5.0.8'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-bootstrap-css'
          href='/wp-content/themes/grandway/assets/css/bootstrap.min.css?ver=3.2.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-cubeportfolio-css'
          href='/wp-content/themes/grandway/assets/css/cubeportfolio-3.min.css?ver=1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-jcarousel-css'
          href='/wp-content/themes/grandway/assets/css/jcarousel.responsive.css?ver=1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-owl-carousel-css'
          href='/wp-content/themes/grandway/assets/css/owl.carousel.css?ver=1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-fontawesome-css'
          href='/wp-content/themes/grandway/assets/css/font-awesome.min.css?ver=4.1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-et-line-css'
          href='/wp-content/themes/grandway/assets/css/et-line.css?ver=1.0.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-bxslider-css'
          href='/wp-content/themes/grandway/assets/css/jquery.bxslider.css?ver=1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-testimonialrotator-css'
          href='/wp-content/themes/grandway/assets/css/testimonialrotator.css?ver=1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-magnific-css'
          href='/wp-content/themes/grandway/assets/css/magnific.css?ver=1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='grandway-main-css'
          href='/wp-content/themes/grandway/style.css?ver=1.0' type='text/css'
          media='all'/>
    <link rel='stylesheet' id='grandway-responsive-css'
          href='/wp-content/themes/grandway/assets/css/responsive.css?ver=1.0'
          type='text/css' media='all'/>
    <link rel='stylesheet' id='wp-add-custom-css-css' href='?display_custom_css=css&#038;ver=4.3.1'
          type='text/css' media='all'/>

    <%-- Google Analytics Script --%>
    <script>
        (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r;
            i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
            a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a, m)
        })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

        ga('create', 'UA-62544517-1', 'auto');
        ga('send', 'pageview');
    </script>

    <%-- Scripts --%>
    <script>
        var themeRootDirectory = "";
        var PhoenixTeam = {
            "THEME_SLUG": "grandway",
            "ajaxUrl": "http:\/\/68.181.97.192\/wordpress\/wp-admin\/admin-ajax.php",
            "nonce": "8147f93bd0"
        };
    </script>
    <script src="scripts/jquery1.7.2.js"></script>
    <script type='text/javascript'
            src='/wp-includes/js/jquery/jquery-migrate.min.js?ver=1.2.1'></script>
    <script type='text/javascript'
            src='/wp-content/plugins/photo-gallery/js/bwg_frontend.js?ver=1.2.70'></script>
    <script type='text/javascript'
            src='/wp-content/plugins/photo-gallery/js/jquery.mobile.js?ver=1.2.70'></script>
    <script type='text/javascript'
            src='/wp-content/plugins/photo-gallery/js/jquery.mCustomScrollbar.concat.min.js?ver=1.2.70'></script>
    <script type='text/javascript'
            src='/wp-content/plugins/photo-gallery/js/jquery.fullscreen-0.4.1.js?ver=0.4.1'></script>
    <script type='text/javascript'>
        /* <![CDATA[ */
        var bwg_objectL10n = {
            "bwg_field_required": "field is required.",
            "bwg_mail_validation": "This is not a valid email address.",
            "bwg_search_result": "There are no images matching your search."
        };
        /* ]]> */
    </script>
    <script type='text/javascript'
            src='/wp-content/plugins/photo-gallery/js/bwg_gallery_box.js?ver=1.2.70'></script>
    <script type='text/javascript'
            src='/wp-content/plugins/revslider/public/assets/js/jquery.themepunch.tools.min.js?ver=5.0.8'></script>
    <script type='text/javascript'
            src='/wp-content/plugins/revslider/public/assets/js/jquery.themepunch.revolution.min.js?ver=5.0.8'></script>
    <script type='text/javascript'
            src='/wp-content/themes/grandway/assets/js/modernizr.custom.js?ver=1.0.0'></script>
    <script type='text/javascript'
            src='/wp-content/themes/grandway/assets/customjs/tabcontrol.js?ver=4.3.1'></script>

    <!-- backend scripts -->
    <script src="scripts/admin-control-panel.js"></script>
    <script src="lib/fancybox/source/jquery.fancybox.pack.js"></script>
    <!-- end backend scripts -->

    <link rel="EditURI" type="application/rsd+xml" title="RSD" href="/wordpress/xmlrpc.php?rsd"/>
    <link rel="wlwmanifest" type="application/wlwmanifest+xml"
          href="/wp-includes/wlwmanifest.xml"/>
    <meta name="generator" content="WordPress 4.3.1"/>
    <link rel='canonical' href='/'/>
    <link rel='shortlink' href='/'/>
    <style type="text/css">.recentcomments a {
        display: inline !important;
        padding: 0 !important;
        margin: 0 !important;
    }</style>
    <meta name="generator" content="Powered by Visual Composer - drag and drop page builder for WordPress."/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" type="text/css"
          href="http://68.181.97.192/wordpress/wp-content/plugins/js_composer/assets/css/vc_lte_ie9.css" media="screen">
    <![endif]--><!--[if IE  8]>
    <link rel="stylesheet" type="text/css"
          href="http://68.181.97.192/wordpress/wp-content/plugins/js_composer/assets/css/vc-ie8.css" media="screen">
    <![endif]-->
    <meta name="generator"
          content="Powered by Slider Revolution 5.0.8 - responsive, Mobile-Friendly Slider Plugin for WordPress with comfortable drag and drop interface."/>
    <noscript>
        <style> .wpb_animate_when_almost_visible {
            opacity: 1;
        }</style>
    </noscript>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic|Open+Sans+Condensed:300,300italic,700'
          rel='stylesheet' type='text/css'>

    <jsp:invoke fragment="styles"/>

</head>
<body class="home page page-id-5 page-template page-template-template-home page-template-template-home-php home-page wpb-js-composer js-comp-ver-4.7 vc_responsive">
<header class="wrapper phoenixteam-wrapper-full">
    <div class="page_head">
        <div id="nav-container" class="nav-container" style="height: auto;">
            <nav role="navigation">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6 pull-left">
                            <div class="logo">
                                <a href="#"><a href="#"><img
                                        src="/wp-content/uploads/2016/02/header-logo.png"
                                        alt="Logo"></a></a>
                            </div>
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-9 col-xs-6 pull-right">
                            <div class="menu phoenixteam-menu-wrapper">
                                <button type="button" class="navbar-toggle" data-toggle="collapse"
                                        data-target=".navbar-collapse"></button>
                                <div class="navbar-collapse collapse">
                                    <div class="menu-main-menu-container">
                                        <ul>
                                            <li id="menu-item-30"
                                                class="menu-item menu-item-type-post_type menu-item-object-page page_item page-item-5">
                                                <a href="#">Home</a></li>
                                            <li id="menu-item-29"
                                                class="menu-item menu-item-type-post_type menu-item-object-page menu-item-has-children">
                                                <a href="/about-us/">About Us<span
                                                        class="topmenu-description">Learn more</span></a>
                                                <ul class="sub-menu">
                                                    <li id="menu-item-48"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/about-us/">Our Mission</a>
                                                    </li>
                                                    <li id="menu-item-49"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/staff/">Staff</a></li>
                                                    <li id="menu-item-50"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/sponsors/">Sponsors</a></li>
                                                    <li id="menu-item-300"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/contact-us/">Contact Us</a>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li id="menu-item-51"
                                                class="menu-item menu-item-type-post_type menu-item-object-page menu-item-has-children">
                                                <a href="/camp-info/">Camp Info<span
                                                        class="topmenu-description">Essentials</span></a>
                                                <ul class="sub-menu">
                                                    <li id="menu-item-52"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/camp-info/">General Info</a>
                                                    </li>
                                                    <li id="menu-item-53"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/camps/">Current Camps
                                                            Offered</a></li>
                                                    <li id="menu-item-82"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/schedule/">Schedule</a></li>
                                                    <li id="menu-item-81"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/key-documents/">Key
                                                            Documents</a></li>
                                                    <li id="menu-item-345"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/pictures-videos/">Pictures /
                                                            Videos</a></li>
                                                </ul>
                                            </li>
                                            <li id="menu-item-57"
                                                class="menu-item menu-item-type-post_type menu-item-object-page menu-item-has-children">
                                                <a href="/resources/">Resources<span
                                                        class="topmenu-description">Helpful tools</span></a>
                                                <ul class="sub-menu">
                                                    <li id="menu-item-68"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/directions-parking/">Directions
                                                            / Parking</a></li>
                                                    <li id="menu-item-67"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/tutorials/">Tutorials</a>
                                                    </li>
                                                    <li id="menu-item-66"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/post-camp-projects/">Post-Camp
                                                            Projects</a></li>
                                                    <li id="menu-item-304"
                                                        class="menu-item menu-item-type-post_type menu-item-object-page">
                                                        <a href="/publications/">Publications</a>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li id="menu-item-312"
                                                class="menu-item menu-item-type-post_type menu-item-object-page"><a
                                                    href="/testimonials/">Testimonials</a>
                                            </li>
                                            <li id="menu-item-72"
                                                class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current-page-item">
                                                <a href="/SummerCamp/login">Login<span class="topmenu-description">Apply Now!</span></a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</header>
<div id="page-title">
    <h1 style="text-align:center;">Welcome ${name}</h1>
</div>
<div class="container" id="container">
    <br class="clear"/>
    <jsp:doBody/>
</div>
</body>
<footer class="footer general-font-area footer-bottom-top-section-present">
    <div class="footer-header">
        <div class="theme-banner"></div>
        <div class="highlight-banner"></div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-3 col-sm-6">
                <div id="text-3" class="widget_text footer-widget"><h4 class="widget-title">Quick Links</h4>
                    <div class="textwidget">
                        <ul>
                            <li class="row">
                                <div class="image-container col-xs-2"><span class="vertical-align-helper"></span><img
                                        src="/wp-content/uploads/2015/10/link.png"/>
                                </div>
                                <a class="col-xs-10" href="http://cs.usc.edu/">Department of Computer Science</a></li>
                            <li class="row">
                                <div class="image-container col-xs-2"><span class="vertical-align-helper"></span><img
                                        src="/wp-content/uploads/2015/10/link.png"/>
                                </div>
                                <a class="col-xs-10" href="http://viterbi.usc.edu/">Viterbi School of Engineering</a>
                            </li>
                            <li class="row">
                                <div class="image-container col-xs-2"><span class="vertical-align-helper"></span><img
                                        src="/wp-content/uploads/2015/10/link.png"/>
                                </div>
                                <a class="col-xs-10" href="http://www.usc.edu/">University of Southern California</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-4 col-sm-6">
                <div class="widget_text footer-widget">
                    <h4 class="widget-title">Contact Us</h4>
                    <ul class="textwidget link-list">
                        <li class="list-item list-item-person row">
                            <img class="col-xs-2 link-image"
                                 src="/wp-content/uploads/2015/10/person@2x.png"/>
                            <span class="col-xs-10 link-text">Professor Jeffrey Miller</span>
                        </li>
                        <li class="list-item list-item-email row">
                            <img class="col-xs-2 link-image"
                                 src="/wp-content/uploads/2015/10/email@2x.png"/>
                            <span class="col-xs-10 link-text">Email: <a
                                    href="mailto:cscamps@usc.edu">cscamps@usc.edu</a></span>
                        </li>
                        <li class="list-item list-item-phone row">
                            <img class="link-image col-xs-2"
                                 src="/wp-content/uploads/2015/10/phone@2x.png"/>

                            <span class="col-xs-10 link-text">Phone: (213) 740-7129</span>
                        </li>
                        <li class="list-item list-item-location row">
                            <div class="image-container col-xs-2">
                                <span class="vertical-align-helper"></span>
                                <img class="link-image"
                                     src="/wp-content/uploads/2015/10/location@2x.png"/>
                            </div>
                            <span class="col-xs-10 link-text">Salvatori Computer Science Center<br/>941 Bloom Walk, SAL 342<br/>Los Angeles, California 90089-0781</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-md-4 footer-map-container">
                <div class="footer-widget">
                    <iframe width="100%" class="footer-map" frameborder="0" style="border:0"
                            src="https://www.google.com/maps/embed/v1/place?q=Salvatori+Computer+Science+Center&key=AIzaSyC2_ff79YHTcR0BSeSCijKJVOgKE-vJO1k"
                            allowfullscreen></iframe>
                </div>
            </div>
        </div>
    </div>

</footer>
<div class="footer_bottom">
    <div class="container">
        <div class="footer-bottom">
            <div class="row text-center">
                <div class="foot_menu" style="float: none; display: block;">
                    <div class="menu-footer-menu-container">
                        <ul>
                            <li id="menu-item-69"
                                class="menu-item menu-item-type-custom menu-item-object-custom menu-item-69"><a
                                    href="http://www.usc.edu/">University of Southern California</a></li>
                            <li id="menu-item-70"
                                class="menu-item menu-item-type-custom menu-item-object-custom menu-item-70"><a
                                    href="http://cs.usc.edu/">Department of Computer Science</a></li>
                            <li id="menu-item-71"
                                class="menu-item menu-item-type-custom menu-item-object-custom menu-item-71"><a
                                    href="http://viterbi.usc.edu/">Viterbi School of Engineering</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript'
        src='/wp-content/plugins/contact-form-7/includes/js/jquery.form.min.js?ver=3.51.0-2014.06.20'></script>
<script type='text/javascript'>
    var _wpcf7 = {
        "loaderUrl": "http:\/\/68.181.97.192\/wordpress\/wp-content\/plugins\/contact-form-7\/images\/ajax-loader.gif",
        "sending": "Sending ..."
    };
</script>
<script type='text/javascript'
        src='/wp-content/plugins/contact-form-7/includes/js/scripts.js?ver=4.3'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/sticky-be.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/bootstrap.min.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/jquery.bxslider.min.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/retina.min.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/jquery.cycle.all.js?ver=1.0.0'></script>
<script type='text/javascript'>
    var ajax_var = {"url": "http:\/\/68.181.97.192\/wordpress\/wp-admin\/admin-ajax.php", "nonce": "06cd892ae2"};
</script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/likes.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/jquery.cubeportfolio.min.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/jcarousel.responsive.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/jquery.jcarousel.min.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/owl.carousel.min.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/magnific.popup.min.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/testimonialrotator.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/contacts.js?ver=1.0.0'></script>
<script type='text/javascript'
        src='/wp-content/themes/grandway/assets/js/main.js?ver=1.0.0'></script>

<jsp:invoke fragment="scripts"/>

</html>
