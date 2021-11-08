<?php
/*
  ReduxFramework Config File
  For full documentation, please visit: https://docs.reduxframework.com
*/
if (!class_exists('Monolite_by_Cu7ious_Redux_Framework_Config')) {

    class Monolite_by_Cu7ious_Redux_Framework_Config {

        public $ReduxFramework;
        private $path;
        public $args     = array();
        public $sections = array();

        public function __construct ()
        {

            if (!class_exists('ReduxFramework')) {
                return;
            }

            $this->initSettings();
        }

        public function initSettings ()
        {
            $this->setSettingsPath();

            // Set the default arguments
            $this->setArguments();

            // Set a few help tabs so you can see how it's done
            $this->setHelpTabs();

            // Create the sections and fields
            $this->setSections();

            if (!isset($this->args['opt_name'])) { // No errors please
                return;
            }

            $this->ReduxFramework = new ReduxFramework($this->sections, $this->args);
        }


        private function setSettingsPath ()
        {
            $this->path = dirname(__FILE__);
        }


        private function getRSS ()
        {
            // return get_bloginfo('rss2_url');
            return home_url('/feed/');
        }

        /*

          All the possible arguments for Redux.
          For full documentation on arguments, please refer to: https://github.com/ReduxFramework/ReduxFramework/wiki/Arguments

         */
        public function setArguments ()
        {
            $theme = wp_get_theme(); // For use with some settings. Not necessary.

            $this->args = array(
                // TYPICAL -> Change these values as you need/desire
                'opt_name'          => 'phoenixdata_opts',        // This is where your data is stored in the database and also becomes your global variable name.
                'display_name'      => $theme->get('Name'),         // Name that appears at the top of your panel
                'display_version'   => "v." .$theme->get('Version'). ', <span>by <a target="_blank" href="//themeforest.net/user/'. MONOLITE_TEAM .'">'. MONOLITE_TEAM .'</a></span>',      // Version that appears at the top of your panel
                'menu_type'         => 'menu',                      //Specify if the admin menu should appear or not. Options: menu or submenu (Under appearance only)
                'allow_sub_menu'    => false,                        // Show the sections below the admin menu item or not
                'menu_title'        => MONOLITE_NAME . ' ' . __('Opts', 'monolite-core'),
                'page_title'        => MONOLITE_NAME . ' ' . __('Options', 'monolite-core'),

                'async_typography'  => false,                       // Use a asynchronous font on the front end or font string
                'admin_bar'         => false,                        // Show the panel pages on the admin bar
                'global_variable'   => 'PhoenixData',                      // Set a different name for your global variable other than the opt_name
                'dev_mode'          => false,                       // Show the time the page took to load, etc
                'customizer'        => false,                        // Enable basic customizer support

                // OPTIONAL -> Give you extra features
                'intro_text'        => null,
                'page_parent'       => 'themes.php',
                'page_permissions'  => 'manage_options',
                'menu_icon'         => 'dashicons-forms',           // Specify a custom URL to an icon
                'last_tab'          => '',                          // Force your panel to always open to a specific tab (by id)
                'page_icon'         => 'dashicons-forms',           // Icon displayed in the admin panel next to your menu_title
                'page_slug'         => MONOLITE_SLUG . '_options',     // Page slug used to denote the panel
                'save_defaults'     => true,                        // On load save the defaults to DB before user clicks save or not
                'default_show'      => false,                       // If true, shows the default value next to each field that is not the default value.
                'default_mark'      => '<sup>*</sup>',                         // What to print by the field's title if the value shown is default. Suggested: *
                'show_import_export' => false,                       // Shows the Import/Export panel when not used as a field.
                'footer_credit'     => '&copy; ' . MONOLITE_TEAM . ', ' . date('Y'),
            );

            $this->args['share_icons'][] = array(
                'url'   => '//twitter.com/Ph0enixTeam',
                'title' => __('Follow us on Twitter', 'monolite-core'),
                'icon'  => 'el-icon-twitter'
            );
            $this->args['share_icons'][] = array(
                'url'   => '//themeforest.net/user/' . MONOLITE_TEAM,
                'title' => __('Buy our items on Envato', 'monolite-core'),
                'icon'  => 'el-icon-share-alt'
            );

            // Panel Intro text -> before the form
            if (!isset($this->args['global_variable']) || $this->args['global_variable'] !== false) {
                if (!empty($this->args['global_variable'])) {
                    $v = $this->args['global_variable'];
                } else {
                    $v = str_replace('-', '_', $this->args['opt_name']);
                }
            }
        }

        public function setHelpTabs ()
        {
            // Custom page help tabs, displayed using the help API. Tabs are shown in order of definition.
            $this->args['help_tabs'][] = array(
                'id'        => 'online-docs',
                'title'     => __('Theme Online Documentation', 'monolite-core'),
                'content'   => __('<p>Please see it online at <a target="_blank" href="//phoenix-theme.com/documentation/grandway/">http://phoenix-theme.com</a>.</p>', 'monolite-core')
            );
            $this->args['help_tabs'][] = array(
                'id'        => 'online-knowledge',
                'title'     => __('Our Products Knowledge Base', 'monolite-core'),
                'content'   => '<p>It will bee ready soon. Apologise.</p>'
            );
            $this->args['help_tabs'][] = array(
                'id'        => 'online-support',
                'title'     => __('Get Online Support', 'monolite-core'),
                'content'   => '<p>Need help? Feel free to ask us at our <a target="_blank" href="//themeforest.net/user/'. MONOLITE_TEAM .'">Themeforest Profile</a>. We are friendly and we are always help our customers.</p>'
            );

            // Set the help sidebar
            $this->args['help_sidebar'] = '<br /><a target="_blank" href="//themeforest.net/"><img style="width: 100%;" src="'.MONOLITE_THEME_URI.'/screenshot.png" alt="Theme-logo.png" /></a>';
        }

        public function setSections ()
        {
            // HEADER SETTINGS
            require_once $this->path . '/settings/header.php';
            // BLOG SETTINGS
            require_once $this->path . '/settings/blog.php';
            // Page SETTINGS
            require_once $this->path . '/settings/page.php';
            // PORTFOLIO SETTINGS
            require_once $this->path . '/settings/portfolio.php';
            // FOOTER SETTINGS
            require_once $this->path . '/settings/footer.php';
            // Socials Settings
            require_once $this->path . '/settings/social.php';
            // Sidebars Generator
            require_once $this->path . '/settings/sidebars.php';
            // Appearence SETTINGS
            require_once $this->path . '/settings/appearance.php';
            // WooCommerce Settings
            if ($this->dep_exists('woocommerce'))
                require_once $this->path . '/settings/woocommerce.php';
            // Other SETTINGS
            require_once $this->path . '/settings/other.php';
            // Update SETTINGS
            require_once $this->path . '/settings/update.php';
        }

        // Check if dependency plugin is active (VC, Woo, Regenerate Thumbnails etc)
        private function dep_exists ($name, $dir = null)
        {
            if (!$dir)
                $dir = $name;

            $active_plugins = apply_filters('active_plugins', get_option( 'active_plugins' ));

            if (in_array($dir .'/'. $name .'.php', $active_plugins))
                return true;

            return false;
        }

    }

}

global $reduxConfig;
$reduxConfig = new Monolite_by_Cu7ious_Redux_Framework_Config();
