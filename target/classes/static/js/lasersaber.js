var sound = new Audio('/js/sounds/sableLaser.mp3');
sound.volume = 0.5;
function play() {
	sound.play();
}
function stop() {
	sound.pause();
	sound.currentTime = 0;
}