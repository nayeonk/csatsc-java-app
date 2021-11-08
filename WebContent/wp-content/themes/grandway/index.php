<?php
    get_header();

    global $PhoenixData;

    $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : null;
    $PHT_sidebar = isset($PhoenixData['blog_sidebar_position']) ? $PhoenixData['blog_sidebar_position'] : 'right';
    $PHT_layout = isset($PhoenixData['blog_layout']) ? $PhoenixData['blog_layout'] : 'classic';

    $PHT_page = PhoenixTeam_Utils::check_posts_page();
?>

<?php
    if ($PHT_page) {
            $PHT_page_subtitle = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_subtitle', null, $PHT_page->ID) : false;
            $PHT_page_crumbs   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_breadcrumbs', null, $PHT_page->ID) : false;
?>
            <div class="page-in">
              <div class="container">
                <div class="row">

                  <div class="col-lg-6 pull-left">
                    <div class="page-in-name">
<?php
                        echo esc_html( $PHT_page->post_title );

                        if ($PHT_page_subtitle)
                            echo ": <span>". esc_html( $PHT_page_subtitle ) ."</span>";
?>
                    </div>
                  </div>
<?php
                if ($PHT_gen_crumbs && $PHT_page_crumbs === '-1') :

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

            <div <?php post_class(array('container', 'general-font-area', 'marg50')); ?>>
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

        echo wp_kses_post($PHT_page->post_content);

        wp_reset_postdata();
    } else {
?>
            <div class="container general-font-area marg50">
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
    } // if (page) END


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

                $PHT_post_format = get_post_format();
                if (!$PHT_post_format) {
                    echo '<div class="row '.esc_html($PHT_layout).'-blog post-standard">';
                    get_template_part( 'format', 'standard' );
                } else {
                    echo '<div class="row '.esc_html($PHT_layout).'-blog post-'.$PHT_post_format.'">';
                    get_template_part( 'format', get_post_format() );
                }
                echo '</div>';

        }

        echo '<div class="row"><div class="col-lg-12">';
        $PHT_pagination = PhoenixTeam_Utils::pagination('pride_pg', $PHT_query);
        if (!$PHT_pagination) posts_nav_link();
        echo '</div></div>';
    } else {
?>
        <div class="container marg50">
            <h1 style="display: block; text-align: center;"><?php _e('Sorry, nothing to display.', 'grandway'); ?></h1>
        </div>
<?php
    }
?>
            </div>

        <?php if ($PHT_sidebar == 'right') : ?>
            <!-- sidebar -->
            <div class="col-lg-3">
                <?php dynamic_sidebar('blog-sidebar'); ?>
            </div><!-- sidebar end-->
        <?php endif; ?>

        </div>

        </div><!-- container marg50 -->

<?php get_footer(); ?>
