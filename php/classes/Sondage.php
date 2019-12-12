<?php
spl_autoload_register (function ($class) {
	require $class.'.php';
});

class Sondage implements JsonSerializable {
	private static $id_cpt=1000;
	private $id;
	private $titre;
	private $questions;
	private $reponses;
	private $nbUtilisateurs;
	private $responsable;
	private static $json_dir = '../../json/sondages/';

	public function __construct($titre, $questions, $responsable, $id) {
		if($id==-1) {
				$this->id=self::$id_cpt;
				self::$id_cpt+=1;
		}else{
				$this->id = $id;
		}
		$this->titre=$titre;
		if($questions!=null)
			for($i=0; $i<count($questions); $i++)
			    $this->questions[]=$questions[$i];
		$this->responsable=$responsable;
		$this->reponses=array();
		$this->nbUtilisateurs=0;
	}

	public function getId() {
		return $this->id;
	}

	public function getTitre() {
		return $this->titre;
	}

	public function getResponsable() {
		return $this->responsable;
	}

	public function getQuestions() {
		return $this->questions;
	}

	public function getNbUtilisateurs() {
		return $this->nbUtilisateurs;
	}

	public function getReponses() {
		return $this->reponses;
	}

	public function incrementNbUtilisateurs() {
		$this->nbUtilisateurs+=1;
	}

	public function ajouterQuestion($intitule, $choix) {
		$q=new Question($intitule, $choix);
		$this->questions[]=$q;
	}

	public function ajouterReponse($r) {
	    $this->reponses[]=$r;
	    $this->incrementNbUtilisateurs();
	}

	public function jsonSerialize() {
		$q=array();
		$r=array();
		for($i=0; $i<count($this->questions); $i++)
			$q[$i]=$this->questions[$i]->jsonSerialize();
        return array('id'=>$this->id, 'titre'=>$this->titre, 'responsable'=>$this->responsable->jsonSerialize(), 'nbUtilisateurs'=>$this->nbUtilisateurs, 'questions'=>$q);
   	}

    public function creerArbo() {
        if (!is_dir(self::$json_dir.'sondage'.$this->id)) {
            mkdir(self::$json_dir.'sondage'.$this->id);
            $fichier = fopen(self::$json_dir.'sondage'.$this->id.'/sondage.json', "w");
            $json=json_encode($this, JSON_PRETTY_PRINT);
            fwrite($fichier, $json);
            fclose($fichier);

            mkdir(self::$json_dir.'sondage'.$this->id.'/reponses');
            for($i=0; $i<count($this->reponses); $i++) {
                $fichier = fopen(self::$json_dir.'sondage'.$this->id.'/reponses/'.$this->reponses[$i]->getUtilisateur()->getId().".json", "w");
                $json=json_encode($this->reponses[$i], JSON_PRETTY_PRINT);
                fwrite($fichier, $json);
                fclose($fichier);
            }

            return true;
        }else{
            return false;
        }
    }

	public static function fromJSON($nom_json_dir) {
        $fichier = fopen(self::$json_dir.$nom_json_dir.'/sondage.json', "r");
        $json = fread($fichier, filesize(self::$json_dir.$nom_json_dir.'/sondage.json'));
				// echo $json;
				$array = json_decode($json, true);
				// echo "array<br>";
				// var_dump($array);

        $q=array();
				for($i=0; $i<count($array['questions']);$i++)
						$q[]=Question::fromJSON(json_encode($array['questions'][$i]));
				// echo "titre<br>";
				// echo $array['titre'];
				$res = new Sondage($array['titre'], $q, Utilisateur::fromJSON(json_encode($array['responsable'])), $array['id']);
        $dir = new DirectoryIterator(self::$json_dir.$nom_json_dir."/reponses");
        foreach($dir as $file) {
            if(!$file->isDot()) {
                $fichier = fopen($file->getPathname(), "r");
                $json = fread($fichier, filesize($file->getPathname()));
                $r = Reponse::fromJSON($json);
                $res->ajouterReponse($r);
            }
        }
				// echo "from<br>";
				// echo $res;
        return $res;
	}

	public function __toString() {
	    $str = "<b>".$this->titre."#".$this->id."</b>";
	    for($i=0; $i<count($this->questions); $i++) {
	        $str = $str.$this->questions[$i];
	    }
	    return $str;
	}
}
