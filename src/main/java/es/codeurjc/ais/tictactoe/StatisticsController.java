package es.codeurjc.ais.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatisticsController {

	@Autowired
	private StatisticsService stats;

	@RequestMapping("/stats/{name}")
	public String statistics(@PathVariable(value = "name") String nickname, Model model) {

		List<PlayerScore> orderedRanking = new ArrayList<>(stats.getRanking().values());

		Collections.sort(orderedRanking); // Merge sort

		model.addAttribute("WinsPlayer", orderedRanking);
		model.addAttribute("nickname", nickname);

		return "statistics";
	}

	@RequestMapping("/")
	public String login() {

		return "login";
	}

	@RequestMapping("/menuprincipal")
	public String menuprincipal(@RequestParam(name = "nameuser") String nameid, Model model) {
		String nick = nameid.trim();
		PlayerScore p = stats.getRanking().get(nick);
		if (p == null) {
			model.addAttribute("ganadas", 0);
			model.addAttribute("perdidas", 0);
			model.addAttribute("empatadas", 0);
		} else {
			model.addAttribute("ganadas", p.getWins());
			model.addAttribute("perdidas", p.getDefeats());
			model.addAttribute("empatadas", p.getDraws());

		}
		return "index";
	}

}
