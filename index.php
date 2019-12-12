<?php
spl_autoload_register (function ($class) {
	require 'php/'.$class.'.php';
});

$q = new Question("Voici une question ?", null, null);
$q->ajouterChoix("choix 1");
$q->ajouterChoix("choix 2");
$q->ajouterChoix("choix 3");

echo $q."<br>";

$u = new Utilisateur("Potato", "0000", "email@mail.com", -1);
echo $u."<br>";

$r = new Reponse($u, [1, 1, 3]);
print_r($r->jsonSerialize());
$json=json_encode($r, JSON_PRETTY_PRINT);
print($json);

$u2 = new Utilisateur("user2", "", "mail", -1);
$s = new Sondage("Sondage 1", [$q], $u);
$s->ajouterQuestion("hein ?", ["ok", "nope", "UwU"]);
echo $s."<br>";
$s->ajouterReponse(new Reponse($u, [1, 2]));
$s->ajouterReponse(new Reponse($u2, [2, 2]));
$ok=$s->creerArbo();
var_dump($ok);
$s1 = Sondage::fromJSON('sondage1000');
var_dump($s1);