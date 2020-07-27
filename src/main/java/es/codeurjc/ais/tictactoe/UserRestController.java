package es.codeurjc.ais.tictactoe;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserRepository repUser;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	private ResponseEntity<User> addUser(@RequestBody User user) {
		if (user != null) {
			repUser.save(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	private Collection<User> allUsers() {
		return repUser.findAll();
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	private ResponseEntity<User> findUser(@PathVariable(value = "username") String username) {
		User u = repUser.findByNameUser(username);

		if (u != null)
			return new ResponseEntity<>(u, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
