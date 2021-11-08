<?php

abstract class PhoenixTeam_Utils {

    public static $fa_social_icons = array("fa-android" => "android", "fa-apple" => "apple", "fa-behance" => "behance", "fa-bitbucket" => "bitbucket", "fa-bitcoin" => "bitcoin", "fa-btc" => "btc", "fa-codepen" => "codepen", "fa-css3" => "css3", "fa-delicious" => "delicious", "fa-deviantart" => "deviantart", "fa-digg" => "digg", "fa-dribbble" => "dribbble", "fa-dropbox" => "dropbox", "fa-drupal" => "drupal", "fa-empire" => "empire", "fa-facebook" => "facebook", "fa-flickr" => "flickr", "fa-foursquare" => "foursquare", "fa-git" => "git", "fa-github" => "github", "fa-gittip" => "gittip", "fa-google" => "google", "fa-html5" => "html5", "fa-instagram" => "instagram", "fa-joomla" => "joomla", "fa-jsfiddle" => "jsfiddle", "fa-linkedin" => "linkedin", "fa-linux" => "linux", "fa-maxcdn" => "maxcdn", "fa-openid" => "openid", "fa-pagelines" => "pagelines", "fa-pied-piper" => "pied", "fa-pinterest" => "pinterest", "fa-qq" => "qq", "fa-rebel" => "rebel", "fa-reddit" => "reddit", "fa-renren" => "renren", "fa-skype" => "skype", "fa-slack" => "slack", "fa-soundcloud" => "soundcloud", "fa-spotify" => "spotify", "fa-steam" => "steam", "fa-stumbleupon" => "stumbleupon", "fa-trello" => "trello", "fa-tumblr" => "tumblr", "fa-twitter" => "twitter", "fa-vine" => "vine", "fa-vk" => "vk", "fa-wechat" => "wechat", "fa-weibo" => "weibo", "fa-weixin" => "weixin", "fa-windows" => "windows", "fa-wordpress" => "wordpress", "fa-xing" => "xing", "fa-yahoo" => "yahoo", "fa-youtube" => "youtube", "fa-vimeo-square" => "vimeo", "fa-google-plus" => array("google +", "google plus", "googleplus"), "fa-hacker-news" => array("hackernews", "hacker news"), "fa-stack-exchange" => array("stackexchange", "stack exchange"), "fa-stack-overflow" => array("stackoverflow", "stack overflow")
    );


    public static function javascript_globals ()
    {
        return array(
            'THEME_SLUG' => THEME_SLUG,
            'ajaxUrl' => site_url() . '/wp-admin/admin-ajax.php',
            'nonce' => wp_create_nonce(THEME_SLUG . "-port-security")
        );
    }


    public static function create_nav ($location=null, $depth=3, $class='menu', $id=null, $walker=null)
    {
      wp_nav_menu(
        array(
          'theme_location'  => $location,
          'menu'            => '',
          'container_class' => '',
          'container_id'    => '',
          'menu_class'      => $class,
          'menu_id'         => $id,
          'echo'            => true,
          'fallback_cb'     => false,
          'before'          => '',
          'after'           => '',
          'link_before'     => '',
          'link_after'      => '',
          'items_wrap'      => '<ul>%3$s</ul>',
          'depth'           => $depth,
          'walker'          => $walker
        )
      );
    }


    public static function breadcrumbs ()
    {
        global $post, $pagename;

        /* ================ Settings ================ */
        $delimiter = ' / '; // separator
        $sign = null; // text before breadcrumbs
        $home = __('Home', 'grandway'); // homepage name
        $showCurrent = 1; // 1 - show current page, 0 - don't show
        $before = null; // before crumb tag
        $after = null; // after crumb tag
        /* ============== Settings END ============== */

        $homeLink = home_url();
        $get_post_type = get_post_type();

        // WooCommerce
        $woo = PhoenixTeam_Utils::dep_exists('woocommerce');

        if ($woo) {
            if (function_exists('is_shop')) {
                $woo_shop = is_shop();
                ob_start();
                woocommerce_page_title();
                $woo_title = ob_get_clean();
            }
        }

            echo '<div class="col-lg-6 pull-right"><div class="page-in-bread">';
            echo wp_kses_post($sign) . '<a href="'. esc_url($homeLink) .'" title="'. __('Home Page', 'grandway') .'">'. esc_html($home) .'</a> ' . esc_html($delimiter) . ' ';

            $posts_page = self::check_posts_page();
            if ($posts_page && $pagename == $posts_page->post_name) {
                if (get_query_var('paged')) {
                    $this_permalink = get_permalink($posts_page->ID);
                    echo esc_html($before) . '<a href="'. esc_url($this_permalink) .'" title="'. esc_attr($posts_page->post_title) .'">'. esc_html( $posts_page->post_title ) .'</a>' . esc_html($after);
                } else {
                    echo esc_html($before) . esc_html($posts_page->post_title) . esc_html($after);
                }
            }

            if (is_category()) {
                $thisCat = get_category(get_query_var('cat'), false);
                if ($thisCat->parent != 0) echo '' . get_category_parents($thisCat->parent, TRUE, ' ' . esc_html($delimiter) . ' ') . '';
                echo esc_html($before) . single_cat_title('', false) . esc_html($after);

            } elseif (is_search()) {
                echo esc_html($before) . __('Search for: ', 'grandway') . ' "' . get_search_query() . '"' . esc_html($after);

            } elseif (is_day()) {
                echo '<a href="' . get_year_link(get_the_time('Y')) . '">' . get_the_time('Y') . '</a> ' . esc_html($delimiter) . ' ';
                echo '<a href="' . get_month_link(get_the_time('Y'), get_the_time('m')) . '">' . get_the_time('F') . '</a> ' . esc_html($delimiter) . ' ';
                echo esc_html($before) . get_the_time('d') . esc_html($after);

            } elseif (is_month()) {
                echo '<a href="' . get_year_link(get_the_time('Y')) . '">' . get_the_time('Y') . '</a> ' . esc_html($delimiter) . ' ';
                echo esc_html($before) . get_the_time('F') . esc_html($after);

            } elseif (is_year()) {
                echo esc_html($before) . get_the_time('Y') . esc_html($after);

            } elseif (is_single() && !is_attachment()) {
                if ($get_post_type != 'post') {
                    $post_type = get_post_type_object($get_post_type);
                    $slug = $post_type->rewrite;
                    echo '<a href="' . $homeLink . '/' . $slug['slug'] . '/" onclick="jQuery(function($){event.preventDefault(); window.history.back();});">' . $post_type->labels->name . '</a>';
                    if ($showCurrent == 1) echo ' ' . esc_html($delimiter) . ' ' . esc_html($before) . esc_html(get_the_title()) . esc_html($after) . "\n";
                } else {
                    $cat = get_the_category(); $cat = $cat[0];
                    $cats = get_category_parents($cat, TRUE, ' ' . esc_html($delimiter) . ' ');
                    if ($showCurrent == 0) $cats = preg_replace("#^(.+)\sesc_html($delimiter)\s$#", "$1", $cats);
                    echo wp_kses_post($cats);
                    if ($showCurrent == 1) echo esc_html($before) . esc_html(get_the_title()) . esc_html($after);
                }

            // WooCommerce
            } elseif ($woo && $woo_shop) {
                echo esc_html($before) . esc_html($woo_title) . esc_html($after);

            // Othec CPTs
            } elseif (!is_single() && !is_page() && $get_post_type != 'post' && !is_404()) {
                $post_type = get_post_type_object($get_post_type);
                echo esc_html($before) . esc_html($post_type->labels->singular_name) . "/" . esc_html($after);

            } elseif (is_attachment()) {
                $parent = get_post($post->post_parent);
                $cat = get_the_category($parent->ID);

                if ($cat) {
                    $cat = $cat[0];
                    echo '' . get_category_parents($cat, TRUE, ' ' . esc_html($delimiter) . ' ') . '';
                }

                echo '<a href="' . esc_url(get_permalink($parent)) . '">' . esc_html($parent->post_title) . '</a>';

                if ($showCurrent == 1)
                    echo ' ' . esc_html($delimiter) . ' ' . esc_html($before) . esc_html(get_the_title()) . esc_html($after);

            } elseif ( is_page() && !$post->post_parent ) {
                if ( get_query_var('paged') ) {
                    if ($showCurrent == 1) echo esc_html($before) . '<a href="' . esc_url(get_permalink($post->ID)) . '">' . esc_html( get_the_title($post->ID) ) . '</a>' . esc_html($after);
                } else {
                    if ($showCurrent == 1) echo esc_html($before) . esc_html( get_the_title() ) . esc_html($after);
                }

            } elseif ( is_page() && $post->post_parent ) {
                $parent_id  = $post->post_parent;
                $breadcrumbs = array();

                while ($parent_id) {
                    $page = get_page($parent_id);
                    $breadcrumbs[] = '<a href="' . esc_url(get_permalink($page->ID)) . '">' . esc_html( get_the_title($page->ID) ) . '</a>';
                    $parent_id  = $page->post_parent;
                }

                $breadcrumbs = array_reverse($breadcrumbs);
                for ($i = 0; $i < count($breadcrumbs); $i++) {
                    echo wp_kses_post($breadcrumbs[$i]);
                    if ($i != count($breadcrumbs)-1) echo ' ' . esc_html($delimiter) . ' ';
                }
                if ($showCurrent == 1) echo esc_html($delimiter) . esc_html($before) . esc_html( get_the_title() ) . esc_html($after);

            } elseif ( is_tag() ) {
                echo esc_html($before) . single_tag_title('', false) . esc_html($after);

            } elseif ( is_author() ) {
                global $author;
                $userdata = get_userdata($author);
                echo esc_html($before) . ' ' . $userdata->display_name . esc_html($after);

            } elseif ( is_404() ) {
                echo esc_html($before) . __('404 page', 'grandway') . esc_html($after);
            }

            if ( get_query_var('paged') ) {
                if ( is_category() || is_day() || is_month() || is_year() || is_search() || is_tag() || is_author() )
                    echo esc_html($delimiter) . __('Page ' , 'grandway') . get_query_var('paged');
            }

        echo '</div></div>';

    }


    // Custom Comments Callback
    public static function comments_callback ($comment, $args, $depth)
    {
        $GLOBALS['comment'] = $comment;

        $tag = 'div';
        $add_below = 'comment';
        $classes = array();
        $comment_parent_class = empty( $args['has_children'] ) ? null : 'parent';
        $divide_line = empty( $args['has_children'] ) ? '<div class="cl-blog-line-com"></div>' : null;
        if ($comment_parent_class) $classes[] = $comment_parent_class;
        $classes[] = 'marg25';
?>
        <div <?php comment_class($classes); ?> id="comment-<?php comment_ID() ?>">

            <?php echo get_avatar($comment, 80); ?>
            <div class="comm_name">
                <?php echo get_comment_author(); ?> <span>- <?php echo get_comment_date('j F Y'); ?> <?php edit_comment_link(__('Edit', 'grandway'),'  ','' ); ?> <?php comment_reply_link(array_merge( $args, array('add_below' => $add_below, 'depth' => $depth, 'max_depth' => $args['max_depth']))) ?></span>
            </div>

            <?php if ($comment->comment_approved == '0') : ?>
                <i class="comment-awaiting-moderation"><?php _e('Your comment is awaiting moderation.', 'grandway') ?></i>
            <?php endif; ?>

            <p class="text_cont com_top"><?php echo get_comment_text() ?></p>

        </div>

        <?php echo wp_kses(
            $divide_line,
            array(
                'div' => array(
                    'class' => array()
                )
            )
        ); ?>
<?php
    }

    // Programmatically get excerpt (by characters quantity)
    public static function excerpt ( $excerpt, $charlength )
    {
        $charlength++;

        if ( mb_strlen( $excerpt ) > $charlength ) {
            $subex = mb_substr( $excerpt, 0, $charlength - 5 );
            $exwords = explode( ' ', $subex );
            $excut = - ( mb_strlen( $exwords[ count( $exwords ) - 1 ] ) );
            if ( $excut < 0 ) {
                return mb_substr( $subex, 0, $excut );
            } else {
                return $subex .'...';
            }
                return '...';
        } else {
            return $excerpt;
        }
    }

    public static function find_child_term ($term, $taxonomy)
    {
        $terms = get_term_by('slug', $term, $taxonomy);
        $children = get_term_children($terms->term_id, $taxonomy);

        return $children;
    }


    public static function pagination ($class = '', $_query = null, $num_pages = 9, $stepLink = 9, $echo = true)
    {
        /* ================ Settings ================ */
        // $text_num_page = _("Page", 'grandway').' ({current} '. _('of', 'grandway') .' {last})';
        $text_num_page = null;
        $dotright_text = ' ... ';
        $dotright_text2 = ' ... ';
        $backtext = __('Prev', 'grandway');
        $nexttext = __('Next', 'grandway');
        $first_page_text = '';
        $last_page_text = '';
        /* ============== Settings END ============== */

        if (!$_query) {
            global $wp_query;

            $posts_per_page = (int) $wp_query->query_vars['posts_per_page'];
            $paged = (int) $wp_query->query_vars['paged'];
            $max_page = $wp_query->max_num_pages;
        } else {
            $posts_per_page = (int) $_query->query_vars['posts_per_page'];
            $paged = (int) $_query->query_vars['paged'];
            $max_page = $_query->max_num_pages;
        }

        if ($max_page <= 1 ) return false;

        if (empty($paged) || $paged == 0) $paged = 1;

        $pages_to_show = intval($num_pages);
        $pages_to_show_minus_1 = $pages_to_show - 1;

        $half_page_start = floor($pages_to_show_minus_1 / 2);
        $half_page_end = ceil($pages_to_show_minus_1 / 2);

        $start_page = $paged - $half_page_start;
        $end_page = $paged + $half_page_end;

        if ($start_page <= 0)
            $start_page = 1;

        if (($end_page - $start_page) != $pages_to_show_minus_1)
            $end_page = $start_page + $pages_to_show_minus_1;

        if ($end_page > $max_page) {
            $start_page = $max_page - $pages_to_show_minus_1;
            $end_page = (int) $max_page;
        }

        if ($start_page <= 0) $start_page = 1;

        $out = null;

        $out .= '<div class="' . esc_attr($class) . '">' . "\n";

            if ($text_num_page) {
                $text_num_page = preg_replace ('!{current}|{last}!','%s',$text_num_page);
                $out .= sprintf ("<span class='pages'>$text_num_page</span>",$paged,$max_page);
            }

            if ($backtext && $paged!=1) {
                $out.= '<a class="prevpostslink" href="'.esc_url(get_pagenum_link(($paged-1))).'">'.esc_html($backtext).'</a>';
            } elseif ($backtext) {
                // $out .= '<span class="prevpostslink pagenavi_no_click">'.esc_html($backtext).'</span>';
            }

            if ($start_page >= 2 && $pages_to_show < $max_page) {
                if ($dotright_text && $start_page != 2)
                    $out .= '<span class="extend page">'.$dotright_text.'</span>';
            }

            for($i = $start_page; $i <= $end_page; $i++) {
                if ($i == $paged) {
                    $out .= '<span class="current">'.esc_html($i).'</span>';
                } else {
                    $out.= '<a class="page" href="'.esc_url(get_pagenum_link($i)).'">'.esc_html($i).'</a>';
                }
            }

            if ($stepLink && $end_page < $max_page) {
                for ($i = $end_page + 1; $i <= $max_page; $i++) {
                    if ($i % $stepLink == 0 && $i !== $num_pages) {
                        $out .= '<span class="extend page">'.esc_html($dotright_text2).'</span>';
                        $out .= '<a class="page" href="'.esc_url(get_pagenum_link($i)).'">'.esc_html($i).'</a>';
                    }
                }
            }

            if ($end_page < $max_page) {
                if($dotright_text && $end_page!=($max_page-1)) $out.= '<span class="extend page">'.esc_html($dotright_text2).'</span>';
            }

            if ($nexttext && $paged != $end_page) {
                $out .= '<a class="nextpostslink" href="'.esc_url(get_pagenum_link(($paged+1))).'">'.esc_html($nexttext).'</a>';
            } elseif ($nexttext) {
                // $out .= '<span class="nextpostslink">'.esc_html($nexttext).'</span>';
            }

        $out.= "</div>" . "\n";

        wp_reset_query();

        if ($echo) {
            echo "\n<!-- pagination -->\n";
            echo wp_kses_post($out);
            echo "<!-- pagination end-->\n";
        } else {
            return $out;
        }
        return true;
    }


    public static function is_blog ()
    {
        global $is_blog;

        if ($is_blog == true)
            return true;

        $blog_page_id = theme_get_option('blog','blog_page');

        if (empty($blog_page_id))
            return false;

        if (wpml_get_object_id($blog_page_id,'page') == get_queried_object_id()) {
            $is_blog = true;
            return true;
        }

        return false;
    }


    public static function embed_url ($url)
    {
        return wp_oembed_get($url);
    }


    public static function check_posts_page ()
    {
        if (get_option('show_on_front') == 'page') {

            $page_id = get_option('page_for_posts');

            if ($page_id)
                $page = get_post($page_id);
            else
                $page = false;

        } else {
            $page = false;
        }

        return $page;
    }


    public static function show_top_socials ()
    {
        global $PhoenixData;

        $show_socials = isset($PhoenixData['header_social']) ? $PhoenixData['header_social'] : true;

        $top_socials = null;

        if ($show_socials) {
            $tops = array();

            $tops['facebook'] = isset($PhoenixData['facebook']) ? $PhoenixData['facebook'] : null;
            $tops['twitter'] = isset($PhoenixData['twitter']) ? $PhoenixData['twitter'] : null;
            $tops['dribbble'] = isset($PhoenixData['dribbble']) ? $PhoenixData['dribbble'] : null;
            $tops['flickr'] = isset($PhoenixData['flickr']) ? $PhoenixData['flickr'] : null;
            $tops['github'] = isset($PhoenixData['github']) ? $PhoenixData['github'] : null;
            $tops['instagram'] = isset($PhoenixData['instagram']) ? $PhoenixData['instagram'] : null;
            $tops['pinterest'] = isset($PhoenixData['pinterest']) ? $PhoenixData['pinterest'] : null;
            $tops['youtube'] = isset($PhoenixData['youtube']) ? $PhoenixData['youtube'] : null;
            $tops['apple'] = isset($PhoenixData['apple']) ? $PhoenixData['apple'] : null;
            $tops['linkedin'] = isset($PhoenixData['linkedin']) ? $PhoenixData['linkedin'] : null;
            $tops['googleplus'] = isset($PhoenixData['googleplus']) ? $PhoenixData['googleplus'] : null;
            $tops['vk'] = isset($PhoenixData['vk']) ? $PhoenixData['vk'] : null;
            $tops['tumblr'] = isset($PhoenixData['tumblr']) ? $PhoenixData['tumblr'] : null;
            $tops['foursquare'] = isset($PhoenixData['foursquare']) ? $PhoenixData['foursquare'] : null;
            $tops['android'] = isset($PhoenixData['android']) ? $PhoenixData['android'] : null;
            $tops['windows'] = isset($PhoenixData['windows']) ? $PhoenixData['windows'] : null;
            $tops['behance'] = isset($PhoenixData['behance']) ? $PhoenixData['behance'] : null;
            $tops['delicious'] = isset($PhoenixData['delicious']) ? $PhoenixData['delicious'] : null;
            $tops['skype'] = isset($PhoenixData['skype']) ? $PhoenixData['skype'] : null;
            $tops['vimeo'] = isset($PhoenixData['vimeo']) ? $PhoenixData['vimeo'] : null;

            foreach ($tops as $key => $value) {
                if ($value) {
                    if (in_array($key, self::$fa_social_icons) || in_array($key, self::$fa_social_icons['fa-google-plus'])) {
                        $icon = array_search($key, self::$fa_social_icons);
                        if ($key == 'googleplus') {
                            $icon = 'fa-google-plus';
                        }
                        $top_socials .= '<li class="'.esc_attr($key).'"><a href="'. esc_url($value) .'" target="_blank"><i class="fa '. esc_attr($icon) .' ic_soc"></i></a></li>';
                    }
                }
            }

            unset($tops);
        }

        if ($top_socials) {
            return $top_socials;
        }

        return false;
    }

    public static function show_rss_feed ()
    {
        global $PhoenixData;

        $text = isset($PhoenixData['rss_text']) ? $PhoenixData['rss_text'] : null;
        $rss = isset($PhoenixData['rss']) ? $PhoenixData['rss'] : null;

        return '<p>'. esc_html($text) .' <a target="_blank" href="'. esc_url($rss) .'">' . __('RSS Feed', 'grandway') . '</a></p>';
    }


    // LOGO & RETINA LOGO
    public static function show_logo ()
    {
        global $PhoenixData;
        $logo = isset($PhoenixData['custom_logo']['url']) ? $PhoenixData['custom_logo']['url'] : THEME_URI . '/assets/images/logo.png';
        $retina_logo = isset($PhoenixData['custom_retina_logo']['url']) ? $PhoenixData['custom_retina_logo']['url'] : THEME_URI . '/assets/images/logo@2x.png';

        $logo = '<a href="'. home_url() .'"><img src="'. esc_url($logo) .'" data-at2x="'. esc_url($retina_logo) .'" alt="Logo"></a>';

        return $logo;
    }


    public static function single_socials ()
    {
        global $PhoenixData;

        $show_socials = isset($PhoenixData['show_single_socials']) ? $PhoenixData['show_single_socials'] : false;
        $socials = isset($PhoenixData['single_socil_buttons']) ? $PhoenixData['single_socil_buttons'] : null;

        if ($show_socials && $socials) {

            $return = '<div class="soc-blog-single"><ul class="soc-blog">';

            foreach ($socials as $key) {
                switch ($key) {
                    case 'facebook':
                        $return .= '<li><a href="#" title="Facebook"><i class="fa fa-facebook"></i></a></li>';
                        break;
                    case 'twitter':
                        $return .= '<li><a href="#" title="Twitter"><i class="fa fa-twitter"></i></a></li>';
                        break;
                    case 'googleplus':
                        $return .= '<li><a href="#" title="Google +"><i class="fa fa-google-plus"></i></a></li>';
                        break;
                    case 'pinterest':
                        $return .= '<li><a href="#" title="Pinterest"><i class="fa fa-pinterest"></i></a></li>';
                        break;
                    case 'linkedin':
                        $return .= '<li><a href="#" title="LinkedIn"><i class="fa fa-linkedin"></i></a></li>';
                        break;
                    case 'tumblr':
                    $return .= '<li><a href="#" title="Tumblr"><i class="fa fa-tumblr"></i></a></li>';
                        break;
                    case 'vk':
                        $return .= '<li><a href="#" title="Vk"><i class="fa fa-vk"></i></a></li>';
                        break;
                    default:
                        break;
                }
            }

            $return .= '</ul></div>' . "\n";

            $return .= "<script>
                            ( function($) {
                                $('.soc-blog-single a').click(function(e){

                                    e.preventDefault();

                                    var thisOne = $(this),
                                        thisName = thisOne.attr('title'),
                                        thisLink = null,
                                        pageLink = encodeURIComponent(document.URL);

                                    switch (thisName) {
                                        case 'Facebook':
                                            thisLink = '//www.facebook.com/sharer/sharer.php?u=';
                                            break;
                                        case 'Twitter':
                                            thisLink = '//twitter.com/share?url=';
                                            break;
                                        case 'Google +':
                                            thisLink = '//plus.google.com/share?url=';
                                            break;
                                        case 'Pinterest':
                                            thisLink = '//www.pinterest.com/pin/create/button/?url=';
                                            break;
                                        case 'LinkedIn':
                                            thisLink = '//www.linkedin.com/cws/share?url=';
                                            break;
                                        case 'Vk':
                                        thisLink = '//vk.com/share.php?url=';
                                            break;
                                        case 'Tumblr':
                                            thisLink = '//www.tumblr.com/share/link?url=';
                                            break;
                                        default:
                                            break;
                                    }

                                    openShareWindow(thisLink + pageLink, thisName);
                                });

                                function openShareWindow (link, name) {
                                    var leftvar = (screen.width-640)/2;
                                    var topvar = (screen.height-480)/2;
                                    var openWindow = window.open(link, name, 'width=640,height=480,left='+leftvar+',top='+topvar+',status=no,toolbar=no,menubar=no,resizable=yes');
                                }

                            } )(jQuery);
                        </script>\n";

            return $return;
        }

        return false;
    }


    public static function get_member_socials ($id)
    {
        global $PhoenixData;

        if ($id) {

            $multisocials = isset($PhoenixData['multisocials']) ? $PhoenixData['multisocials'] : null;

            if ($multisocials) {
                $names = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_socials_name', null, $id) : false;
                $urls  = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_socials_url', null, $id) : false;
                $names_qty = null;
                $urls_qty = null;
                $result = array();

                if (is_array($names) && is_array($urls)) {
                    $names_qty = count($names);
                    $urls_qty = count($urls);

                    if ($names_qty === $urls_qty) {
                        $middleware = array_combine($urls, $names);

                        foreach ($middleware as $key => $value) {
                            $value = trim($value);
                            $comparer = strtolower($value);

                            if (strpos($comparer, " ")) {
                                foreach (self::$fa_social_icons as $k => $item) {
                                    if (is_array($item)) {
                                        if (in_array($comparer, $item)) {
                                            $result[] = array("url" => $key, "icon" => $k, "name" => $value);
                                        }
                                    }
                                }
                            }

                            if (in_array($comparer, self::$fa_social_icons)) {
                                $icon = array_search($comparer, self::$fa_social_icons);
                                $result[] = array("url" => $key, "icon" => $icon, "name" => $value);
                            }
                        }

                        unset($middleware, $names, $urls);

                        if (!empty($result)) {
                            return $result;
                        }

                        return false;
                    }
                }

            } else {
                $result = array();
                $result[] = array(
                    "url" => function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_fb_url', null, $id) : false,
                    "icon" => "facebook",
                    "name" => "Facebook"
                );
                $result[] = array(
                    "url" => function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_twt_url', null, $id) : false,
                    "icon" => "twitter",
                    "name" => "Twitter"
                );
                $result[] = array(
                    "url" => function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_linkedin_url', null, $id) : false,
                    "icon" => "linkedin",
                    "name" => "LinkedIn"
                );
                $result[] = array(
                    "url" => function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_gplus_url', null, $id) : false,
                    "icon" => "googleplus",
                    "name" => "Google +"
                );

                return $result;
            }

            return false;
        }

        return false;
    }


    public static function favicons ()
    {
        global $PhoenixData;

        $favicon = isset($PhoenixData['favicon']['url']) ? $PhoenixData['favicon']['url'] : null;
        $icons = null;

        if ($favicon)
            $icons = '<link rel="shortcut icon" href="'. esc_url($favicon) .'" />' . "\n";
        $favicon_iphone = isset($PhoenixData['favicon_iphone']['url']) ? $PhoenixData['favicon_iphone']['url'] : null;

        if ($favicon_iphone)
            $icons .= '<link rel="apple-touch-icon-precomposed" sizes="144x144" href="'. esc_url($favicon_iphone) .'" />' . "\n";
        $favicon_retina_iphone = isset($PhoenixData['favicon_retina_iphone']['url']) ? $PhoenixData['favicon_retina_iphone']['url'] : null;

        if ($favicon_retina_iphone)
            $icons .= '<link rel="apple-touch-icon-precomposed" sizes="114x114" href="'. esc_url($favicon_retina_iphone) .'" />' . "\n";
        $favicon_ipad = isset($PhoenixData['favicon_ipad']['url']) ? $PhoenixData['favicon_ipad']['url'] : null;

        if ($favicon_ipad)
            $icons .= '<link rel="apple-touch-icon-precomposed" sizes="72x72" href="'. esc_url($favicon_ipad) .'" />' . "\n";
        $favicon_retina_ipad = isset($PhoenixData['favicon_retina_ipad']['url']) ? $PhoenixData['favicon_retina_ipad']['url'] : null;

        if ($favicon_retina_ipad)
            $icons .= '<link rel="apple-touch-icon-precomposed" href="'. esc_url($favicon_retina_ipad) .'" />' . "\n";

        return $icons;
    }


    public static function template_layout ()
    {
        global $PhoenixData;
        $boxed = isset($PhoenixData['boxed_swtich']) ? $PhoenixData['boxed_swtich'] : null;
        if ($boxed) {
            $boxed = " phoenixteam-wrapper-" . $boxed;
        }
        return $boxed;
    }


    public static function id_formatter ($string = null)
    {
        if ($string) {
            $string = str_replace(' ', '-', $string); // Replaces all spaces with hyphens.
            $string = preg_replace('/[^A-Za-z0-9\-]/', '', $string); // Removes special chars.
            $string = preg_replace('/-+/', '-', $string); // Replaces multiple hyphens with single one.

            return $string;
        }

        return false;
    }


    // Check if dependency plugin is active (VC, Woo etc)
    public static function dep_exists ($name, $dir = null)
    {
        if (!$dir)
            $dir = $name;

        $active_plugins = apply_filters('active_plugins', get_option( 'active_plugins' ));

        if (in_array($dir .'/'. $name .'.php', $active_plugins))
            return true;

        return false;
    }


    // HEX to RGB
    public static function hex_to_rgb ($hex)
    {
       $hex = str_replace("#", "", $hex);

       if(strlen($hex) == 3) {
          $r = hexdec(substr($hex,0,1).substr($hex,0,1));
          $g = hexdec(substr($hex,1,1).substr($hex,1,1));
          $b = hexdec(substr($hex,2,1).substr($hex,2,1));
       } else {
          $r = hexdec(substr($hex,0,2));
          $g = hexdec(substr($hex,2,2));
          $b = hexdec(substr($hex,4,2));
       }

       $rgb = array($r, $g, $b);

       // returns the rgb values separated by commas
       return implode(",", $rgb);
    }


    // ???
    public static function user_menu ($slug = null)
    {
      $locations = get_nav_menu_locations();

      pr($locations);

      if (array_key_exists($slug, $locations))
        return true;

      return false;
    }


    public static function footer_twitter ()
    {
      global $PhoenixData;

      $use = isset($PhoenixData['twitter_in_footer']) ? $PhoenixData['twitter_in_footer'] : true;

      if (!$use)
        return false;

      $instance = array();
      $result   = null;
      $type     = 'PhoenixTeam_Widget_Twitter';
      $args     = array();
      $instance['title'] = null;
      $instance['username'] = isset($PhoenixData['twitter_username']) ? $PhoenixData['twitter_username'] : 'DankovTheme';
      $instance['qty'] = isset($PhoenixData['twitter_qty']) ? $PhoenixData['twitter_qty'] : 10;

      $result =
        '<div class="twitter_fot">
          <div class="phoenix-twitter-slider-wrapper">
            <div class="container">
              <div class="row">
                <div class="col-lg-11 col-md-10">';

                  ob_start();
                  the_widget($type, $instance, $args);
                  $result .= ob_get_clean();

      $result .=
                '</div>
                <div class="col-lg-1  col-md-2 hidden-pag">
                  <div class="paginat">
                    <a id="prev"><i class="fa fa-angle-left"></i></a>
                    <a id="next"><i class="fa fa-angle-right"></i></a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>';

      return $result;
    }


    // Temporary autop
    public static function unautop ($content) {

      $pattern = array(
        '&lt;p&gt;',
        '&lt;/p&gt;',
        '&lt;br /&gt;',
        '&lt;br/&gt;',
        '&lt;br&gt;'
      );

      return str_replace($pattern, '', $content);
    }


    /**
     * Resize image on the fly
     *
     * @param  int     $attachment_id Attachment ID
     * @param  int     $width         Width
     * @param  int     $height        Height
     * @param  boolean $crop          Crop or not
     *
     * @return string|bool            URL of resized image, false if error
     */
    public static function img_resize ($attachment_id, $width, $height, $crop = true)
    {
      // Get upload directory info
      $upload_info = wp_upload_dir();
      $upload_dir  = $upload_info['basedir'];
      $upload_url  = $upload_info['baseurl'];
      // Get file path info
      $path      = get_attached_file( $attachment_id );
      $path_info = pathinfo( $path );
      $ext       = $path_info['extension'];
      $rel_path  = str_replace(array($upload_dir, ".$ext"), '', $path);
      $suffix    = "{$width}x{$height}";
      $dest_path = "{$upload_dir}{$rel_path}-{$suffix}.{$ext}";
      $url       = "{$upload_url}{$rel_path}-{$suffix}.{$ext}";
      // If file exists: do nothing
      if (file_exists($dest_path))
        return $url;
      // Generate thumbnail
      if (image_make_intermediate_size($path, $width, $height, $crop))
        return $url;
      // Fallback to full size
      return "{$upload_url}{$rel_path}.{$ext}";
    }

}


class PhoenixTeam_Navmenu_Walker extends Walker_Nav_Menu {

  public function start_el (&$output, $item, $depth = 0, $args = array(), $id = 0)
  {
    global $wp_query;
    $indent = ($depth) ? str_repeat("\t", $depth) : null;

    $class_names = null;
    $prepend = null;
    $append = null;
    $value = null;

    $classes = empty($item->classes) ? array() : (array)$item->classes;

    $class_names = join(' ', apply_filters('nav_menu_css_class', array_filter($classes), $item));
    $class_names = ' class="'. esc_attr($class_names) . '"';

    $output .= $indent . '<li id="menu-item-'. $item->ID .'"' . $value . $class_names .'>';

    $attributes  = !empty($item->attr_title) ? ' title="'  . esc_attr( $item->attr_title ) .'"' : null;
    $attributes .= !empty($item->target) ? ' target="' . esc_attr( $item->target) .'"' : null;
    $attributes .= !empty($item->xfn) ? ' rel="'    . esc_attr( $item->xfn) .'"' : null;
    $attributes .= !empty($item->url) ? ' href="'   . esc_attr( $item->url) .'"' : null;

    $description  = !empty($item->description ) ? '<span class="topmenu-description">'.esc_attr( $item->description ).'</span>' : null;

    if ($depth != 0)
      $description = $append = $prepend = null;

    if (is_array($args))
      return false;

    $item_output  = $args->before;
    $item_output .= '<a'. $attributes .'>';
    $item_output .= $args->link_before . $prepend . apply_filters('the_title', $item->title, $item->ID ) . $append;
    $item_output .= $description . $args->link_after;
    $item_output .= '</a>';
    $item_output .= $args->after;

    $output .= apply_filters('walker_nav_menu_start_el', $item_output, $item, $depth, $args, $id);
  }
}
