package app.flashcard.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.flashcard.entity.TagsEntity;

@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, Integer> {

	@Query(value = "SELECT T.id AS id, T.tagName AS tagName, COUNT(C.id) AS cardCount FROM Tags AS T"
			+ " LEFT JOIN Cards AS C ON T.id = C.tagId GROUP BY T.id")
	List<Map<String, Object>> getTags();
}
