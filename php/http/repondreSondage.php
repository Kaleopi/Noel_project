<?php
spl_autoload_register (function ($class) {
	require '../classes/'.$class.'.php';
});

if(isset($_POST['sondage'])) {
		$gest = new GestionnaireSondage();
		$sondage = $gest->trouverId(intval($_POST['sondage']));
		if($sondage!=null) {
				$s = $sondage->jsonSerialize();
				$json = array('code'=>'OK', 'sondage'=>$s, 'message'=>'Sondage trouve');
				$json = json_encode($json);
				echo $json;
		}else{
		    echo '{"code":"erreur", "sondage":"null", "message":"Pas de sondage avec cet id"}';
		}
}else{
    echo '{"code":"erreur", "sondage":"null", "message":"Pas de données dans le poste"}';
}
