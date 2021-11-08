<?php
/**
 * The template for displaying product content within loops.
 *
 * Override this template by copying it to yourtheme/woocommerce/content-product.php
 *
 * @author 		WooThemes
 * @package 	WooCommerce/Templates
 * @version   2.4.0
 */

if ( ! defined( 'ABSPATH' ) ) exit; // Exit if accessed directly

global $product, $woocommerce_loop;

$PHT_shopID = get_option('woocommerce_shop_page_id');
$PHT_shopID = get_permalink($PHT_shopID);

// Store loop count we're currently on
if (empty($woocommerce_loop['loop'])) {
  $woocommerce_loop['loop'] = 0;
}

// Store column count for displaying the grid
if (empty($woocommerce_loop['columns'])) {
  $woocommerce_loop['columns'] = apply_filters('loop_shop_columns', 3);
}

// Ensure visibility
if (!$product || !$product->is_visible()) {
  return;
}

// Increase loop count
$woocommerce_loop['loop']++;

// Extra post classes
$PHT_classes = array();
if ( 0 == ( $woocommerce_loop['loop'] - 1 ) % $woocommerce_loop['columns'] || 1 == $woocommerce_loop['columns'] )
  $PHT_classes[] = 'first';
if ( 0 == $woocommerce_loop['loop'] % $woocommerce_loop['columns'] )
  $PHT_classes[] = 'last';

    $PHT_classes[] = 'grandway-product-block';
?>
<li <?php post_class($PHT_classes); ?>>

  <?php do_action( 'woocommerce_before_shop_loop_item' ); ?>

  <div class="portfolio-phoenixteam">
    <div class="portfolio-image">
      <?php
        echo '<a href="' . esc_url(get_permalink()) . '"><div class="woo-img-class">';
          do_action('woocommerce_before_shop_loop_item_title');
        echo '</div></a>';

        $PHT_ID = get_the_id();

      ?>
        <figcaption>
          <p class="icon-links">
            <a href="<?php echo esc_url($PHT_shopID); ?>?add-to-cart=<?php echo esc_attr($PHT_ID); ?>" rel="nofollow" data-quantity="1" "data-product_id="<?php echo esc_attr($PHT_ID); ?>" class="cbp-singlePageInline"><i class="icon-basket"></i></a>
            <a href="<?php the_permalink(); ?>" class="cbp-lightbox" data-title="<?php the_title(); ?>"><i class="icon-paperclip"></i></a>
          </p>
          </figcaption>

      </div>
      <div class="woo-title-price">
          <h2><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h2>
          <p><?php echo wp_kses_post(woocommerce_template_loop_price()); ?></p>
      </div>

  </div>

</li>
