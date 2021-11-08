<?php
/**
 * Navigation Widget Class
 */
class PhoenixTeam_Widget_Socials extends WP_Widget {

    private $socials = array();

    public function __construct ()
    {
        $this->set_socials();
        parent::__construct(
          THEME_SLUG . '-social-buttons',
          THEME_NAME . ' - Social Buttons',
          array('description' => __("Social Buttons Widget.", 'grandway'))
        );
    }

    private function set_socials ()
    {
        global $PhoenixData;
        $this->socials['facebook'] = isset($PhoenixData['facebook']) ? $PhoenixData['facebook'] : null;
        $this->socials['twitter'] = isset($PhoenixData['twitter']) ? $PhoenixData['twitter'] : null;
        $this->socials['tumblr'] = isset($PhoenixData['tumblr']) ? $PhoenixData['tumblr'] : null;
        $this->socials['linkedin'] = isset($PhoenixData['linkedin']) ? $PhoenixData['linkedin'] : null;
        $this->socials['google'] = isset($PhoenixData['googleplus']) ? $PhoenixData['googleplus'] : null;
        $this->socials['pinterest'] = isset($PhoenixData['pinterest']) ? $PhoenixData['pinterest'] : null;
        $this->socials['instagram'] = isset($PhoenixData['instagram']) ? $PhoenixData['instagram'] : null;
        $this->socials['flickr'] = isset($PhoenixData['flickr']) ? $PhoenixData['flickr'] : null;
        $this->socials['youtube'] = isset($PhoenixData['youtube']) ? $PhoenixData['youtube'] : null;
        $this->socials['foursquare'] = isset($PhoenixData['foursquare']) ? $PhoenixData['foursquare'] : null;
        $this->socials['apple'] = isset($PhoenixData['apple']) ? $PhoenixData['apple'] : null;
        $this->socials['android'] = isset($PhoenixData['android']) ? $PhoenixData['android'] : null;
        $this->socials['windows'] = isset($PhoenixData['windows']) ? $PhoenixData['windows'] : null;
        $this->socials['behance'] = isset($PhoenixData['behance']) ? $PhoenixData['behance'] : null;
        $this->socials['dribbble'] = isset($PhoenixData['dribbble']) ? $PhoenixData['dribbble'] : null;
        $this->socials['delicious'] = isset($PhoenixData['delicious']) ? $PhoenixData['delicious'] : null;
        $this->socials['skype'] = isset($PhoenixData['skype']) ? $PhoenixData['skype'] : null;
        $this->socials['github'] = isset($PhoenixData['github']) ? $PhoenixData['github'] : null;
        $this->socials['vimeo'] = isset($PhoenixData['vimeo']) ? $PhoenixData['vimeo'] : null;
        $this->socials['vk'] = isset($PhoenixData['vk']) ? $PhoenixData['vk'] : null;
    }

    public function widget ( $args, $instance )
    {
        extract($args);
        extract($this->socials);
        $title = apply_filters('PhoenixTeam_Widget_Socials', $instance['title']);

        foreach ($this->socials as $key => $value) {
            if ($value)
                ${$key} = empty( $instance[$key] ) ? '0' : '1';
        }

        echo $args['before_widget'];

        if ($title) {
            echo $args['before_title'];
            echo esc_html( $title );
            echo $args['after_title'];
        }

        echo '<ul class="soc-footer">';

        foreach ($this->socials as $key => $value) {
            switch ($key) {
                case 'google':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'-plus-square"></i></a></li>';
                    break;
                case 'vk':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'instagram':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'flickr':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'foursquare':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'apple':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'android':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'windows':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'dribbble':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'delicious':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                case 'skype':
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'"></i></a></li>';
                    break;
                default:
                    if ($value && ${$key} !== '0')
                        echo '<li><a href="'.esc_url($value).'"><i class="fa fa-'.esc_attr($key).'-square"></i></a></li>';
            }
        }

        echo '</ul>';

        echo $args['after_widget'];
    }

    public function update( $new_instance, $old_instance )
    {
        $instance = $old_instance;
        $instance['title'] = strip_tags($new_instance['title']);

        foreach ($this->socials as $key => $value) {
            if ($value)
                $instance[$key] = $new_instance[$key] ? 1 : 0;
        }

        return $instance;
    }

    public function form( $instance )
    {
        /* Set up some default widget settings. */
        $defaults = array();
        foreach ($this->socials as $key => $value) {
            if ($value)
                $defaults[$key] = true;
        }

        $instance = wp_parse_args( $instance, $defaults );

        $title = isset($instance['title']) ? $instance['title'] : '';
        $checkboxes = null;

        foreach ($this->socials as $key => $value) {
            if ($value) {
                ${$key} = $instance[$key] ? 'checked="checked"' : null;

                if ($key == 'google') {
                    $box_title = ucfirst($key) . ' Plus';
                } else {
                    $box_title = ucfirst($key);
                }

                $checkboxes .= '<input class="checkbox" type="checkbox" ' .
                                ${$key} .
                                ' id="'.$this->get_field_id($key).'"' .
                                'name="'.$this->get_field_name($key).'" />' .
                                '<label for="'.$this->get_field_id($key).'"> ' .
                                esc_html($box_title) .
                                '</label><br/>';
            }
        }

        $kses = array(
          'input' => array(
            'class' => array(),
            'type' => array(),
            'id' => array(),
            'name' => array(),
            'value' => array(),
          ),
          'label' => array(
            'for' => array(),
          ),
          'br' => array(),
        );
?>
        <div class="social-buttons-item-container">
            <p><label>Title: <input class="widefat" name="<?php echo esc_attr($this->get_field_name('title')) ?>" type="text" value="<?php echo esc_attr($title); ?>" /></label></p>
            <p><?php echo wp_kses($checkboxes, $kses); ?></p>
        </div>
<?php

    }

}
