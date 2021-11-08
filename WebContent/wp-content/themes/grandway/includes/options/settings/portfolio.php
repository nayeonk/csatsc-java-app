<?php

$this->sections[] = array(
    'title'     => __('Portfolio', 'monolite-core'),
    'icon'      => 'el-icon-folder-open',
    'fields'    => array(

        // PORTFOLIO
        array(
            'id'        => 'port_layout',
            'type'      => 'button_set',
            'title'     => __('Portfolio Page Layout.', 'monolite-core'),
            'subtitle'  => __('Select layout for portfolio pages.', 'monolite-core'),
            'options'   => array(
                '2-cols' => __('2 Columns', 'monolite-core'),
                '3-cols' => __('3 Columns', 'monolite-core'),
                '4-cols' => __('4 Columns', 'monolite-core'),
                'full'   => __('Fullwidth', 'monolite-core'),
            ),
            'default'   => '3-cols'
        ),

        array(
            'id'       => 'port_quantity',
            'type'     => 'spinner',
            'title'    => __('Portfolio Items quantity', 'monolite-core'),
            'subtitle' => __('How many portfolio items will be shown on portfolio page.','monolite-core'),
            'desc'     => null,
            'default'  => '9',
            'min'      => '3',
            'step'     => '1',
            'max'      => '100',
        ),

        array(
            'id'        => 'port_single_layout',
            'type'      => 'button_set',
            'title'     => __('Portfolio Single Items Layout', 'monolite-core'),
            'subtitle'  => __('Select layout for portfolio single items.', 'monolite-core'),
            'options'   => array(
                'half' => __('Half', 'monolite-core'),
                'wide' => __('Wide', 'monolite-core')
            ),
            'default'   => 'half'
        ),

        array(
            'id'       => 'port_related_quantity',
            'type'     => 'spinner',
            'title'    => __('Related Items quantity', 'monolite-core'),
            'subtitle' => __('How many portfolio items will be shown on portfolio single page.','monolite-core'),
            'desc'     => null,
            'default'  => '6',
            'min'      => '3',
            'step'     => '1',
            'max'      => '9',
        )
        // PORTFOLIO END

    )
);
