<?php

new PhoenixTeam_Scripts();

class PhoenixTeam_Scripts {

    private $gaID;
    private $jsCODE;

    public function __construct ()
    {
        global $data;

        add_action('wp_enqueue_scripts', array($this, 'enqueue_scripts')); // Add Custom Scripts to wp_head
        add_action('wp_enqueue_scripts', array($this, 'conditional_scripts')); // Add Conditional Page Scripts

        $analytics = isset($data['analytics_switch']) ? $data['analytics_switch'] : false;
        $this->gaID = isset($data['ga_id']) ? $data['ga_id'] : null;
        $this->jsCODE = isset($data['js_code']) ? $data['js_code'] : false;

        // Add Google Analytics
        if ($analytics && $this->gaID) {
            add_action('wp_footer', array($this, 'PhoenixTeam_ga'), 99);
        }

        // Add Custom JS
        if ($this->jsCODE) {
            add_action('wp_footer', array($this, 'PhoenixTeam_custom_js'), 99);
        }
    }

    // Load Custom JS
    public function PhoenixTeam_custom_js ()
    {
        echo '<script type="text/javascript">' . esc_js($this->jsCODE) . '</script>';
    }

    // Load Google Analytics
    public function PhoenixTeam_ga ()
    {
        echo "
        <script type='text/javascript'>
            var _gaq = _gaq || []; _gaq.push(['_setAccount', '". esc_js($this->gaID) ."']); _gaq.push(['_trackPageview']); (function() { var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true; ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js'; var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s); })();
        </script>\n";
    }

    // Load theme scripts
    public function enqueue_scripts ()
    {
        global $PhoenixData, $template;

        $show_sticky_menu = isset($PhoenixData['use_sticky']) ? $PhoenixData['use_sticky'] : true;

        if ($GLOBALS['pagenow'] != 'wp-login.php' && !is_admin()) {

            // In Header
            wp_enqueue_script(
              THEME_SLUG . '-modernizr',
              THEME_URI . '/assets/js/modernizr.custom.js',
              array(),
              '1.0.0'
            );

            // Enqueue scripts
            wp_enqueue_script('jquery');

            // Localize scripts
            wp_localize_script('jquery', THEME_TEAM, PhoenixTeam_Utils::javascript_globals());

            // In Footer
            if ($show_sticky_menu)
                wp_enqueue_script(THEME_SLUG . '-sticky', THEME_URI . '/assets/js/sticky.js', array('jquery'), '1.0.0', true);

            wp_enqueue_script(THEME_SLUG . '-bootstrap', THEME_URI . '/assets/js/bootstrap.min.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-bxslider', THEME_URI . '/assets/js/jquery.bxslider.min.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-retina', THEME_URI . '/assets/js/retina.min.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-jquery-cycle', THEME_URI . '/assets/js/jquery.cycle.all.js', array('jquery'), '1.0.0', true);

            wp_enqueue_script(THEME_SLUG . '-likes', THEME_URI . '/assets/js/likes.js', array('jquery'), '1.0.0', true);
            wp_localize_script(
              THEME_SLUG . '-likes',
              'ajax_var',
              array(
                'url' => admin_url('admin-ajax.php'),
                'nonce' => wp_create_nonce('ajax-nonce')
              )
            );

            wp_enqueue_script(THEME_SLUG . '-jquery.cubeportfolio', THEME_URI . '/assets/js/jquery.cubeportfolio.min.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-jcarousel-responsive', THEME_URI . '/assets/js/jcarousel.responsive.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-jquery-jcarousel', THEME_URI . '/assets/js/jquery.jcarousel.min.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-owl-carousel', THEME_URI . '/assets/js/owl.carousel.min.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-magnific-popup', THEME_URI . '/assets/js/magnific.popup.min.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-testimonialrotator', THEME_URI . '/assets/js/testimonialrotator.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-contact-form', THEME_URI . '/assets/js/contacts.js', array('jquery'), '1.0.0', true);
            wp_enqueue_script(THEME_SLUG . '-main', THEME_URI . '/assets/js/main.js', array('jquery'), '1.0.0', true);
        }
    }

    // Load conditional scripts
    public function conditional_scripts ()
    {
        global $PhoenixData, $template;
        $what_template = get_page_template_slug();

        if ($what_template == 'template-portfolio.php' ||
            basename($template) == 'single-' . THEME_SLUG . '_portfolio.php') {
              $cubeportfolio = array(
                  'inlineError' => __("Error! Please refresh the page!", 'grandway'),
                  'moreLoading' => __("Loading...", 'grandway'),
                  'moreNoMore' => __("No More Works", 'grandway')
              );

              $port_layout = isset($PhoenixData['port_layout']) ? $PhoenixData['port_layout'] : '2-cols';

              switch ($port_layout) {
                  case '2-cols': $cube_js = 'portfolio-2'; break;
                  case '3-cols': $cube_js = 'portfolio-3'; break;
                  case '4-cols': $cube_js = 'portfolio-4'; break;
                  case 'full': $cube_js = 'portfolio-fullwidth'; break;
                  default: $cube_js = 'portfolio-2'; break;
              }

              wp_register_script(THEME_SLUG . '-portfolio', THEME_URI . '/assets/js/'. $cube_js .'.js', array('jquery'), '1.0.0', true);
              wp_enqueue_script(THEME_SLUG . '-portfolio');

              wp_localize_script(THEME_SLUG . '-jquery.cubeportfolio', 'portSetts', $cubeportfolio);
        }

        if (is_singular(THEME_SLUG . '_portfolio')) {

            wp_dequeue_script(THEME_SLUG . '-portfolio');

            wp_enqueue_script(
                THEME_SLUG . '-portfolio-single',
                THEME_URI . '/assets/js/portfolio-3.js',
                array('jquery'),
                '1.0.0',
                true
            );
        }
    }

}
