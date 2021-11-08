<?php
    get_header();

    global $PhoenixData;

    $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : true;
    $PHT_is_shop = is_shop();
    $ID = woocommerce_get_page_id('shop');

    if (function_exists('rwmb_meta')) {
      $PHT_page_crumbs = rwmb_meta(THEME_SLUG . '_page_breadcrumbs', "", $ID) ? rwmb_meta(THEME_SLUG . '_page_breadcrumbs', "", $ID) : $PHT_gen_crumbs;
      $PHT_sidebar_pos = rwmb_meta(THEME_SLUG . '_page_layout', "", $ID) ? rwmb_meta(THEME_SLUG . '_page_layout', "", $ID) : 'right';
      $PHT_sidebar_ar  = rwmb_meta(THEME_SLUG . '_page_widgets_area', "", $ID) ? rwmb_meta(THEME_SLUG . '_page_widgets_area', "", $ID) : 'woo-sidebar';
    } else {
      $PHT_page_crumbs = $PHT_sidebar_pos = $PHT_sidebar_ar = false;
    }

    $PHT_page_header = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_header_bg', array('type' => 'image_advanced'), $ID) : false;
    $PHT_header_adv = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_header_advanced', "", $ID) : false;

    if ($PHT_header_adv) {
        $PHT_page_bgcol  = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_header_bgcol', "", $ID) : false;
        $PHT_bgcol_opac  = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_header_bgcol_opacity', "", $ID) : false;
        $PHT_title_col   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_title_col', "", $ID) : false;
    } else {
        $PHT_page_bgcol = $PHT_bgcol_opac = $PHT_title_col = null;
    }

    $PHT_bg_css = null;

    if (!$PHT_is_shop)
        $ID = $post->ID;

    $PHT_page_subtitle = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_subtitle', "", $ID) : false;

    if ($PHT_page_header) {
        $PHT_bg_css = ' style="';

        $PHT_page_header = array_shift($PHT_page_header);
        $PHT_page_header = 'background: url('. esc_url($PHT_page_header['full_url']) .') center repeat;';

        if ($PHT_title_col)
            $PHT_title_col = 'color:'. $PHT_title_col .';';

        if ($PHT_page_bgcol) {

            if ($PHT_bgcol_opac && $PHT_bgcol_opac != 1) {
                $PHT_page_bgcol = PhoenixTeam_Utils::hex_to_rgb($PHT_page_bgcol);
                $PHT_page_bgcol = 'rgba('. $PHT_page_bgcol .','. $PHT_bgcol_opac .')';
            }

            $PHT_page_bgcol = '<div style="background-color: '. $PHT_page_bgcol .';">';
        } else {
            $PHT_page_bgcol = '<div>';
        }

        $PHT_bg_css .= $PHT_page_header . $PHT_title_col;

        $PHT_bg_css .= '"';

        echo '<div class="page-in" '. esc_html( $PHT_bg_css ) .'>' . esc_html( $PHT_page_bgcol );
    } else {
        echo '<div class="page-in"><div>';
    }
?>
              <div class="container">
                <div class="row">

                  <div class="col-lg-6 pull-left">
                    <div class="page-in-name">
<?php
                        if ($PHT_is_shop)
                            woocommerce_page_title();
                        else
                            echo esc_html( get_the_title() );

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
                    echo "<!-- Breadcrumbs turned off -->";
                endif;
?>
                </div>
              </div>
            </div>
        </div>

            <section id="woocommerce_page" class="marg50 container general-font-area<?php echo ' phoenixteam-sidebar-'. esc_attr( $PHT_sidebar_pos ); ?>">
                <div>
<?php
                if ($PHT_sidebar_pos == 'no') {
                    echo '<div class="col-lg-12">' . "\n";
                } elseif ($PHT_sidebar_pos == 'right') {
                    echo '<div class="row">' . "\n";
                    echo '<div class="col-lg-9">' . "\n";
                } elseif ($PHT_sidebar_pos == 'left') {
?>
                    <!-- sidebar -->
                    <div class="row">
                    <div class="col-lg-3">
                        <?php dynamic_sidebar($PHT_sidebar_ar); ?>
                    </div><!-- sidebar end-->

                    <div class="col-lg-9">
<?php
                }

                woocommerce_content();
?>


<?php
                if ($PHT_sidebar_pos == 'right') :
?>
                        </div>
                        <!-- sidebar -->
                        <div class="col-lg-3">
                            <?php dynamic_sidebar($PHT_sidebar_ar); ?>
                        </div><!-- sidebar end-->
                    </div>
                <?php elseif ($PHT_sidebar_pos == 'left') : ?>
                    </div>
                <?php endif; ?>

            </div>
            </section>

<?php get_footer(); ?>
