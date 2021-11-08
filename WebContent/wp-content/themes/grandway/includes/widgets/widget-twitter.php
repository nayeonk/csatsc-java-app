<?php
require_once THEME_DIR . '/includes/helpers/twitter/config.php';

/**
 * Twitter Widget Class
 */
class PhoenixTeam_Widget_Twitter extends WP_Widget {

  function __construct() {
    parent::__construct(
      THEME_SLUG . '-twitter',
      THEME_NAME . ' - Twitter',
      array('description' => __("Show recent tweets.", 'grandway'))
  );
  }

    function widget ($args, $instance )
    {
        extract($args);
        $title = apply_filters('PhoenixTeam_Widget_Twitter', $instance['title']);
        $username = ($instance['username']) ?  $instance['username'] : null;
        $number = isset($instance['qty']) ? $instance['qty'] : null;

        static $counter = 1; // IDs for Widget;
        // echo $args['before_widget']; // It's the right way, but doesn't work with VC :(
        echo '<div id="'. THEME_SLUG .'-twitter-'. esc_attr($counter) .'" class="footer-twitter widget_'. THEME_SLUG .'-twitter">';

            if ($title)
              echo '<h4 class="widget-title">'. esc_html($title) .'</h4>';

            // $tweets = $this->get_tweets($args['widget_id'], $instance); // Good old, but doesn't work with VC
            $tweets = $this->get_tweets(THEME_SLUG .'-twitter-'. $counter, $instance);

            if(!empty($tweets['tweets']) && empty($tweets['tweets']->errors)) {

                $user = current($tweets['tweets']);

                if (is_object($user))
                  $user = $user->user;

                echo '<ul class="tweet_list">';

                $checker = 0;
                foreach($tweets['tweets'] as $tweet) {
                  if ($checker <= $number) {
                    if (isset($tweet->text)) {
                      if (is_object($tweet)) {
                        $avatar = $user->profile_image_url;
                        $username = $user->screen_name;
                        $user_url = 'https://twitter.com/' . $username;
                        $tweet_text = htmlentities($tweet->text, ENT_QUOTES, 'UTF-8');
                        $tweet_text = make_clickable($tweet_text);
                        $tweet_text = popuplinks($tweet_text);
                        if ($tweet_text) {
                          echo
                            '<li>
                              <a class="tweet_avatar" href="'. esc_url($user_url) .'">
                                <img src="'. esc_url($avatar) .'" alt="'. esc_attr($username) .'" title="'. esc_attr($username) .'">
                              </a>
                              <span class="tweet_text">'. wp_kses_post($tweet_text) .'</span>
                            </li>';
                          $checker++;
                        }
                      }
                    } else {
                      if ($checker == 0) {
                        echo '<li><span class="content">' . __("There's no tweets in your feed...", 'grandway') . '</span></li>';
                        break;
                      }
                      break;
                    }
                  }
                }

              echo '</ul>';

            } elseif ($tweets['tweets']->errors) {
              _e('Authentication failed! Please check your Twitter app data.', 'grandway');
            } elseif (!$tweets['tweets']) {
              _e("There's no tweets there", 'grandway');
            }

        echo $args['after_widget'];

        $counter++;
    }

    function update ($new_instance, $old_instance)
    {
        $instance = $old_instance;

        /* Strip tags for title and name to remove HTML (important for text inputs). */
        $instance['title'] = strip_tags($new_instance['title']);
        $instance['username'] = strip_tags($new_instance['username']);
        $instance['qty'] = strip_tags($new_instance['qty']);

        return $instance;
    }

    function form ($instance)
    {
        $defaults = array(
            'qty' => 3
      );

        $instance = wp_parse_args($instance, $defaults);

        $title = isset($instance['title']) ? $instance['title'] : null;
        $user = isset($instance['username']) ? $instance['username'] : null;
        $number = isset($instance['qty']) ? $instance['qty'] : null;
?>
        <p>
          <label for="<?php echo esc_attr($this->get_field_id('title')); ?>"><?php _e('Title', 'grandway') ?>:</label> <input class="widefat" id="<?php echo sanitize_html_class($this->get_field_id('title')); ?>" name="<?php echo esc_attr($this->get_field_name('title')); ?>" type="text" value="<?php echo esc_attr($title); ?>" />
        </p>
        <p>
          <label for="<?php echo esc_attr($this->get_field_id('username')); ?>"><?php _e('Twitter Username:', 'grandway') ?></label><input class="widefat" id="<?php echo sanitize_html_class($this->get_field_id('username')); ?>" name="<?php echo esc_attr($this->get_field_name('username')); ?>" type="text" value="<?php echo esc_attr($user); ?>" /></p>
        <p><label for="<?php echo esc_attr($this->get_field_id('qty')); ?>"><?php _e('Tweets to Show:', 'grandway') ?></label>
          <select class="widefat" id="<?php echo sanitize_html_class($this->get_field_id('qty')); ?>" name="<?php echo esc_attr($this->get_field_name('qty')); ?>">
        <?php
            for ($i = 1; $i <= 10; $i++) {
                echo ' <option ';
                if ($number == $i) {echo 'selected="selected"';}
                echo ' value="'. esc_attr($i) .'">' . esc_html($i) .'</option>';
            }
?>
        </select></p>
<?php
    }

    function retrieve_tweets ($widget_id, $instance)
    {
        $cb = $GLOBALS[THEME_SLUG .'_twitter'];
        $timeline = $cb->statuses_userTimeline('screen_name=' . $instance['username']. '&count=' . $instance['qty'] . '&exclude_replies=true');

        return $timeline;
    }

    function save_tweets ($widget_id, $instance)
    {
        $timeline = $this->retrieve_tweets($widget_id, $instance);
        $tweets = array('tweets' => $timeline, 'update_time' => time() + (60 * 1 ));
        update_option('awesome_tweets_' . $widget_id, $tweets);

        return $tweets;
    }

    function get_tweets ($widget_id, $instance)
    {
        $tweets = get_option('awesome_tweets_' . $widget_id);

        if(empty($tweets) || time() > $tweets['update_time'])
            $tweets = $this->save_tweets($widget_id, $instance);

        return $tweets;
    }

}
