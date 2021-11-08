<?php

$this->sections[] = array(
    'title'     => __('Header', 'monolite-core'),
    'icon'      => 'el-icon-website',
    'fields'    => array(

        array(
            'id'        => 'custom_logo',
            'type'      => 'media',
            'url'       => true,
            'title'     => __('Custom Logo', 'monolite-core'),
            //'mode'      => false, // Can be set to false to allow any media type, or can also be set to any mime type.
            'subtitle'  => __('Upload your logo.', 'monolite-core'),
            'default'   => array('url' => get_template_directory_uri() . '/assets/images/logo.png')
        ),

        array(
            'id'        => 'custom_retina_logo',
            'type'      => 'media',
            'url'       => true,
            'title'     => __('Custom Retina Logo', 'monolite-core'),
            //'mode'      => false, // Can be set to false to allow any media type, or can also be set to any mime type.
            'subtitle'  => __('Upload your retina logo.', 'monolite-core'),
            'default'   => array('url' => get_template_directory_uri() . '/assets/images/logo@2x.png') 
        ),

         array(
            'id'        => 'use_sticky',
            'type'      => 'button_set',
            'title'     => __('Use sticky menu?', 'monolite-core'),
            'options'   => array(
                1        => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default'   => 1
        ),

    ),

);
