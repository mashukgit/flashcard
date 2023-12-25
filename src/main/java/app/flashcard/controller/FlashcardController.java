package app.flashcard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.flashcard.entity.CardsEntity;
import app.flashcard.entity.TagsEntity;
import app.flashcard.service.CardsService;
import app.flashcard.service.TagsService;

@Controller
public class FlashcardController {

	@Autowired
	private CardsService cardsService;

	@Autowired
	private TagsService tagsService;

	@GetMapping("/")
	public String home(Model model) {
		this.initModel(model);
		model.addAttribute("tags", this.tagsService.getTagsWithCardCount());

		return "home";
	}

	@GetMapping({ "/cards/{tagId}", "cards/{tagId}/{filter}" })
	public String cards(@PathVariable("tagId") Integer tagId,
			@PathVariable(name = "filter", required = false) String filter, Model model) {
		this.initModel(model);
		prepareCardsModel(tagId, filter, model);
		return "cards";
	}

	@GetMapping({ "/memorize/{tagId}", "memorize/{tagId}/{filter}" })
	public String memorize(@PathVariable("tagId") Integer tagId,
			@PathVariable(name = "filter", required = false) String filter, Model model) {
		this.initModel(model);
		prepareCardsModel(tagId, filter, model);
		return "memorize";
	}

	@GetMapping("/add-card")
	public String addCardForm(Model model) {
		this.initModel(model);
		model.addAttribute("isNewCard", true);
		model.addAttribute("formTitle", "Add new card");
		model.addAttribute("card", new CardsEntity());
		model.addAttribute("types", this.cardsService.getTypes());
		model.addAttribute("tags", this.tagsService.getTags());
		return "add-edit-card";
	}

	@GetMapping("/edit-card/{id}")
	public String addCardForm(@PathVariable("id") Integer id, Model model) {
		this.initModel(model);
		model.addAttribute("isNewCard", false);
		model.addAttribute("formTitle", "Edit card");
		model.addAttribute("card", this.cardsService.getCard(id));
		model.addAttribute("types", this.cardsService.getTypes());
		model.addAttribute("tags", this.tagsService.getTags());
		return "add-edit-card";
	}

	@PostMapping("/add-card")
	public String addCard(@ModelAttribute CardsEntity card, Model model) {
		cardsService.addCard(cardsService.checkExists(card));
		return "redirect:/";

	}

	@GetMapping("/tags")
	public String tags(Model model) {
		this.initModel(model);
		model.addAttribute("formTitle", "Tags");
		model.addAttribute("tags", this.tagsService.getTags());
		model.addAttribute("ntag", new TagsEntity());
		return "tags";
	}

	@PostMapping("/add-tag")
	public String addTag(@ModelAttribute TagsEntity tag, Model model) {
		tagsService.saveTag(tag);
		return "redirect:tags";
	}
	
	@PostMapping("/mark-known")
	public Model markAsKnown(@RequestParam("cardId") Integer cardId, @RequestParam("known") Integer known, Model model) {
		
		model.addAttribute("success", true);
		return model;
	}

	private void initModel(Model model) {
		model.addAttribute("title", "Flashcard");
		model.addAttribute("logo", this.logo);
	}

	private void prepareCardsModel(Integer tagId, String filter, Model model) {
		if (filter == null)
			filter = "all";
		if (tagId == null)
			tagId = 0;

		List<CardsEntity> cards = this.cardsService.getCardsByTag(tagId, filter);
		model.addAttribute("addFlipCartStyle", true);
		model.addAttribute("tagId", tagId);
		model.addAttribute("filter", "known".equals(filter) ? 1 : 0);
		model.addAttribute("cards", cards);
		model.addAttribute("empty", cards.isEmpty());
	}

	private String logo = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxNiIgaGVpZ2h0PSIxNiIgZmlsbD0iY3VycmVudENvbG9yIiBjbGFzcz0iYmkgYmktcG9zdGNhcmQiIHZpZXdCb3g9IjAgMCAxNiAxNiI+CiAgPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBkPSJNMiAyYTIgMiAwIDAgMC0yIDJ2OGEyIDIgMCAwIDAgMiAyaDEyYTIgMiAwIDAgMCAyLTJWNGEyIDIgMCAwIDAtMi0yek0xIDRhMSAxIDAgMCAxIDEtMWgxMmExIDEgMCAwIDEgMSAxdjhhMSAxIDAgMCAxLTEgMUgyYTEgMSAwIDAgMS0xLTF6bTcuNS41YS41LjUgMCAwIDAtMSAwdjdhLjUuNSAwIDAgMCAxIDB6TTIgNS41YS41LjUgMCAwIDEgLjUtLjVINmEuNS41IDAgMCAxIDAgMUgyLjVhLjUuNSAwIDAgMS0uNS0uNW0wIDJhLjUuNSAwIDAgMSAuNS0uNUg2YS41LjUgMCAwIDEgMCAxSDIuNWEuNS41IDAgMCAxLS41LS41bTAgMmEuNS41IDAgMCAxIC41LS41SDZhLjUuNSAwIDAgMSAwIDFIMi41YS41LjUgMCAwIDEtLjUtLjVNMTAuNSA1YS41LjUgMCAwIDAtLjUuNXYzYS41LjUgMCAwIDAgLjUuNWgzYS41LjUgMCAwIDAgLjUtLjV2LTNhLjUuNSAwIDAgMC0uNS0uNXpNMTMgOGgtMlY2aDJ6Ii8+Cjwvc3ZnPg==";

}
