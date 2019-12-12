<?php
spl_autoload_register (function ($class) {
	require '../classes/'.$class.'.php';
});

if(isset($_POST['sondage'])) {
    $gest = new GestionnaireSondage();
    $sondage = $gest->trouverId(intval($_POST['sondage']));
    if($sondage!=null) {
        $res = array();
        $res = $sondage->getReponses();
        if(!empty($res)) {
            $json = array('code'=>'OK', 'reponses'=>$res, 'message'=>'voici les reponses');
            $json = json_encode($json);
            echo $json;
        }else{
            echo '{"code":"erreur", "reponses":"null", "message":"Personne n\'a repondu a ce sondage"}';
        }
    }else{
        echo '{"code":"erreur", "reponses":"null", "message":"Mauvais id"}';
    }
}else{
    echo '{"code":"erreur", "reponses":"null", "message":"Pas de données dans le post"}';
}
