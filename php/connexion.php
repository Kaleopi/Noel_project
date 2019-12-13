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
                $_SESSION['login']=$_POST['login'];
                $_SESSION['mdp'] = $_POST['pwd'];
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
    elseif(isset($_POST['inscription'])){
        $URL = "http://localhost:8080/create.html";

        if(isset($_POST["login"])&&isset($_POST["pwd"])&&isset($_POST["email"])) {
            $options = [
                'http' => [
                    'method' => 'POST',
                    'header' => 'Content-type: application/x-www-form-irmencoded',
                    'content' => 'login='.$_POST["login"].'&'.'mdp='.$_POST["pwd"].'&'.'email='.$_POST["email"]
                ]
            ];
            // echo $_POST["login"].'&'.$_POST["mdp"];
            $contexte = stream_context_create($options);

            if(($jsonTexte = @file_get_contents($URL, false, $contexte)) !== false) {
                echo $jsonTexte;
                $tableau = json_decode($jsonTexte, true);
                if($tableau['code'] == "OK"){
                    // echo '{"code":"OK", "message":"'.$tableau['message'].'"}';
                    header("Location: accueil.php");
                    $_SESSION['login']=$_POST['login'];
                    $_SESSION['mdp'] = $_POST['pwd'];
                }
                else{
                    // echo '{"code":"erreur", "message":"'.$tableau['message'].', "user":"'.$tableau['user'].'"}';
                    header("Location: index.php");
                    echo "probleme";
                }
            }else{
                // echo '{"code":"erreur", "message":"Erreur lors de la recuperation de donnees"}';
                header("Location: index.php");
                echo "probleme";
            }
        }else{
            // echo '{"code":"erreur", "message":"Pas de donnees dans le poste"}';
            header("Location: index.php");
            echo "probleme";
        }
    }
}else{
    header("Location: index.php");
    echo "problème";
    // echo '{"code":"erreur", "message":"Pas de données dans le poste"}';
}