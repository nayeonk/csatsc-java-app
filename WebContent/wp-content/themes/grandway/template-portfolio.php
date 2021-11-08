<?php
    # Template name: Portfolio

    get_header();

    global $PhoenixData;

    $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : null;
    $PHT_sidebar = isset($PhoenixData['port_sidebar_position']) ? $PhoenixData['port_sidebar_position'] : 'no';
    $PHT_quantity = isset($PhoenixData['port_quantity']) ? $PhoenixData['port_quantity'] : 8;
    $PHT_port_layout = isset($PhoenixData['port_layout']) ? $PhoenixData['port_layout'] : '3-cols';
?>

<?php
    if (have_posts()) {
        while(have_posts()) {
            the_post();

            $PHT_page_subtitle = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_subtitle') : false;
            $PHT_page_crumbs   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_breadcrumbs') : false;
            $PHT_port_cat      = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_portfolio_cat') : false;
?>
            <div class="page-in"><div>
              <div class="container">
                <div class="row">

                  <div class="col-lg-6 pull-left">
                    <div class="page-in-name">
<?php
                        the_title();

                        if ($PHT_page_subtitle)
                            echo ": <span>". esc_html($PHT_page_subtitle) ."</span>";
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
                    <div class="col-lg-12">
                        <div id="filters-container-portfolio" class="cbp-l-filters-button"<?php if ($PHT_port_layout == 'full') echo ' style="display:table;margin:auto;"'; ?>>
<?php
                            if ($PHT_port_cat && $PHT_port_cat != 'none') {
                                $PHT_cats = get_terms(THEME_SLUG . '_portfolio_category', 'orderby=count&hide_empty=1&child_of=' . $PHT_port_cat);
                            } else {
                                $PHT_cats = get_terms(THEME_SLUG . '_portfolio_category', 'orderby=count&hide_empty=1');
                            }

                            $PHT_to_return = array();
                            $PHT_to_return[] = '<button data-filter="*" class="cbp-filter-item cbp-filter-item-active">'. __("All", 'grandway') .'<div class="cbp-filter-counter"></div></button>';

                            foreach ($PHT_cats as $cat) {
                              if (is_object($cat)) {
                                $term = get_term_by('id', $cat->term_id, THEME_SLUG . '_portfolio_category');
                                $PHT_to_return[] = '<button data-filter=".'. $term->slug .'" class="cbp-filter-item">'. $term->name .'<div class="cbp-filter-counter"></div></button>';
                              }
                            }

                            echo implode("\n", $PHT_to_return);
?>
                        </div>
                    </div>
                </div>
            </div>

            <div class="marg50 general-font-area<?php if ($PHT_port_layout != 'full') echo " container"; ?>">
                <div class="row">
                    <div class="col-lg-12">
<?php
                        the_content();
        }
        wp_reset_postdata();
    }
?>
                    </div><!-- col-any -->
                </div><!-- row -->
<?php
    $PHT_ajaxPaged = (get_query_var('paged')) ? get_query_var('paged') : 1;
    $PHT_query_args = array(
        'post_type' => THEME_SLUG . '_portfolio',
        'posts_per_page' => $PHT_quantity,
        'post_status' => 'publish',
        'paged' => $PHT_ajaxPaged,
  );

    if ($PHT_port_cat && $PHT_port_cat != 'none') {
        $PHT_query_args['tax_query'] = array(
            array(
                'taxonomy' => THEME_SLUG . '_portfolio_category',
                'field'    => 'term_id',
                'include_children' => true,
                'terms' => $PHT_port_cat
          )
      );
    }

    $PHT_query = new WP_Query($PHT_query_args);

    if ($PHT_query->have_posts($PHT_query->query_vars)) {

        echo '<script>'.THEME_TEAM.'["queryVars"] = \''. serialize($PHT_query->query_vars) .'\'; '.THEME_TEAM.'["currentPage"] = '. $PHT_ajaxPaged .';</script>';
?>
        <div class="phoenix-shortcode-portfolio-grid portfolio-grey">
            <div class="cbp-l-grid-projects" id="grid-container-portfolio">
                <ul>
<?php
        while($PHT_query->have_posts()) {
            $PHT_query->the_post();

            $ID = get_the_id();

            $the_cat = get_the_terms($ID , THEME_SLUG . '_portfolio_category');
            $categories = '';
            if (is_array($the_cat)) {
                foreach($the_cat as $cur_term) {
                    $categories .= $cur_term->slug . ' ';
                }
            }

            $PHT_thumb_params = array('width' => 800,'height' => 600, 'crop' => true);
            $PHT_title= get_the_title();
            $PHT_author = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_author') : false;

            if (has_post_thumbnail()) {
                $PHT_thumb = wp_get_attachment_image_src(get_post_thumbnail_id($ID), 'full', true);
                $PHT_thumb = $PHT_thumb[0];
            } else {
                $PHT_thumb = THEME_URI . "/assets/images/nopicture.png";
            }
?>
            <li class="cbp-item <?php echo sanitize_html_class($categories); ?>">
                <div class="cbp-item-wrapper">
                    <div class="portfolio-phoenixteam">
                      <div class="portfolio-image">
<?php                   if ($PHT_thumb)
                          echo '<img data-no-retina="1" src="'. bfi_thumb($PHT_thumb, $PHT_thumb_params) .'" alt="'. esc_attr($PHT_title) .'" />';
?>
                        <figcaption>
                            <p class="icon-links">
                                <a href="<?php echo esc_url(get_permalink()); ?>"><i class="icon-attachment"></i></a>
                                <a href="<?php echo esc_url($PHT_thumb); ?>" class="cbp-lightbox" data-title="<?php echo esc_attr($PHT_title); ?>"><i class="icon-magnifying-glass"></i></a>
                            </p>
                        </figcaption>
                    </div>
                    <h2><?php echo esc_html($PHT_title); ?></h2>
                    <p>by <?php echo esc_html($PHT_author); ?></p>
                    </div>
                </div>
            </li>
<?php
        }
                echo '</ul></div>
                <div class="col-lg-12">
                  <div class="button-center"><a href="#" class="btn-simple cbp-l-loadMore-button-link">Load Full Portfolio</a></div>
                </div>';
                echo '</div>';
    }
?>
            </div>

<?php get_footer(); ?>
