<?php

new PhoenixTeam_Theme_Internals();

class PhoenixTeam_Theme_Internals {

    public function __construct ()
    {
        global $PhoenixData;
        $admin_bar = isset($PhoenixData['show_adminbar']) ? $PhoenixData['show_adminbar'] : true;

        // Add title tag settings
        add_filter('wp_title', array($this, 'wp_title'), 10, 2);

        // Add '...' button instead of [...] for Excerpts
        add_filter('excerpt_more', array($this, 'setCustomExcerpt'));

        // Add slug to body class (Starkers build)
        add_filter('body_class', array($this, 'add_slug_to_body_class'));

        add_filter('get_search_form', array($this, 'filter_search_form'));

        add_filter('wp_list_categories', array($this, 'add_span_cat_count'));

        add_filter('get_avatar', array($this, 'change_avatar_css_class'));

        // Set content type "text/html"
        add_filter('wp_mail_content_type', array($this, 'set_html_content_type'));

        // Enable Threaded Comments
        add_action('get_header', array($this, 'enable_threaded_comments'));

        // Add Subtitles to pages & posts
        add_action('edit_form_after_title', array($this, 'add_subtitle_field'));

        // Add thumbnails to list of posts in admin area
        add_filter('manage_posts_columns', array($this, 'add_thumb_column'));
        add_action('manage_posts_custom_column', array($this, 'add_thumb_value'), 10, 2);

        // Add contact form handler
        add_action('wp_ajax_' . THEME_SLUG . '_contact_form_ajax_handler', array($this, 'contact_form_ajax_handler'));
        add_action('wp_ajax_nopriv_' . THEME_SLUG . '_contact_form_ajax_handler', array($this, 'contact_form_ajax_handler'));

        // Add portfolio ajax loaders
        add_action('wp_ajax_' . THEME_SLUG . '_get_more_portfolio', array($this, 'more_portfolio'));
        add_action('wp_ajax_nopriv_' . THEME_SLUG . '_get_more_portfolio', array($this, 'more_portfolio'));
        add_action('wp_ajax_' . THEME_SLUG . '_get_inline_portfolio', array($this, 'inline_portfolio'));
        add_action('wp_ajax_nopriv_' . THEME_SLUG . '_get_inline_portfolio', array($this, 'inline_portfolio'));

        add_action('admin_bar_menu', array($this, 'adminbar_menu'), 1000);


        // Remove Admin bar
        if (!$admin_bar) {
            show_admin_bar( false );
        }
    }


    // Custom 'More' link to Post
    public function setCustomExcerpt ($more)
    {
        global $post;
        return '...';
    }


    // Add page slug to body class, love this - Credit: Starkers Wordpress Theme
    public function add_slug_to_body_class($classes)
    {
        global $post;
        if (is_home()) {
            $key = array_search('blog', $classes);
            if ($key > -1) {
                unset($classes[$key]);
            }
        } elseif (is_page()) {
            $classes[] = sanitize_html_class($post->post_name);
        } elseif (is_singular()) {
            $classes[] = sanitize_html_class($post->post_name);
        }

        return $classes;
    }


    public function filter_search_form ($form)
    {
        $form = null;
        $form .= '<form role="search" method="get" id="searchform" class="searchform" action="' . esc_url(home_url( '/' )) . '" >';
        $form .= '<input id="s-input" name="s" type="text" placeholder="'. __("Search...", 'grandway') .'" value="'. esc_attr(get_search_query()) .'" />';
        $form .= '</form>';

        return $form;
    }


    public function change_avatar_css_class ($class)
    {
        $class = str_replace("class='avatar", "class='avatar img_comm", $class) ;
        return $class;
    }


    public function enable_threaded_comments ()
    {
        global $PhoenixData;
        $page_comments = isset($PhoenixData['page_display_comments']) ?$PhoenixData['page_display_comments'] : 0;

        if (!is_admin()) {
            if (is_singular() && comments_open() && (get_option('thread_comments') == 1)) {
                wp_enqueue_script('comment-reply');
            }
            if ($page_comments && is_page() && comments_open() && (get_option('thread_comments') == 1)) {
                wp_enqueue_script('comment-reply');
            }
        }
    }


    public function add_subtitle_field ()
    {
        $screen = get_current_screen();

        if (
            $screen->post_type == 'page'
            || $screen->post_type == 'post'
            || $screen->post_type == THEME_SLUG . '_portfolio'
            || $screen->post_type == 'product'
           )
        {
            $sub = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_subtitle') : false;
            $margin_top = ($screen->post_type == THEME_SLUG . '_portfolio') ? '11' : '5';

            // echo script, related to the inputfield.
            echo "<script>
                jQuery(function($) {
                    'use strict';\n
                    var ". THEME_SLUG . "AwesomeSubtitle = $('<input />', {
                        type: 'text',
                        id: '". THEME_SLUG . "_subtitle_hooked "."',
                        name: '". THEME_SLUG . "_subtitle_hooked "."',
                        'class': 'widefat',
                        value: '". esc_js($sub) ."',
                        placeholder: '". __("Subtitle", 'grandway') ."',
                        style: 'margin-top: ". esc_js($margin_top) ."px; line-height: 19px; margin-bottom: 0;',
                        tabindex: '1'
                    }).insertAfter($('#titlediv .inside'));\n" .

                    "setTimeout(function() {\n
                        var vcSwitch = $('.composer-switch');\n
                        if (vcSwitch.length == 1) {
                            ". THEME_SLUG . "AwesomeSubtitle.css('margin-bottom', '20px');
                        }
                    }, 1000);\n" .

                    "var hidden = $('[name = " . THEME_SLUG . "_subtitle]');\n" .
                    THEME_SLUG ."AwesomeSubtitle.change(function() {
                        hidden.val(". THEME_SLUG ."AwesomeSubtitle.val());
                    });
                });" .
            "</script>";
        }
        return false;
    }

    public function add_thumb_column ($cols)
    {
        $cols['thumbnail'] = __('Thumbnail', 'grandway');
        return $cols;
    }


    public function add_thumb_value ($column_name, $post_id)
    {
        $width = (int) 60;
        $height = (int) 60;

        if ( 'thumbnail' == $column_name ) {
            $thumbnail_id = get_post_meta( $post_id, '_thumbnail_id', true );

            $children = array(
                'post_parent' => $post_id,
                'post_type' => 'attachment',
                'post_mime_type' => 'image'
            );

            $attachments = get_children($children);

            if ($thumbnail_id) {
                $thumb = wp_get_attachment_image( $thumbnail_id, array($width, $height), true );
            } elseif ($attachments) {
                foreach ( $attachments as $attachment_id => $attachment ) {
                    $thumb = wp_get_attachment_image( $attachment_id, array($width, $height), true );
                }
            }
            if ( isset($thumb) && $thumb ) {
                echo wp_kses(
                    $thumb,
                    array('img' => array(
                            'src' => array(),
                            'alt' => array()
                        )
                    ),
                    array('http', 'https')
                );
            } else {
                _e('None', 'grandway');
            }
        }
    }


    public function contact_form_ajax_handler ()
    {
        if (!isset($_POST['security']) || !wp_verify_nonce($_POST['security'], THEME_SLUG . '-cf-security')) {
            die;
        }

        if (isset($_POST['submitted'])) {

            global $PhoenixData;
            $mailTo = isset($PhoenixData['contact_mail']) ? $PhoenixData['contact_mail'] : array(get_option('admin_email'));

            header('Content-Type: application/json');

            $return = $allFields = array();

            if (trim($_POST['name']) === '') {
                $nameError = true;
                $hasError = true;
            } else {
                $name = trim($_POST['name']);
                $name = sanitize_text_field( $name );
                $allFields['Name'] = $name;
            }

            if (trim($_POST['email']) === '')  {
                $emailError = true;
                $hasError = true;
            } elseif (!is_email(trim($_POST['email']))) {
                $emailError = true;
                $hasError = true;
            } else {
                $email = trim($_POST['email']);
                $allFields['Email'] = $email;
            }

            if (trim($_POST['subject']) === '') {
                $subjectError = true;
                $hasError = true;
            } else {
                $subject = trim($_POST['subject']);
                $subject = sanitize_text_field( $subject );
                $allFields['Subject'] = $subject;
            }

            if (trim($_POST['message']) === '') {
                $messageError = true;
                $hasError = true;
            } else {
                $message = stripslashes(wp_kses_post(trim($_POST['message'])));
                $message = balanceTags($message);
                $allFields['Message'] = $message;
            }

            if (!isset($hasError)) {
                $body = $this->body($allFields);
                $headers = 'From: '.$name. ' <'. $email .'>' . "\r\n" . 'Reply-To: ' . $email;

                $emailSent = wp_mail($mailTo, $subject, $body, $headers);

                if ($emailSent) {
                    $return["emailSent"] = true;
                    echo json_encode($return);
                    die;
                } else {
                    $return["emailSent"] = false;
                    echo json_encode($return);
                    die;
                }
            } else {
                if (isset($nameError)) {$return["nameError"] = "nameError";}
                if (isset($emailError)) {$return["emailError"] = "emailError";}
                if (isset($subjectError)) {$return["subjectError"] = "subjectError";}
                if (isset($messageError)) {$return["messageError"] = "messageError";}

                echo esc_js(json_encode($return));
                die;
            }
        }

        die;
    }


    private function body ($fields)
    {
        $url = get_site_url();
        $body = "<h3 style=\"color:#0066CC;border-bottom:1px solid #0066CC;\">". __("Details", 'grandway') ."</h3>\n";

        foreach ($fields as $key => $val) {
            if (isset($fields[$key])) {

                if ($fields[$key] != THEME_SLUG .'-cf-security') {
                    $field = $key;
                    $body .= "<strong>". esc_html($field) .": </strong>". esc_html($val) ."<br />";
                }

            }
        }

        $body .= "<br />";
        $body .= "<small>". __("This message was sent via contact form from", 'grandway') ." <a href=\"". esc_url($url) ."\">". esc_html($url) ."</a></small>";

        return $body;
    }


    function set_html_content_type ()
    {
        return 'text/html';
    }


    public function more_portfolio ()
    {
        $args = unserialize(stripslashes($_POST['query']));
        $args['paged'] = $_POST['page'] + 1; // next page

        $query = new WP_Query($args);

        while($query->have_posts()) {
            $query->the_post();

            $ID = get_the_id();

            $the_cat = get_the_terms($ID , THEME_SLUG . '_portfolio_category');
            $categories = '';
            if (is_array($the_cat)) {
                foreach($the_cat as $cur_term) {
                    $categories .= $cur_term->slug . ' ';
                }
            }

            $thumb_params = array('width' => 800,'height' => 600, 'crop' => true);
            $thumb = null;

            $title = get_the_title();
            $author = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_author') : false;
            $link = get_permalink();

            if (has_post_thumbnail()) {
                $thumb = wp_get_attachment_image_src( get_post_thumbnail_id($ID), 'full', true );
                $thumb = $thumb[0];
            } else {
                $thumb = THEME_URI . "/assets/images/nopicture.png";
            }
?>
            <li class="cbp-item <?php echo esc_attr($categories); ?>">
                <div class="cbp-item-wrapper">
                    <div class="portfolio-phoenixteam"><div class="portfolio-image"><?php if ($thumb) echo '<img src="'. bfi_thumb( $thumb, $thumb_params ) .'" alt="'. esc_attr($title) .'" />'; ?>
                        <figcaption>
                            <p class="icon-links">
                                <a href="<?php echo esc_url(get_permalink()); ?>"><i class="icon-attachment"></i></a>
                                <a href="<?php echo esc_url($thumb); ?>" class="cbp-lightbox" data-title="<?php echo esc_attr($title); ?>"><i class="icon-magnifying-glass"></i></a>
                            </p>
                        </figcaption>
                    </div>
                    <h2><?php echo esc_html($title); ?></h2>
                    <p><?php _e('by', 'grandway'); ?> <?php echo esc_html($author); ?></p>
                    </div>
                </div>
            </li>
<?php
        }
        wp_reset_postdata();
        die;
    }


    public function inline_portfolio ()
    {
        check_ajax_referer(THEME_SLUG . "-port-security", 'security');

        $id = parse_url($_GET['url']);
        $args = "post_type=".THEME_SLUG."_portfolio&{$id['query']}&showposts=1";

        $query = new WP_Query($args);

        while($query->have_posts()) {
            $query->the_post();

            $ID = get_the_id();

            $thumb_params = array('width' => 800,'height' => 600);
            $thumb = null;

            $title = get_the_title();
            $author = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_author') : false;
            $description = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_description') : false;
            $link = get_permalink();

            if (has_post_thumbnail()) {
                $thumb = wp_get_attachment_image_src( get_post_thumbnail_id($ID), 'full', true );
                $thumb = $thumb[0];
            } else {
                $thumb = THEME_URI . "/assets/images/nopicture.png";
            }
?>
            <div class="cbp-l-inline">
                <div class="cbp-l-inline-left">
                    <?php if ($thumb) echo '<img src="'. esc_url(bfi_thumb($thumb, $thumb_params)) .'" alt="'. esc_attr( $title ) .'" class="cbp-l-project-img" />'; ?>
                </div>
                <div class="cbp-l-inline-right">
                    <div class="cbp-l-inline-title"><?php echo esc_html($title); ?></div>
                    <div class="cbp-l-inline-subtitle"><?php if ($author) { echo __('by', 'grandway') ." ". esc_html( $author );} ?></div>
                    <div class="cbp-l-inline-desc"><?php echo wp_kses_post( $description ); ?></div>
                    <a href="<?php echo esc_url($link); ?>" target="_blank" class="btn-simple"><?php _e('View Project', 'grandway'); ?></a>
                </div>
            </div>
<?php
        }
        wp_reset_postdata();
        die;
    }

    public function adminbar_menu ()
    {
        if (!is_super_admin() || !is_admin_bar_showing())
            return;

        global $wp_admin_bar;

        $wp_admin_bar->add_menu(
            array(
                'id' => THEME_SLUG . '_support',
                'meta' => array('title' => __('Support', 'grandway'), 'target' => '_blank'),
                'title' => THEME_NAME . " ". __('Support', 'grandway'),
                'href' => 'http://themeforest.net/user/' . THEME_TEAM
            )
        );
    }


    public function add_span_cat_count ($links)
    {
        $links = str_replace('</a> (', '</a> <span class="oi_cat_count">', $links);
        $links = str_replace(')', '</span>', $links);

        return $links;
    }


    public function wp_title ($title, $sep)
    {
        global $paged, $page;

        $title = str_replace('&raquo;', '' , $title);

        if ( is_feed() )
            return $title;

        // Add the site name.
        $title = get_bloginfo( 'name' ) . ' : ' . $title;

        // Add the site description for the home/front page.
        $site_description = get_bloginfo( 'description', 'display' );
        if ( $site_description && ( is_home() || is_front_page() ) )
            $title = $title .' : '. $site_description;

        // Add a page number if necessary.
        if ( $paged >= 2 || $page >= 2 )
            $title = $title .' : ' . sprintf( __('Page %s', 'grandway'), max( $paged, $page ) );

        return $title;
    }


}
