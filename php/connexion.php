<?php
session_start();
$URL = "http://localhost:8080/login.html";

if(isset($_POST["login"])&&isset($_POST["mdp"])) {
    $options = [
        'http' => [
            'method' => 'POST',
            'header' => 'Content-type: applocation/x-www-form-irmencoded',
            'content' => 'login='.$_POST["login"].'&'.'mdp='.$_POST["pwd"]
        ]
    ];
    // echo $_POST["login"].'&'.$_POST["mdp"];
    $contexte = stream_context_create($options);

    if(($jsonTexte = @file_get_contents($URL, false, $contexte)) !== false) {
        echo $jsonTexte;
        $tableau = json_decode($jsonTexte, true);
        if($tableau['code'] == "OK")
            echo '{"code":"OK", "message":"'.$tableau['message'].', "user":"'.$tableau['user'].'"}';
        else
            // echo "<p>Erreur lors de la connexion : ".$tableau['message']."</p>";
            echo '{"code":"erreur", "message":"'.$tableau['message'].', "user":"'.$tableau['user'].'"}';
    }else{
        echo '{"code":"erreur", "message":"Erreur lors de la recuperation de donnees"}';
    }
}else{
    echo '{"code":"erreur", "message":"Pas de données dans le poste"}';
}
