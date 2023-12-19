package app.flashcard.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import app.flashcard.entity.CardsEntity;
import app.flashcard.entity.TypeEntity;
import app.flashcard.repo.CardsRepository;

@Service
public class CardsService {

	@Autowired
	private CardsRepository cardsRepository;

	public List<CardsEntity> getCards(String filter) {
		Sort sort = Sort.by(Direction.DESC, "id");
		List<CardsEntity> cards;
		if ("general".equals(filter))
			cards = this.cardsRepository.findByType(1, sort);
		else if ("code".equals(filter))
			cards = this.cardsRepository.findByType(2, sort);
		else if ("known".equals(filter))
			cards = this.cardsRepository.findByKnown(true, sort);
		else if ("unknown".equals(filter))
			cards = this.cardsRepository.findByKnown(false, sort);
		else
			cards = this.cardsRepository.findAll(sort);

		if (cards == null) {
			cards = new ArrayList<>();
		}

		System.out.println(cards.size());

		return cards;
	}
	public List<CardsEntity> getCardsByTag(int tagId, String filter) {
		Sort sort = Sort.by(Direction.DESC, "id");
		List<CardsEntity> cards;
		if ("general".equals(filter))
			cards = this.cardsRepository.findByTagIdAndType(tagId, 1, sort);
		else if ("code".equals(filter))
			cards = this.cardsRepository.findByTagIdAndType(tagId, 2, sort);
		else if ("known".equals(filter))
			cards = this.cardsRepository.findByTagIdAndKnown(tagId, true, sort);
		else if ("unknown".equals(filter))
			cards = this.cardsRepository.findByTagIdAndKnown(tagId, false, sort);
		else
			cards = this.cardsRepository.findAllByTagId(tagId, sort);

		if (cards == null) {
			cards = new ArrayList<>();
		}

		System.out.println(cards.size());

		return cards;
	}
	public CardsEntity getCard(Integer id) {
		return this.cardsRepository.findById(id).orElse(new CardsEntity());
	}

	@Transactional
	public void addCard(CardsEntity card) {
		if (card == null) {
			System.out.println("Card is null");
			return;
		}
		Integer id = card.getId();
		if (id == null)
			id = -1;
		CardsEntity exCard = this.cardsRepository.findById(id).orElse(null);
		if (exCard == null)
			exCard = card;
		else {
			exCard.setBack(card.getBack());
			exCard.setFront(card.getFront());
			exCard.setId(card.getId());
			exCard.setKnown(card.isKnown());
			exCard.setType(card.getType());
			exCard.setTagId(card.getTagId());
		}
		this.cardsRepository.saveAndFlush(exCard);
	}

	public List<TypeEntity> getTypes() {
		List<TypeEntity> types = new ArrayList<>(2);
		types.add(TypeEntity.of(1, "Theory"));
		types.add(TypeEntity.of(2, "Code"));
		return types;
	}
}
