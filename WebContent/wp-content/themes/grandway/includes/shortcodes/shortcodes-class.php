<?php

new PhoenixTeam_Shortcodes();

class PhoenixTeam_Shortcodes extends WPBakeryShortCode {

    public function __construct ()
    {
        add_shortcode(THEME_SLUG . '_promo_title', array($this, 'promo_title'));
        add_shortcode(THEME_SLUG . '_portfolio_carousel', array($this, 'portfolio_carousel'));
        add_shortcode(THEME_SLUG . '_service', array($this, 'services'));
        // add_shortcode(THEME_SLUG . '_service', array($this, 'service'));
        add_shortcode(THEME_SLUG . '_team', array($this, 'team_member'));
        add_shortcode(THEME_SLUG . '_get_in_touch', array($this, 'widget_get_in_touch'));
        add_shortcode(THEME_SLUG . '_tweetfeed', array($this, 'widget_twitter'));
        add_shortcode(THEME_SLUG . '_cform', array($this, 'widget_contact_form'));
        add_shortcode(THEME_SLUG . '_postbox', array($this, 'post_box'));
        add_shortcode(THEME_SLUG . '_testimonial', array($this, 'testimonial'));
        add_shortcode(THEME_SLUG . '_clients', array($this, 'clients_slider'));
        add_shortcode(THEME_SLUG . '_facts', array($this, 'facts'));
    }


    public function facts ($attrs, $content = null)
    {
        extract(shortcode_atts(array(
            "icon"       => null,
            "data"       => null,
            "name"       => null,
            "link"       => null,
            "link_place" => null,
            "target"     => null,
            "style"      => null,
            "css"        => null,
        ), $attrs));

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        if ($style)
          $style = " phoenix-shortcode-facts-second ";

        if ($icon)
            $icon = '<div class="fact-icon"><i class="'. esc_attr($icon) .'"></i></div>';

        if ($data)
            $data = '<div class="fact-numb">'. esc_html($data) .'</div>';

        if ($name)
            $name = '<div class="fact-name">'. esc_html($name) .'</div>';

        if ($target)
            $target = ' target="_blank"';

        if ($link) {
            $link = '<a class="phoenixteam-facts-link" href="'. esc_url($link) .'"'. esc_attr($target) .'>';

            if ($link_place == "icon") {
                $return =
                '<div class="phoenixteam-shortcode-facts'. $style . esc_attr($vcCssClass) .'">' .
                    $link .
                    '</a>' .
                    $data .
                    $name .
                '</div>';
            } elseif ($link_place == 'data') {
                $return =
                '<div class="phoenixteam-shortcode-facts'. $style . esc_attr($vcCssClass) .'">' .
                    $icon .
                    $link .
                    $data .
                    '</a>' .
                    $name .
                '</div>';
            } elseif ($link_place == 'text') {
                $return =
                '<div class="phoenixteam-shortcode-facts'. $style . esc_attr($vcCssClass) .'">' .
                    $icon .
                    $data .
                    $link .
                    $name .
                    '</a>' .
                '</div>';
            }

        } else {
            $return =
            '<div class="phoenixteam-shortcode-facts'. $style . esc_attr($vcCssClass) .'">' .
                $icon .
                $data .
                $name .
            '</div>';
        }

        return $return;
    }


    public function post_box ($attrs, $content = null)
    {
        extract(shortcode_atts(array(
            "qty"   => 3,
            "css"   => null,
            "title" => !isset($attrs['title']) ? __("Recent Posts", 'grandway') : $attrs['title'],
            "cat"   => null
        ), $attrs));

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        if ($qty) {
            $postsbox = array(
                "post_type" => "post",
                "post_status" => array("publish", "private"),
                "posts_per_page" => $qty,
                "paged" => false
          );

            if ($cat && $cat != "cat == false")
                $postsbox['category_name'] = $cat;

            $return = '<div class="row"><div class="phoenixteam-shortcode-posts-carousel">';

            $return .= '<div class="col-lg-12">
                            <div class="promo-block"><div class="promo-text">'. esc_html($title) .'</div></div>
                        </div>';

            $return .= '<div class="post-owl-carousel col-lg-12">';

            $postsbox = new WP_Query($postsbox);

            if (!isset($postsbox->post))
                return false;

            if ($postsbox->have_posts()) {
                while($postsbox->have_posts()) {
                    $postsbox->the_post();

                    $ID = get_the_ID();
                    $title = get_the_title();

                    $image = wp_get_attachment_image_src(get_post_thumbnail_id($ID), 'full', false);
                    $image = $image[0];
                    $link = get_permalink($ID);

                    $excerpt = get_the_excerpt();
                    $text = ($excerpt) ? $excerpt : get_the_content();
                    $text = PhoenixTeam_Utils::excerpt($text, 142);

                    $auth = get_post_field('post_author', $ID);

                    $author = get_the_author_meta('display_name', $auth);
                    $author_link = get_author_posts_url($auth);

                    $comments = get_comment_count($ID);
                    $comments = $comments['approved'];

                    $cat = wp_get_post_categories($ID);

                    if (is_array($cat) && count($cat) > 0) {
                        $cat = $cat[0];
                        $cat = get_category($cat);
                        $cat = __('in', 'grandway') .
                              ' <a href="' .
                              esc_url(get_category_link($cat->cat_ID)) .'">' .
                              esc_html($cat->name) .
                              '</a>';
                    }

                    $format = get_post_format($ID);
                    if (!$format) $format = 'standard';

                    switch ($format) {
                        case 'standard': $format = 'icon-pencil'; break;
                        case 'image': $format = 'icon-camera'; break;
                        case 'video': $format = 'icon-video'; break;
                        case 'link': $format = 'icon-attachment'; break;
                        case 'quote': $format = 'icon-chat'; break;
                        case 'gallery': $format = 'icon-pictures'; break;
                        case 'audio': $format = 'icon-mic'; break;
                        default: $format = 'icon-pencil'; break;
                    }

                    $return .= '
                        <div class="col-lg-12 '. esc_attr($vcCssClass) .'">
                            <div class="blog-images">
                                <div class="post-thumbnail">
                                    <div class="single-item"></div>
                                    <div class="single-action">
                                        <span><a href="'.esc_url($link).'"><i class="'.esc_attr($format).'"></i></a></span>
                                    </div>
                                     <img data-no-retina="1" src="'.esc_url($image).'" alt="'.esc_attr($title).'">
                                </div>
                            </div>
                            <div class="blog-name"><a href="'.esc_url($link).'">'. esc_html($title) .'</a></div>
                            <div class="blog-text">'. esc_html($text) .'</div>
                            <div class="blog-line">
                                <div class="blog-date">' .get_the_time('j F, Y ') .'</div>
                                <div class="blog-icons"><a href="'.esc_url($link).'"><i class="icon-chat"></i> '. $comments .'</a> <a href="'.esc_url($link).'"><i class="icon-heart"></i> 137</a></div>
                            </div>
                            </div>';
                }
            }

            wp_reset_postdata();

            $return .= "</div></div></div>";

            return $return;
        }

        return false;
    }


    public function testimonial ($attrs, $content = null)
    {
        $return = null;

        extract(shortcode_atts(array(
            'name' => null,
            'position' => null,
            'pic' => null,
            'css' => null
        ), $attrs));

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        $pic = $pic ? PhoenixTeam_Utils::img_resize($pic, 87, 87) : THEME_URI . "/assets/images/nopicture.png";

        $return = '<div class="phoenixteam-testimonial-shortcode testimonial'. esc_attr($vcCssClass) .'">';
        $return .= '<div class="author">
                      <img data-no-retina="1" src="'. esc_url($pic) .'" alt="'. esc_attr($name) .'" title="' . esc_attr($name) . '" class="img-circle img75">
                    </div>';
        $return .= '<blockquote>';
        $return .= '<p>'. PhoenixTeam_Utils::unautop(esc_html($content)) .'</p>';
        $return .= '<cite><b>'. esc_html($name) .'</b> / '. esc_html($position) .'</cite>';
        $return .= '</blockquote>';
        $return .= '</div>';

        return $return;
    }


    public function widget_contact_form ($attrs, $content = null)
    {
        extract(shortcode_atts(array(
            'css' => null
        ), $attrs));

        $type = 'PhoenixTeam_Widget_ContactForm';
        $args = array();

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        $result = '<div class="'. THEME_SLUG .'-shortcode-contact-form'. esc_attr($vcCssClass) .'">';

        ob_start();
        the_widget($type, $attrs, $args);
        $result .= ob_get_clean();

        $result .= '</div>';

        return $result;
    }


    public function widget_twitter ($attrs, $content = null)
    {
        $result = null;

        extract(shortcode_atts(array(
            'style' => 'box',
            'css' => null
        ), $attrs));

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        $attrs['title'] = isset($attrs['title']) ? $attrs['title'] : null;
        $attrs['username'] = isset($attrs['username']) ? $attrs['username'] : THEME_TEAM;
        $attrs['qty'] = isset($attrs['qty']) ? $attrs['qty'] : 3;

        $result = '<div class="'. THEME_SLUG .'-shortcode-twitter-feed'. esc_attr($vcCssClass) .'">';

        if ($style == 'slider') {
            $result .= '<div class="prl-3 marg50 phoenixteam-twitter-slider-wrapper">
            <div class="prlx-3">
              <div class="container marg75">
              <div class="row">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 col-ms-12">
                  <div class="twit-icon"><i class="fa fa-twitter"></i></div>
                </div>
                <div class="col-lg-10 col-md-10 col-sm-10 col-xs-11 col-ms-12">';
        }

        $type = 'PhoenixTeam_Widget_Twitter';
        $args = array();

        ob_start();
        the_widget($type, $attrs, $args);
        $result .= ob_get_clean();

        if ($style == 'slider') {
            $result .= '</div>';
            $result .= '<div class="col-lg-1 col-md-1 col-sm-1 hidden-xs">
                          <div class="paginat">
                              <a id="prev">&lsaquo;</a>
                              <a id="next">&rsaquo;</a>
                          </div>
                      </div>';
            $result .= '</div>';
            $result .= '</div>';
            $result .= '</div>';
        }
        $result .= '</div>';

        return $result;
    }


    public function team_member ($attrs, $content = null)
    {
        extract(shortcode_atts(array(
            'id' => null,
            'title' => null,
            'css' => null
        ), $attrs));

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        if ($id) {
            $member = array(
                "post_type" => THEME_SLUG . "_team",
                "post_status" => "publish",
                "p" => $id
          );

            $member = new WP_Query($member);
            wp_reset_postdata();

            if (!isset($member->post))
                return false;

            $member = $member->post;

            if (!$title)
                $title = $member->post_title;

            $pic = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_member_pic', array('type' => 'image', 'size' => 'medium'), $id) : false;
            if (is_array($pic) && count($pic)) {
                $pic = array_shift($pic);
                $pic = $pic['full_url'];
                $pic = '<img data-no-retina="1" src="'.esc_url($pic).'" alt="'.esc_attr($title).'">';
            } else {
                $pic = '<img data-no-retina="1" src="'.THEME_URI . '/assets/images/nopicture.png" alt="'.$title.'">';
            }

            $about = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_member_text', null, $id) : false;
            $position = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_member_position', null, $id) : false;
            $email = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_team_member_email', null, $id) : false;

            if ($position)
                $position = '<div class="about-desc">'.esc_html($position).'</div>';

            if ($about)
                $about = '<div class="about-texts">'.esc_html($about).'</div>';

            $socials = PhoenixTeam_Utils::get_member_socials($id);

            $socials_html = null;
            $socials_before = null;
            $socials_after = null;

            if ($socials) {
                $count = count($socials);

                for ($i=0; $i < $count; $i++) {
                    if ($socials[$i]['url']) {

                        switch ($socials[$i]['icon']) {
                          case 'fa-facebook':
                            $icon_classes = 'icon-' . str_replace('fa-', '', $socials[$i]['icon']); break;
                          case 'fa-twitter':
                            $icon_classes = 'icon-' . str_replace('fa-', '', $socials[$i]['icon']); break;
                          case 'fa-linkedin':
                            $icon_classes = 'icon-' . str_replace('fa-', '', $socials[$i]['icon']); break;
                          case 'fa-google-plus':
                            $icon_classes = 'icon-' . str_replace('-', '', str_replace('fa', '', $socials[$i]['icon'])); break;
                          case 'fa-dribbble':
                            $icon_classes = 'icon-' . str_replace('fa-', '', $socials[$i]['icon']); break;

                          default: $icon_classes = 'fa ' . $socials[$i]['icon']; break;
                        }

                        $socials_html .=
                          '<li>
                            <a href="' .
                              esc_url($socials[$i]['url']).'"' .
                              ' title="'.esc_attr($title).' '.esc_attr($socials[$i]['name']) .
                              ' '.__("profile", 'grandway'). '">' .
                              '<i class="'. esc_attr($icon_classes) .'"></i>' .
                              '</a>
                          </li>';
                    }
                }
            }

            if ($email) {
                $socials_html .= '<li><a href="mailto:'. antispambot($email) .'"><i class="fa fa-envelope"></i></a></li>';
            }

            if ($socials_html) {
                $socials_before = '<ul class="soc-about">';
                $socials_after = '</ul>';
            }

            $return = '
                <div class="phoenixteam-shortcode-about-us about-us'. esc_attr($vcCssClass) .'">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 col-ms-12">' .
                            $pic .
                        '</div>' .
                        '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 col-ms-12">'.
                            '<div class="team-block"><div class="about-name">'.esc_html($title).'</div>' .
                            $position .
                            $about .
                            $socials_before .
                            $socials_html .
                            $socials_after .
                        '</div></div>
                    </div>
                </div>';

            return $return;
        }

        return false;

    }


    public function clients_slider ($attrs, $content = null)
    {
        extract(shortcode_atts(array(
          'images'   => null,
          'autoplay' => null,
          "title" => !isset($attrs['title']) ? __("Some of Our Clients", 'grandway') : $attrs['title'],
          'css'      => null
        ), $attrs));

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        if ($images && count($images) > 0) {
          $images = explode(',', $images);
        } else {
          return
            '<div class="phoenixteam-clients-carousel">' .
              '<p>'. __("You didn't select any image.", 'grandway') . '</p>' .
            '</div>';
        }

        $return =
        '<div class="phoenixteam-clients-carousel'. esc_attr($vcCssClass) .'">
          <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 col-ms-12">' .
              '<div class="promo-block">
                <div class="promo-text">'. esc_html($title) .'</div>' .
              '</div>' .
            '</div>';

            $return .=
            '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 col-ms-12 marg25">' .
              '<ul class="clients-owl-carousel">';

              foreach ($images as $attach_id) {
                $thumb = wp_get_attachment_image_src($attach_id, 'full', true);
                $thumb = $thumb[0];

                $return .= '<li><img data-no-retina="1" src="'. esc_url($thumb) .'" alt=""></li>';
              }

              $return .=
              '</ul>
            </div>
          </div>
        </div>';

        return $return;
    }


    public function services ($attrs, $content = null)
    {
        $return = null;
        $icon = null;
        $text = null;

        extract( shortcode_atts(array(
            'id' => null,
            'title' => null,
            'layout' => 'block',
            'css' => null,
            "link"  => null,
            "target" => null
        ), $attrs) );

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        if ($id) {
            $service = array(
                "post_type" => THEME_SLUG . '_services',
                "post_status" => "publish",
                "p" => $id
            );

            $service = new WP_Query($service);
            wp_reset_postdata();

            if (!isset($service->post))
                return false;

            $service = $service->post;

            if (!$title)
                $title = $service->post_title;

            $icon = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_services_icons_list', null, $id) : false;
            $text = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_services_text', null, $id) : false;

            if ($target)
                $target = ' target="_blank"';

            if ($link) {
                $link = '<a class="phoenixteam-service-link" href="'. esc_url($link) .'"'. esc_attr($target) .'>';
                $link_closed = '</a>';
            } else {
                $link = $link_closed = null;
            }

            if ($layout == 'list') {
                $return = '
                    <div class="other-serv'. esc_attr($vcCssClass) .'">
                        <div class="serv-icon">'. $link .'<i class="fa '.esc_attr($icon).'"></i>'. $link_closed .'</div>
                        <div class="serv-block-list"><h2 class="serv-name">'.esc_html($title).'</h2><p class="serv-desc">'.esc_html($text).'</p></div>
                    </div>';
            } else {
                $return = '
                    <div class="hi-icon-effect'. esc_attr($vcCssClass) .'">' .
                        $link . '<div class="hi-icon fa '. esc_attr($icon) .'"></div>' . $link_closed .
                        '<div class="service-name">'.esc_html($title).'</div>
                        <div class="service-text">'.esc_html($text).'</div>
                    </div>';
            }

            return $return;
        }

        return false;
    }


    // public function service ($attrs, $content = null)
    // {
    //     $return = null;
    //
    //     extract(shortcode_atts(array(
    //         "icon" => null,
    //         "title" => null,
    //         "link"  => null,
    //         "css" => null
    //     ), $attrs));
    //
    //     $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);
    //
    //     $link = vc_build_link($link);
    //
    //     $link['title'] = $link['title'] ? $link['title'] : __('Read More', 'grandway');
    //
    //     $link = $link['url'] ?
    //       '<p class="read">' .
    //         '<a href="'. esc_url($link['url']) .
    //           '" target="'. esc_attr($link['target']) .'">' .
    //           $link['title'] .' →</a>' .
    //       '</p>' :
    //       null;
    //
    //     $return =  '<div class="phoenixteam-service iconbox'. esc_attr($vcCssClass) .'">';
    //     $return .=   '<span class="">'. $icon .'</span><br>';
    //     $return .=   '<h3>'. esc_html($title) .'</h3>';
    //     $return .=   '<p>'. PhoenixTeam_Utils::unautop(esc_html($content)) .'</p>';
    //     $return .=   $link;
    //     $return .= '</div>';
    //
    //     return $return;
    // }


//
    public function portfolio_carousel ($attrs, $content = null)
    {
        $return = null;
        $text = null;

        extract(shortcode_atts(array(
            'qty'   => 6,
            'css'   => null,
            'title' => !isset($attrs['title']) ? __("Recent Projects", 'grandway') : $attrs['title']
        ), $attrs));

        wp_enqueue_script(THEME_SLUG . '-portfolio');

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        $portfolio = array(
            'post_type' => THEME_SLUG . '_portfolio',
            'posts_per_page' => $qty,
            'post_status' => 'publish',
      );

        $portfolio = new WP_Query($portfolio);

        if ($portfolio->have_posts()) {

            $autoplay = true;
            $uID = null;
            $script = null;

            $return = '
            <div class="row">
              <div class="col-lg-12">
                <div class="promo-block">
                  <div class="promo-text">'. esc_html($title) .'</div>
                </div>
              </div>
              <div class="col-lg-12">
              <div class="marg25 phoenixteam-portfolio-carousel'. esc_attr($vcCssClass) .'">
                <div class="portfolio-owl-carousel" id="phoenixteam-portfolio-owl-carousel-'. esc_attr($uID) .'">';
                  while($portfolio->have_posts()) {
                    $portfolio->the_post();

                    $ID = get_the_id();

                    $the_cat = get_the_terms($ID , THEME_SLUG . '_portfolio_category');
                    $categories = '';
                    if (is_array($the_cat)) {
                      foreach($the_cat as $cur_term) {
                          $categories .= $cur_term->slug . ' ';
                      }
                    }

                    $thumb_params = array('width' => 360,'height' => 240, 'crop' => true);
                    $thumb = null;

                    $title = get_the_title();
                    $text  = function_exists('rwmb_meta') ? rwmb_meta(THEME_SLUG . '_portfolio_excerpt') : false;
                    $text  = PhoenixTeam_Utils::excerpt($text, 120);
                    $link  = get_permalink();

                    if (has_post_thumbnail()) {
                      $thumb = wp_get_attachment_image_src(get_post_thumbnail_id($ID), 'full', true);
                      $thumb = $thumb[0];
                    } else {
                      $thumb = THEME_URI . "/assets/images/nopicture.png";
                    }

                    $return .=
                    '<div class="cbp-item '.esc_attr($categories).'">
                      <div class="portfolio-phoenixteam">
                        <div class="portfolio-image">';

                    if ($thumb) {
                      $return .= '<img data-no-retina="1" src="'. esc_url(bfi_thumb($thumb, $thumb_params)) .'" alt="'. esc_attr($title) .'" />
                          <figcaption class="detailholder">
                            <p class="icon-links">
                                <a href="'.esc_url($link).'" class="cbp-singlePageInline"><i class="icon-attachment"></i></a>
                                <a href="'.esc_url($thumb).'" class="cbp-lightbox" data-title="'.esc_attr($title).'"><i class="icon-magnifying-glass"></i></a>
                            </p>
                          </figcaption>
                      </div>';
                    }

                    $return .= '
                        <div class="portfolio-name">'.esc_html($title).'</div>
                        <div class="portfolio-text">'.esc_html($text).'</div>
                      </div>
                    </div>';
                  }

            $return .= '</div></div></div></div>';

            $return .= $script;

            wp_reset_postdata();

            return $return;
        } else {
            return '<div class="phoenixteam-clients-carousel"><p>'. __("You didn't select any image.", 'grandway') .'</p></div>';
        }

        return false;
    }


    public function promo_title ($attrs, $content = null )
    {
        extract(shortcode_atts(array(
            'title' => 'Title',
            'css' => null
        ), $attrs));

        $cssClass = "phoenixteam-shortcode-promo-block";
        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        $result = '<div class="'. esc_attr($cssClass . $vcCssClass) .'">';
          $result .= '<div class="promo-block">';

        if ($title)
            $result .= '<div class="promo-text">'. esc_html($title) .'</div>';

        $result .= '</div>
                  </div>';

        return $result;
    }


    public function widget_get_in_touch ($attrs, $content = null )
    {
        $result = null;

        extract(shortcode_atts(array(
            'adress' => null,
            'phone' => null,
            'fax' => null,
            'skype' => null,
            'email' => null,
            'weekend' => null,
            'css' => null
        ), $attrs));

        $vcCssClass = apply_filters(VC_SHORTCODE_CUSTOM_CSS_FILTER_TAG, vc_shortcode_custom_css_class( $css, ' ' ), $this->settings['base'], $attrs);

        $result = '<div class="'. THEME_SLUG .'-shortcode-contact'. esc_attr($vcCssClass) .'">';
        $type = 'PhoenixTeam_Widget_GetInTouch';
        $args = array();

        ob_start();
        the_widget($type, $attrs, $args);
        $result .= ob_get_clean();

        $result .= '</div>';

        return $result;
    }

}
