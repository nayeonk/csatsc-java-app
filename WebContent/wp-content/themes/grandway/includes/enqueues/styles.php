<?php

new PhoenixTeam_Styles();

class PhoenixTeam_Styles {

    public function __construct ()
    {
        add_action('wp_enqueue_scripts', array($this, 'conditional_styles')); // Add Conditional Page Scripts
        add_action('wp_enqueue_scripts', array($this, 'phoenixteam_theme_styles')); // Add Theme Stylesheets
    }

    // Load styles
    public function phoenixteam_theme_styles ()
    {
        global $PhoenixData;
        $port_layout = isset($PhoenixData['port_layout']) ? $PhoenixData['port_layout'] : '2-cols';
        $theme_skin = isset($PhoenixData['theme_skin']) ? $PhoenixData['theme_skin'] : 'default';
        $set_color = isset($PhoenixData['set_color']) ? $PhoenixData['set_color'] : false;
        $custom_skin_color = isset($PhoenixData['custom_skin_color']) ? $PhoenixData['custom_skin_color'] : null;
        $css_code = isset($PhoenixData['css_code']) ? $PhoenixData['css_code'] : null;
        $footer_skin = isset($PhoenixData['footer_skin']) ? $PhoenixData['footer_skin'] : 'dark';

        switch ($port_layout) {
            case '2-cols': $cube_css = 'cubeportfolio-2'; break;
            case '3-cols': $cube_css = 'cubeportfolio-3'; break;
            case 'full': $cube_css = 'cubeportfolio-3'; break;
            case '4-cols': $cube_css = 'cubeportfolio-4'; break;
            default: $cube_css = 'cubeportfolio-2'; break;
        }

        wp_enqueue_style(
            THEME_SLUG . '-bootstrap',
            THEME_URI . '/assets/css/bootstrap.min.css',
            array(),
            '3.2.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-cubeportfolio',
            THEME_URI . '/assets/css/'. $cube_css .'.min.css',
            array(),
            '1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-jcarousel',
            THEME_URI . '/assets/css/jcarousel.responsive.css',
            array(),
            '1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-owl-carousel',
            THEME_URI . '/assets/css/owl.carousel.css',
            array(),
            '1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-fontawesome',
            THEME_URI . '/assets/css/font-awesome.min.css',
            array(),
            '4.1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-et-line',
            THEME_URI . '/assets/css/et-line.css',
            array(),
            '1.0.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-bxslider',
            THEME_URI . '/assets/css/jquery.bxslider.css',
            array(),
            '1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-testimonialrotator',
            THEME_URI . '/assets/css/testimonialrotator.css',
            array(),
            '1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-magnific',
            THEME_URI . '/assets/css/magnific.css',
            array(),
            '1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-main',
            get_stylesheet_uri(),
            array(),
            '1.0',
            'all'
        );

        wp_enqueue_style(
            THEME_SLUG . '-responsive',
            THEME_URI . '/assets/css/responsive.css',
            array(),
            '1.0',
            'all'
        );

        if ($footer_skin == 'light')
            wp_enqueue_style(
                THEME_SLUG . '-footer-light-skin',
                THEME_URI . '/assets/css/layouts/light-skin.css',
                array(),
                '1.0',
                'all'
            );

        if ($theme_skin != 'default' && !$custom_skin) {
            wp_enqueue_style(
                THEME_SLUG . '-theme-skin',
                THEME_URI . '/assets/css/layouts/'. $theme_skin .'.css',
                array(),
                '1.0',
                'all'
            );
        }

        if ($set_color && $custom_skin_color) {
            $custom_skin_css =
            '/* Custom Color CSS */' .
                'a {color: '.$custom_skin_color.';}' .
                'a:hover,a:focus{color:'.$custom_skin_color.';}' .
                '.top_line { background: '.$custom_skin_color.' }' .
                '.menu ul li.current-menu-item a {color: '.$custom_skin_color.';}' .
                '.menu li a:hover {color: '.$custom_skin_color.';}' .
                '.menu ul li ul li:first-child a { border-top: 1px solid '.$custom_skin_color.' }' .
                '.menu ul li ul li:first-child a:hover { border-top: 1px solid '.$custom_skin_color.' }' .
                '.center-line {background: '.$custom_skin_color.'; }' .
                '.hi-icon {color: '.$custom_skin_color.'}' .
                '.grid figcaption {background: '.$custom_skin_color.'}' .
                '.cbp-l-filters-button .cbp-filter-item-active {background-color: '.$custom_skin_color.'; border-color: '.$custom_skin_color.'}' .
                '.cbp-l-filters-button .cbp-filter-counter {background-color: '.$custom_skin_color.'}' .
                '.cbp-l-filters-button .cbp-filter-counter:before {border-top: 4px solid '.$custom_skin_color.'}' .
                '.portfolio-phoenixteam {border-bottom: 1px solid '.$custom_skin_color.'}' .
                '.portfolio-image {background: '.$custom_skin_color.'}' .
                '.page-in-name span { color: '.$custom_skin_color.'}' .
                '.page-in-bread a {color: '.$custom_skin_color.'}' .
                '.first-letter {background: '.$custom_skin_color.'}' .
                '.list-check li  i {color: '.$custom_skin_color.'}' .
                '.blog-name a:hover {color: '.$custom_skin_color.'}' .
                '.blog-icons a:hover { color: '.$custom_skin_color.'}' .
                '.sticky-lou.has-post-thumbnail .cl-blog-img:before {color: '.$custom_skin_color.'}' .
                '.sticky-lou:before {color: '.$custom_skin_color.'}' .
                '.cl-blog-name a:hover {color: '.$custom_skin_color.'}' .
                '.cl-blog-type {color: '.$custom_skin_color.'}' .
                '.cl-blog-read a i  {color:'.$custom_skin_color.'}' .
                '.cl-blog-like .phoenix-liked {color:'.$custom_skin_color.'}' .
                '.cl-blog-read a:hover {color: '.$custom_skin_color.'}' .
                '.pride_pg .current {color: '.$custom_skin_color.'; border: 1px solid '.$custom_skin_color.'}' .
                '.pride_pg a:hover {color: '.$custom_skin_color.'; border: 1px solid '.$custom_skin_color.'}' .
                '.blog-category li i {color: '.$custom_skin_color.'}' .
                '.widget ul li > a:before {color: '.$custom_skin_color.'}' .
                '.recentcomments:before {color: '.$custom_skin_color.';}' .
                '.twitter_fot {background: '.$custom_skin_color.'}' .
                '.recentcomments a { color: '.$custom_skin_color.' !important}' .
                '.rsswidget:before {color: '.$custom_skin_color.'}' .
                '.rsswidget { color: '.$custom_skin_color.'}' .
                '.tweet_list a { color: '.$custom_skin_color.'}' .
                '.footer-widget .tagcloud a:hover {border-color: '.$custom_skin_color.'; background: '.$custom_skin_color.'}' .
                '.tweet_text a { color: '.$custom_skin_color.'}' .
                '.tags-blog li a:hover {color: '.$custom_skin_color.'}' .
                '.soc-blog li a:hover { color: '.$custom_skin_color.'}' .
                '.comm_name {color: '.$custom_skin_color.'}' .
                '.soc-about {border-bottom: 1px solid '.$custom_skin_color.'}' .
                '.soc-about li a:hover { color: '.$custom_skin_color.'}' .
                '.fact-icon {color: '.$custom_skin_color.'}' .
                '.progress-bar-info { background-color: '.$custom_skin_color.'}' .
                '.serv-marg i {color: '.$custom_skin_color.'}' .
                '.serv-icon i { color: '.$custom_skin_color.'}' .
                '.oops {color: '.$custom_skin_color.'}' .
                '.ac-container label:hover {color: '.$custom_skin_color.'}' .
                '.ac-container input:checked + label,.ac-container input:checked + label:hover {color: '.$custom_skin_color.'}' .
                '.plan.featured h3 {color: '.$custom_skin_color.'}' .
                '.plan.featured .price { color: '.$custom_skin_color.'}' .
                '.btn-price {background-color: '.$custom_skin_color.'; border-color: '.$custom_skin_color.'}' .
                '.btn-price:hover {color: '.$custom_skin_color.'; border-color: '.$custom_skin_color.'}' .
                '.shortcode_tab_item_title.active {color: '.$custom_skin_color.'}' .
                '.shortcode_tab_item_title:hover {color: '.$custom_skin_color.'}' .
                '.tooltip_s { color: '.$custom_skin_color.'}' .
                '.tooltip_s:hover { color: '.$custom_skin_color.'}' .
                '.btn-info {background-color: '.$custom_skin_color.'}' .
                '.btn-grandway {background-color: '.$custom_skin_color.'}' .
                '.menu-menu-container > ul > li.current-menu-item > a {color:'.$custom_skin_color.'}' .
                '.menu-menu-container > ul > li.current_page_item > a {color:'.$custom_skin_color.'}' .
                '.wpb_accordion_header.ui-accordion-header-active a {color: '.$custom_skin_color.' !important}' .
                '.wpb_accordion_header.ui-state-hover a {color: '.$custom_skin_color.' !important}' .
                '.woocommerce span.onsale,.woocommerce-page span.onsale {background: '.$custom_skin_color.' !important;}' .
                '.woocommerce-page ul.products li.product .price {color: '.$custom_skin_color.';}' .
                '.single-product.woocommerce .star-rating span:before,.woocommerce-page .star-rating span:before { color: '.$custom_skin_color.' }' .
                '.woocommerce .star-rating span:before,.woocommerce-page .star-rating span:before {color: '.$custom_skin_color.';}' .
                '.woocommerce-page .star-rating span:before { color: '.$custom_skin_color.' }' .
                '.woocommerce .widget_price_filter .ui-slider .ui-slider-handle,.woocommerce-page .widget_price_filter .ui-slider .ui-slider-handle {border: 2px solid '.$custom_skin_color.'}' .
                '.woocommerce .widget_price_filter .ui-slider .ui-slider-range,.woocommerce-page .widget_price_filter .ui-slider .ui-slider-range {background: '.$custom_skin_color.';}' .
                '.grandway-product-rate-n-price {background: '.$custom_skin_color.';}' .
                '.woocommerce div.product span.price,.woocommerce div.product p.price,.woocommerce #content div.product span.price,.woocommerce #content div.product p.price,.woocommerce-page div.product span.price,.woocommerce-page div.product p.price,.woocommerce-page #content div.product span.price,.woocommerce-page #content div.product p.price { color: '.$custom_skin_color.' }' .
                '.wpmenucartli a:hover i {color: '.$custom_skin_color.' }' .
                '.woo-title-price h2:hover a { color: '.$custom_skin_color.' }' .
                '.flickr_widget_wrapper img:hover {border: 2px solid '.$custom_skin_color.' }' .
                '.button_switch li a {background: '.$custom_skin_color.' }' .
                '.button_switch li a.active {color: '.$custom_skin_color.' }' .
                '.button_switch li a:hover {color: '.$custom_skin_color.' }' .
                '.btn-item:hover {color: '.$custom_skin_color.' }' .
                '.item-heart i {color: '.$custom_skin_color.' }' .
                '.tp-caption.Gym-Menuitem{background-color: '.$custom_skin_color.' !important }' .
                '.tp-caption.Video-Title{background-color: '.$custom_skin_color.' !important }' .

                '@media screen and (max-width: 991px) {' .
                '.menu-main-menu-container {background: '.$custom_skin_color.';}' .
                '.phoenixteam-menu-wrapper button:hover, .phoenixteam-menu-wrapper button.dl-active, .dl-menuwrapper ul { background: '.$custom_skin_color.' }' .
                '.phoenixteam-menu-wrapper button {background: '.$custom_skin_color.' }' .
                '.menu ul li a {background: '.$custom_skin_color.';}' .
                '.menu ul ul {background: '.$custom_skin_color.';}' .
                '}' .

            '/* Custom Color CSS END */';

            wp_add_inline_style(THEME_SLUG . '-responsive', $custom_skin_css);
        }

        if ($css_code)
            wp_add_inline_style(THEME_SLUG . '-responsive', '/* Custom CSS */' . $css_code . '/* Custom CSS END */');

        $this->custom_background();
    }


    private function custom_background ()
    {
        global $PhoenixData;
        $boxed = isset($PhoenixData['boxed_swtich']) ? $PhoenixData['boxed_swtich'] : 'full';

        if ($boxed == 'boxed') {

            $bg_size = ( isset($PhoenixData['boxed_background']['background-size']) && $PhoenixData['boxed_background']['background-size'] != null ) ? 'background-size: '. $PhoenixData['boxed_background']['background-size'] . "; " : null;
            $bg_color = ( isset($PhoenixData['boxed_background']['background-color']) && $PhoenixData['boxed_background']['background-color'] != null ) ? 'background-color: ' .$PhoenixData['boxed_background']['background-color'] . "; " : null;
            $bg_image = ( isset($PhoenixData['boxed_background']['background-image']) && $PhoenixData['boxed_background']['background-image'] != null ) ? 'background-image: url("' . $PhoenixData['boxed_background']['background-image'] . '")' . "; " : null;
            $bg_repeat = ( isset($PhoenixData['boxed_background']['background-repeat']) && $PhoenixData['boxed_background']['background-repeat'] != null ) ? 'background-repeat: ' . $PhoenixData['boxed_background']['background-repeat'] . "; " : null;
            $bg_position = ( isset($PhoenixData['boxed_background']['background-position']) && $PhoenixData['boxed_background']['background-position'] != null ) ? 'background-position: ' . $PhoenixData['boxed_background']['background-position'] . "; " : null;
            $bg_attachment = ( isset($PhoenixData['boxed_background']['background-attachment']) && $PhoenixData['boxed_background']['background-attachment'] != null ) ? 'background-attachment: ' . $PhoenixData['boxed_background']['background-attachment'] . "; " : null;

            $boxed_css = " body { ";
                if ($bg_size) $boxed_css .= $bg_size;
                if ($bg_color) $boxed_css .= $bg_color;
                if ($bg_image) $boxed_css .= $bg_image;
                if ($bg_repeat) $boxed_css .= $bg_repeat;
                if ($bg_position) $boxed_css .= $bg_position;
                if ($bg_attachment) $boxed_css .= $bg_attachment;
            $boxed_css .= " }";

            wp_add_inline_style(THEME_SLUG . '-main', '/* Boxed CSS Layout */' . $boxed_css . '/* Boxed CSS Layout END */');

            return true;
        }

        return false;
    }


    public function conditional_styles ()
    {
        if (is_singular(THEME_SLUG . '_portfolio')) {

            wp_dequeue_style(THEME_SLUG . '-cubeportfolio');

            wp_enqueue_style(
                THEME_SLUG . '-cubeportfolio-single',
                THEME_URI . '/assets/css/cubeportfolio-3.min.css',
                array(),
                '1.0',
                'all'
            );
        }
    }

}
