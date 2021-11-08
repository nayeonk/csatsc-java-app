<?php

/*
update_option( 'siteurl', 'http://68.181.97.192/wordpress' );
update_option( 'home', 'http://68.181.97.192' );
*/

	require_once (get_template_directory() . '/includes/core.php');
PhoenixTeam_Theme::initInstance();

function add_tabcontrol() {
	wp_enqueue_script(
		'tabcontrol',
		get_template_directory_uri() . '/assets/customjs/tabcontrol.js',
		array( 'jquery' )
	);
}

add_action( 'wp_enqueue_scripts', 'add_tabcontrol' );