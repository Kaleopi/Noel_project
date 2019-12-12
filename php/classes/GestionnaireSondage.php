<?php
spl_autoload_register (function ($class) {
	require $class.'.php';
});

class GestionnaireSondage {
    private static $json_dir = '../../json/sondages/';
    private $sondages;

    public function __construct() {
        $s=array();
        $r=array();
        $i=array();
        $dir = new DirectoryIterator(self::$json_dir);
        foreach($dir as $file) {
            if(!$file->isDot()) {
								// echo $file->getFilename();
                $s[] = Sondage::fromJSON($file->getFilename());
								// var_dump($s[count($s)-1]);
								$r[] = $s[count($s)-1]->getResponsable()->getId();
                $i[] = $s[count($s)-1]->getId();
            }
        }
        $this->sondages = array_map(null, $i, $r, $s);
				// var_dump($this->sondages);
    }

    public function listerSondages() {
        foreach ($this->sondages as $sondage) {
            echo $sondage[0]."- ".$sondage[2]->getTitre()."     \n";
        }
    }

    public function trouverId($id) {
        foreach($this->sondages as $sondage) {
            if($sondage[0]==$id)
                return $sondage[2];
        }
        return null;
    }

    public function trouverResponsable($id) {
        $res = array();
        foreach($this->sondages as $sondage) {
            if($sondage[1]==$id)
                $res[] = $sondage[2];
        }
        return $res;
    }
}
