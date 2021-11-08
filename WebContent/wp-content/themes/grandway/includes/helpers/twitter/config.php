<?php
require_once 'codebird.php';

// Twitter App Consumer Key
$consumer_key = '2S66eOki73WdrkG2XgVQ';
// Twitter App Consumer Secret
$consumer_secret = 'e7hQbvYUWhd8v2cNx489XxBG7EnM54ZJnJq83GVw';
// Twitter App Access Token
$access_token = '1395807720-LNsy6yfB0CxzxDkV7y508yW4wk6PyJlc8trtGmV';
// Twitter App Access Token Secret
$access_secret = "MVBFXPIDLVz4yRUgasFM120X8lt4TZRp7hif0t3KQIw";

$cb = PhoenixTeam_Codebird::getInstance();
$GLOBALS[THEME_SLUG .'_twitter'] = $cb;
$cb->setConsumerKey($consumer_key, $consumer_secret);
$cb->setToken($access_token, $access_secret);
