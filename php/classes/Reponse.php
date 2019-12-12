<?php
spl_autoload_register (function ($class) {
	require $class.'.php';
});

class Reponse implements JsonSerializable {
	private $utilisateur; //Utilisateur
	private $choix; //tableau int

	public function __construct($u, $c){
		$this->utilisateur=$u;
		$this->choix=$c;
	}

	public function getUtilisateur() {
		return $this->utilisateur;
	}

	public function getChoix() {
		return $this->choix;
	}

	public function setChoix($c) {
		$this->choix[]=$c;
	}	

	public function jsonSerialize() {
		return array('utilisateur'=>$this->utilisateur->jsonSerialize(), 'choix'=>$this->choix);
	}

	public static function fromJSON($json) {
		$array = json_decode($json, true);
		return new Reponse(Utilisateur::fromJSON(json_encode($array['utilisateur'])), $array['choix']);
	}

	public function __toString() {
		return $this->utilisateur->getPseudo()." a choisi l'option ".$this->choix;
	}
}