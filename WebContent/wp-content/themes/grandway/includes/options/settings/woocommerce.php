<?php

$regenerator = $this->dep_exists('force-regenerate-thumbnails');

if ($regenerator)
    $regenerate_thumbs = __('Click to <a href="tools.php?page=force-regenerate-thumbnails">regenerate</a>', 'monolite-core');
else
    $regenerate_thumbs = __('Click to <a target="_blank" href="plugin-install.php?tab=plugin-information&amp;plugin=force-regenerate-thumbnails&amp;TB_iframe=true">install plugin</a>', 'monolite-core');

$this->sections[] = array(
    'title'     => __('WooCommerce', 'monolite-core'),
    'icon'      => 'el-icon-shopping-cart',
    'fields'    => array(

        //Product Image Sizes Section
        array(
           'id' => 'section-woo-img-sizes',
           'type' => 'section',
           'subtitle' => '
              <div class="redux_field_th" style="font-size: 14px;color: #333;">'.
                __('Product Image Sizes', 'monolite-core') .
              '</div>' .
              __('These settings affect the actual dimensions of images in your catalog - the display on the front-end will still be affected by CSS styles.', 'monolite-core') .
              '<br />' .
              sprintf(__('Also please note, that if you want to change "Hard Crop" options for WooCommerce images, you should go to its %s.', 'monolite-core'), '<a target="_blank" href="?page=wc-settings&tab=products">settings page</a>') .
              '<br /><br />' .
              sprintf(__('After changing these settings you may need to regenerate your thumbnails. %1$s.', 'monolite-core'), $regenerate_thumbs),
           'indent' => true
        ),

            array(
                'id'       => 'shop_catalog_image_size',
                'type'     => 'dimensions',
                'units'    => false,
                'title'    => __('Catalog Images', 'monolite-core'),
                'desc'     => __('Dimensions of catalog images.', 'monolite-core'),
                'default'  => array(
                    'width'   => '270',
                    'height'  => '270'
                ),
            ),

            array(
                'id'       => 'shop_single_image_size',
                'type'     => 'dimensions',
                'units'    => false,
                'title'    => __('Single Product Images', 'monolite-core'),
                'desc'     => __('Dimensions of single product images.', 'monolite-core'),
                'default'  => array(
                    'width'   => '547',
                    'height'  => '547'
                ),
            ),

            array(
                'id'       => 'shop_thumbnail_image_size',
                'type'     => 'dimensions',
                'units'    => false,
                'title'    => __('Product Thumbnails', 'monolite-core'),
                'desc'     => __('Dimensions of product thumbnails.', 'monolite-core'),
                'default'  => array(
                    'width'   => '90',
                    'height'  => '90'
                ),
            ),

        array(
            'id'     => 'section-woo-img-sizes-end',
            'type'   => 'section',
            'indent' => false
        ),

        // Quantity & Columns Section
        array(
           'id' => 'section-woo-qtys',
           'type' => 'section',
           'subtitle' => '<div class="redux_field_th" style="font-size: 14px;color: #333;margin-top: 20px;">'. __('Quantity & Columns', 'monolite-core') .'</div>' . __('These settings let you choose how many products per row will be displayed on your shop main page and at the related products section. <br />Also here you can set a quantity of related products.', 'monolite-core'),
           'indent' => true
        ),

            array(
                'id'       => 'goods_grid_qty',
                'type'     => 'spinner',
                'title'    => __('Catalog Products Columns', 'monolite-core'),
                'desc'     => __('Products of your main shop catalog will be organized in this number of columns.', 'monolite-core'),
                'default'  => '3',
                'min'      => '1',
                'step'     => '1',
                'max'      => '12',
            ),

            array(
                'id'       => 'related_goods_cols',
                'type'     => 'spinner',
                'title'    => __('Related Products Columns', 'monolite-core'),
                'desc'     => __('Related products will be organized in this number of columns.', 'monolite-core'), // arranged in 4 columns
                'default'  => '3',
                'min'      => '1',
                'step'     => '1',
                'max'      => '12',
            ),

            array(
                'id'       => 'related_goods_qty',
                'type'     => 'spinner',
                'title'    => __('Related Products Quantity', 'monolite-core'),
                'desc'     => __('How many related products to show.', 'monolite-core'),
                'default'  => '3',
                'min'      => '1',
                'step'     => '1',
                'max'      => '12',
            ),

        array(
            'id'     => 'section-woo-qtys-end',
            'type'   => 'section',
            'indent' => false
        ),

    ),
);
