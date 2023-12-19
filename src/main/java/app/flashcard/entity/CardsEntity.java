package app.flashcard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Cards")
public class CardsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@Column(name = "type", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
	private byte type;
	@Column(name = "front", nullable = false)
	private String front;
	@Column(name = "back", nullable = false)
	private String back;
	@Column(name = "known")
	private boolean known;
	@Column(name = "tagId", columnDefinition = "INT NOT NULL DEFAULT 0")
	private Integer tagId;
	
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getFront() {
		return front;
	}

	public void setFront(String front) {
		this.front = front;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public boolean isKnown() {
		return known;
	}

	public void setKnown(boolean known) {
		this.known = known;
	}

}
