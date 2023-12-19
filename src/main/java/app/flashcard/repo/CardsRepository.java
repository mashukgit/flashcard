package app.flashcard.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.flashcard.entity.CardsEntity;

@Repository
public interface CardsRepository extends JpaRepository<CardsEntity, Integer>{
	
	List<CardsEntity> findByType(int type, Sort sort);
	List<CardsEntity> findByKnown(boolean known, Sort sort);
	List<CardsEntity> findByTagIdAndType(int tagId, int type, Sort sort);
	List<CardsEntity> findByTagIdAndKnown(int tagId, boolean known, Sort sort);
	List<CardsEntity> findAllByTagId(int tagId, Sort sort);
}
