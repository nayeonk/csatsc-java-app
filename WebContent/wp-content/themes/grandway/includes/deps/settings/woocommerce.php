<?php

new PhoenixTeam_eCommerce();

class PhoenixTeam_eCommerce {

    private $reduxData;
    private $whereSetDimentions;

    public function __construct ()
    {
        $this->setReduxData();
        $page = isset($_GET['page']) ? $_GET['page'] : null;

        if ($page && $page == THEME_SLUG . '_options') {
            $this->whereSetDimentions = 'redux';
            add_action('admin_init', array($this, 'wooSetImageSizes'));
        } elseif ($page && $page == 'wc-settings') {
            $this->whereSetDimentions = 'woo';
            add_action('woocommerce_settings_saved', array($this, 'wooSetImageSizes'));
        }

        add_action('init', array($this, 'wooRemoveCrumbs'));
        add_filter('woocommerce_show_page_title', array($this, 'wooNotOverrideTitle'));
        add_filter('loop_shop_columns', array($this, 'wooLoopColumns'));
        add_filter('woocommerce_output_related_products_args', array($this, 'wooRelatedGoods'));
        remove_action('woocommerce_after_shop_loop_item_title', 'woocommerce_template_loop_price', 10);
    }


    public function wooRemoveCrumbs ()
    {
        remove_action('woocommerce_before_main_content', 'woocommerce_breadcrumb', 20, 0);
    }


    public function wooNotOverrideTitle ()
    {
        return false;
    }


    public function wooRelatedGoods ($args)
    {
        $data = $this->reduxData;
        $qty = $data['related_goods_qty'] ? $data['related_goods_qty'] : 3;
        $cols = $data['related_goods_cols'] ? $data['related_goods_cols'] : 3;

        $args['posts_per_page'] = $qty; // 'n' related products
        $args['columns'] = $cols; // arranged in 'n' columns

        return $args;
    }


    function wooLoopColumns ()
    {
        $data = $this->reduxData;
        $qty = $data['goods_grid_qty'] ? $data['goods_grid_qty'] : 3;

        return $qty; // 'n' products per row
    }


    function wooSetImageSizes ()
    {
        global $reduxConfig;
        $data = $this->reduxData;
        $redux_img_sizes = array();
        $redux_img_sizes['shop_catalog_image_size'] = isset($data['shop_catalog_image_size']) ? $data['shop_catalog_image_size'] : null;
        $redux_img_sizes['shop_single_image_size'] = isset($data['shop_single_image_size']) ? $data['shop_single_image_size'] : null;
        $redux_img_sizes['shop_thumbnail_image_size'] = isset($data['shop_thumbnail_image_size']) ? $data['shop_thumbnail_image_size'] : null;

        foreach ($redux_img_sizes as $name => $val) {
            if ($redux_img_sizes[$name]) {
                $redux_img_size  =  $redux_img_sizes[$name];
                $woo_img_size    =  get_option($name);
                $redux_crop      =  isset($woo_img_size['crop']) ? $woo_img_size['crop'] : null;

                if ($redux_crop)
                    $redux_img_size['crop'] = $redux_crop;

                if ($woo_img_size !== $redux_img_size) {
                    if ($this->whereSetDimentions == 'redux') {
                        update_option($name, $redux_img_size);
                    } elseif ($this->whereSetDimentions == 'woo') {
                        $reduxConfig->ReduxFramework->set($name, $woo_img_size);
                    }
                }
            }
        }

        return false;
    }


    private function setReduxData ()
    {
        global $PhoenixData;
        $this->reduxData = $PhoenixData;
        return false;
    }

}
