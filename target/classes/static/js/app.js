var soundC1 = new Audio('/js/sounds/check1.mp3');

var soundC2 = new Audio('/js/sounds/check2_2.mp3');

var backgroundSound = new Audio('/js/sounds/background_music.mp3');

// Lowering volume of the music and sounds

soundC1.volume = 0.3;
soundC2.volume = 0.3;
backgroundSound.volume = 0.2;


const events = {
    
	outgoing: {
        JOIN_GAME: 'JOIN_GAME',
        MARK: 'MARK',
        RESTART: 'RESTART'
    },
    
    incoming: {
        JOIN_GAME: 'JOIN_GAME',
        MARK: 'MARK',
        SET_TURN: 'SET_TURN',
        OPPONENT_READY: 'OPPONENT_READY',
        GAME_OVER: 'GAME_OVER',
        ERROR: 'ERROR',
        RESTART: 'RESTART',
        RECONNECT: 'RECONNECT'
    }
}


let container = document.querySelector('#gameBoard');
let joinForm = document.querySelector('#joinForm');

let startBtn = document.querySelector('#startBtn');

let scoreBoard = [
    document.querySelector('#p1Score'),
    document.querySelector('#p2Score')
];

let socket;
let player = {};
let board;

function initJoinForm(){
	player = {};
	board = new Board(scoreBoard);
	startBtn.setAttribute('disabled', true);

}

function start(){
	var turno = true;
	initJoinForm();

	socket = new WebSocket('ws://'+location.hostname+(location.port ? ':'+location.port: '')+'/tictactoe')
	socket.onmessage = event => {

	    var msg = JSON.parse(event.data);

	    switch (msg.action) {

	    	case events.incoming.ERROR:
	            alert('Error: ' + msg.data);
	            break;

	        case events.incoming.JOIN_GAME:

	        	for(let msgPlayer of msg.data){
	        		board.addPlayer(msgPlayer);
	        		if (msgPlayer.name === player.name) {
	        			player = msgPlayer;
	        			startGame();
	        		}
	        	}

	            break;

	        case events.incoming.SET_TURN:
	            board.highlightScoreboard(msg.data.id);
	            board.ready = true;
	            if (msg.data.id === player.id) {
	                board.enableTurn();
	            }

	            break;

	        case events.incoming.MARK:
	            if(turno){
	                soundC1.play();
	                turno = false;
	            }else{
	                soundC2.play();
	               turno = true;
	            }
	            board.doMark(msg.data.cellId, msg.data.player.label);

	            break;

	        case events.incoming.GAME_OVER:
	            if (msg.data) {

	                board.doWinner(msg.data.player.name, msg.data.pos);
	            } else {
	            	board.doDraw();
	            }

	            if(player.id === 1){
	            	setTimeout(()=>
	            		sendMessage(events.outgoing.RESTART, { playerId: player.id}), 2000);
	            }

	            break;

	        case events.incoming.RESTART:

	        	board.restart();

	            break;

	        case events.incoming.RECONNECT:

	        	joinForm.style.display = "block";

	        	while (container.lastChild) {
	    	        container.removeChild(container.lastChild);
	    	    }

	        	socket.close();

	        	start();

	        	break;
	    }
	};

	socket.onopen = event => {
	    startBtn.removeAttribute('disabled');

	};

	board = new Board(scoreBoard);
	board.onMark = cellId => {
		sendMessage(events.outgoing.MARK, { playerId: player.id, cellId: cellId });
	};

	player = {};
}

startBtn.addEventListener('click', event => {
    var url = window.location.href;
	var url = new URL(url);
	var name = url.searchParams.get("nameuser");
    history.pushState(null, "", "menuprincipal?nameuser="+name); // Added for keeping the information of the player name in the URL to distinguish each POV
    player.name = name;
    sendMessage(events.outgoing.JOIN_GAME, { name: name });

});

function startGame() {
	
	joinForm.style.display = "none";
    
    if (board.players.length === 1) {
        scoreBoard[1].textContent = 'waiting...';
    }
    else {
    	backgroundSound.play();
    	
    }

    board.addTable(container);
}

function sendMessage(action, data) {

    let resp = {
        action: action,
        data: data
    };

    socket.send( JSON.stringify(resp) );
}

start();
