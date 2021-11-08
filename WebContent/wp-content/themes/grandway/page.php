<?php get_header(); ?>



<?php

  global $PhoenixData;



  $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : null;

  $PHT_sidebar_area = isset($PhoenixData['page_sidebar_widgets_area']) ? $PhoenixData['page_sidebar_widgets_area'] : 'blog-sidebar';

  $PHT_sidebar_pos = isset($PhoenixData['page_sidebar_position']) ? $PhoenixData['page_sidebar_position'] : 'no';

  $PHT_comments = isset($PhoenixData['page_display_comments']) ? $PhoenixData['page_display_comments'] : null;

?>



<?php

  if (have_posts()) {

    while(have_posts()) {

      the_post();



      $PHT_page_subtitle = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_subtitle') : "/wp-content/uploads/2015/10/children-learning.png"; /* changed to relative from http://domain/wordpress/wp-content/... */

      $PHT_page_crumbs   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_breadcrumbs') : false;

      $PHT_page_layout   = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_layout') : false;

      $PHT_page_area     = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_page_widgets_area') : false;



      if ($PHT_page_layout && $PHT_page_layout != $PHT_sidebar_pos)

        $PHT_sidebar_pos = $PHT_page_layout;



      if ($PHT_page_area && $PHT_page_area != $PHT_sidebar_area)

        $PHT_sidebar_area = $PHT_page_area;

?>

            <div class="page-in no-bottom-border"><div>



        <div class="relative-container main-background-image" style="background-image: url(

		<?php if($PHT_page_subtitle) { 

			 echo $PHT_page_subtitle; 

		      } 

			/* if featured image is set, use featured image as header image */

			else if ( has_post_thumbnail() ) {

				$url = wp_get_attachment_url( get_post_thumbnail_id($post->ID) );

				echo $url;

			} 



			else {

				echo site_url();

				echo '/wp-content/uploads/2015/10/miller-yortsos.png'; 

		     } ?>

		); background-size: cover; background-position: center;">

     		<div class="absolute-container">

          		<div class="absolute-title col-lg-4 pull-left text-center page-in-name">

               			<h1><?php the_title(); ?></h1>

          		</div>

     		</div>

	</div>

        <!-- <div class="container">

          <div class="row">



            <div class="col-lg-6 pull-left">

              <div class="page-in-name">

<?php

                the_title();



                if ($PHT_page_subtitle)

                  echo ': <span>'. esc_html( $PHT_page_subtitle ) .'</span>';

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

        </div> -->

            </div>

    </div>



      <section id="page-<?php the_ID(); ?>" <?php post_class('marg50 container general-font-area phoenixteam-sidebar-'. $PHT_sidebar_pos); ?>>

        <div class="row">

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

                        <?php dynamic_sidebar($PHT_sidebar_area); ?>

                    </div><!-- sidebar end-->



                    <div class="col-lg-9">

<?php

                }



        the_content();



?>



<div class="spacer">&nbsp;</div>



<?php



        if ($PHT_sidebar_pos == 'right') : ?>

            </div>

            <!-- sidebar -->

            <div class="col-lg-3">

              <?php dynamic_sidebar($PHT_sidebar_area); ?>

            </div><!-- sidebar end-->

          </div>

        <?php elseif ($PHT_sidebar_pos == 'left') : ?>

          </div>

        <?php endif; ?>



        <?php if ($PHT_comments) : ?>

          <div class="cl-blog-line"></div>

          <!-- comments -->

          <div class="container">

            <?php comments_template('', 'true'); ?>

          </div>

          <!-- /comments -->

        <?php endif; ?>

      </div>

      </section>

<?php

    }

  }

?>



<?php get_footer(); ?>