<?php

$this->sections[] = array(
    'title'     => __('Blog', 'monolite-core'),
    'icon'      => 'el-icon-align-right',
    'fields'    => array(

        array(
            'id'        => 'blog_sidebar_position',
            'type'      => 'image_select',
            'title'     => __('Blog Sidebar Position', 'monolite-core'),
            'subtitle'  => __('Select a sidebar position for blog pages.', 'monolite-core'),
            'options'   => array(
                'right' => array('alt' => 'Sidebar Right',  'img' => ReduxFramework::$_url . 'assets/img/2cr.png'),
                'no' => array('alt' => 'No Sidebar',  'img' => ReduxFramework::$_url . 'assets/img/1col.png'),
                'left' => array('alt' => 'Sidebar Left',  'img' => ReduxFramework::$_url . 'assets/img/2cl.png')
            ),
            'default'   => 'right'
        ),

        array(
            'id'        => 'blog_sidebar_widgets_area',
            'type'      => 'select',
            'title'     => __('Blog Sidebar Widgets Area', 'monolite-core'),
            'subtitle'  => __('Select widgets area for blog pages.', 'monolite-core'),
            'data' => 'sidebars',
            'default'   => 'blog-sidebar'
        ),

        array(
            'id'        => 'blog_layout',
            'type'      => 'button_set',
            'title'     => __('Blog Layout Type.', 'monolite-core'),
            'subtitle'  => __('Select layout type for blog pages.', 'monolite-core'),
            'options'   => array(
                'classic' => __('Classic', 'monolite-core'),
                'medium' => __('Medium', 'monolite-core')
            ),
            'default'   => 'medium'
        ),

        array(
            'id'        => 'show_single_socials',
            'type'      => 'button_set',
            'title'     => __('Use Social Share Buttons?', 'monolite-core'),
            'subtitle'  => __('Enable/Disable social share buttons for a single posts.', 'monolite-core'),
            'options'   => array(
                1  => '&nbsp;I&nbsp;',
                0  => 'O'
            ),
            'default'   => 1
        ),

        array(
            'id'       => 'single_socil_buttons',
            'type'     => 'button_set',
            'title'    => __('Post Social Share Buttons', 'monolite-core'),
            'subtitle' => __('Choose what buttons you need to show on a single post.', 'monolite-core'),
            'required'  => array('show_single_socials', '=', 1),
            'multi'    => true,
            'options' => array(
                'facebook' => 'Facebook',
                'twitter' => 'Twitter',
                'googleplus' => 'Google +',
                'pinterest' => 'Pinterest',
                'linkedin' => 'LinkedIn',
                'tumblr' => 'Tumblr',
                'vk' => 'Vk',
             ),
            'default' => array('facebook', 'twitter', 'googleplus', 'pinterest')
        ),

    )
);
