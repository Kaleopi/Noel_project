<?php
class Question implements JsonSerializable {
	private $intitule;
	private $choix;
	
	public function __construct($intitule, $choix) {
		$this->intitule=$intitule;
		if($choix!=null)
			for($i=0; $i<count($choix); $i++)
			    $this->choix[]=$choix[$i];
	}
	
	public function getIntitule() {
		return $this->intitule;
	}
	
	public function getChoix() {
		return $this->choix;
	}
	
	public function setIntitule($intitule) {
		$this->intitule=$intitule;
	}
	
	/*public function setChoix($choix) {
		$this->choix=array($choix);
	}*/
	
	public function ajouterChoix($un_choix) {
		$this->choix[]=$un_choix;
	}

	public function jsonSerialize() {
		$r=array();
        return array('intitule'=>$this->intitule, 'choix'=>$this->choix);
   	}

	public static function fromJSON($json) {
		$array = json_decode($json, true);
		return new Question($array['intitule'], $array['choix']);
	}
	
	public function __toString() {
		$str = "<p> Question : ".$this->intitule."<br>";
		$i = 1;
		foreach($this->choix as $ch) {
			$str = $str.$i."- ".$ch;
			$i++;
			$str = $str."<br>";
		}
		$str = $str."</p>";
		return $str;
	}
}