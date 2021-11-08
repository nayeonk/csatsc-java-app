<?php get_header(); ?>

<?php
    global $PhoenixData;

    $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : null;
    $PHT_related_qty = isset($PhoenixData['port_related_quantity']) ? $PhoenixData['port_related_quantity'] : 6;
    $PHT_layout = isset($PhoenixData['port_single_layout']) ? $PhoenixData['port_single_layout'] : 'wide';
    $PHT_layout = esc_html($PHT_layout);
    $PHT_kses = array(
      'li' => array(),
      'a' => array('href' => array())
    );

    if ($PHT_layout == 'wide') {
        $PHT_layout_class = 'col-lg-12';
    } elseif ($PHT_layout == 'half') {
        $PHT_layout_class = 'col-lg-9';
    }

    $PHT_thisID = array();
?>

<?php
if (have_posts()) :
    while (have_posts()) :
        the_post();

        $PHT_post_subtitle = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_subtitle') : false;
        $PHT_post_crumbs   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_port_breadcrumbs') : false;
        $PHT_port_cat      = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_recent_works_cat') : false;
?>
        <div class="page-in">
          <div class="container">
            <div class="row">

              <div class="col-lg-6 pull-left">
                <div class="page-in-name">
<?php
                    echo esc_html(get_the_title());

                    if ($PHT_post_subtitle)
                        echo ": <span>". esc_html($PHT_post_subtitle) ."</span>";
?>
                </div>
              </div>
<?php
                if ($PHT_gen_crumbs && $PHT_post_crumbs === '-1') :
                    PhoenixTeam_Utils::breadcrumbs();
                elseif ($PHT_post_crumbs === '1') :
                    PhoenixTeam_Utils::breadcrumbs();
                else :
                    echo "<!-- Breadcrumbs turned off -->\n";
                endif;
?>
            </div>
          </div>
        </div>

<?php
        $PHT_ID = get_the_id();
        $PHT_thisID[] = $PHT_ID;

        $PHT_thumb = null;
        if (has_post_thumbnail()) {
            $PHT_thumb = wp_get_attachment_image_src( get_post_thumbnail_id($PHT_ID), 'full', true );
            $PHT_thumb = array('full_url' => $PHT_thumb[0]);
        }

        $PHT_Gallery = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_gallery', array('type' => 'image_advanced')) : false;

        if ($PHT_thumb)
            array_unshift($PHT_Gallery, $PHT_thumb);

        $PHT_Name = get_the_title();
        if ($PHT_Name)
            $PHT_Name = '<li>'. __('Name', 'grandway') .': '. $PHT_Name .'</li>';

        $PHT_Date = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_date') : false;
        if ($PHT_Date)
            $PHT_Date = '<li>'. __('Date', 'grandway') .': '. $PHT_Date .'</li>';

        $PHT_Category = get_the_terms($PHT_ID, THEME_SLUG . '_portfolio_category');
        if ($PHT_Category) {
            $PHT_Category = array_reverse($PHT_Category);
            $PHT_Category = $PHT_Category[0]->name;
            $PHT_Category = '<li>'. __('Category', 'grandway') .': '. $PHT_Category .'</li>';
        }

        $PHT_Description = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_description') : false;
        $PHT_Author = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_author') : false;
        $PHT_AuthorUrl = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_author_url') : false;
        $PHT_Client = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_client') : false;
        $PHT_ClientUrl = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_client_url') : false;

        if ($PHT_AuthorUrl && $PHT_Author) {
            $PHT_Author = '<li>'. __('Author', 'grandway') . ': <a href="'. esc_url($PHT_AuthorUrl) .'">'. $PHT_Author .'</a></li>';
        } elseif($PHT_Author) {
            $PHT_Author = '<li>'. __('Author', 'grandway') . ': '. $PHT_Author .'</li>';
        } else {
            $PHT_Author = null;
        }

        if ($PHT_ClientUrl && $PHT_Client) {
            $PHT_Client = '<li>'. __('Client', 'grandway') . ': <a href="'. esc_url($PHT_ClientUrl) .'">' . $PHT_Client .'</a></li>';
        } elseif ($PHT_Client) {
            $PHT_Client = '<li>'. __('Client', 'grandway') . ': ' . $PHT_Client .'</li>';
        } else {
            $PHT_Client = null;
        }

        $PHT_prev_post = get_previous_post();
        if ($PHT_prev_post)
            $PHT_prev_post = get_permalink($PHT_prev_post->ID);

        $PHT_next_post = get_next_post();
        if ($PHT_next_post)
            $PHT_next_post = get_permalink($PHT_next_post->ID);
?>

        <div id="<?php echo sanitize_html_class($PHT_ID); ?>" <?php post_class(array('container', 'general-font-area', 'marg50')); ?>>
            <div class="row">
                <div class="<?php echo sanitize_html_class($PHT_layout_class); ?>">
                  <div id="main">
                      <?php if (count($PHT_Gallery)) : ?>
                        <ul class="bxslider">
<?php
                            foreach ($PHT_Gallery as $item) {;
                              echo '<li><img data-no-retina="1" src="'. esc_url($item['full_url']) .'" alt=""></li>';
                            }
?>
                        </ul>
                      <?php endif; ?>
                  </div>
                </div>


<?php if ($PHT_layout == 'wide') : ?>

            <?php if ($PHT_prev_post) : ?>
                <div class="col-lg-4 col-xs-4 pull-left"><a href="<?php echo esc_url($PHT_prev_post); ?>" class="btn-item pull-left">&laquo; <?php _e('Prev', 'grandway'); ?></a></div>
            <?php endif; ?>
            <!-- <div class="col-lg-4 col-xs-4"><div class="item-heart"><i class="icon-heart"></i></div></div> -->
            <?php if ($PHT_next_post) : ?>
                <div class="col-lg-4 col-xs-4 pull-right"><a href="<?php echo esc_url($PHT_next_post); ?>" class="btn-item pull-right"><?php _e('Next', 'grandway'); ?> &raquo;</a></div>
            <?php endif; ?>

            </div>

                <div class="row marg25">
                    <div class="col-lg-3">
                        <ul class="portfolio-item">
                          <?php echo wp_kses($PHT_Name, $PHT_kses); ?>
                          <?php echo wp_kses($PHT_Date, $PHT_kses); ?>
                          <?php echo wp_kses($PHT_Category, $PHT_kses); ?>
                          <?php echo wp_kses($PHT_Author, $PHT_kses); ?>
                          <?php echo wp_kses($PHT_Client, $PHT_kses); ?>
                        </ul>
                    </div>
                    <div class="col-lg-9">
                        <ul class="portfolio-item">
                          <li><?php _e('Description', 'grandway') ?>:</li>
                        </ul>
                        <div class="portfolio-item-text">
                            <?php echo wp_kses_post($PHT_Description); ?>
                        </div>
                    </div>
                </div>

<?php elseif ($PHT_layout == 'half') : ?>

                <div class="col-lg-3">
                  <ul class="portfolio-item">
                    <?php echo wp_kses($PHT_Name, $PHT_kses); ?>
                    <?php echo wp_kses($PHT_Date, $PHT_kses); ?>
                    <?php echo wp_kses($PHT_Category, $PHT_kses); ?>
                    <?php echo wp_kses($PHT_Author, $PHT_kses); ?>
                    <?php echo wp_kses($PHT_Client, $PHT_kses); ?>
                  </ul>

                  <div class="row">
                    <?php if ($PHT_prev_post) : ?>
                        <div class="col-lg-4 col-xs-4 pull-left"><a href="<?php echo esc_url($PHT_prev_post); ?>" class="btn-item pull-left">&laquo; <?php _e('Prev', 'grandway'); ?></a></div>
                    <?php endif; ?>
                    <!-- <div class="col-lg-4 col-xs-4"><div class="item-heart"><i class="icon-heart"></i></div></div> -->
                    <?php if ($PHT_next_post) : ?>
                        <div class="col-lg-4 col-xs-4 pull-right"><a href="<?php echo esc_url($PHT_next_post); ?>" class="btn-item pull-right"><?php _e('Next', 'grandway'); ?> &raquo;</a></div>
                    <?php endif; ?>
                  </div>
                </div>

            </div>

            <div class="row marg25">
                <div class="col-lg-12">
                    <ul class="portfolio-item">
                        <li><?php _e('Description', 'grandway') ?>:</li>
                    </ul>
                    <div class="portfolio-item-text">
                        <?php echo wp_kses_post($PHT_Description); ?>
                    </div>
                </div>
            </div>



<?php endif; ?>

            <div class="marg75">
              <div class="row">
                <div class="col-lg-12">
                  <div class="promo-block">
                    <div class="promo-text"><?php _e('Recent Works', 'grandway'); ?></div>
                  </div>
                </div>
              </div>
            </div>

            <div class="general-font-area marg25">
<?php
    $PHT_ajaxPaged = (get_query_var('paged')) ? get_query_var('paged') : 1;
    $PHT_query_args = array(
        'post_type' => THEME_SLUG . '_portfolio',
        'posts_per_page' => $PHT_related_qty,
        'post_status' => 'publish',
        'paged' => $PHT_ajaxPaged,
        'post__not_in' => $PHT_thisID
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

    if ($PHT_query->have_posts()) {

        echo '<script>portSetts = {inlineError: "'.__("Error! Please refresh the page!", 'grandway').'", moreLoading: "'.__("Loading...", 'grandway').'", moreNoMore: "'.__("No More Works", 'grandway').'"}; '.THEME_TEAM.'["queryVars"] = \''. serialize($PHT_query->query_vars) .'\'; '.THEME_TEAM.'["currentPage"] = '. $PHT_ajaxPaged .';</script> ' . "\n";
?>
        <div class="phoenix-shortcode-portfolio-grid portfolio-grey">
            <div class="cbp-l-grid-projects" id="grid-container-portfolio">
                <ul>
<?php
        while($PHT_query->have_posts()) {
            $PHT_query->the_post();

            $PHT_ID = get_the_id();

            $PHT_the_cat = get_the_terms( $PHT_ID , THEME_SLUG . '_portfolio_category');
            $PHT_categories = '';
            if (is_array($PHT_the_cat)) {
                foreach($PHT_the_cat as $cur_term) {
                    $PHT_categories .= $cur_term->slug . ' ';
                }
            }

            $PHT_thumb_params = array('width' => 800,'height' => 600, 'crop' => true);
            $PHT_thumb = null;

            $PHT_title = esc_html( get_the_title() );
            $PHT_author = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_author') : false;
            $link = get_permalink();

            if (has_post_thumbnail()) {
                $PHT_thumb = wp_get_attachment_image_src( get_post_thumbnail_id($PHT_ID), 'full', true );
                $PHT_thumb = $PHT_thumb[0];
            } else {
                $PHT_thumb = THEME_URI . "/assets/images/nopicture.png";
            }
?>

            <li class="cbp-item <?php echo esc_attr($PHT_categories); ?>">
                <div class="cbp-item-wrapper">
                    <div class="portfolio-phoenixteam"><div class="portfolio-image"><?php if ($PHT_thumb) echo '<img data-no-retina="1" src="'. bfi_thumb( $PHT_thumb, $PHT_thumb_params ) .'" alt="'. esc_attr( $PHT_title ) .'" />'; ?>
                        <figcaption>
                            <p class="icon-links">
                                <a href="<?php echo esc_url(get_permalink()); ?>"><i class="icon-attachment"></i></a>
                                <a href="<?php echo esc_url($PHT_thumb); ?>" class="cbp-lightbox" data-title="<?php echo esc_attr( $PHT_title ); ?>"><i class="icon-magnifying-glass"></i></a>
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
                  <div class="button-center"><a href="#" class="btn-simple cbp-l-loadMore-button-link">'. __('Load Full Portfolio', 'grandway') .'</a></div>
                </div>';
    }
?>
                <!-- bxslider init -->
                <script type="text/javascript">
                    jQuery(document).ready(function(){
                        jQuery('.bxslider').bxSlider({
                            adaptiveHeight: true,
                            mode: 'fade',
                            slideMargin: 0,
                            pager: false,
                            controls: true
                        });
                    });
                </script>

            </div>
        </div>
</div>
    <?php endwhile; ?>

    <?php else: ?>

        <div class="container general-font-area marg50">
            <h1 style="display: block; text-align: center;"><?php _e('Sorry, nothing to display.', 'grandway'); ?></h1>
        </div>

    <?php endif; ?>

<?php get_footer(); ?>
