 <?php
// session_start();
class Utilisateur implements JsonSerializable {
	const NOM_SESSION='connexion';
	private static $cpt_id=1000;
	private $id;
	private $pseudo="";
	private $mdp="";
	private $email="";

	public function __construct($pseudo, $mdp, $email, $id) { //si $id == -1 ==> créer un nouveau id
		$this->pseudo=(string)$pseudo;
		$this->mdp=(string)$mdp;
		$this->email=$email;
        if($id==-1) {
            $this->id=self::$cpt_id;
            self::$cpt_id+=1;
        }else{
            $this->id=$id;
        }
	}

	public function getPseudo() {
	    return $this->pseudo;
	}

	public function setPseudo($pseudo) {
	    return $this->pseudo=$pseudo;
	}

	public function getId() {
	    return $this->id;
	}

	public function getEmail() {
	    return $this->email;
	}

	public function creerSondage() {
	    //return $sondage;
	}

	public function repondreSondage($sondage) {
	    //
	}

	public function jsonSerialize() {
        	return array('id'=>$this->id, 'pseudo'=>$this->pseudo, 'email'=>$this->email);
   	}

	/*a voir (pas fichier a lui même ?)*/
	public static function fromJSON($json) {
		$array = json_decode($json, true);
    // var_dump($array);
		return new Utilisateur($array['pseudo'], '', $array['email'], $array['id']);
	}

	public function __toString() {
		return $this->pseudo."#".$this->id.", email = ".$this->email;
	}

	public function connexion() {
		$_SESSION[self::NOM_SESSION]=new Utilisateur(($this->pseudo), ($this->mdp));
	}

	public static function estConnecte() {
		if(!isset($_SESSION[self::NOM_SESSION]) || $_SESSION[self::NOM_SESSION]==null) {
			return false;
		}else
			return true;
	}

	public static function deconnexion() {
		unset($_SESSION[self::NOM_SESSION]);
	}

	public function verification() {
        echo "ok<br>";
	    $file_path='./password.txt';
    	$found = false;
	    $users = file($file_path);
        $i = 0;
        while(!$found && $i<count($users)) {
            $user = $users[$i];
            $couple = explode(";", trim($user));
            //echo "affichage cp = ".$couple[0].", cm = ".$couple[1].", p = ".$this->pseudo.", m = ".$this->mdp."<br>";
            if(strcmp($this->pseudo, $couple[0])==0 && strcmp($this->mdp, $couple[1])==0) {
                $found = true;
            }
            $i += 1;
        }
        return($found);
	}

	public static function afficherForm() {
		echo '<form action="./traitement.php" method="post" name="pseudo">
            <fieldset>
            <legend>Login :</legend>
            <label>Pseudo</label>
            <input type="text" name="pseudo"/>
            <label>Password</label>
            <input type="password" name="password"/>
            <button type="submit">Submit</button>
            </fieldset>
            </form>';
	}
}
