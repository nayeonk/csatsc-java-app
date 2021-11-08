<?php
/**
 * Twitter Widget Class
 */
class PhoenixTeam_Widget_GetInTouch extends WP_Widget {

    function __construct() {
        parent::__construct(
        THEME_SLUG . '-get-in-touch',
        THEME_NAME . ' - Get in Touch',
        array('description' => __("This widget displays your contact information.", 'grandway'))
      );
    }

    function widget ( $args, $instance )
    {
        extract($args);
        $title = isset($instance['title']) ? apply_filters('PhoenixTeam_Widget_GetInTouch', $instance['title'] ) : null;
        $address = isset($instance['address']) ? $instance['address'] : null;
        $phone = isset($instance['phone']) ? $instance['phone'] : null;
        $fax = isset($instance['fax']) ? $instance['fax'] : null;
        $skype = isset($instance['skype']) ? $instance['skype'] : null;
        $email = isset($instance['email']) ? $instance['email'] : null;
        $weekend = isset($instance['weekend']) ? $instance['weekend'] : null;

        echo $args['before_widget'];

            if ($title) {
                echo $args['before_title'];
                echo esc_html( $title );
                echo $args['after_title'];
            }

            echo '<ul class="contact-footer contact-composer">';

            if ($address) echo '<li><i class="icon-map-pin"></i> <span>'.esc_html( $address ).'</span></li>';
            if ($phone) echo '<li><i class="icon-phone"></i> <span>'.esc_html( $phone ).'</span></li>';
            if ($fax) echo '<li><i class="icon-printer"></i> <span>'.esc_html( $fax ).'</span></li>';
            if ($skype) echo '<li><i class="icon-megaphone"></i> <span>'.esc_html( $skype ).'</span></li>';
            if ($email) echo '<li><i class="icon-envelope"></i> <span>'.antispambot($email).'</span></li>';
            if ($weekend) echo '<li><i class="icon-key"></i> <span>'.esc_html( $weekend ).'</span></li>';

            echo '</ul>';

        echo $args['after_widget'];
    }

    function update ( $new_instance, $old_instance )
    {
        $instance = $old_instance;

        /* Strip tags for title and name to remove HTML (important for text inputs). */
        $instance['title'] = strip_tags( $new_instance['title'] );
        $instance['address'] = strip_tags($new_instance['address']);
        $instance['phone'] = strip_tags($new_instance['phone']);
        $instance['fax'] = strip_tags($new_instance['fax']);
        $instance['skype'] = strip_tags($new_instance['skype']);
        $instance['email'] = strip_tags($new_instance['email']);
        $instance['weekend'] = strip_tags($new_instance['weekend']);

        return $instance;
    }

    function form ( $instance )
    {
        $defaults = array(
            'title' => __('Get in Touch', 'grandway'),
            'address' => null,
            'phone' => null,
            'fax' => null,
            'skype' => null,
            'email' => null,
            'weekend' => null,
        );

        $instance = wp_parse_args( $instance, $defaults );

        extract($instance);

        $fields = null;

        foreach ($instance as $key => $value) {
            $input_title = ucfirst($key) . ': ';
            $fields .= '<p><label for="'.$this->get_field_id($key).'"> ' .
                        esc_html($input_title) .
                        '<input class="widefat" type="text" ' .
                        ${$key} .
                        ' id="'.$this->get_field_id($key).'"' .
                        'name="'.$this->get_field_name($key).'" value="'. esc_attr($value) .'" />' .
                        '</label></p>';
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
          'p' => array(),
        );

        echo wp_kses($fields, $kses);
    }

}
