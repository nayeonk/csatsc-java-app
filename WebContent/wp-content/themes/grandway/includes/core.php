<?php

class PhoenixTeam_Theme {

    private $config = array(
        'team' => 'PhoenixTeam',
        'name' => 'GrandWay',
        'slug' => 'grandway'
    );
    protected static $instance = null;

    /*
     Constructor
    */
    private function __construct ()
    {
        $this->defineConstants();
        $this->setContentWidth();
        $this->loadHelpers();
        $this->registerSidebars();
        $this->includeEnqueues();
        $this->registerNavMenus();
        $this->addShortcodes();
        $this->includeDependencies();

        add_action('after_setup_theme', array($this, 'setTextDomain'));
        add_action('after_setup_theme', array($this, 'addThemeSupports'));
        add_action('widgets_init', array($this, 'initializeWidgets'));
    }


    private function __clone ()  {}
    private function __wakeup () {}


    /*
     Singleton
    */
    public static function initInstance ()
    {
        if (empty(self::$instance))
            self::$instance = new self();

        return self::$instance;
    }


    /*
     Defines constants for use within the theme.
    */
    private function defineConstants ()
    {
        define('THEME_TEAM', $this->config['team']);
        define('THEME_NAME', $this->config['name']);
        define('THEME_SLUG', $this->config['slug']);
        define('THEME_DIR', get_template_directory());
        define('THEME_URI', get_template_directory_uri());

        // Contact Form
        define('IS_AJAX',
            isset($_SERVER['HTTP_X_REQUESTED_WITH']) &&
            strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest'
        );
    }


    /*
     Set theme text domain
    */
    public function setTextDomain ()
    {
        load_theme_textdomain(THEME_SLUG, THEME_DIR . 'includes/langs');
    }


    /*
     Set max width of the main wrapper
    */
    private function setContentWidth ()
    {
        if (!isset($content_width)) {
            $content_width = 1140;
            return true;
        }
        return false;
    }


    /*
     Adds WP theme features.
    */
    public function addThemeSupports ()
    {
        if (function_exists('add_theme_support')) {

            // Enables post and comment RSS feed links to head
            add_theme_support('automatic-feed-links');

            // Add Menu Support
            add_theme_support('menus');

            // Add Title Tag
            add_theme_support('title-tag');

            // Add post formats
            add_theme_support( 'post-formats',
                array(
                    'image',
                    'video',
                    'audio',
                    'gallery',
                    'link',
                    'quote',
                    // 'aside',
                    // 'chat',
                    // 'status'
                )
            );

            // Add thumbnails
            add_theme_support('post-thumbnails', array('post', THEME_SLUG . '_portfolio'));
            // HTML5 Form
            add_theme_support('html5', array('comment-list', 'comment-form', 'search-form', 'gallery', 'caption'));
            // WooCommerce support declaration
            add_theme_support('woocommerce');
        }
    }


    /*
     Registers the theme sidebars.
    */
    private function registerSidebars ()
    {
        require_once THEME_DIR . '/includes/widgets/sidebars.php';
    }


    /*
     Loads the theme helpers.
    */
    private function loadHelpers ()
    {
        require_once THEME_DIR . '/includes/helpers/utils.php';
        require_once THEME_DIR . '/includes/helpers/internals.php';
        require_once THEME_DIR . '/includes/helpers/BFI_Thumb.php';
        require_once THEME_DIR . '/includes/helpers/likes.php';
    }


    /*
     Registers and initializes widgets.
    */
    public function initializeWidgets ()
    {
        require_once THEME_DIR . '/includes/widgets/widget-socials.php';
        require_once THEME_DIR . '/includes/widgets/widget-twitter.php';
        require_once THEME_DIR . '/includes/widgets/widget-get-in-touch.php';
        require_once THEME_DIR . '/includes/widgets/widget-flickr.php';
        require_once THEME_DIR . '/includes/widgets/widget-contact-form.php';

        register_widget('PhoenixTeam_Widget_Socials');
        register_widget('PhoenixTeam_Widget_Twitter');
        register_widget('PhoenixTeam_Widget_GetInTouch');
        register_widget('PhoenixTeam_Widget_Flickr');
        register_widget('PhoenixTeam_Widget_ContactForm');
    }


    /*
     Registers and enqueues theme styles & scripts.
    */
    private function includeEnqueues ()
    {
        require_once THEME_DIR . '/includes/enqueues/styles.php';
        require_once THEME_DIR . '/includes/enqueues/scripts.php';
    }


    /*
     Registers theme navigation.
    */
    private function registerNavMenus ()
    {
        register_nav_menus(
            array (
                'header-menu' => __('Main Menu', 'grandway'),
                'footer-menu' => __('Footer Menu', 'grandway')
            )
        );
    }


    /*
     Register theme's shortcodes.
    */
    private function addShortcodes ()
    {
        if (class_exists('WPBakeryVisualComposerAbstract')) {
            require_once THEME_DIR . '/includes/shortcodes/shortcodes-class.php';
            require_once THEME_DIR . '/includes/shortcodes/shortcodes-mapper.php';
        }
    }


    /*
     Load theme dependencies.
    */
    private function includeDependencies ()
    {
        require_once THEME_DIR . '/includes/deps/config.php';
    }

}
