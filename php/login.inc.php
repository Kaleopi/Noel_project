<script src="js/snow.js"></script>
<style>
    body{
        background-image: url("img/bg-three.jpg");
        background-size: cover;
    }
</style>
<form class="login_form shadow p-3 mb-5 bg-light rounded" method="POST" action="login.php">
    <div class="form-group">
        <label for="login">Login</label>
        <input type="text" class="form-control" id="login" required>
    </div>
    <div class="form-group">
        <label for="pwd">Mot de passe</label>
        <input type="password" class="form-control" id="pwd" required>
    </div>
    <button type="submit" class="btn btn-primary">Me connecter !</button>
</form>