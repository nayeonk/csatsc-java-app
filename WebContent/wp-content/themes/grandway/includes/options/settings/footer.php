<?php

$this->sections[] = array(
    'title'     => __('Footer', 'monolite-core'),
    'icon'      => 'el-icon-minus',
    'fields'    => array(

        array(
            'id'        => 'use_footer',
            'type'      => 'button_set',
            'title'     => __('Use Footer Widgets?', 'monolite-core'),
            'subtitle'  => __('You can turn off footer widgets, if you need.', 'monolite-core'),
            'options'   => array(
                1       => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default'   => 1
        ),

        array(
            'id'        => 'twitter_in_footer',
            'type'      => 'button_set',
            'title'     => __('Show Twitter Feed in Footer?', 'monolite-core'),
            'options'   => array(
                1       => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default'   => 1
        ),

        array(
            'id'        => 'twitter_username',
            'type'      => 'text',
            'title'     => __('Twitter Userame', 'monolite-core'),
            'subtitle'  => __('The account name, from which the feed will be displayed.', 'monolite-core'),
            'desc'  => __("Please note that changes may require up to 10 minutes.", 'monolite-core'),
            'required'    => array('twitter_in_footer','equals', 1),
            'default'   => 'DankovTheme'
        ),

        array(
            'id'       => 'twitter_qty',
            'type'     => 'spinner',
            'title'    => __('Twitter Feed Quantity', 'monolite-core'),
            'subtitle'     => __('How many tweets you want to display?', 'monolite-core'),
            'desc'  => __("Please note that changes may require up to 10 minutes.", 'monolite-core'),
            'required'    => array('twitter_in_footer','equals', 1),
            'default'  => '10',
            'step'     => '1',
            'min'     => '1',
        ),

        array(
            'id'        => 'footer_layout',
            'type'      => 'button_set',
            'title'     => __('Footer Layout', 'monolite-core'),
            'subtitle'  => __('You can choose how many columns you want in footer.', 'monolite-core'),
            'options'   => array(
                3 => '3 Columns',
                4 => "4 Columns"
            ),
            'required' => array('use_footer','equals', 1),
            'default'   => 4
        ),

        array(
            'id'        => 'copyright_text',
            'type'      => 'editor',
            'title'     => __('Copytight Text', 'monolite-core'),
            'subtitle'  => __('Place here your copyright. HTML tags are allowed.', 'monolite-core'),
            'default'   => 'Copyright &copy; 2015 GrandWay. Coded by <a href="http://themeforest.net/user/'. MONOLITE_TEAM .'" target="_blank">'. MONOLITE_TEAM .'</a>',
        )

    )
);
