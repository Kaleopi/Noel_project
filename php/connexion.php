<?php
session_start();
if(isset($_POST["login"])&& isset($_POST["pwd"])) {
    if(isset($_POST["connexion"])){
        $URL = "http://localhost:8080/login.html";
        $options = [
            'http' => [
                'method' => 'POST',
                'header' => 'Content-type: application/x-www-form-irmencoded',
                'content' => 'login='.$_POST["login"].'&'.'mdp='.$_POST["pwd"]
            ]
        ];
        $contexte = stream_context_create($options);

        if(($jsonTexte = @file_get_contents($URL, false, $contexte)) !== false) {
            echo $jsonTexte;
            $tableau = json_decode($jsonTexte, true);
            // var_dump($tableau);
            if($tableau['code'] == "OK"){
                header("Location: accueil.php");
                // echo 'teeeest {"code":"OK", "message":"'.$tableau['message'].', "user":"'.$tableau['user'].'"}';
            }
            else{
                // echo '{"code":"erreur", "message":"'.$tableau['message'].', "user":"'.$tableau['user'].'"}';
                header("Location: index.php");
                echo "probleme";
            }
        }else{
            header("Location: index.php");
            echo "probleme";
            // echo '{"code":"erreur", "message":"Erreur lors de la recuperation de donnees"}';
        }
    }
    else
}else{
    header("Location: index.php");
    echo "problème";
    // echo '{"code":"erreur", "message":"Pas de données dans le poste"}';
}