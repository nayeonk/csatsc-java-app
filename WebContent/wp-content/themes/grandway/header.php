<!DOCTYPE html>

<html <?php language_attributes(); ?> class="no-js">

    <head>
	<title>CS@SC Summer Camps <?php echo the_title(); ?></title>
        <meta charset="<?php bloginfo('charset'); ?>">

	<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
 
  ga('create', 'UA-62544517-1', 'auto');
  ga('send', 'pageview');
 
</script>

        <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->

	<!-- this is duplicated by wp_head() -->
        <!-- <title><?php /*wp_title();*/ /*duplicated by wp_head */ ?></title> -->

        <meta content="width=device-width, initial-scale=1, maximum-scale=1" name="viewport"/>

        <meta content="<?php bloginfo('description'); ?>" name="description">
        <?php if (!function_exists('has_site_icon') || !has_site_icon()) echo PhoenixTeam_Utils::favicons(); ?>

        <?php wp_head(); ?>

	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic&#124;Open+Sans+Condensed:300,300italic,700' rel='stylesheet' type='text/css'>

	<style>
		body { background-color: #990000;}

	</style>
    </head>

<div style="float:right">
<a href="http://www.usc.edu"><img size="100px" src="http://identity.usc.edu/files/2012/01/gateway-usc-shield-name.png"/></a></div>
<div style="float:right; padding-right:20px; padding-top:3px;">
<a href="https://www.facebook.com/CSatSCsummercamps" target="_blank"><img size="100px" src="https://summercamp.usc.edu/wp-content/uploads/2017/10/facebook-small.png"/></a>
<a href="http://instagram.com/sc_summercamps" target="_blank"><img size="100px" src="https://summercamp.usc.edu/wp-content/uploads/2017/10/instagram-small.png"/></a>
<a href="https://twitter.com/SC_SummerCamps" target="_blank"><img size="100px" src="https://summercamp.usc.edu/wp-content/uploads/2017/10/twitter-small.png"></a>
</div>
    <body <?php body_class(); ?>>


    <div class="wrapper<?php echo PhoenixTeam_Utils::template_layout(); ?>">

        <header>

            <!-- <div class="top_line">

                <div class="container">

                    <div class="row">

                        <div class="col-lg-6 col-md-6 col-xs-12 pull-left"><?php echo PhoenixTeam_Utils::show_rss_feed(); ?></div>

                        <div class="col-lg-6 col-md-6 col-xs-12 pull-right">

                            <ul class="social-links">

                                <?php echo PhoenixTeam_Utils::show_top_socials(); ?>

                            </ul>

                        </div>

                    </div>

                </div>

            </div> -->

        </header>

        <div class="page_head">

            <div id="nav-container" class="nav-container" style="height: auto;">

                <nav role="navigation">

                    <div class="container">

                        <div class="row">

                            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6 pull-left">

                                <div class="logo">

                                    <!--<a href="/">--><?php echo PhoenixTeam_Utils::show_logo(); ?><!--</a>-->

                                </div>

                            </div>

                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-6 pull-right">

                                <div class="menu phoenixteam-menu-wrapper">

                                    <?php if (PhoenixTeam_Utils::dep_exists('megamenu')) : ?>

                                        <?php PhoenixTeam_Utils::create_nav('header-menu'); ?>

					
                                    <?php else : ?>

                                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"></button>
                                        <div class="navbar-collapse collapse">

                                            <?php PhoenixTeam_Utils::create_nav('header-menu', 3, 'menu', null, new PhoenixTeam_Navmenu_Walker()); ?>

                                    <?php endif; ?>
					
                                        </div>

                                </div>

                            </div>

                        </div>

                    </div>

                </nav>

            </div>

        </div>