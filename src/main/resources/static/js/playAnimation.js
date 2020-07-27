var sound = new Audio('/js/sounds/choque.mp3');
sound.volume = 0.5;

function mostrar() {
	sound.play();
	document.getElementById('animacion').style.display = 'block';
}

function ocultar() {
	document.getElementById('animacion').style.display = 'none';
}