package es.codeurjc.ais.tictactoe;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "chatbd", path = "chatbd")
public interface ChatRepository extends JpaRepository<ChatBD, Integer> {

	@Query(value = "SELECT * FROM CHATBD ORDER BY ID DESC", nativeQuery = true)
	List<ChatBD> listDeschat();

}
