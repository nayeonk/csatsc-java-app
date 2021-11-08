<?php get_header(); ?>

<?php
    global $PhoenixData;
    $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : null;
    $PHT_sidebar = isset($PhoenixData['blog_sidebar_position']) ? $PhoenixData['blog_sidebar_position'] : 'right';
?>

<?php
if (have_posts()) :
    while (have_posts()) :
        the_post();
?>
        <div class="page-in">
          <div class="container">
            <div class="row">

              <div class="col-lg-6 pull-left">
                <div class="page-in-name">
<?php
                    _e('Attachment', 'grandway');
                    echo ": <span>". esc_html( get_the_title() ) ."</span>";
?>
                </div>
              </div>
<?php
                if ($PHT_gen_crumbs) :
                    PhoenixTeam_Utils::breadcrumbs();
                else :
?>
                    <!-- Breadcrumbs turned off -->
<?php
                endif;
?>
            </div>
          </div>
        </div>

        <div id="attachment-<?php the_ID(); ?>" <?php post_class( array('container', 'marg50') ); ?>>
            <div class="row">
<?php
                if ($PHT_sidebar == 'no') {
                    echo '<div class="col-lg-12">' . "\n";
                } elseif ($PHT_sidebar == 'right') {
                    echo '<div class="col-lg-9">' . "\n";
                } elseif ($PHT_sidebar == 'left') {
?>                  <!-- sidebar -->
                    <div class="col-lg-3">
                        <?php dynamic_sidebar('blog-sidebar'); ?>
                    </div><!-- sidebar end-->

                    <div class="col-lg-9">
<?php
                }

                $PHT_permalink = get_permalink();
?>

                <?php if (has_post_thumbnail()) : ?>
                    <div class="cl-blog-img">
                        <?php the_post_thumbnail( array( null, null, 'bfi_thumb' => true ) ); ?>
                    </div>
                <?php endif; ?>

                <div class="cl-blog-naz">
                    <div class="cl-blog-type"><i class="icon-camera"></i></div>

                    <div class="cl-blog-name">
                        <a href="<?php echo esc_url($PHT_permalink); ?>"><?php the_title(); ?></a>
                    </div >
                    <div class="cl-blog-detail">
                        <?php the_time('j F Y'); ?> - <?php the_time('G:i'); ?>,
                        <?php _e('by', 'grandway'); ?> <?php the_author_posts_link(); ?>,
                        <?php _e('in', 'grandway'); ?> <?php the_category(', '); ?>, <?php comments_popup_link( __('No comments', 'grandway'), __('1 comment', 'grandway'), __( '% comments', 'grandway'), null, __('Comments off', 'grandway') ); ?>
                    </div>

                    <div class="cl-blog-text">
                        <div class="entry-attachment">
<?php
                            if ( wp_attachment_is_image( $post->id ) ) :
                                $att_image = wp_get_attachment_image_src( $post->id, "full");
?>
                                <p class="attachment">
                                    <a href="<?php echo esc_attr( wp_get_attachment_url($post->id) ); ?>" title="<?php esc_attr( the_title() ); ?>" rel="attachment"><img data-no-retina="1" src="<?php echo esc_url($att_image[0]);?>" class="attachment-medium" alt="<?php $post->post_excerpt; ?>" /></a>
                                </p>
                            <?php else : ?>
                                <?php the_content(); ?>
                            <?php endif; ?>
                        </div>

                    </div>

                </div><!-- cl-blog-naz -->

                    <div class="row">
                        <div class="col-lg-12">
                            <?php echo PhoenixTeam_Utils::single_socials(); ?>
                        </div>
                    </div>

                    <div class="cl-blog-line"></div>

                    <!-- comments -->
                    <div>
                        <?php comments_template(); ?>
                    </div>
                    <!-- /comments -->

                </div>

                <?php if ($PHT_sidebar == 'right') : ?>
                    <!-- sidebar -->
                    <div class="col-lg-3">
                        <?php dynamic_sidebar('blog-sidebar'); ?>
                    </div><!-- sidebar end-->
                <?php endif; ?>

            </div>
        </div>

    <?php endwhile; ?>

    <?php else: ?>

        <div class="container marg50">
            <h1 style="display: block; text-align: center;"><?php _e('Sorry, nothing to display.', 'grandway'); ?></h1>
        </div>

    <?php endif; ?>

<?php get_footer(); ?>
