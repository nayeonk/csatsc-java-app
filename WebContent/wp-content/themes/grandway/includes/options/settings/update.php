<?php

$this->sections['updates'] = array(
    'title'     => __('Updates', 'monolite-core'),
    'icon'      => ' el-icon-refresh',
    'fields'    => array()
);

$this->sections['updates']['fields'][] =
    array(
        'id'        => 'use_woocommerce',
        'type'      => 'button_set',
        'title'     => __('Recommend WooCommerce?', 'monolite-core'),
        'subtitle'  => __('You can disable notification about it here.', 'monolite-core'),
        'options'   => array(
            1       => '&nbsp;I&nbsp;',
            0       => 'O',
        ),
        'default'   => 1
    );

$this->sections['updates']['fields'][] =
    array(
        'id'        => 'use_envato_wp_toolkit',
        'type'      => 'button_set',
        'title'     => __('Recommend Envato WordPress Toolkit?', 'monolite-core'),
        'subtitle'  => __('You can disable notification about it here.', 'monolite-core'),
        'options'   => array(
            1       => '&nbsp;I&nbsp;',
            0       => 'O',
        ),
        'default'   => 1
    );


if (class_exists('Envato_WP_Toolkit')) {
    $updater = get_option('envato-wordpress-toolkit');

    if ($updater && is_array($updater) && count($updater) != 0) {
        if ($updater['user_name'] && $updater['api_key']) {

                $this->sections['updates']['fields'][] =
                    array(
                        'id'    => 'update_success',
                        'type'  => 'info',
                        'title' => __('All Ok! Your theme updates is configured correctly', 'monolite-core'),
                        'style' => 'success',
                        'required'  => array('use_envato_wp_toolkit', '=', '1'),
                        'notice' => true,
                        // 'icon'  => 'el-icon-asl',
                        'icon'  => 'el-icon-ok',
                        'desc'  => __('To change your settings, visit <a href="?page=envato-wordpress-toolkit">this page</a>.', 'monolite-core')
                    );

                $this->sections['updates']['fields'][] =
                    array(
                        'id'   =>'updates_divider',
                        'type' => 'divide'
                    );

        } else {

                $this->sections['updates']['fields'][] =
                    array(
                        'id'    => 'update_warning',
                        'type'  => 'info',
                        'title' => __('Your Envato Keys is not set!', 'monolite-core'),
                        'style' => 'warning',
                        'required'  => array('use_envato_wp_toolkit', '=', '1'),
                        'notice' => true,
                        'icon'  => 'el-icon-warning-sign',
                        'desc'  => __('To enable update notifications, please visit <a href="?page=envato-wordpress-toolkit">this page</a>.', 'monolite-core')
                    );

                $this->sections['updates']['fields'][] =
                    array(
                        'id'   =>'updates_divider',
                        'type' => 'divide'
                    );

        }
    } else {

            $this->sections['updates']['fields'][] =
                array(
                    'id'    => 'update_warning',
                    'type'  => 'info',
                    'title' => __('Your Envato Keys is not set!', 'monolite-core'),
                    'style' => 'warning',
                    'required'  => array('use_envato_wp_toolkit', '=', '1'),
                    'notice' => true,
                    'icon'  => 'el-icon-warning-sign',
                    'desc'  => __('To enable update notifications, please visit <a href="?page=envato-wordpress-toolkit">this page</a>.', 'monolite-core')
                );

            $this->sections['updates']['fields'][] =
                array(
                    'id'   =>'updates_divider',
                    'type' => 'divide'
                );

    }

} else {
        $this->sections['updates']['fields'][] =
            array(
                'id'    => 'update_warning',
                'type'  => 'info',
                'title' => __('Envato WordPress Toolkit plugin does not installed or it is not active.', 'monolite-core'),
                'style' => 'warning',
                'required'  => array('use_envato_wp_toolkit', '=', '1'),
                'notice' => true,
                'icon'  => 'el-icon-warning-sign',
                'desc'  => __('To install or activate Envato WordPress Toolkit plugin, please visit <a href="?page='.MONOLITE_SLUG.'-plugin-activator">this page</a>.', 'monolite-core')
            );

        $this->sections['updates']['fields'][] =
            array(
                'id'   =>'updates_divider',
                'type' => 'divide'
            );
}
