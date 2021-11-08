<?php
  get_header();
  global $PhoenixData;

  $PHT_gen_crumbs = isset($PhoenixData['breadcrumbs']) ? $PhoenixData['breadcrumbs'] : null;
?>
<div class="page-in">
  <div class="container">
    <div class="row">
      <div class="col-lg-6 pull-left">
        <div class="page-in-name">
<?php
          echo "404:";
          echo " <span>". __('Error', 'garndway') ."</span>\n";
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

<div class="container general-font-area marg75">
  <div class="col-lg-12">
    <div class="main_pad">
      <strong class="colored oops"><?php _e("Oops, 404 Error!", 'garndway'); ?></strong><br/><br/>
      <p><?php _e("The page you were looking for could not be found.", 'garndway'); ?></p>
    </div>
  </div>
</div>

<?php get_footer(); ?>
