<?php

$this->sections[] = array(
    'title'     => __('Page', 'monolite-core'),
    'fields'    => array(


        array(
            'id'        => 'page_sidebar_position',
            'type'      => 'image_select',
            'title'     => __('Page Sidebar Position', 'monolite-core'),
            'subtitle'  => __('Select a sidebar position for pages.', 'monolite-core'),
            'options'   => array(
                'right' => array('alt' => 'Sidebar Right',  'img' => ReduxFramework::$_url . 'assets/img/2cr.png'),
                'no' => array('alt' => 'No Sidebar',  'img' => ReduxFramework::$_url . 'assets/img/1col.png'),
                'left' => array('alt' => 'Sidebar Left',  'img' => ReduxFramework::$_url . 'assets/img/2cl.png')
            ),
            'default'   => 'no'
        ),

        array(
            'id'        => 'page_sidebar_widgets_area',
            'type'      => 'select',
            'title'     => __('Page Sidebar Widgets Area', 'monolite-core'),
            'subtitle'  => __('Select widgets area for pages.', 'monolite-core'),
            'data' => 'sidebars',
            'default'   => 'blog-sidebar'
        ),

        array(
            'id'        => 'breadcrumbs',
            'type'      => 'button_set',
            'title'     => __('Show Breadcrumbs?', 'monolite-core'),
            'subtitle'  => __('Show Breadcrumbs on top of the pages?', 'monolite-core'),
            'options'   => array(
                1       => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default'   => 1
        ),

        array(
            'id'        => 'page_display_comments',
            'type'      => 'button_set',
            'title'     => __('Show Comments on Pages?', 'monolite-core'),
            'subtitle'  => __('Enables comments for static pages.', 'monolite-core'),
            'options'   => array(
                1       => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default'   => 0
        ),
    ),
);
