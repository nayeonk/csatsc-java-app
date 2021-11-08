<?php

new PhoenixTeam_Shortcodes_Mapper();

class PhoenixTeam_Shortcodes_Mapper extends WPBakeryShortCode {

    public function __construct ()
    {
        $this->remove_vc_shortcodes();
        $this->set_vc_templates();

        add_action('vc_before_init', array($this, 'map_promo_title'));
        add_action('vc_before_init', array($this, 'map_portfolio_carousel'));
        add_action('vc_before_init', array($this, 'map_service'));
        // add_action('vc_before_init', array($this, 'map_service'));
        add_action('vc_before_init', array($this, 'map_team_member'));
        add_action('vc_before_init', array($this, 'map_widget_get_in_touch'));
        add_action('vc_before_init', array($this, 'map_widget_twitter'));
        add_action('vc_before_init', array($this, 'map_widger_cform'));
        add_action('vc_before_init', array($this, 'map_testimonial'));
        add_action('vc_before_init', array($this, 'map_post_box'));
        add_action('vc_before_init', array($this, 'map_clients_slider'));
        add_action('vc_before_init', array($this, 'map_facts'));
    }


    public function remove_vc_shortcodes ()
    {
        vc_remove_element('vc_toggle');
        vc_remove_element('vc_posts_grid');
        vc_remove_element('vc_gallery');
        vc_remove_element('vc_images_carousel');
        vc_remove_element('vc_posts_slider');
        vc_remove_element('vc_carousel');
    }

    public function set_vc_templates ()
    {
        if (function_exists('vc_set_shortcodes_templates_dir'))
            vc_set_shortcodes_templates_dir(THEME_DIR . '/includes/shortcodes/vc_templates');
    }


    public function map_post_box ()
    {
        $args = array(
          'orderby' => 'name',
          'order' => 'ASC'
          );
        $categories = get_categories($args);

        $cats_list = array(__("None", 'grandway') => 'cat == false');

        foreach ($categories as $cat) {
            $cats_list[$cat->name] = $cat->slug;
        }

        if (count($cats_list) == 0)
            $cats_list["-- ".__('You sould create some posts before you can use this widget', 'grandway')." --"] = null;

        vc_map(
            array(
                "name" => __("Post Box", 'grandway'),
                "base" => THEME_SLUG . "_postbox",
                "class" => "",
                "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
                "params" => array(
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Title", 'grandway'),
                        "param_name" => "title",
                        "value" => __("Recent Posts", 'grandway'),
                        "description" => __("Title text.", 'grandway'),
                    ),
                    array(
                        "type" => "textfield",
                        "heading" => __('Select Posts Quantity', 'grandway'),
                        "param_name" => "qty",
                        "description" => __("How many posts to show in posts box", 'grandway'),
                        "value" => 2
                    ),
                    array(
                        "type" => "dropdown",
                        "heading" => __('Select Posts Category', 'grandway'),
                        "param_name" => "cat",
                        "description" => __("You sould create some categorise & asosiate them with posts before you can use this option.", 'grandway'),
                        "value" => $cats_list
                    ),
                    array(
                        'type' => 'css_editor',
                        'heading' => __('Css', 'grandway'),
                        'param_name' => 'css',
                        'group' => __('Design options', 'grandway')
                    )
                )
            )
        );
    }


    public function map_widger_cform ()
    {
        global $PhoenixData;
        $email = isset($PhoenixData['contact_mail']) ? $PhoenixData['contact_mail'] : array(get_option('admin_email'));

        vc_map(array(
            "name" => __("Contact Form", 'grandway'),
            "base" => THEME_SLUG . '_cform',
            "is_container" => true,
            // "icon" => "icon-wpb-twitter",
            "show_settings_on_create" => true,
            "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
            "description" => __('Contact form for your site.', 'grandway'),
            "params" => array(
                array(
                    "type" => "textfield",
                    "heading" => __("Title", 'grandway'),
                    "param_name" => "title",
                    "description" => __('Email will be sent to ', 'grandway').'<u>'. $email[0] .'</u> '. __('and other addresses that you have chosen.', 'grandway') .'<br/><a href="'. admin_url() .'?page='. THEME_SLUG .'_options&tab=7">'. __('Change email(s)', 'grandway'). '</a>'
                )
            )
        ));
    }


    public function map_widget_twitter ()
    {
        vc_map(array(
            "name" => __("Twitter Feed", 'grandway'),
            "base" => THEME_SLUG . '_tweetfeed',
            "is_container" => true,
            "icon" => "icon-wpb-twitter",
            "show_settings_on_create" => true,
            "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Social', 'grandway') ),
            "description" => __('Twitter Feed with last tweets.', 'grandway'),
            "params" => array(
                array(
                    "type" => "textfield",
                    "heading" => __("Title", 'grandway'),
                    "param_name" => "title",
                    "description" => ""
                ),
                array(
                    "type" => "textfield",
                    "heading" => __("Twitter Username", 'grandway'),
                    "param_name" => "username",
                    "description" => __("Ex: @envato, Ph0enixTeam", 'grandway')
                ),
                array(
                    "type" => "dropdown",
                    "heading" => __("Tweets to Show", 'grandway'),
                    "param_name" => "qty",
                    "description" => __("Select how many tweets to show.", 'grandway'),
                    "std" => 3,
                    "value" => array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                ),
                array(
                    "type" => "dropdown",
                    "heading" => __("Layout", 'grandway'),
                    "param_name" => "style",
                    "description" => __("Select Twitter Feed layout.", 'grandway'),
                    "std" => "box",
                    "value" => array(
                        __("Box", 'grandway') => "box",
                        __("Slider", 'grandway') => "slider"
                    )
                ),
            ),
        ));
    }


    public function map_promo_title ()
    {
        vc_map(
            array(
                "name" => __("Promo Title", 'grandway'),
                "base" => THEME_SLUG . "_promo_title",
                "class" => "",
                "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
                "params" => array(
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Promo Title", 'grandway'),
                        "param_name" => "title",
                        "value" => __("Title", 'grandway'),
                        "description" => __("Title text.", 'grandway'),
                    ),
                    array(
                        'type' => 'css_editor',
                        'heading' => __('Css', 'grandway'),
                        'param_name' => 'css',
                        'group' => __('Design options', 'grandway')
                    )
                )
            )
        );
    }


    public function map_portfolio_carousel ()
    {
        vc_map(
            array(
                'name' => __('Portfolio Carousel', 'grandway'),
                'base' => THEME_SLUG . '_portfolio_carousel',
                "category" => array((THEME_NAME . " " . __("Exclusive", 'grandway')), __('Content', 'grandway') ),
                'icon' => 'icon-wpb-images-carousel',
                'description' => __('Portfolio items in grid view', 'grandway'),
                'params' => array(
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Title", 'grandway'),
                        "param_name" => "title",
                        "value" => __("Recent Projects", 'grandway'),
                        "description" => __("Title text.", 'grandway'),
                    ),
                    array(
                        'type' => 'textfield',
                        "holder" => "div",
                        "class" => "",
                        'heading' => __('Items per page', 'grandway'),
                        'param_name' => 'qty',
                        'value' => 6,
                        'description' => __('How many items do you want to see in this block. Select "-1" to show all of them.', 'grandway')
                    ),
                    array(
                        'type' => 'css_editor',
                        'heading' => __('Css', 'grandway'),
                        'param_name' => 'css',
                        'group' => __('Design options', 'grandway')
                    )
                )
            )
        );
    }


    public function map_clients_slider ()
    {
        vc_map(
            array(
                'name' => __('Clients Carousel', 'grandway'),
                'base' => THEME_SLUG . '_clients',
                'icon' => 'icon-wpb-images-carousel',
                "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Social', 'grandway') ),
                'description' => __('Animated carousel with images', 'grandway'),
                'params' => array(
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Title", 'grandway'),
                        "param_name" => "title",
                        "value" => __("Some of Our Clients", 'grandway'),
                        "description" => __("Title text.", 'grandway'),
                    ),
                    array(
                        'type' => 'attach_images',
                        'heading' => __('Images', 'grandway'),
                        'param_name' => 'images',
                        'value' => '',
                        'description' => __('Select images from media library.', 'grandway')
                    ),
                    array(
                        "type" => "checkbox",
                        "param_name" => "autoplay",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Use autoscroll?", 'grandway'),
                        "description" => __("The carousel will scrolls automatically.", 'grandway'),
                        "value" => array("Yes" => true)
                    ),
                )
            )
        );
    }


    public function map_facts ()
    {
        vc_map(
            array(
                'name' => __('Interesting Facts', 'grandway'),
                'base' => THEME_SLUG . '_facts',
                // 'icon' => 'icon-wpb-images-carousel',
                "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway') ),
                'description' => __('Some interesting facts with icons', 'grandway'),
                'params' => array(
                    array(
                        'type' => 'dropdown',
                        'heading' => __('Icon', 'grandway'),
                        'param_name' => 'icon',
                        'value' => array(
                            __('mobile', 'grandway') => "icon-mobile",
                            __('laptop', 'grandway') => "icon-laptop",
                            __('desktop', 'grandway') => "icon-desktop",
                            __('tablet', 'grandway') => "icon-tablet",
                            __('Phone', 'grandway') => "icon-phone",
                            __('Document', 'grandway') => "icon-document",
                            __('documents', 'grandway') => "icon-documents",
                            __('search', 'grandway') => "icon-search",
                            __('clipboard', 'grandway') => "icon-clipboard",
                            __('newspaper', 'grandway') => "icon-newspaper",
                            __('notebook', 'grandway') => "icon-notebook",
                            __('book-open', 'grandway') => "icon-book-open",
                            __('browser', 'grandway') => "icon-browser",
                            __('calendar', 'grandway') => "icon-calendar",
                            __('presentation', 'grandway') => "icon-presentation",
                            __('picture', 'grandway') => "icon-picture",
                            __('pictures', 'grandway') => "icon-pictures",
                            __('video', 'grandway') => "icon-video",
                            __('camera', 'grandway') => "icon-camera",
                            __('printer', 'grandway') => "icon-printer",
                            __('toolbox', 'grandway') => "icon-toolbox",
                            __('briefcase', 'grandway') => "icon-briefcase",
                            __('wallet', 'grandway') => "icon-wallet",
                            __('gift', 'grandway') => "icon-gift",
                            __('bargraph', 'grandway') => "icon-bargraph",
                            __('grid', 'grandway') => "icon-grid",
                            __('expand', 'grandway') => "icon-expand",
                            __('focus', 'grandway') => "icon-focus",
                            __('adjustments', 'grandway') => "icon-adjustments",
                            __('ribbon', 'grandway') => "icon-ribbon",
                            __('hourglass', 'grandway') => "icon-hourglass",
                            __('lock', 'grandway') => "icon-lock",
                            __('megaphone', 'grandway') => "icon-megaphone",
                            __('shield', 'grandway') => "icon-shield",
                            __('trophy', 'grandway') => "icon-trophy",
                            __('flag', 'grandway') => "icon-flag",
                            __('map', 'grandway') => "icon-map",
                            __('puzzle', 'grandway') => "icon-puzzle",
                            __('basket', 'grandway') => "icon-basket",
                            __('envelope', 'grandway') => "icon-envelope",
                            __('streetsign', 'grandway') => "icon-streetsign",
                            __('telescope', 'grandway') => "icon-telescope",
                            __('gears', 'grandway') => "icon-gears",
                            __('key', 'grandway') => "icon-key",
                            __('paperclip', 'grandway') => "icon-paperclip",
                            __('attachment', 'grandway') => "icon-attachment",
                            __('pricetags', 'grandway') => "icon-pricetags",
                            __('lightbulb', 'grandway') => "icon-lightbulb",
                            __('layers', 'grandway') => "icon-layers",
                            __('pencil', 'grandway') => "icon-pencil",
                            __('tools', 'grandway') => "icon-tools",
                            __('tools-2', 'grandway') => "icon-tools-2",
                            __('scissors', 'grandway') => "icon-scissors",
                            __('paintbrush', 'grandway') => "icon-paintbrush",
                            __('magnifying-glass', 'grandway') => "icon-magnifying-glass",
                            __('circle-compass', 'grandway') => "icon-circle-compass",
                            __('linegraph', 'grandway') => "icon-linegraph",
                            __('mic', 'grandway') => "icon-mic",
                            __('strategy', 'grandway') => "icon-strategy",
                            __('beaker', 'grandway') => "icon-beaker",
                            __('caution', 'grandway') => "icon-caution",
                            __('recycle', 'grandway') => "icon-recycle",
                            __('anchor', 'grandway') => "icon-anchor",
                            __('profile-male', 'grandway') => "icon-profile-male",
                            __('profile-female', 'grandway') => "icon-profile-female",
                            __('bike', 'grandway') => "icon-bike",
                            __('wine', 'grandway') => "icon-wine",
                            __('hotairballoon', 'grandway') => "icon-hotairballoon",
                            __('globe', 'grandway') => "icon-globe",
                            __('genius', 'grandway') => "icon-genius",
                            __('map-pin', 'grandway') => "icon-map-pin",
                            __('dial', 'grandway') => "icon-dial",
                            __('chat', 'grandway') => "icon-chat",
                            __('heart', 'grandway') => "icon-heart",
                            __('cloud', 'grandway') => "icon-cloud",
                            __('upload', 'grandway') => "icon-upload",
                            __('download', 'grandway') => "icon-download",
                            __('target', 'grandway') => "icon-target",
                            __('hazardous', 'grandway') => "icon-hazardous",
                            __('piechart', 'grandway') => "icon-piechart",
                            __('speedometer', 'grandway') => "icon-speedometer",
                            __('global', 'grandway') => "icon-global",
                            __('compass', 'grandway') => "icon-compass",
                            __('lifesaver', 'grandway') => "icon-lifesaver",
                            __('clock', 'grandway') => "icon-clock",
                            __('aperture', 'grandway') => "icon-aperture",
                            __('quote', 'grandway') => "icon-quote",
                            __('scope', 'grandway') => "icon-scope",
                            __('alarmclock', 'grandway') => "icon-alarmclock",
                            __('refresh', 'grandway') => "icon-refresh",
                            __('happy', 'grandway') => "icon-happy",
                            __('sad', 'grandway') => "icon-sad",
                            __('facebook', 'grandway') => "icon-facebook",
                            __('twitter', 'grandway') => "icon-twitter",
                            __('googleplus', 'grandway') => "icon-googleplus",
                            __('rss', 'grandway') => "icon-rss",
                            __('tumblr', 'grandway') => "icon-tumblr",
                            __('linkedin', 'grandway') => "icon-linkedin",
                            __('dribbble', 'grandway') => "icon-dribbble"
                        ),
                        'description' => __('Select icon.', 'grandway'),
                        'admin_label' => true
                    ),
                    array(
                        'type' => 'textfield',
                        "holder" => "div",
                        "class" => "",
                        'heading' => __('Data', 'grandway'),
                        'param_name' => 'data',
                        'value' => '',
                        'description' => ''
                    ),
                    array(
                        'type' => 'textfield',
                        "holder" => "div",
                        "class" => "",
                        'heading' => __('Description', 'grandway'),
                        'param_name' => 'name',
                        'value' => '',
                        'description' => ''
                    ),
                    array(
                        'type' => 'dropdown',
                        "holder" => "div",
                        "class" => "",
                        'heading' => __('Style', 'grandway'),
                        'param_name' => 'icon',
                        'value' => array(
                            __('Default', 'grandway') => "",
                            __('Digits', 'grandway') => "digits",
                        ),
                        'description' => __('The style of shortcode', 'grandway')
                    ),
                    array(
                        'type' => 'textfield',
                        "holder" => "div",
                        "class" => "",
                        'heading' => __('Link', 'grandway'),
                        'param_name' => 'link',
                        'value' => '',
                        'description' => ''
                    ),
                    array(
                        'type' => 'dropdown',
                        'heading' => __('Where to put link?', 'grandway'),
                        'param_name' => 'link_place',
                        'value' => array(
                            __('To icon', 'grandway') => "icon",
                            __('To data', 'grandway') => "data",
                            __('To description', 'grandway') => "text",
                        ),
                        'admin_label' => true
                    ),
                    array(
                        "type" => "checkbox",
                        "param_name" => "target",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Open this link in another tab?", 'grandway'),
                        "description" => "",
                        "value" => array('Yes' => true),
                    ),
                    array(
                        'type' => 'css_editor',
                        'heading' => __('Css', 'grandway'),
                        'param_name' => 'css',
                        'group' => __('Design options', 'grandway')
                    )
                )
            )
        );
    }


    public function map_service ()
    {
        $services = array(
            "post_type" => THEME_SLUG . '_services',
            "post_status" => "publish"
        );

        $services = new WP_Query($services);
        wp_reset_postdata();

        $services = $services->posts;

        $services_list = array();

        foreach ($services as $serv) {
            $services_list[$serv->post_title] = $serv->ID;
        }

        if (count($services_list) == 0)
            $services_list["-- ".__('You sould create some services before you can use this widget', 'grandway')." --"] = null;

        vc_map(
            array(
                "name" => __("Service", 'grandway'),
                "base" => THEME_SLUG . "_service",
                "class" => "",
                "category" => array( THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
                "params" => array(
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Service Title", 'grandway'),
                        "param_name" => "title",
                        "description" => __("Leave it blank to use your predefined service title.", 'grandway'),
                    ),
                    array(
                        "type" => "dropdown",
                        "heading" => __('Select Servise', 'grandway'),
                        "param_name" => "id",
                        "description" => __("You sould create some services before you can use this widget.", 'grandway'),
                        "value" => $services_list
                    ),
                    array(
                        "type" => "dropdown",
                        "heading" => __('Servise Layout', 'grandway'),
                        "param_name" => "layout",
                        "value" => array(
                            __('Block', 'grandway') => 'block',
                            __('List', 'grandway') => 'list'
                        )
                    ),
                    array(
                        'type' => 'textfield',
                        "holder" => "div",
                        "class" => "",
                        'heading' => __('Link', 'grandway'),
                        'param_name' => 'link',
                        'value' => '',
                        'description' => ''
                    ),
                    array(
                        "type" => "checkbox",
                        "param_name" => "target",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Open this link in another tab?", 'grandway'),
                        "description" => "",
                        "value" => array('Yes' => true),
                    ),
                    array(
                        'type' => 'css_editor',
                        'heading' => __('Css', 'grandway'),
                        'param_name' => 'css',
                        'group' => __('Design options', 'grandway')
                    )
                )
            )
        );
    }

    // public function map_service ()
    // {
    //   vc_map(
    //     array(
    //       "name" => __("Service", 'grandway'),
    //       "base" => THEME_SLUG . "_service",
    //       "class" => "",
    //       "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
    //       "params" => array(
    //         array(
    //           'type' => 'iconpicker',
    //           'heading' => __('Icon', 'js_composer' ),
    //           'param_name' => 'icon',
    //           'value' => 'vc_li vc_li-heart', // default value to backend editor admin_label
    //           'settings' => array(
    //             'emptyIcon' => false,
    //             'type' => 'linecons',
    //             'iconsPerPage' => 4000,
    //           ),
    //           'description' => __('Select icon from library.', 'grandway'),
    //         ),
    //         array(
    //           "type" => "textfield",
    //           "holder" => "div",
    //           "class" => "",
    //           "heading" => __("Service Title", 'grandway'),
    //           "param_name" => "title",
    //           "description" => __("Leave it blank to use your predefined service title.", 'grandway'),
    //         ),
    //         array(
    //           "type" => "textarea",
    //           "holder" => "div",
    //           "class" => "",
    //           "heading" => __("Service Title", 'grandway'),
    //           "param_name" => "content",
    //         ),
    //         array(
    //           'type' => 'vc_link',
    //           "holder" => "div",
    //           "class" => "",
    //           'heading' => __('Link', 'grandway'),
    //           'param_name' => 'link',
    //           'value' => '',
    //         ),
    //         array(
    //           'type' => 'css_editor',
    //           'heading' => __('Css', 'grandway'),
    //           'param_name' => 'css',
    //           'group' => __('Design options', 'grandway')
    //         )
    //       )
    //     )
    //   );
    // }


    public function map_team_member ()
    {
        $members = array(
            "post_type" => THEME_SLUG . '_team',
            "post_status" => "publish"
        );

        $members = new WP_Query($members);
        wp_reset_postdata();

        $members = $members->posts;

        $members_list = array();

        foreach ($members as $member) {
            $members_list[$member->post_title] = $member->ID;
        }

        if (count($members_list) == 0)
            $members_list["-- ".__('You sould create some team members before you can use this widget', 'grandway')." --"] = null;

        vc_map(
            array(
                "name" => __("Team Member", 'grandway'),
                "base" => THEME_SLUG . "_team",
                "class" => "",
                "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
                "params" => array(
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Team Member name", 'grandway'),
                        "param_name" => "title",
                        "description" => __("Leave it blank to use your predefined name.", 'grandway'),
                    ),
                    array(
                        "type" => "dropdown",
                        "heading" => __('Select Team Member', 'grandway'),
                        "param_name" => "id",
                        "description" => __('You sould create some team members before you can use this widget.', 'grandway'),
                        "value" => $members_list
                    ),
                    array(
                        'type' => 'css_editor',
                        'heading' => __('Css', 'grandway'),
                        'param_name' => 'css',
                        'group' => __('Design options', 'grandway')
                    )
                )
            )
        );
    }


    public function map_widget_get_in_touch ()
    {
        vc_map(
            array(
                "name" => __("Get In Touch", 'grandway'),
                "base" => THEME_SLUG . "_get_in_touch",
                "class" => "",
                "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
                "params" => array(
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Adress", 'grandway'),
                        "param_name" => "address",
                        // "value" => null,
                        "description" => __('Ex: "Address: 455 Martinson, Los Angeles"', 'grandway'),
                    ),
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Phone", 'grandway'),
                        "param_name" => "phone",
                        // "value" => null,
                        "description" => __('Ex: "Phone: 8 (043) 567 - 89 - 30"', 'grandway'),
                    ),
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Fax", 'grandway'),
                        "param_name" => "fax",
                        // "value" => null,
                        "description" => __('Ex: "Fax: 8 (057) 149 - 24 - 64"', 'grandway'),
                    ),
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Skype", 'grandway'),
                        "param_name" => "skype",
                        // "value" => null,
                        "description" => __('Ex: "Skype: companyname"', 'grandway'),
                    ),
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Email", 'grandway'),
                        "param_name" => "email",
                        // "value" => null,
                        "description" => __('Your e-mail will be published with "antispambot" protection.<br/>Ex: "E-mail: support@email.com"', 'grandway'),
                    ),
                    array(
                        "type" => "textfield",
                        "holder" => "div",
                        "class" => "",
                        "heading" => __("Weekend", 'grandway'),
                        "param_name" => "weekend",
                        // "value" => null,
                        "description" => __('Ex: "Weekend: from 9 am to 6 pm"', 'grandway'),
                    ),
                )
            )
        );
    }


    public function map_testimonial ()
    {
        vc_map(
            array(
                "name" => __("Testimonial", 'grandway'),
                "base" => THEME_SLUG . '_testimonial',
                "is_container" => false,
                // "icon" => "icon-wpb-twitter",
                "show_settings_on_create" => true,
                "category" => array(THEME_NAME . " " . __("Exclusive", 'grandway'), __('Content', 'grandway') ),
                "description" => __('Displays all your testimonials.', 'grandway'),
                "params" => array(
                  array(
                    'type' => 'attach_image',
                    'heading' => __('Image', 'grandway'),
                    'param_name' => 'pic',
                    'value' => '',
                    'description' => __('Select image from media library.', 'grandway')
                  ),
                  array(
                    'type' => 'textfield',
                    'heading' => __('Autor name', 'grandway'),
                    'param_name' => 'name'
                  ),
                  array(
                    'type' => 'textfield',
                    'heading' => __('Autor position', 'grandway'),
                    'param_name' => 'position',
                    'description' => __('For Ex: Web Developer', 'grandway')
                  ),
                  array(
                    'type' => 'textarea',
                    'heading' => __('Text', 'grandway'),
                    'param_name' => 'content',
                  ),
                  array(
                    'type' => 'css_editor',
                    'heading' => __('Css', 'grandway'),
                    'param_name' => 'css',
                    'group' => __('Design options', 'grandway')
                  )
                )
            )
        );
    }

}
