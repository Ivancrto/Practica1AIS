package es.codeurjc.ais.tictactoe;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;

public class TicTacToeGame {

	enum EventType {
		JOIN_GAME, GAME_READY, MARK, SET_TURN, GAME_OVER, RESTART, RECONNECT
	}

	static class Cell {
		volatile boolean active = false;
		volatile String value;
	}

	static class WinnerResult {
		boolean win;
		int[] pos;
	}

	static class CellMarkedValue implements Serializable {
		int cellId;
		Player player;
	}

	static class WinnerValue implements Serializable {
		Player player;
		int[] pos;
	}

	static class Event implements Serializable {
		EventType type;
		Object data;
	}

	private List<Connection> connections = new CopyOnWriteArrayList<>();
	private List<Player> players = new CopyOnWriteArrayList<>();
	private Board board = new Board();

	@Autowired
	private StatisticsService stats;

	private int currentTurn = 0;
	private boolean ready = false;

	// Default no-args constructor redefinition required for Spring
	public TicTacToeGame(StatisticsService stats) {
		this.stats = stats;
	}

	public void disableAll() {
		board.disableAll();
	}

	public void enableAll() {
		board.enableAll();
	}

	public boolean mark(int cellId) {

		Cell cell = this.board.getCell(cellId);

		if (cell == null) {
			return false;
		}

		if (this.ready && cell.active) {

			Player player = this.players.get(this.currentTurn);

			cell.value = player.getLabel();
			cell.active = false;

			CellMarkedValue value = new CellMarkedValue();
			value.cellId = cellId;
			value.player = player;

			this.sendEvent(EventType.MARK, value);

			WinnerResult res = this.checkWinner();

			if (res.win) {

				this.disableAll();

				WinnerValue winner = new WinnerValue();
				winner.player = this.players.get(this.currentTurn);
				winner.pos = res.pos;

				this.sendEvent(EventType.GAME_OVER, winner);

			} else if (this.checkDraw()) {

				this.sendEvent(EventType.GAME_OVER, null);

			} else {

				changeTurn();
			}
		}

		return true;
	}

	private void changeTurn() {
		this.currentTurn = (this.currentTurn + 1) % 2;
		this.sendEvent(EventType.SET_TURN, this.players.get(this.currentTurn));
	}

	public boolean checkTurn(int playerId) {
		return this.players.get(this.currentTurn).getId() == playerId;
	}

	public WinnerResult checkWinner() {

		Player winnerPlayer = this.players.get(this.currentTurn); // with currentTurn we get the position of the active
																	// player
		Player looserPlayer = this.players.get((this.currentTurn + 1) % 2); // with this formula we get the non-active
																			// player in this turn

		int[] winPos = board.getCellsIfWinner(winnerPlayer.getLabel());

		WinnerResult result = new WinnerResult();
		result.win = (winPos != null); // Holds the value true if game is ended and has a winner
		result.pos = winPos;

		if (result.win) { // Statistics handling when game ends algorithm
			String winnerPlayerName = winnerPlayer.getName();
			String looserPlayerName = looserPlayer.getName();

			stats.addWinnerPlayerToRanking(winnerPlayerName);
			stats.addLooserPlayerToRanking(looserPlayerName);
		}

		return result;
	}

	public boolean checkDraw() {
		boolean drawCondition = board.checkDraw();
		if (drawCondition) { // If there is a draw update both players in the StatisticsService structures
			// Get both players name and repeat the statistics handling when game ends
			// algorithm
			String player1Name = this.players.get(this.currentTurn).getName();
			String player2Name = this.players.get((this.currentTurn + 1) % 2).getName();

			stats.addDrawPlayerToRanking(player1Name);
			stats.addDrawPlayerToRanking(player2Name);
		}

		return drawCondition; // Returns true if there is a draw condition, false otherwise
	}

	public void addPlayer(Player player) {

		if (this.players.size() < 2) {

			if (this.players.isEmpty() || players.get(0).getId() != player.getId()) {

				this.players.add(player);
				this.ready = this.players.size() == 2;

				this.sendEvent(EventType.JOIN_GAME, players);

				if (this.ready) {
					this.enableAll();
					this.sendEvent(EventType.SET_TURN, this.players.get(this.currentTurn));
				}
			}
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void addConnection(Connection connection) {
		this.connections.add(connection);
	}

	public void restart() {

		board = new Board();

		sendEvent(EventType.RESTART, null);

		changeTurn();
	}

	private void sendEvent(EventType type, Object value) {

		for (Connection c : connections) {
			c.sendEvent(type, value);
		}
	}
	
}
