<?php

$this->sections[] = array(
    'title'     => __('Other', 'monolite-core'),
    'icon'      => ' el-icon-puzzle',
    'fields'    => array(

        array(
            'id'        => 'contact_mail',
            'type'      => 'multi_text',
            'validate'  => 'email',
            'title'     => __('Contact Form E-mail', 'monolite-core'),
            'subtitle'  => __('Your email(s) for contact form widget.', 'monolite-core'),
            'add_text' => __('Add more email addresses', 'monolite-core'),
            'default'   => array(get_option('admin_email'))
        ),

        array(
            'id'        => 'show_adminbar',
            'type'      => 'button_set',
            'title'     => __('Show WordPress Admin Bar?', 'monolite-core'),
            'subtitle'  => __('You can disable it for <i><b>all users</b></i> here.', 'monolite-core'),
            'options'   => array(
                1       => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default'   => 1
        ),

        array(
            'id'        => 'multisocials',
            'type'      => 'button_set',
            'title'     => __('Use Multisocial Buttons?', 'monolite-core'),
            'subtitle'  => __('Use multisocial (dynamically defined) or predifined social buttons for Team Members', 'monolite-core'),
            'options' => array(
                1  => '&nbsp;I&nbsp;',
                0  => 'O'
            ),
            'default'   => 1
        ),

        array(
            'id'        => 'analytics_switch',
            'type'      => 'button_set',
            'title'     => __('Enable Google Analytics?', 'monolite-core'),
            'subtitle'  => __('Enables/Disables GA Tracking Code for your website.', 'monolite-core'),
            'options'   => array(
                1        => '&nbsp;I&nbsp;',
                0       => 'O',
            ),
            'default' => 0
        ),

            array(
                'id'        => 'ga_id',
                'type'      => 'text',
                'required'  => array('analytics_switch', '=', '1'),
                'title'     => __('Google Analytics Property ID', 'monolite-core'),
                'desc'  => __('Place here your Google Analytics Property ID. It should look like `UA-XXXXX-X`.<br />You can find it inside your Google Analytics Dashboard.', 'monolite-core'),
                'default'   => null
            ),


        array(
            'id'        => 'css_code',
            'type'      => 'ace_editor',
            'title'     => __('Custom CSS', 'monolite-core'),
            'subtitle'  => __('Paste your CSS code her.', 'monolite-core'),
            'mode'      => 'css',
            'validate'  => 'css',
            'theme'     => 'chrome',
            'desc'      => 'CSS will be enqueued in header.',
            'default'   => ''
        ),

        array(
            'id'        => 'js_code',
            'type'      => 'ace_editor',
            'title'     => __('Custom JavaScript', 'monolite-core'),
            'subtitle'  => __('Paste your JavaScript code here.', 'monolite-core'),
            'mode'      => 'javascript',
            'validate'  => 'js',
            'theme'     => 'chrome',
            'desc'      => 'JS code will be enqueued before &lt/body&gt; tag.',
            'default'   => ''
        ),
    ),
);
