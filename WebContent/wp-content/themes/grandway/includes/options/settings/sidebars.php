<?php

$this->sections[] = array(
    'icon'      => 'el-icon-credit-card',
    'title'     => __('Sidebars', 'monolite-core'),
    'fields'    => array(

        array(
            'id'=>'sidebars_list',
            'type' => 'multi_text',
            'title' => __('Sidebars Generator', 'monolite-core'),
            'validate' => 'no_special_chars',
            'add_text' => __('Add Sidebar', 'monolite-core'),
            'subtitle' => __('Add unlimited custom sidebars to you site.', 'monolite-core'),
            'default'  => null
        )

    ),
);
