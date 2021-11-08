<?php

class PhoenixTeam_Sidebars {

    public function __construct ()
    {
        add_action('widgets_init', array($this, 'register_sidebars'));
        add_action('widgets_init', array($this, 'create_custom_sidebars'));
    }

    public function register_sidebars ()
    {
        global $PhoenixData;
        $use_footer = isset($PhoenixData['use_footer']) ? $PhoenixData['use_footer'] : 1;
        $layout = isset($PhoenixData['footer_layout']) ? $PhoenixData['footer_layout'] : 3;
        $woo = PhoenixTeam_Utils::dep_exists('woocommerce');

        // If Dynamic Sidebar Exists
        if (function_exists('register_sidebar'))
        {

            // Define Blog Sidebar
            register_sidebar(
                array(
                    'name' => __('Blog Sidebar', 'grandway'),
                    'description' => __('This widgets area is used for blog pages by default.', 'grandway'),
                    'id' => 'blog-sidebar',
                    'before_widget' => '<div id="%1$s" class="%2$s widget">',
                    'after_widget' => '</div>',
                    'before_title' => '<h4 class="widget-title">',
                    'after_title' => '</h4>'
                )
            );

            // Define WooCommerce Sidebar
            if ($woo) {
                register_sidebar(
                    array(
                        'name' => __('WooCommerce Sidebar', 'grandway'),
                        'description' => __('This widgets area is used for woocommerce shop by default.', 'grandway'),
                        'id' => 'woo-sidebar',
                        'before_widget' => '<div id="%1$s" class="%2$s widget">',
                        'after_widget' => '</div>',
                        'before_title' => '<h4 class="widget-title">',
                        'after_title' => '</h4>'
                    )
                );
            }

            if ($use_footer) {
                // Define Footer 1
                register_sidebar(
                    array(
                        'name' => __('Footer Area 1', 'grandway'),
                        'description' => __('This widgets area is used in footer.', 'grandway'),
                        'id' => 'footer-1',
                        'before_widget' => '<div id="%1$s" class="%2$s footer-widget">',
                        'after_widget' => '</div>',
                        'before_title' => '<h4 class="widget-title">',
                        'after_title' => '</h4>'
                    )
                );
                // Define Footer 2
                register_sidebar(
                    array(
                        'name' => __('Footer Area 2', 'grandway'),
                        'description' => __('This widgets area is used in footer.', 'grandway'),
                        'id' => 'footer-2',
                        'before_widget' => '<div id="%1$s" class="%2$s footer-widget">',
                        'after_widget' => '</div>',
                        'before_title' => '<h4 class="widget-title">',
                        'after_title' => '</h4>'
                    )
                );
                // Define Footer 3
                register_sidebar(
                    array(
                        'name' => __('Footer Area 3', 'grandway'),
                        'description' => __('This widgets area is used in footer.', 'grandway'),
                        'id' => 'footer-3',
                        'before_widget' => '<div id="%1$s" class="%2$s footer-widget">',
                        'after_widget' => '</div>',
                        'before_title' => '<h4 class="widget-title">',
                        'after_title' => '</h4>'
                    )
                );

                // Define Footer 4
                if ($layout == 4) {
                    register_sidebar(
                        array(
                            'name' => __('Footer Area 4', 'grandway'),
                            'description' => __('This widgets area is used in footer.', 'grandway'),
                            'id' => 'footer-4',
                            'before_widget' => '<div id="%1$s" class="%2$s footer-widget">',
                            'after_widget' => '</div>',
                            'before_title' => '<h4 class="widget-title">',
                            'after_title' => '</h4>'
                        )
                    );
                }
            }
        }
    }

    public function create_custom_sidebars ()
    {
        global $PhoenixData;
        $custom_sidebars = isset($PhoenixData['sidebars_list']) ? $PhoenixData['sidebars_list'] : null;

        if($custom_sidebars && is_array($custom_sidebars)) {
            foreach ($custom_sidebars as $sidebar) {
                if ($sidebar) {
                    if (function_exists('register_sidebar')) {
                        register_sidebar(
                            array(
                                'name' => $sidebar,
                                'description' => __('This is your custom sidebar.', 'grandway'),
                                'id' => PhoenixTeam_Utils::id_formatter(strtolower($sidebar)),
                                'before_widget' => '<div id="%1$s" class="%2$s widget">',
                                'after_widget' => '</div>',
                                'before_title' => '<h4 class="widget-title">',
                                'after_title' => '</h4>'
                            )
                        );
                    }
                }
            }
        }

    }

}

new PhoenixTeam_Sidebars();
