<?php

global $wp_version;

$wp_icon = get_option('site_icon');
if ($wp_icon) {
  $icons_section_text = sprintf(__("You have set the Site icon via WordPress customizer. If you want to use our fallback version instead, you should remove the Site icon <a target='_blank' href='%s'>here</a>.", 'monolite-core'), admin_url('customize.php?autofocus[control]=site_icon'));
} else {
  $icons_section_text = sprintf(__("You see this section, because you don't set the Site icon. Since v.4.3 WordPress has native icons, this section of the theme is deprecated and will be removed soon, so please set the Site icon <a target='_blank' href='%s'>here</a>.", 'monolite-core'), admin_url('customize.php?autofocus[control]=site_icon'));
}

$this->sections['appearance'] = array(
    'title'     => __('Appearance', 'monolite-core'),
    'icon'      => 'el-icon-brush',
    'fields'    => array()
);

    $this->sections['appearance']['fields'][] =
        array(
            'id'        => 'boxed_swtich',
            'type'      => 'button_set',
            'title'     => __('Layout Style', 'monolite-core'),
            'subtitle'  => __('Choose boxed or full page mode', 'monolite-core'),
            'options'   => array(
                "full"  => __("Full Width", 'monolite-core'),
                "boxed" => __("Boxed", 'monolite-core')
            ),
            'default'   => 'full'
        );

    $this->sections['appearance']['fields'][] =
            array(
                'id'=> 'boxed_background',
                'type'     => 'background',
                'title'    => __('Body Background', 'monolite-core'),
                'subtitle' => __('Body background with image, color, etc.', 'monolite-core'),
                'desc'     => __('Will be used if you choose a boxed layout.', 'monolite-core'),
                'required' => array('boxed_swtich','equals','boxed'),
                'default'   => null
            );

    $this->sections['appearance']['fields'][] =
        array(
            'id'        => 'set_color',
            'type'      => 'button_set',
            'title'     => __('Use Custom Color?', 'monolite-core'),
            'subtitle'  => __('Choose custom color for headers, menus, etc.', 'monolite-core'),
            'options'   => array(
                1        => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default' => 0
        );

    $this->sections['appearance']['fields'][] =
        array(
            'id'        => 'custom_skin_color',
            'type'      => 'color',
            'validate'  => 'color',
            'required'  => array('set_color','equals', 1),
            'title'     => __('Custom Color', 'monolite-core'),
            'subtitle'  => __('You can define a new custom color for the theme  scheme.', 'monolite-core'),
            'transparent' => false,
            'default'   => '#99CC66'
        );

    $this->sections['appearance']['fields'][] =
        array(
            'id'        => 'custom_fonts',
            'type'      => 'button_set',
            'title'     => __('Use Custom Fonts?', 'monolite-core'),
            'subtitle'  => __('Choose custom fonts for headers, menus, etc.', 'monolite-core'),
            'options'   => array(
                1        => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default' => 0
        );


        $this->sections['appearance']['fields'][] =
            array(
                'id'          => 'page_title_font',
                'type'        => 'typography',
                'title'       => __('Titles Font', 'monolite-core'),
                'google'      => true,
                'output'      => array(
                                    '.grid figcaption h3',
                                    '.blog-name a',
                                    '.testi-author',
                                    '.cl-blog-name',
                                    '.testimonials p.testimonial-author',
                                    '.woocommerce ul.products li.product h3, .woocommerce-page ul.products li.product h3',
                                    '.blog-name a',
                                    '.cbp-l-inline-title',
                                    '.testauthor-desc',
                                ),
                'units'       =>'px',
                'text-align'  => false,
                'line-height'  => false,
                'font-size'   => false,
                'color'  => false,
                'required'    => array('custom_fonts','equals', 1),
                'preview'     => array(
                    'text' => 'Grumpy wizards make toxic brew for the evil Queen and Jack.',
                    'font-size' => '23px',
                    'always_display' => true,
                ),
                // 'subtitle'    => __('Typography option with each property can be called individually.'),
                'default'     => array(
                    'font-style'  => '300',
                    'font-family' => 'Roboto',
                    'google'      => true,
                )
            );

        $this->sections['appearance']['fields'][] =
            array(
                'id'          => 'general_font',
                'type'        => 'typography',
                'title'       => __('General Font', 'monolite-core'),
                'google'      => true,
                'output'      => array(
                                    '.wrapper',
                                    'h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6',
                                    '.cbp-l-filters-button .cbp-filter-counter',
                                    '.cbp-l-filters-button .cbp-filter-item',
                                    '.cbp-l-loadMore-button-link',
                                    '.cbp-l-inline-desc',
                                    '.cbp-l-inline-subtitle',
                                ),
                'units'       =>'px',
                'text-align'  => false,
                'line-height'  => false,
                'color'  => false,
                'required'    => array('custom_fonts','equals', 1),
                'preview'     => array(
                    'text' => 'Grumpy wizards make toxic brew for the evil Queen and Jack.',
                    'font-size' => '14px',
                    'always_display' => true,
                ),
                // 'subtitle'    => __('Typography option with each property can be called individually.'),
                'default'     => array(
                    'font-style'  => '300',
                    'font-family' => 'Roboto',
                    'google'      => true,
                    'font-size'   => '14px',
                )
            );

        $this->sections['appearance']['fields'][] =
          array(
              'id' => 'section-start',
              'type' => 'section',
              'title' => __('Theme Icons', 'monolite-core'),
              'subtitle' => $icons_section_text,
              'indent' => true
          );

          if (!$wp_icon || $wp_version < 4.3) { // Theme Icons
              $this->sections['appearance']['fields'][] =
                  array(
                      'id'        => 'favicon',
                      'type'      => 'media',
                      'url'       => false,
                      'title'     => __('Favicon', 'monolite-core'),
                      'subtitle'  => __('Upload a 16px x 16px png/gif/ico icon', 'monolite-core'),
                      'default'   => array('url' => MONOLITE_THEME_URI . '/favicon.ico'),
                  );

              $this->sections['appearance']['fields'][] =
                  array(
                      'id'        => 'favicon_iphone',
                      'type'      => 'media',
                      'url'       => false,
                      'title'     => __('Apple iPhone Icon', 'monolite-core'),
                      'subtitle'  => __('Upload a 57px x 57px png/gif/ico icon for Classic iPhone', 'monolite-core'),
                      'default'   => array('url' => MONOLITE_THEME_URI . '/assets/images/57.png'),
                  );

              $this->sections['appearance']['fields'][] =
                  array(
                      'id'        => 'favicon_retina_iphone',
                      'type'      => 'media',
                      'url'       => false,
                      'title'     => __('Apple iPhone Retina Icon', 'monolite-core'),
                      'subtitle'  => __('Upload a 114px x 114px png/gif/ico icon for Retina iPhone', 'monolite-core'),
                      'default'   => array('url' => MONOLITE_THEME_URI . '/assets/images/114.png'),
                  );

              $this->sections['appearance']['fields'][] =
                  array(
                      'id'        => 'favicon_ipad',
                      'type'      => 'media',
                      'url'       => false,
                      'title'     => __('Apple iPad Icon', 'monolite-core'),
                      'subtitle'  => __('Upload a 72px x 72px png/gif/ico icon for Classic iPad', 'monolite-core'),
                      'default'   => array('url' => MONOLITE_THEME_URI . '/assets/images/72.png'),
                  );

              $this->sections['appearance']['fields'][] =
                  array(
                      'id'        => 'favicon_retina_ipad',
                      'type'      => 'media',
                      'url'       => false,
                      'title'     => __('Apple iPad Retina Icon', 'monolite-core'),
                      'subtitle'  => __('Upload a 144px x 144px png/gif/ico icon for Retina iPad', 'monolite-core'),
                      'default'   => array('url' => MONOLITE_THEME_URI . '/assets/images/144.png'),
                  );

          } // Theme Icons END

        $this->sections['appearance']['fields'][] =
            array(
                'id'     => 'section-end',
                'type'   => 'section',
                'indent' => false,
            );
