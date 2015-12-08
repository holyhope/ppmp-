package fr.mlv.school;

public enum Permission {
	ADD_BOOK, REMOVE_BOOK;

	public String toString() {
		switch (this) {
			case ADD_BOOK:
				return "ADD_BOOK";
			case REMOVE_BOOK:
				return "REMOVE_BOOK";
			default:
				throw new IllegalArgumentException("This Permission is not valid");
		}
	};

	public static Permission fromString(String type) {
		if (type.equals(Permission.ADD_BOOK.toString())) {
			return Permission.ADD_BOOK;
		}
		if (type.equals(Permission.REMOVE_BOOK.toString())) {
			return Permission.REMOVE_BOOK;
		}
		throw new IllegalArgumentException("This permission does not exists");
	}
}
