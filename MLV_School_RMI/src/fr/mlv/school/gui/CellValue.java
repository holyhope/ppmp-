package fr.mlv.school.gui;

import java.rmi.RemoteException;

import fr.mlv.school.Book;
import fr.mlv.school.Library;

public class CellValue {
	private final Book	  book;
	private final Library library;

	public CellValue(Library library, Book book) {
		this.library = library;
		this.book = book;
	}

	public String toString() {
		try {
			return library.isBookAvailable(book) ? "Emprunter" : "Non disponible";
		} catch (RemoteException e) {
			return "Emprunter";
		}
	}
}
