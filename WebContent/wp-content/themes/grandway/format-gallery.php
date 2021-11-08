<?php
    global $PHT_layout;

    $ID = get_the_id();
    $PHT_permalink = get_permalink();

    $PHT_thumb = null;
    if (has_post_thumbnail()) {
        $PHT_thumb = wp_get_attachment_image_src( get_post_thumbnail_id($ID), 'full', true );
        $PHT_thumb = array('full_url' => esc_url($PHT_thumb[0]));
    }

    $PHT_gallery = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . "_postformat_gallery", array('type' => 'image_advanced')) : false;

    if ($PHT_thumb)
        array_unshift($PHT_gallery, $PHT_thumb);
?>

<?php if (count($PHT_gallery)) : ?>

    <?php if ($PHT_layout == 'medium') : ?>
        <div class="col-lg-5">
    <?php endif; ?>

        <ul id="bxslider-<?php echo esc_attr($ID); ?>" class="bxslider">
        <?php
            foreach ($PHT_gallery as $item) {;
                echo '<li><img data-no-retina="1" src="'. esc_url($item['full_url']) .'" alt=""></li>';
            }
        ?>
        </ul>

        <!-- bxslider init -->
        <script type="text/javascript">
            jQuery(function() {
                jQuery('#bxslider-<?php echo esc_js($ID); ?>').bxSlider({
                    adaptiveHeight: true,
                    mode: 'fade',
                    slideMargin: 0,
                    pager: false,
                    controls: true
					max-height: 500px;
                });
            });
        </script>

    <?php if ($PHT_layout == 'medium') : ?>
        </div>
    <?php endif; ?>

<?php endif; ?>

<?php if ($PHT_layout == 'medium') : ?>
    <?php if (count($PHT_gallery)) {
            echo '<div class="col-lg-7">';
        } else {
            echo '<div class="col-lg-12">';
        }
    ?>
<?php endif; ?>

<div class="cl-blog-naz">
    <div class="cl-blog-type"><i class="icon-camera"></i></div>

    <div class="cl-blog-name">
        <a href="<?php echo esc_url($PHT_permalink); ?>"><?php the_title(); ?></a>
    </div >
    <div class="cl-blog-detail">
        <?php the_time('j F Y'); ?>,
        <?php _e('by', 'grandway'); ?> <?php the_author_posts_link(); ?>,
        <?php _e('in', 'grandway'); ?> <?php the_category(', '); ?>, <?php comments_popup_link( __('No comments', 'grandway'), __('1 comment', 'grandway'), __( '% comments', 'grandway'), null, __('Comments off', 'grandway') ); ?>
    </div>

    <?php if ( is_single() ) : ?>
        <div class="cl-blog-text">
            <?php the_content(); ?>
            <div class="cl-blog-like single-like"><?php echo PhoenixTeam_Likes::getPostLikeLink($post->ID); ?></div>
        </div>

</div><!-- cl-blog-naz -->
    <?php else : ?>
        <div class="cl-blog-text">
            <?php echo get_the_excerpt(); ?>
        </div>

</div><!-- cl-blog-naz -->
    <div style="overflow: hidden; width: 100%;">
        <div class="cl-blog-read"><a href="<?php echo esc_url($PHT_permalink); ?>"><i class="fa fa-angle-double-right"></i> <?php _e('Read More', 'grandway'); ?></a></div>
        <?php if (!is_single()) : ?>
          <div class="cl-blog-like"><?php echo PhoenixTeam_Likes::getPostLikeLink($post->ID); ?></div>
        <?php endif; ?>
    </div>
    <?php endif; ?>
    <br clear="all" />
<?php if ($PHT_layout == 'medium') : ?>
</div>
<?php endif; ?>
       <div class="col-lg-12"> <div class="cl-blog-line"></div></div>
