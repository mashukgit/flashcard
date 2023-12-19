package app.flashcard.entity;

public class TypeEntity {
	private int id;
	private String typeName;

	public static TypeEntity of(int id, String typeName) {
		TypeEntity entity = new TypeEntity();
		entity.setId(id);
		entity.setTypeName(typeName);
		return entity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
