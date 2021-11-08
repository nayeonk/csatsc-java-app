<?php

new PhoenixTeam_PageBuilder();

class PhoenixTeam_PageBuilder {

    public function __construct ()
    {
        add_action('vc_before_init', array($this, 'vcSetAsTheme'));

        add_filter('vc_load_default_templates', array($this, 'vcRemoveDefaultTemplates'));

        add_action('vc_load_default_templates', array($this, 'vcContactsPageTemplate'));
        add_action('vc_load_default_templates', array($this, 'vcServicesPageTemplate'));
        add_action('vc_load_default_templates', array($this, 'vcAboutUsPageTemplate'));
        add_action('vc_load_default_templates', array($this, 'vcHomepageTemplate'));
    }

    public function vcRemoveDefaultTemplates ($data)
    {
        return array();
    }


    public function vcSetAsTheme ()
    {
        vc_set_as_theme(true);
    }


    public function vcHomepageTemplate ($data)
    {
        $template                 = array();
        $template['name']         = __('Homepage', 'grandway');
        // $template['image_path']   = preg_replace( '/\s/', '%20', plugins_url( 'js_composer/assets/vc/templates/landing_page.png', __FILE__ ) );
        $template['custom_class'] = 'custom_template_for_vc_custom_template';
        $template['content']      =

<<<CONTENT
[vc_row full_width="stretch_row"][vc_column][rev_slider_vc alias="slider"][/vc_column][/vc_row][vc_row css=".vc_custom_1441377403112{margin-top: 50px !important;}"][vc_column][vc_row_inner][vc_column_inner width="1/3"][grandway_service id="14"][/vc_column_inner][vc_column_inner width="1/3"][grandway_service id="13"][/vc_column_inner][vc_column_inner width="1/3"][grandway_service id="12"][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row css=".vc_custom_1439990802030{margin-top: 50px !important;}"][vc_column][grandway_portfolio_carousel qty="6"][/vc_column][/vc_row][vc_row full_width="stretch_row" css=".vc_custom_1440065769501{margin-top: 75px !important;padding-top: 50px !important;padding-bottom: 50px !important;background-color: #fcfcfc !important;}"][vc_column][vc_row_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-speedometer" data="SEO Optimized" name="Luckily friends do ashamed suppose. Tried meant smile. Exquisite behaviour as."][/vc_column_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-basket" data="WooCommerce" name="Luckily friends do ashamed suppose. Tried meant smile. Exquisite behaviour as."][/vc_column_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-lifesaver" data="Premium Support" name="Luckily friends do ashamed suppose. Tried meant smile. Exquisite behaviour as."][/vc_column_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-wallet" data="Only $59" name="Luckily friends do ashamed suppose. Tried meant smile. Exquisite behaviour as."][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row][vc_column width="7/12" css=".vc_custom_1441479101066{margin-top: 75px !important;}"][grandway_postbox qty="6" css=".vc_custom_1440541313141{margin-top: 25px !important;}"][/vc_column][vc_column width="5/12" css=".vc_custom_1441479094886{margin-top: 75px !important;}"][vc_row_inner][vc_column_inner][grandway_promo_title title="Our Services" css=".vc_custom_1440083636855{margin-bottom: 25px !important;}"][/vc_column_inner][/vc_row_inner][vc_tta_accordion shape="square" color="white" c_position="right" active_section="1"][vc_tta_section title="Wordpress Theme" tab_id="1439995279043-e2213f4f-0c28"][vc_column_text]

<section id="one">
<div>

Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

</div>
</section><section id="two"></section>[/vc_column_text][/vc_tta_section][vc_tta_section title="HTML 5 / CSS 3 " tab_id="1439995932386-4dadb754-d376"][vc_column_text]

<section id="one">
<div>

Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

</div>
</section><section id="two"></section>[/vc_column_text][/vc_tta_section][vc_tta_section title="Javascript " tab_id="1439995966498-4ed3bf62-1dcb"][vc_column_text]

<section id="one">
<div>

Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

</div>
</section><section id="two"></section>[/vc_column_text][/vc_tta_section][vc_tta_section title="PHP Development" tab_id="1439996005188-74ddabf9-1b24"][vc_column_text]

<section id="one">
<div>

Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

</div>
</section><section id="two"></section>[/vc_column_text][/vc_tta_section][vc_tta_section title="Web Design" tab_id="1439995973353-fe468a9c-7cfc"][vc_column_text]

<section id="one">
<div>

Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

</div>
</section><section id="two"></section>[/vc_column_text][/vc_tta_section][/vc_tta_accordion][/vc_column][/vc_row][vc_row css=".vc_custom_1440091442961{margin-top: 75px !important;}"][vc_column][vc_row_inner][vc_column_inner][grandway_promo_title title="Clients Testimonials" css=".vc_custom_1440091421784{margin-bottom: 25px !important;}"][/vc_column_inner][/vc_row_inner][vc_row_inner][vc_column_inner width="1/2"][grandway_testimonial pic="283" name="Georg Brown" position="SEO Company"]
Praesent sodales ullamcorper felis, eget rutrum nisi dignissim vel. Nulla non aringilla metus, in tincidunt lacus aenean scelerisque cillum dolore eu rugiat nulla pariatur

[/grandway_testimonial][/vc_column_inner][vc_column_inner width="1/2"][grandway_testimonial pic="281" name="Milena Markovna" position="Blog Network"]
Praesent sodales ullamcorper felis, eget rutrum nisi dignissim vel. Nulla non aringilla metus, in tincidunt lacus aenean scelerisque cillum dolore eu rugiat nulla pariatur

[/grandway_testimonial][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row css=".vc_custom_1440092708411{margin-top: 75px !important;}"][vc_column][grandway_clients images="59,58,57,56,55,54" autoplay="1"][/vc_column][/vc_row]
CONTENT;

        array_unshift($data, $template);

        return $data;
    }


    public function vcAboutUsPageTemplate ($data)
    {
        $template                 = array();
        $template['name']         = __('About Us Page', 'grandway');
        // $template['image_path']   = preg_replace( '/\s/', '%20', plugins_url( 'js_composer/assets/vc/templates/landing_page.png', __FILE__ ) );
        $template['custom_class'] = 'custom_template_for_vc_custom_template';
        $template['content']      =

<<<CONTENT
[vc_row row_type="container-in" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off" video_opts="" multi_color_overlay=""][vc_column][vc_row_inner css=".vc_custom_1440929913254{margin-bottom: 35px !important;}"][vc_column_inner width="1/2"][vc_single_image image="242" img_size="full"][/vc_column_inner][vc_column_inner width="1/2"][vc_custom_heading text="We doing business better" font_container="tag:h2|text_align:left|color:%23444444" google_fonts="font_family:Roboto%20Slab%3A100%2C300%2Cregular%2C700|font_style:300%20light%20regular%3A300%3Anormal" el_class="about-us-h2" css=".vc_custom_1440929971977{margin-top: 5px !important;}"][vc_column_text]Advantage old had otherwise sincerity dependent additions. It in adapted natural hastily is justice. Six draw you him full not mean evil. Prepare garrets it expense windows shewing do an. She projection advantages resolution son indulgence. Part sure on no long life am at ever. In songs above he as drawn to. Gay was outlived peculiar rendered led six.
<ul class="list-check">
  <li><i class="fa fa-check"></i> Clean And Minimal Design</li>
  <li><i class="fa fa-check"></i> We Love Our Clients</li>
  <li><i class="fa fa-check"></i> Powerful &amp; Flexible Settings</li>
  <li><i class="fa fa-check"></i> Online Premium Support</li>
</ul>
View fine me gone this name an rank. Compact greater and demands mrs the parlors. Park be fine easy am size away. Him and fine bred knew. At of hardly sister favour.[/vc_column_text][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row row_type="fullwidth" css=".vc_custom_1441042536345{margin-top: 50px !important;background-color: #ffffff !important;}" id="our-awesome-team" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off"][vc_column][vc_row_inner][vc_column_inner][grandway_promo_title title="Our Awesome Team"][/vc_column_inner][/vc_row_inner][vc_row_inner css=".vc_custom_1441292515737{margin-top: 25px !important;}"][vc_column_inner width="1/4"][grandway_team id="258"][/vc_column_inner][vc_column_inner width="1/4"][grandway_team id="264"][/vc_column_inner][vc_column_inner width="1/4"][grandway_team id="265"][/vc_column_inner][vc_column_inner width="1/4"][grandway_team id="266"][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row css=".vc_custom_1441295172185{margin-top: 75px !important;margin-bottom: 25px !important;}"][vc_column][vc_row_inner][vc_column_inner][grandway_promo_title title="Testimonials"][/vc_column_inner][/vc_row_inner][vc_row_inner css=".vc_custom_1441295117227{margin-top: 25px !important;}"][vc_column_inner width="1/2"][grandway_testimonial pic="112" name="Angel Forton" position="Lenovo"]

Praesent sodales ullamcorper felis, eget rutrum nisi dignissim vel. Nulla non aringilla metus, in tincidunt lacus aenean scelerisque cillum dolore eu rugiat nulla pariatur

[/grandway_testimonial][grandway_testimonial pic="280" name="Angel Forton" position="PhoenixTeam" css=".vc_custom_1441296790625{margin-top: 25px !important;}"]Praesent sodales ullamcorper felis, eget rutrum nisi dignissim vel. Nulla non aringilla metus, in tincidunt lacus aenean scelerisque cillum dolore eu rugiat nulla pariatur

[/grandway_testimonial][/vc_column_inner][vc_column_inner width="1/2"][grandway_testimonial pic="111" name="Mark Bertion" position="Motorola"]
Praesent sodales ullamcorper felis, eget rutrum nisi dignissim vel. Nulla non aringilla metus, in tincidunt lacus aenean scelerisque cillum dolore eu rugiat nulla pariatur

[/grandway_testimonial][grandway_testimonial pic="282" name="Angel Forton" position="Lenovo" css=".vc_custom_1441296803981{margin-top: 25px !important;}"]Praesent sodales ullamcorper felis, eget rutrum nisi dignissim vel. Nulla non aringilla metus, in tincidunt lacus aenean scelerisque cillum dolore eu rugiat nulla pariatur

[/grandway_testimonial][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row full_width="stretch_row" css=".vc_custom_1441297267420{margin-top: 65px !important;margin-bottom: 50px !important;padding-top: 50px !important;padding-bottom: 50px !important;background-color: #fcfcfc !important;}"][vc_column][vc_row_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-book-open" data="Book Read" name="Certainly elsewhere my do allowance at. The address farther six hearted hundred."][/vc_column_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-target" data="Successful Projects" name="Certainly elsewhere my do allowance at. The address farther six hearted hundred."][/vc_column_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-happy" data="Happy Customers" name="Certainly elsewhere my do allowance at. The address farther six hearted hundred."][/vc_column_inner][vc_column_inner width="1/4"][grandway_facts icon="icon-envelope" data="Book Read" name="Certainly elsewhere my do allowance at. The address farther six hearted hundred."][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row css=".vc_custom_1441297064451{margin-top: 50px !important;}"][vc_column][vc_row_inner css=".vc_custom_1441297072690{margin-top: 25px !important;}"][vc_column_inner][grandway_clients title="Top of Clients" images="59,58,57,56,55,54"][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row]
CONTENT;

        array_unshift($data, $template);

        return $data;
    }


    public function vcServicesPageTemplate ($data)
    {
        $template                 = array();
        $template['name']         = __('Services Page', 'grandway');
        // $template['image_path']   = preg_replace( '/\s/', '%20', plugins_url( 'js_composer/assets/vc/templates/landing_page.png', __FILE__ ) );
        $template['custom_class'] = 'custom_template_for_vc_custom_template';
        $template['content']      =

<<<CONTENT
[vc_row row_type="fullwidth" css=".vc_custom_1440789029327{background-color: #ffffff !important;}" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" video_opts="" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay="" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off"][vc_column][vc_row_inner][vc_column_inner width="1/3"][grandway_service id="13"][/vc_column_inner][vc_column_inner width="1/3"][grandway_service id="14"][/vc_column_inner][vc_column_inner width="1/3"][grandway_service id="12"][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row row_type="fullwidth" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" video_opts="" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay="" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off" css=".vc_custom_1441047166943{margin-top: 75px !important;background-color: #ffffff !important;}"][vc_column][vc_row_inner][vc_column_inner][grandway_promo_title title="Our skills" dot=""][/vc_column_inner][/vc_row_inner][vc_row_inner css=".vc_custom_1441377459226{margin-top: 25px !important;}"][vc_column_inner width="1/2"][vc_progress_bar values="%5B%7B%22label%22%3A%22Javascript%22%2C%22value%22%3A%2290%22%7D%2C%7B%22label%22%3A%22Design%22%2C%22value%22%3A%2287%22%7D%5D" bgcolor="custom" view="outside" units="%" custombgcolor="#99cc66" customtxtcolor="#ffffff"][/vc_column_inner][vc_column_inner width="1/2"][vc_progress_bar values="%5B%7B%22label%22%3A%22Front-end%22%2C%22value%22%3A%2283%22%7D%2C%7B%22label%22%3A%22Wordpress%22%2C%22value%22%3A%2295%22%7D%5D" bgcolor="custom" units="%" custombgcolor="#99cc66" customtxtcolor="#ffffff"][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row row_type="container" css=".vc_custom_1441377824570{margin-top: 75px !important;}" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" video_opts="" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay="" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off"][vc_column width="1/2"][vc_row_inner][vc_column_inner][grandway_promo_title title="Other Services" dot=""][/vc_column_inner][/vc_row_inner][vc_row_inner css=".vc_custom_1441377694048{padding-top: 25px !important;}"][vc_column_inner width="1/2"][grandway_service id="298" layout="list"][grandway_service id="301" layout="list"][/vc_column_inner][vc_column_inner width="1/2"][grandway_service id="300" layout="list"][grandway_service id="302" layout="list"][/vc_column_inner][/vc_row_inner][/vc_column][vc_column width="1/2"][vc_row_inner][vc_column_inner][grandway_promo_title title="Testimonials" dot=""][/vc_column_inner][/vc_row_inner][vc_row_inner css=".vc_custom_1441377700557{padding-top: 25px !important;}"][vc_column_inner][grandway_testimonial pic="111" name="John Doe" position="Google"]

Inhabiting discretion the her dispatched decisively boisterous joy. So form were wish open is able of mile of. Waiting express if prevent it we an musical.

[/grandway_testimonial][grandway_testimonial pic="112" name="Mila Markovna" position="Apple" css=".vc_custom_1441047066644{margin-top: 25px !important;}"]
Inhabiting discretion the her dispatched decisively boisterous joy. So form were wish open is able of mile of. Waiting express if prevent it we an musical.

[/grandway_testimonial][/vc_column_inner][/vc_row_inner][/vc_column][/vc_row][vc_row row_type="container" css=".vc_custom_1441377846460{margin-top: 75px !important;}" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" video_opts="" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay="" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off"][vc_column][grandway_clients images="59,54,55,56,57,58" autoplay="1" popup=""][/vc_column][/vc_row]
CONTENT;

        array_unshift($data, $template);

        return $data;
    }


    public function vcContactsPageTemplate ($data)
    {
        $template                 = array();
        $template['name']         = __('Contacts Page', 'grandway');
        // $template['image_path']   = preg_replace( '/\s/', '%20', plugins_url( 'js_composer/assets/vc/templates/landing_page.png', __FILE__ ) );
        $template['custom_class'] = 'custom_template_for_vc_custom_template';
        $template['content']      =

<<<CONTENT
[vc_row row_type="fullwidth" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" video_opts="" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay="" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off"][vc_column][vc_gmaps link="#E-8_JTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTIwJTNDaWZyYW1lJTIwd2lkdGglM0QlMjIxMDAlMjUlMjIlMjBoZWlnaHQlM0QlMjI1MDAlMjIlMjBmcmFtZWJvcmRlciUzRCUyMjAlMjIlMjBzY3JvbGxpbmclM0QlMjJubyUyMiUyMG1hcmdpbmhlaWdodCUzRCUyMjAlMjIlMjBtYXJnaW53aWR0aCUzRCUyMjAlMjIlMjBzcmMlM0QlMjJodHRwJTNBJTJGJTJGbWFwcy5nb29nbGUucnUlMkYlM0ZpZSUzRFVURjglMjZhbXAlM0JsbCUzRDQyLjM3NjQ2NyUyQy03MS4wNjMxNzUlMjZhbXAlM0JzcG4lM0QwLjAwNjk2NyUyQzAuMDE2NTEyJTI2YW1wJTNCdCUzRG0lMjZhbXAlM0J6JTNEMTclMjZhbXAlM0JvdXRwdXQlM0RlbWJlZCUyMiUzRSUzQyUyRmlmcmFtZSUzRSUwQQ==" size="600"][/vc_column][/vc_row][vc_row row_type="container" css=".vc_custom_1441379251910{margin-top: 75px !important;}" bg_type="no_bg" bg_grad="background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #E3E3E3));background: -moz-linear-gradient(top,#E3E3E3 0%);background: -webkit-linear-gradient(top,#E3E3E3 0%);background: -o-linear-gradient(top,#E3E3E3 0%);background: -ms-linear-gradient(top,#E3E3E3 0%);background: linear-gradient(top,#E3E3E3 0%);" parallax_style="vcpb-default" bg_image_repeat="repeat" bg_image_size="cover" bg_img_attach="scroll" parallax_sense="30" animation_direction="left-animation" animation_repeat="repeat" video_opts="" bg_override="0" parallax_content_sense="30" fadeout_start_effect="30" overlay_pattern_opacity="80" multi_color_overlay="" multi_color_overlay_opacity="0.6" seperator_type="none_seperator" seperator_position="top_seperator" seperator_shape_size="40" seperator_svg_height="60" seperator_shape_background="#ffffff" seperator_shape_border="none" seperator_shape_border_width="1" icon_type="no_icon" icon_size="32" icon_style="none" icon_color_border="#333333" icon_border_size="1" icon_border_radius="500" icon_border_spacing="50" img_width="48" ult_hide_row_large_screen="off" ult_hide_row_desktop="off" ult_hide_row_tablet="off" ult_hide_row_tablet_small="off" ult_hide_row_mobile="off" ult_hide_row_mobile_large="off"][vc_column width="2/3"][grandway_promo_title title="Contact Form" css=".vc_custom_1441379306610{padding-bottom: 25px !important;}" dot=""][grandway_cform]
[/grandway_cform][/vc_column][vc_column width="1/3"][grandway_promo_title title="Information" css=".vc_custom_1441379315375{padding-bottom: 25px !important;}" dot=""][grandway_get_in_touch address="Address: 455 Martinson, Los Angeles" phone="Phone: 8 (043) 567 - 89 - 30" fax="Fax: 8 (057) 149 - 24 - 64" skype="Skype: companyname" email="E-mail: support@email.com" weekend="Weekend: from 9 am to 6 pm"][/vc_column][/vc_row]
CONTENT;

        array_unshift($data, $template);

        return $data;
    }

}
