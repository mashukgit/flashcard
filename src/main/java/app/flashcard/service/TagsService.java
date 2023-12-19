package app.flashcard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.flashcard.entity.TagsEntity;
import app.flashcard.repo.TagsRepository;

@Service
public class TagsService {

	@Autowired
	private TagsRepository tagsRepository;
	
	public List<TagsEntity> getTags() {
		List<TagsEntity> tags = tagsRepository.findAll();
		if(tags == null) tags = new ArrayList<>();
		return tags;
	}
	
	public List<TagsEntity> getTagsWithCardCount() {
		List<Map<String, Object>> tags = tagsRepository.getTags();
		List<TagsEntity> tagsEntities = new ArrayList<>();
		if(tags != null) {
			for(Map<String, Object> map: tags) {
				TagsEntity entity = new TagsEntity();
				entity.setId(((Integer)map.getOrDefault("id", 0)));
				entity.setTagName((String)map.getOrDefault("tagName", ""));
				entity.setCardCount(((Long)map.getOrDefault("cardCount", 0)).intValue());
				tagsEntities.add(entity);
			}
		}
		return tagsEntities;
	}
	
	public void saveTag(TagsEntity tag) {
		if(tag == null || !StringUtils.hasText(tag.getTagName())) return;
		this.tagsRepository.save(tag);
	}
}
