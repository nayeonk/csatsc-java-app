<?php
global $PhoenixData;
$PHT_use_footer = isset($PhoenixData['use_footer']) ? $PhoenixData['use_footer'] : 1;
$PHT_use_footer = ($PHT_use_footer) ? 'footer-bottom-top-section-present' : null;
$PHT_copyright = isset($PhoenixData['copyright_text']) ? $PhoenixData['copyright_text'] : null;
if ($PHT_use_footer)
    $PHT_layout = isset($PhoenixData['footer_layout']) ? $PhoenixData['footer_layout'] : 4;
?>
<!-- footer -->
<?php /* echo PhoenixTeam_Utils::footer_twitter();*/ ?> 
<div class="footer general-font-area<?php echo ' ' . sanitize_html_class($PHT_use_footer); ?>">
    <?php if ($PHT_use_footer) : ?>
    <div class="footer-header">
      <div class="theme-banner"></div>
      <div class="highlight-banner"></div>
  </div>
  <div class="container">
      <div class="row">
        <?php if ($PHT_layout == 4) : ?>
        <div class="col-md-3 col-sm-6">
          <div class="widget_text footer-widget">
			<h4 class="widget-title">Follow Us</h4>
				<ul class="textwidget link-list">
               <li class="list-item list-item-link row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2017/10/twitter-1.png">
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text"><a href="https://twitter.com/SC_SummerCamps" target="_blank">Twitter</a></span>
                </li>
               <li class="list-item list-item-link row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2017/10/facebook-official-1.png"/>
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text"><a href="https://www.facebook.com/CSatSCsummercamps/" target="_blank">Facebook</a></span>
                </li>
               <li class="list-item list-item-link row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2017/10/instagram-1.png"/>
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text"><a href="https://www.instagram.com/sc_summercamps/" target="_blank">Instagram</a></span>
                </li>       
				</ul>
             <h4 class="widget-title">Quick Links</h4>
             <ul class="textwidget link-list">
                <li class="list-item list-item-link row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2015/10/link.png" alt="Link icon" />
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text"><a href="http://www.usc.edu/">University of Southern California</a></span>
                </li>
                <li class="list-item list-item-link row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2015/10/link.png" alt="Link icon" />
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text"><a href="http://cs.usc.edu/">Department of Computer Science</a></span>
                </li>
                <li class="list-item list-item-link row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2015/10/link.png" alt="Link icon" />
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text"><a href="http://viterbi.usc.edu/">Viterbi School of Engineering</a></span>
                </li>                                
                <li class="list-item list-item-link row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2015/10/link.png" alt="Link icon" />
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text"><a href="http://viterbi.usc.edu/k-12/">VAST</a></span>
                </li>                             
             </ul>
          </div>
        </div>

        <div class="col-md-offset-1 col-md-4 col-sm-6">
          <div class="widget_text footer-widget">
             <h4 class="widget-title">Contact Us</h4>
             <ul class="textwidget link-list">
                <li class="list-item list-item-person row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2015/10/person@2x.png" alt="Person icon" />
                   <!-- <div class="link-text">Professor Jeffrey Miller</div> -->
                   <span class="col-xs-10 link-text">Professor Jeffrey Miller</span>
               </li>
               <li class="list-item list-item-email row">
                   <img class="col-xs-2 link-image" src="/wp-content/uploads/2015/10/email@2x.png" alt="Email icon" />
                   <span class="col-xs-10 link-text">Email: <a href="mailto:cscamps@usc.edu">cscamps@usc.edu</a></span>
               </li>
 
           <li class="list-item list-item-location row">
               <div class="image-container col-xs-2">
                  <span class="vertical-align-helper"></span>
                  <img class="link-image" src="/wp-content/uploads/2015/10/location@2x.png" alt="Location icon" />
              </div>
              <span class="col-xs-10 link-text"><?php echo 'Salvatori Computer Science Center<br />941 Bloom Walk, SAL 342<br />Los Angeles, California 90089-0781'; ?></span>
          </li>
      </ul>
  </div>
</div>
<div class="col-md-4 footer-map-container">
  <div class="footer-widget">
   <iframe width="100%" class="footer-map" frameborder="0" style="border:0" src="https://www.google.com/maps/embed/v1/place?q=Salvatori+Computer+Science+Center&key=AIzaSyC2_ff79YHTcR0BSeSCijKJVOgKE-vJO1k" allowfullscreen></iframe>

</div>
</div>
            <!-- <div class="col-lg-3 col-md-3 col-sm-3">
<?php
                if ( function_exists('dynamic_sidebar') && is_active_sidebar('Footer 3') ) {
                    dynamic_sidebar('Footer 3');
                } else {
                    echo '<div class="widget_text footer-widget">
                        <h4 class="widget-title">Footer Sidebar 3</h4>
                        <div class="textwidget">' . __('Drop a widget on "Footer Sidebar 3" sidebar at Appearance > Widgets page.', 'grandway') . '</div>
                    </div>';                }
?>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-3">
<?php
                if ( function_exists('dynamic_sidebar') && is_active_sidebar('Footer 4') ) {
                    dynamic_sidebar('Footer 4');
                } else {
                    echo '<div class="widget_text footer-widget">
                        <h4 class="widget-title">Footer Sidebar 4</h4>
                        <div class="textwidget">' . __('Drop a widget on "Footer Sidebar 4" sidebar at Appearance > Widgets page.', 'grandway') . '</div>
                    </div>';                }
?>
</div> -->
<?php else : ?>
    <div class="col-lg-4 col-md-4 col-sm-4">
        <?php
        if ( function_exists('dynamic_sidebar') && is_active_sidebar('Footer 1') ) {
            dynamic_sidebar('Footer 1');
        } else {
            echo '<div class="widget_text footer-widget">
            <h4 class="widget-title">Footer Sidebar 1</h4>
            <div class="textwidget">' . __('Drop a widget on "Footer Sidebar 1" sidebar at Appearance > Widgets page.', 'grandway') . '</div>
            </div>';                }
            ?>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4">
            <?php
            if ( function_exists('dynamic_sidebar') && is_active_sidebar('Footer 2') ) {
                dynamic_sidebar('Footer 2');
            } else {
                echo '<div class="widget_text footer-widget">
                <h4 class="widget-title">Footer Sidebar 2</h4>
                <div class="textwidget">' . __('Drop a widget on "Footer Sidebar 2" sidebar at Appearance > Widgets page.', 'grandway') . '</div>
                </div>';                }
                ?>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4">
                <?php
                if ( function_exists('dynamic_sidebar') && is_active_sidebar('Footer 3') ) {
                    dynamic_sidebar('Footer 3');
                } else {
                    echo '<div class="widget_text footer-widget">
                    <h4 class="widget-title">Footer Sidebar 3</h4>
                    <div class="textwidget">' . __('Drop a widget on "Footer Sidebar 3" sidebar at Appearance > Widgets page.', 'grandway') . '</div>
                    </div>';                }
                    ?>
                </div>
            <?php endif; ?>
        </div>
    </div>
<?php endif; ?>
</div>
<div class="footer_bottom">
    <div class="container">
      <div class="footer-bottom">
          <!-- <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pull-left">
            <div class="copyright">
              <?php if ($PHT_copyright) echo wp_kses_post($PHT_copyright); ?>
            </div>
        </div> -->
        <!-- <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pull-right"> -->
        <div class="row text-center">
            <div class="foot_menu" style="float: none; display: block;">
                <?php PhoenixTeam_Utils::create_nav('footer-menu', 1, 'foot_menu'); ?>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<!-- /wrapper -->
<?php wp_footer(); ?>
</body>
</html>