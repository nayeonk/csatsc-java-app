<?php
/**
 * Contact Form Widget Class
 */

class PhoenixTeam_Widget_ContactForm extends WP_Widget {

    public function __construct ()
    {
        parent::__construct(
          THEME_SLUG . '-contact-form',
          THEME_NAME . ' - ' . __('Contact Form', 'grandway'),
          array('description' => __("Contact Form Widget.", 'grandway')) );
    }

    public function widget ( $args, $instance )
    {
        $script_data = array(
            'THEME_SLUG' => THEME_SLUG,
            'Sending' => __('Sending...', 'grandway'),
            'fillAllFields' => __('Fill all form fields please', 'grandway'),
            'nonce' => wp_create_nonce(THEME_SLUG. "-cf-security")
        );
        wp_localize_script(THEME_SLUG . '-contact-form', 'Phoenix', $script_data);
        wp_enqueue_script(THEME_SLUG . '-contact-form');

        extract($args);
        $title = isset($instance['title']) ? apply_filters('PhoenixTeam_Widget_ContactForm', $instance['title']) : null;

        echo $args['before_widget'];

        if ($title) {
            echo $args['before_title'];
            echo esc_html( $title );
            echo $args['after_title'];
        }
?>
        <div class="row">
            <form action="<?php echo admin_url( 'admin-ajax.php' ); ?>" data-form="contactForm" method="post">
                <div class="col-lg-4">
                    <p class="text_cont"><input type="text" name="name" placeholder="<?php _e('Name', 'grandway'); ?>" class="input-cont-textarea"></p>
                </div>
                <div class="col-lg-4">
                    <p class="text_cont"><input type="email" name="email" placeholder="<?php _e('E-mail', 'grandway'); ?>" class="input-cont-textarea"></p>
                </div>
                <div class="col-lg-4">
                    <p class="text_cont"><input type="text" name="subject" placeholder="<?php _e('Subject', 'grandway'); ?>" class="input-cont-textarea"></p>
                </div>

                <div class="col-lg-12">
                    <div class="alert alert-danger error contact-form-error nameError"><i class="icon-cancel"></i> <?php _e("Oh snap! Name field can't stay empty.", 'grandway'); ?></div>
                    <div class="alert alert-danger error contact-form-error emailError"><i class="icon-cancel"></i> <?php _e("Oh snap! There was a mistake when writing a e-mail.", 'grandway'); ?></div>
                    <div class="alert alert-danger error contact-form-error subjectError"><i class="icon-cancel"></i> <?php _e("Oh snap! Subject field can't stay empty.", 'grandway'); ?></div>
                </div>

                <div class="col-lg-12">
                    <p class="text_cont"><textarea name="message" placeholder="<?php _e('Message', 'grandway'); ?>" id="message" class="input-cont-textarea" cols="40" rows="10"></textarea></p>
                    <div class="alert alert-danger error contact-form-error messageError"><i class="icon-cancel"></i> <?php _e("Oh snap! This field can't stay empty.", 'grandway'); ?></div>
                    <div class="alert alert-danger error contact-form-fail messageError"><i class="icon-cancel"></i> <?php _e("Error. You message wasn't sent. Something wrong happen.", 'grandway'); ?></div>
                    <div class="alert alert-success success contact-form-success"><i class="icon-ok"></i> <?php _e("Well done! You message is successfully sent.", 'grandway'); ?></div>
                </div>
                <div class="col-lg-12"><p><input type="submit" class="btn btn-default contact-form-send" value="<?php _e('Send message', 'grandway'); ?>" /></p></div>
            </form>
        </div>
<?php
        echo $args['after_widget'];
    }

    public function update( $new_instance, $old_instance )
    {
        $instance = $old_instance;
        $instance['title'] = strip_tags($new_instance['title']);
        $instance['email'] = strip_tags($new_instance['email']);

        return $instance;
    }

    public function form( $instance )
    {
        global $PhoenixData;
        $title = isset($instance['title']) ? $instance['title'] : null;
        $email = isset($PhoenixData['contact_mail']) ? $PhoenixData['contact_mail'] : get_option('admin_email');
?>
        <p><label for="<?php echo esc_attr($this->get_field_id('title')); ?>">Title: <input id="<?php echo sanitize_html_class($this->get_field_id('title')); ?>" name="<?php echo esc_attr($this->get_field_name('title')); ?>" type="text" value="<?php echo esc_attr($title); ?>" /></label></p>
        <p>
            <?php _e('Email will be sent on', 'grandway'); ?> <u><?php echo antispambot($email); ?></u> <?php _e('and other addresses that you have chosen.', 'grandway') ?><br/>
            <a href="<?php echo admin_url() . '?page='. THEME_SLUG .'_options&tab=7' ?>"><?php _e('Change', 'grandway'); ?></a><br/><br/>
        </p>
<?php
    }
}
