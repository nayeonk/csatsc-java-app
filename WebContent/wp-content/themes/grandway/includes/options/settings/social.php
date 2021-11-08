<?php

$this->sections[] = array(
    'icon'      => 'el-icon-network',
    'title'     => __('Social', 'monolite-core'),
    'fields'    => array(

        array(
            'id'        => 'rss_text',
            'type'      => 'text',
            'title'     => __('RSS Feed Text', 'monolite-core'),
            'desc'      => __("Text for RSS feed on top of header.", 'monolite-core'),
            'default'   => __("Subscribe to be notified for updates:", 'monolite-core')
        ),

        array(
            'id'        => 'rss',
            'type'      => 'text',
            'validate'  => 'url',
            'title'     => __('RSS Feed URL', 'monolite-core'),
            'desc'  => __("If you don't want to show RSS icon, make this field blank.", 'monolite-core'),
            'default'   => $this->getRSS()
        ),

        array(
            'id'        => 'header_social',
            'type'      => 'button_set',
            'title'     => __('Header Social Icons', 'monolite-core'),
            'subtitle'  => __('Enable/Disable social icons in header.', 'monolite-core'),
            'options' => array(
                1  => '&nbsp;I&nbsp;',
                0  => 'O'
            ),
            'default'   => 1
        ),

        // Section Header Social Icons
        array(
           'id' => 'section-header-socials',
           'type' => 'section',
           'subtitle' => null,
           'indent' => true
        ),

            array(
                'id'        => 'facebook',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Facebook URL', 'monolite-core'),
                'default'   => 'http://facebook.com/'
            ),

            array(
                'id'        => 'twitter',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Twitter URL', 'monolite-core'),
                'default'   => 'http://twitter.com/'
            ),

            array(
                'id'        => 'dribbble',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Dribbble URL', 'monolite-core'),
                'default'   => 'http://dribble.com/'
            ),

            array(
                'id'        => 'flickr',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Flickr URL', 'monolite-core'),
                'default'   => 'http://flickr.com/'
            ),

            array(
                'id'        => 'github',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('GitHub URL', 'monolite-core'),
                'default'   => 'http://github.com/'
            ),

            array(
                'id'        => 'instagram',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Instagram URL', 'monolite-core'),
                'default'   => 'http://instagram.com/'
            ),

            array(
                'id'        => 'pinterest',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Pinterest URL', 'monolite-core'),
                'default'   => 'http://pinterest.com/'
            ),

            array(
                'id'        => 'youtube',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Youtube URL', 'monolite-core'),
                'default'   => 'http://youtube.com/'
            ),

            array(
                'id'        => 'apple',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Apple URL', 'monolite-core'),
                'default'   => 'http://apple.com/'
            ),

            array(
                'id'        => 'linkedin',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Linkedin URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'googleplus',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Google Plus URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'vk',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Vk URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'tumblr',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Tumblr URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'foursquare',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Foursquare URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'android',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Android URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'windows',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Windows URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'behance',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Behance URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'delicious',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Delicious URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'skype',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Skype URL', 'monolite-core'),
                'default'   => null
            ),

            array(
                'id'        => 'vimeo',
                'type'      => 'text',
                'validate'  => 'url',
                'required'  => array('header_social', '=', 1),
                'title'     => __('Vimeo URL', 'monolite-core'),
                'default'   => null
            ),

        array(
            'id'     => 'section-header-socials-end',
            'type'   => 'section',
            'indent' => false
        )
    )
);
