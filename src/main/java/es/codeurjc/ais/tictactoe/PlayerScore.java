package es.codeurjc.ais.tictactoe;

import java.util.Objects;

//Private class for saving the score of each player
public class PlayerScore implements Comparable<PlayerScore> {
	String playername;
	int wins;
	int draws;
	int defeats;

	// Constructors
	public PlayerScore(String playername, int wins, int draws, int defeats) {
		this.playername = playername;
		this.wins = wins;
		this.draws = draws;
		this.defeats = defeats;
	}

	// Getters and Setters
	public int getWins() {
		return wins;
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getDraws() {
		return draws;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public int getDefeats() {
		return defeats;
	}

	public void setDefeats(int defeats) {
		this.defeats = defeats;
	}

	// Equals, hashCode and compareTo implementation is needed for redefining the
	// Set operation for checking
	// if an object has been already inserted
	// With this definition of equals the HashMap will overwrite every Player if
	// the scores are repeated just looking
	// at the player name that should be the same in that case
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PlayerScore pscore = (PlayerScore) o;
		return Objects.equals(playername, pscore.playername);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playername, wins, draws, defeats);
	}

	@Override
	public int compareTo(PlayerScore ps) {
		return ps.wins - this.wins;
	}
}
