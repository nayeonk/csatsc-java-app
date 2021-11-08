<?php
/**
 * Flickr Widget Class
 */
class PhoenixTeam_Widget_Flickr extends WP_Widget {

    function __construct ()
    {
        parent::__construct( false,  THEME_NAME . ' - Flickr' );
    }

    function widget ( $args, $instance )
    {
        extract($args);
        $title = apply_filters('PhoenixTeam_Widget_Flickr', $instance['title'] );
        $id = ($instance['name']) ? $instance['name'] : '';
        $max_number = ($instance['show']) ? $instance['show'] : 12;
        $head = '';

        echo $args['before_widget'];

            if ($title) {
                echo $args['before_title'];
                echo esc_html( $title );
                echo $args['after_title'];
            }

            static $counter = 1; // IDs for Widget;

            echo '<div class="flickr_widget_wrapper">';
?>
            <div id="flickr-<?php echo esc_attr($counter); ?>"></div>

            <script>
                jQuery(function($) {

                    "use strict";

                    var id = "<?php echo esc_js($id); ?>",
                        limit = <?php echo esc_js($max_number); ?>,
                        flickApi = "http://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?";

                    // Flickr Photostream feed link.
                    $.getJSON(flickApi,
                        {
                            id: id,
                            format: "json"
                        }
                    )
                    .done(
                        function (data) {

                            $.each(data.items,

                                function(i,item) {
                                    // Number of thumbnails to show.
                                    if(i < limit){
                                        // Create images and append to div id flickr and wrap link around the image.
                                        $("<img/>", {'data-no-retina': 1}).attr("src", item.media.m.replace('_m', '_s')).appendTo("#flickr-<?php echo esc_attr($counter); ?>").wrap("<a href='" + item.media.m.replace('_m', '_z') + "' name='"+ item.link + "' title='" +  item.title +"'></a>");
                                    }
                                }
                            );
                        }
                    );

                });
            </script>
<?php
            echo '</div>';

            $counter++;

        echo $args['after_widget'];
    }

    function update ( $new_instance, $old_instance )
    {
        $instance = $old_instance;

        $instance['title'] = strip_tags($new_instance['title']);
        $instance['name'] = strip_tags($new_instance['name']);
        $instance['show'] = strip_tags($new_instance['show']);

        return $instance;
    }

    function form ( $instance )
    {

        $defaults = array(
            'title' => __('Flickr Photos', 'grandway'),
            'name' => '52617155@N08',
            'show' => 6,
        );

        $instance = wp_parse_args( $instance, $defaults );

        extract($instance);
?>
        <p><label><?php _e('Title:', 'grandway') ?> <input class="widefat" name="<?php echo esc_attr($this->get_field_name('title')); ?>"
        type="text" value="<?php echo esc_attr($title); ?>" /></label></p>
        <p>
            <label><?php _e('Flickr ID:', 'grandway') ?><br />
                <input class="widefat" name="<?php echo esc_attr($this->get_field_name('name')); ?>" type="text" value="<?php echo esc_attr($name); ?>" />
            </label><br/>
            <?php _e('To get your Flickr ID use', 'grandway'); ?> <a href="http://idgettr.com/">idgettr.com</a>.
        </p>
        <p><label><?php _e('# of Images to Show:', 'grandway') ?></label>
            <select name="<?php echo esc_attr($this->get_field_name('show')); ?>">
<?php
            for ($i = 3; $i <= 15; $i = $i + 3) {
                echo ' <option ';

                if ($show == $i){
                    echo 'selected="selected"';
                }

                echo ' value="'. esc_attr($i) .'">' . esc_html($i) .'</option>';
            }
?>
            </select>
        </p>
<?php
    }
}
