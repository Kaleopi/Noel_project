<?php
session_start();

?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="css/accueil.css">
    <title>Santa & CO</title>
</head>

<body>
    <nav class="navbar" id="snow">
        <a class="navbar-brand" href="#">
            <img src="./img/santa.png" width="70" height="70" class="d-inline-block align-top" alt="">
        </a>
    </nav>
    <div class="container">
        <form class="login_form shadow p-3 mb-5 bg-light rounded" method="POST">
            <?php
            if ($_SESSION['login'] !== "Santa") {
                echo "</br>Vous n'êtes pas connecté en tant que Père Noël, et nous n'avons pas encore développé votre partie de l'application</br></br>
        <button type=\"submit\" name=\"retour\" class=\"btn btn-danger\" formaction=\"index.php\">Retour à la connexion</button>";
            } else {

                ?>
                <select class="form-control" name="usines">
                    <option>Usines</option>
                </select>
                </Br>
                <select class="form-control" name="articles">
                    <option>Articles</option>
                </select>
        </form>
    </div>
<?php } ?>

<script src="js/snowaccueil.js"></script>
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