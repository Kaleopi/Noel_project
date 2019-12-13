<?php
session_start();
if(isset($_POST['connexion'])){
    if (isset($_POST["login"]) && isset($_POST["mdp"])) {
        $options = [
            'http' => [
                'method' => 'POST',
                'header' => 'Content-type: applocation/x-www-form-irmencoded',
                'content' => 'login=' . $_POST["login"] . '&' . 'mdp=' . $_POST["pwd"]
            ]
        ];
        // echo $_POST["login"].'&'.$_POST["mdp"];
        $contexte = stream_context_create($options);

        if (($jsonTexte = @file_get_contents($URL, false, $contexte)) !== false) {
            echo $jsonTexte;
            $tableau = json_decode($jsonTexte, true);
            if ($tableau['code'] == "OK")
                echo '{"code":"OK", "message":"' . $tableau['message'] . ', "user":"' . $tableau['user'] . '"}';
            else
                // echo "<p>Erreur lors de la connexion : ".$tableau['message']."</p>";
                echo '{"code":"erreur", "message":"' . $tableau['message'] . ', "user":"' . $tableau['user'] . '"}';
        } else {
            echo '{"code":"erreur", "message":"Erreur lors de la recuperation de donnees"}';
        }
    } else {
        echo '{"code":"erreur", "message":"Pas de données dans le poste"}';
    }
}
else if(isset($_POST['inscription'])){

};
?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="css/app.css">
    <title>Santa & CO</title>
</head>

<body>
    <div class="container">
        <form class="login_form shadow p-3 mb-5 bg-light rounded" method="POST">
            <div class="form-group">
                <label for="login">Login</label>
                <input type="text" class="form-control" name="login" required>
            </div>
            <div class="form-group">
                <label for="pwd">Mot de passe</label>
                <input type="password" class="form-control" name="pwd" required>
            </div>
            <button type="submit" name="connexion" class="btn btn-primary">Me connecter !</button>
            <button type="submit" name="inscription" class="btn btn-warning">M'inscrire !</button>
        </form>
    </div> 
    <script src="js/snow.js"></script>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous">
    </script>
</body>

</html>