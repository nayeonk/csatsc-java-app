<?php
    global $PhoenixData;
    $PHT_page_comments = isset($PhoenixData['page_display_comments']) ?$PhoenixData['page_display_comments'] : 0;
?>
<div class="comments general-font-area">

<?php if (post_password_required()) : ?>
    <p><?php _e('Post is password protected. Enter the password to view any comments.', 'garndway'); ?></p>
</div>
<?php return; endif; ?>

<?php
$PHT_list_comments_args = array(
    'type'      => 'comment',
    'callback'  => 'PhoenixTeam_Utils::comments_callback',
);

$PHT_commenter = wp_get_current_commenter();

$PHT_form_comments_args = array(
  'id_form'           => 'commentform',
  'id_submit'         => 'commentform-submit',
  'label_submit'      => __('Send Comment', 'garndway'),

  'comment_field' =>  '<p class="text_cont">' .
    '<textarea id="comment" name="comment" cols="45" rows="8" aria-required="true" placeholder="'. _x('Comment', 'noun', 'garndway') .'"  class="input-def-textarea" />' .
    '</textarea></p>',

  'must_log_in' => '<p class="text_cont">' .
    sprintf(
      __('You must be <a href="%s">logged in</a> to post a comment.', 'garndway'),
      wp_login_url(apply_filters('the_permalink', get_permalink()))
    ) . '</p>',

  'logged_in_as' => '<p class="text_cont">' .
    sprintf(
    __('Logged in as <a href="%1$s">%2$s</a>. <a href="%3$s" title="Log out of this account">Log out?</a>', 'garndway'),
      admin_url('profile.php'),
      $user_identity,
      wp_logout_url(apply_filters('the_permalink', get_permalink()))
    ) . '</p>',

  'comment_notes_before' => null,

  'comment_notes_after' => null,

  'fields' => apply_filters( 'comment_form_default_fields', array(

    'author' =>
      '<p class="text_cont">' .
      '<input id="author" name="author" type="text" value="' . esc_attr($PHT_commenter['comment_author']) .
      '" size="30"' . ($req ? 'required' : null) . ' placeholder="'.__( 'Name', 'garndway') . ($req ? '*' : null) .'" class="input-def" /></p>',

    'email' =>
      '<p class="text_cont">'.
      '<input id="email" name="email" type="text" value="' . esc_attr(  $PHT_commenter['comment_author_email'] ) .
      '" size="30"' . ($req ? 'required' : null) . ' placeholder="' . __('E-mail', 'garndway') . ($req ? '*' : null) .'" class="input-def" /></p>',

    'url' =>
      '<p class="text_cont">' .
      '<input id="url" name="url" type="text" value="' . esc_attr($PHT_commenter['comment_author_url']) .
      '" size="30" placeholder="'. __('Web Site', 'garndway').'" class="input-def" /></p>'
    )
  ),
);

if (have_comments()) : ?>
    <h3><?php comments_number(); ?> <?php _e('on', 'garndway'); ?> "<?php echo esc_html(get_the_title()); ?>"</h3>
    <!-- <ul> -->
    <?php wp_list_comments($PHT_list_comments_args); // Custom callback ?>
    <!-- </ul> -->

<?php
    // Page comments
    if ($PHT_page_comments && is_page() && !comments_open()) {
        echo '<p>'. __('Comments are closed here.', 'garndway') .'</p>';
    }
?>

<?php elseif ( !comments_open() && !is_page() && post_type_supports( get_post_type(), 'comments' ) ) : ?>
    <p><?php _e('Comments are closed here.', 'garndway'); ?></p>
<?php endif; ?>

<?php next_comments_link(); ?>

<?php comment_form($PHT_form_comments_args); ?>

</div>
