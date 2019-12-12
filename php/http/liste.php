<?php
spl_autoload_register (function ($class) {
	require '../classes/'.$class.'.php';
});

$s = new GestionnaireSondage();
$s->listerSondages();
