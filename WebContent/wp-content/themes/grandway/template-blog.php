<?php
    # Template name: Blog

    get_header();

    global $PhoenixData;

    $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : null;
    $PHT_sidebar_area = isset($PhoenixData['blog_sidebar_widgets_area']) ? $PhoenixData['blog_sidebar_widgets_area'] : 'blog-sidebar';
    $PHT_sidebar_pos = isset($PhoenixData['blog_sidebar_position']) ? $PhoenixData['blog_sidebar_position'] : 'right';
    $PHT_layout = isset($PhoenixData['blog_layout']) ? $PhoenixData['blog_layout'] : 'classic';
?>

<?php
    if (have_posts()) {
        while(have_posts()) {
            the_post();

            $PHT_page_subtitle = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_subtitle') : false;
            $PHT_page_crumbs   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_breadcrumbs') : false;
            $PHT_page_layout   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_layout') : false;
            $PHT_page_area     = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_widgets_area') : false;

            if ($PHT_page_layout && $PHT_page_layout != $PHT_sidebar_pos)
                $PHT_sidebar_pos = $PHT_page_layout;

            if ($PHT_page_area && $PHT_page_area != $PHT_sidebar_area)
                $PHT_sidebar_area = $PHT_page_area;
?>
            <div class="page-in"><div>

              <div class="container">
                <div class="row">

                  <div class="col-lg-6 pull-left">
                    <div class="page-in-name">
<?php
                        the_title();

                        if ($PHT_page_subtitle)
                            echo ": <span>". esc_html( $PHT_page_subtitle ) ."</span>";
?>
                    </div>
                  </div>
<?php
                if ($PHT_gen_crumbs && !$PHT_page_crumbs || $PHT_gen_crumbs && $PHT_page_crumbs === '-1') :

                    PhoenixTeam_Utils::breadcrumbs();

                elseif ($PHT_page_crumbs === '1') :

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
        </div>

            <div <?php post_class(array('container', 'general-font-area', 'marg50')); ?>>
                <div class="row">

<?php
                if ($PHT_sidebar_pos == 'no') {
                    echo '<div class="col-lg-12">' . "\n";
                } elseif ($PHT_sidebar_pos == 'right') {
                    echo '<div class="col-lg-9">' . "\n";
                } elseif ($PHT_sidebar_pos == 'left') {
?>                  <!-- sidebar -->
                    <div class="col-lg-3">
                        <?php dynamic_sidebar($PHT_sidebar_area); ?>
                    </div><!-- sidebar end-->

                    <div class="col-lg-9">
<?php
                }

                the_content();
        }
        wp_reset_postdata();
    }

    $PHT_query_args = array(
        'post_type' => 'post',
        'posts_per_page' => get_option('posts_per_page'),
        'post_status' => array('publish', 'private'),
        'paged' => get_query_var('paged')
    );

    $PHT_query = new WP_Query($PHT_query_args);

    if ($PHT_query->have_posts()) {
        while($PHT_query->have_posts()) {
            $PHT_query->the_post();

                $PHT_is_sticky = is_sticky() ? 'sticky-lou' : null;

                $PHT_post_format = get_post_format();
                if (!$PHT_post_format) {
                    echo '<div class="'. implode(' ', get_post_class(array('row', esc_attr($PHT_layout) .'-blog', 'post-standard', $PHT_is_sticky))) .'">';
                    get_template_part( 'format', 'standard' );
                } else {
                    echo '<div class="'. implode(' ', get_post_class(array('row', esc_attr($PHT_layout).'-blog', 'post-'.$PHT_post_format, $PHT_is_sticky ))).'">';
                    get_template_part( 'format', get_post_format() );
                }
                echo '</div>';

        }


        echo '<div class="row"><div class="col-lg-12">';
        PhoenixTeam_Utils::pagination('pride_pg', $PHT_query);
        echo '</div></div>';
    }

?>
            </div>

        <?php if ($PHT_sidebar_pos == 'right') : ?>
            <!-- sidebar -->
            <div class="col-lg-3">
                <?php dynamic_sidebar($PHT_sidebar_area); ?>
            </div><!-- sidebar end-->
        <?php endif; ?>

        </div>

        </div><!-- container marg50 -->

<?php get_footer(); ?>
