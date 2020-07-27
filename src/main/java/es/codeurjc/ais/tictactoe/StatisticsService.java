package es.codeurjc.ais.tictactoe;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

	// HashMap for storing the ranking in memory
	public HashMap<String, PlayerScore> ranking;

	public StatisticsService() {
		ranking = new HashMap<>();
	}

	// Functionality
	// 2 methods for adding players to the ranking because the methods are different
	// if the player is winner or looser

	public void addWinnerPlayerToRanking(String name) {
		if (ranking.containsKey(name.trim())) { // Update ranking if player already exists
			// Update the winner player from the ranking and put it inside again

			PlayerScore score = ranking.get(name.trim());
			score.setWins(score.getWins() + 1);
			ranking.put(name.trim(), score);

		} else { // Add new winner player to the ranking
			// New Winner player

			PlayerScore newscore = new PlayerScore(name.trim(), 1, 0, 0);
			ranking.put(name.trim(), newscore); // Add it to the rankingMap

		}
	}

	public void addLooserPlayerToRanking(String name) {
		if (ranking.containsKey(name.trim())) {
			// Now we have to update the looser player, just like before

			PlayerScore score = ranking.get(name.trim());
			score.setDefeats(score.getDefeats() + 1);
			ranking.put(name.trim(), score);

		} else { // Add new looser player to the ranking
			// New Looser player

			PlayerScore newscore = new PlayerScore(name.trim(), 0, 0, 1);
			ranking.put(name.trim(), newscore); // Add it to the rankingMap

		}
	}

	public void addDrawPlayerToRanking(String name) {
		// Algorithm is exactly the same as in winner and looser methods but updating
		// the draws of
		// each player

		if (ranking.containsKey(name.trim())) {

			PlayerScore score = ranking.get(name.trim());
			score.setDraws(score.getDraws() + 1);
			ranking.put(name.trim(), score);

		} else { // Add new looser player to the ranking
			// New Draw situation

			PlayerScore newscore = new PlayerScore(name.trim(), 0, 1, 0);
			ranking.put(name.trim(), newscore); // Add it to the rankingMap

		}
	}

	public HashMap<String, PlayerScore> getRanking() {
		return ranking;
	}

}
