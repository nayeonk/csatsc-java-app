<?php

new PhoenixTeam_Deps();

class PhoenixTeam_Deps {

    private $config = array('js_composer', 'woocommerce');

    public function __construct ()
    {
        $this->include_tgm();

        $this->config = array_flip($this->config);
        $this->config = array_map(create_function(null, 'return false;'), $this->config);

        $this->set_deps($this->config);
        $this->get_deps($this->config);
    }

    private function include_tgm ()
    {
        require_once THEME_DIR . '/includes/helpers/class-tgm-plugin-activation.php';
        require_once THEME_DIR . '/includes/deps/tgm.php';
    }


    private function set_deps ($deps)
    {
        foreach ($deps as $dep => $val) {
            $this->config[$dep] = $this->is_active($dep);
        }
   }


   private function get_deps ($deps)
   {

        foreach ($deps as $dep => $active) {
            if ($this->config[$dep] == true)
                require_once THEME_DIR . '/includes/deps/settings/'. $dep .'.php';
        }
   }

    // Check if Visual Composer is active
    private function is_active ($dep)
    {
        if (PhoenixTeam_Utils::dep_exists($dep))
            return true;

        return false;
    }

}
