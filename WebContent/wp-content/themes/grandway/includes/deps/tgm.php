<?php

new PhoenixTeam_Deps_Activator(PhoenixTeam_Deps_Definition::deps_array(), PhoenixTeam_Deps_Definition::tgm_config_array());

abstract class PhoenixTeam_Deps_Definition {

    public static function deps_array ()
    {
        global $PhoenixData;

        $envatoWPT   = isset($PhoenixData['use_envato_wp_toolkit']) ? $PhoenixData['use_envato_wp_toolkit'] : true;
        $wooCommerce = isset($PhoenixData['use_woocommerce']) ? $PhoenixData['use_woocommerce'] : true;

        $tgmDeps = array(
            array(
                'name'               => 'Monolite Core', // The plugin name.
                'slug'               => 'monolite-core', // The plugin slug (typically the folder name).
                'source'             => THEME_DIR . '/includes/plugins/monolite-core.zip', // The plugin source.
                'external_url'       => 'http://cu7io.us',
                'required'           => true, // If false, the plugin is only 'recommended' instead of required.
                'version'            => '1.0.0', // E.g. 1.0.0. If set, the active plugin must be this version or higher.
            ),
            array(
                'name'               => 'WPBakery Visual Composer', // The plugin name.
                'slug'               => 'js_composer', // The plugin slug (typically the folder name).
                'source'             => THEME_DIR . '/includes/plugins/js_composer.zip', // The plugin source.
                'external_url'       => 'http://vc.wpbakery.com/',
                'required'           => true, // If false, the plugin is only 'recommended' instead of required.
                'version'            => '4.6.2', // E.g. 1.0.0. If set, the active plugin must be this version or higher.
            ),

            array(
                'name'               => 'Revolution Slider', // The plugin name.
                'slug'               => 'revslider', // The plugin slug (typically the folder name).
                'source'             => THEME_DIR . '/includes/plugins/revslider.zip', // The plugin source.
                'external_url'       => 'http://revolution.themepunch.com/',
                'required'           => true, // If false, the plugin is only 'recommended' instead of required.
                'version'            => '4.6.93', // E.g. 1.0.0. If set, the active plugin must be this version or higher.
            ),
        );

        // Set WooCommerce as recommended ONLY if needed
        if ($wooCommerce) {
            $tgmDeps[] = array(
                'name'               => 'WooCommerce', // The plugin name.
                'slug'               => 'woocommerce', // The plugin slug (typically the folder name).
                'source'             => 'https://downloads.wordpress.org/plugin/woocommerce.2.4.6.zip', // The plugin source.
                'external_url'       => 'http://www.woothemes.com/woocommerce/',
                'required'           => false, // If false, the plugin is only 'recommended' instead of required.
                'version'            => '2.4.6', // E.g. 1.0.0. If set, the active plugin must be this version or higher.
            );
        }

        // Set envato toolkit as recommended ONLY if needed
        if ($envatoWPT) {
            $tgmDeps[] = array(
                'name'               => 'Envato WordPress Toolkit', // The plugin name.
                'slug'               => 'envato-wordpress-toolkit-master', // The plugin slug (typically the folder name).
                'source'             => 'https://github.com/envato/envato-wordpress-toolkit/archive/master.zip',// The plugin source.
                'external_url'       => 'https://github.com/envato/envato-wordpress-toolkit',
                'required'           => false, // If false, the plugin is only 'recommended' instead of required.
                'version'            => '1.7.3', // E.g. 1.0.0. If set, the active plugin must be this version or higher.
            );
        }

        return $tgmDeps;
    }

    public static function tgm_config_array ()
    {
        return array(
            'id'           => 'tgmpa',                           // Unique ID for hashing notices for multiple instances of TGMPA.
            'default_path' => '',                                // Default absolute path to pre-packaged plugins.
            'menu'         => THEME_SLUG . '-plugin-activator',  // Menu slug.
            'has_notices'  => true,                              // Show admin notices or not.
            'dismissable'  => true,                             // If false, a user cannot dismiss the nag message.
            'dismiss_msg'  => '',                                // If 'dismissable' is false, this message will be output at top of nag.
            'is_automatic' => true,                              // Automatically activate plugins after installation or not.
            'message'      => '',                                // Message to output right before the plugins table.
            'strings'      => array(
                'page_title'                      => __('Install Required Plugins', 'grandway'),
                'menu_title'                      => __('Install Plugins', 'grandway'),
                'installing'                      => __('Installing Plugin: %s', 'grandway'), // %s = plugin name.
                'oops'                            => __('Something went wrong with the plugin API.', 'grandway'),
                'notice_can_install_required'     => _n_noop('This theme requires the following plugin: %1$s.', 'This theme requires the following plugins: %1$s.', 'grandway'), // %1$s = plugin name(s).
                'notice_can_install_recommended'  => _n_noop('This theme recommends the following plugin: %1$s.', 'This theme recommends the following plugins: %1$s.', 'grandway'), // %1$s = plugin name(s).
                'notice_cannot_install'           => _n_noop('Sorry, but you do not have the correct permissions to install the %s plugin. Contact the administrator of this site for help on getting the plugin installed.', 'Sorry, but you do not have the correct permissions to install the %s plugins. Contact the administrator of this site for help on getting the plugins installed.', 'grandway'), // %1$s = plugin name(s).
                'notice_can_activate_required'    => _n_noop('The following required plugin is currently inactive: %1$s.', 'The following required plugins are currently inactive: %1$s.', 'grandway'), // %1$s = plugin name(s).
                'notice_can_activate_recommended' => _n_noop('The following recommended plugin is currently inactive: %1$s.', 'The following recommended plugins are currently inactive: %1$s.', 'grandway'), // %1$s = plugin name(s).
                'notice_cannot_activate'          => _n_noop('Sorry, but you do not have the correct permissions to activate the %s plugin. Contact the administrator of this site for help on getting the plugin activated.', 'Sorry, but you do not have the correct permissions to activate the %s plugins. Contact the administrator of this site for help on getting the plugins activated.', 'grandway'), // %1$s = plugin name(s).
                'notice_ask_to_update'            => _n_noop('The following plugin needs to be updated to its latest version to ensure maximum compatibility with this theme: %1$s.', 'The following plugins need to be updated to their latest version to ensure maximum compatibility with this theme: %1$s.', 'grandway'), // %1$s = plugin name(s).
                'notice_cannot_update'            => _n_noop('Sorry, but you do not have the correct permissions to update the %s plugin. Contact the administrator of this site for help on getting the plugin updated.', 'Sorry, but you do not have the correct permissions to update the %s plugins. Contact the administrator of this site for help on getting the plugins updated.', 'grandway'), // %1$s = plugin name(s).
                'install_link'                    => _n_noop('Begin installing plugin', 'Begin installing plugins', 'grandway'),
                'activate_link'                   => _n_noop('Begin activating plugin', 'Begin activating plugins', 'grandway'),
                'return'                          => __('Return to Required Plugins Installer', 'grandway'),
                'plugin_activated'                => __('Plugin activated successfully.', 'grandway'),
                'complete'                        => __('All plugins installed and activated successfully. %s', 'grandway'), // %s = dashboard link.
                'nag_type'                        => 'updated' // Determines admin notice type - can only be 'updated', 'update-nag' or 'error'.
            )
        );
    }

}


class PhoenixTeam_Deps_Activator {

    private $plugins;
    private $config;

    public function __construct ($plugins, $config)
    {
        $this->plugins = $plugins;
        $this->config = $config;
        add_action('tgmpa_register', array($this, 'registerRequiredPlugins'));
    }

    public function registerRequiredPlugins ()
    {
        tgmpa($this->plugins, $this->config);
    }

}
