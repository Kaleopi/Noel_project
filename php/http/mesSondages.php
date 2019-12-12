<?php
spl_autoload_register (function ($class) {
	require '../classes/'.$class.'.php';
});

// $gest = new GestionnaireSondage();
// $sondages = $gest->trouverResponsable(1000);
// print_r ($sondages);

if(isset($_POST['idResponsable'])) {
    $gest = new GestionnaireSondage();
    $sondages = $gest->trouverResponsable(intval($_POST['idResponsable']));
    if(!empty($sondages)) {
        $res = array();
        for($i=0; $i<count($sondages); $i++) {
            $res[] = $sondages[$i]->jsonSerialize();
        }
        $json = array('code'=>'OK', 'sondages'=>$res, 'message'=>'voici vos sondages');
        $json = json_encode($json);
        echo $json;
    }else{
        echo '{"code":"erreur", "sondages":"null", "message":"Vous n\'avez pas cree des sondages"}';
    }
}else{
    echo '{"code":"erreur", "sondages":"null", "message":"Pas de données dans le post"}';
}
