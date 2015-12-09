package test.fr.mlv.school;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fr.mlv.school.Book;
import fr.mlv.school.BookImpl;
import fr.mlv.school.LibraryImpl;
import fr.mlv.school.Permission;
import fr.mlv.school.UserImpl;
import fr.mlv.school.Users;

public class LibraryTest {

	@Test
	public void testGetName() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);
		assertEquals("MLV-School", libraryImpl.getName());
	}

	@Test
	public void testAddBook() throws RemoteException {
		Users users = new Users();
		UserImpl user = new UserImpl("holyhope", "email.test@domain.com", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.REMOVE_BOOK);
		LibraryImpl libraryImpl = new LibraryImpl(users);
		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
						+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
						+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
						+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
						+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
						+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
				"Gründ", 32.70, 2002, 03, 22);

		assertFalse(libraryImpl.addBook(book, user));

		try {
			libraryImpl.addBook(null, user);
			fail("Null book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			libraryImpl.addBook(book, null);
			fail("Null user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		UserImpl userTeacher = new UserImpl("holyhope2", "email.test@domaine.com", "teacher");
		users.register(userTeacher, "123456");
		users.grantPermission(user, Permission.ADD_BOOK);
		assertTrue(libraryImpl.addBook(book, user));

		try {
			UserImpl userNotRegistered = new UserImpl("Hacker", "hack@wanadoo.fr", "Pirate");
			libraryImpl.addBook(book, userNotRegistered);
			fail("Not registered User should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testDeleteBook() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);
		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
						+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
						+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
						+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
						+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
						+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
				"Gründ", 32.70, 2002, 03, 22);

		UserImpl userTeacher = new UserImpl("holyhope", "email.test@domain.com", "teacher");
		users.register(userTeacher, "AZERTY");
		users.grantPermission(userTeacher, Permission.REMOVE_BOOK);
		users.grantPermission(userTeacher, Permission.ADD_BOOK);

		users.userCan(userTeacher, Permission.REMOVE_BOOK);
		libraryImpl.addBook(book, userTeacher);

		try {
			libraryImpl.deleteBook(null, userTeacher);
			fail("Null book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			libraryImpl.deleteBook(book, null);
			fail("Null user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			BookImpl bookNotRegistered = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
							+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
							+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
							+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
							+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
							+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.deleteBook(bookNotRegistered, null);
			fail("Null Book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			UserImpl userNotRegistered = new UserImpl("Hacker", "hack@wanadoo.fr", "Pirate");
			libraryImpl.deleteBook(book, userNotRegistered);
			fail("Not registered User should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		UserImpl user = new UserImpl("holyhope2", "email.test@domainz.com", "user");
		users.register(user, "123456");
		assertFalse(libraryImpl.deleteBook(book, user));
		assertTrue(libraryImpl.deleteBook(book, userTeacher));
	}

	@Test
	public void testIsBookAvailable() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
						+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
						+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
						+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
						+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
						+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
				"Gründ", 32.70, 2002, 03, 22);

		UserImpl user = new UserImpl("holyhope", "email.test@domain.com", "teacher");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);
		assertTrue(libraryImpl.isBookAvailable(book));
		libraryImpl.getBook(book, user);

		try {
			libraryImpl.isBookAvailable(null);
			fail("Null book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		assertFalse(libraryImpl.isBookAvailable(book));
		try {
			BookImpl bookNotRegistered = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
							+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
							+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
							+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
							+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
							+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.isBookAvailable(bookNotRegistered);
			fail("Not registered book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

	}

	@Test
	public void testGetBook() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
						+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
						+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
						+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
						+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
						+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
				"Gründ", 32.70, 2002, 03, 22);

		UserImpl user = new UserImpl("holyhope", "email.test@domain.com", "teacher");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);
		assertTrue(libraryImpl.getBook(book, user));
		assertFalse(libraryImpl.getBook(book, user));
		try {
			libraryImpl.getBook(null, user);
			fail("Null Book shouldn't be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.getBook(book, null);
			fail("Null User shouldn't be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			BookImpl book2 = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
							+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
							+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
							+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
							+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
							+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.getBook(book2, user);
			fail("Not registered Book shouldn't be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			UserImpl userNotRegistered = new UserImpl("Hacker", "hack@wanadoo.fr", "Pirate");
			libraryImpl.getBook(book, userNotRegistered);
			fail("Not registered User shouldn't be allowed.");
		} catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testRestoreBook() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		
		UserImpl user = new UserImpl("Borrower", "Borrower@test.com", "user");
		users.register(user, "123456");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);
		libraryImpl.getBook(book, user);
		try {
			libraryImpl.restoreBook(book, null);
			fail("Null user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.restoreBook(null, user);
			fail("Null book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			UserImpl userNotRegistered = new UserImpl("Hacker", "hack@wanadoo.fr", "Pirate");
			libraryImpl.restoreBook(book, userNotRegistered);
			fail("Not registered user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			BookImpl bookNotRegistered = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.restoreBook(bookNotRegistered, user);
			fail("Not registered book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		BookImpl bookAvailable = new BookImpl(3L, 3L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		libraryImpl.addBook(bookAvailable, user);
		assertFalse(libraryImpl.restoreBook(bookAvailable, user));

		UserImpl noBorrower = new UserImpl("NoBorrower", "NB@test.fr", "user");
		users.register(noBorrower, "NOBORROWER");
		assertFalse(libraryImpl.restoreBook(book, noBorrower));

		assertTrue(libraryImpl.restoreBook(book, user));
	}

	@Test
	public void testSubscribeToWaitingList() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		
		UserImpl user = new UserImpl("Borrower", "b@test.fr", "teacher");
		users.register(user, "123456");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);

		UserImpl userSubcriber = new UserImpl("Subscriber", "s@test.fr", "user");
		users.register(userSubcriber, "AZERTY");
		assertFalse(libraryImpl.subscribeToWaitingList(book, userSubcriber));
		libraryImpl.getBook(book, user);

		try {
			libraryImpl.subscribeToWaitingList(null, userSubcriber);
			fail("Null book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.subscribeToWaitingList(book, null);
			fail("Null user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			UserImpl userNotRegistered = new UserImpl("Hacker", "hack@wanadoo.fr", "Pirate");
			libraryImpl.subscribeToWaitingList(book, userNotRegistered);
			fail("Not registered user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			BookImpl bookNotRegistered = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.subscribeToWaitingList(bookNotRegistered, null);
			fail("Not registered book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		assertTrue(libraryImpl.subscribeToWaitingList(book, userSubcriber));
	}

	@Test
	public void testUnsubscribeToWaitingList() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		
		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);

		assertFalse(libraryImpl.unsubscribeToWaitingList(book, user));
		UserImpl user2 = new UserImpl("max2", "max2@test.fr", "user");
		users.register(user2, "AZERTY");
		libraryImpl.getBook(book, user2);
		libraryImpl.subscribeToWaitingList(book, user);

		try {
			libraryImpl.unsubscribeToWaitingList(null, user);
			fail("Null book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			libraryImpl.unsubscribeToWaitingList(book, null);
			fail("Null user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			UserImpl userNotRegistered = new UserImpl("Hacker", "hack@wanadoo.fr", "Pirate");
			libraryImpl.unsubscribeToWaitingList(book, userNotRegistered);
			fail("Not registered user should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		try {
			BookImpl bookNotRegistered = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.unsubscribeToWaitingList(bookNotRegistered, user);
			fail("Not registered book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		BookImpl bookNotSubcribed = new BookImpl(3L, 3L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		libraryImpl.addBook(bookNotSubcribed, user);
		assertFalse(libraryImpl.unsubscribeToWaitingList(bookNotSubcribed, user));

		assertFalse(libraryImpl.unsubscribeToWaitingList(book, user2));
		assertTrue(libraryImpl.unsubscribeToWaitingList(book, user));

	}

	@Test
	public void testSearchByBarCode() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		BookImpl book2 = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);

		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);
		libraryImpl.addBook(book2, user);
		try {
			libraryImpl.searchByBarCode(0);
			fail("BarCode should not be equal to 0");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.searchByBarCode(-1);
			fail("BarCode should be positive");
		} catch (IllegalArgumentException iae) {
		}
		assertNotSame(libraryImpl.searchByBarCode(book2.getBarCode()), book);
		assertSame(libraryImpl.searchByBarCode(book.getBarCode()), book);
	}

	@Test
	public void testSearchByISBN() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		BookImpl book2 = new BookImpl(1L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		BookImpl book3 = new BookImpl(1L, 3L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		
		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);
		libraryImpl.addBook(book2, user);
		libraryImpl.addBook(book3, user);

		try {
			libraryImpl.searchByISBN(0);
			fail("ISBN should not be equal to 0");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.searchByISBN(-1);
			fail("ISBN should be positive");
		} catch (IllegalArgumentException iae) {
		}
		Book[] books = libraryImpl.searchByISBN(1L);
		List<Book> expectedList = new ArrayList<>();
		expectedList.add(book);
		expectedList.add(book2);
		expectedList.add(book3);
		List<Book> list = Arrays.asList(books);
		assertEquals(list.size(), expectedList.size());
		for (Book bookInList : list) {
			assertTrue(expectedList.contains(bookInList));
		}
	}

	@Test
	public void testSearchByTitle() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Dix", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		BookImpl book2 = new BookImpl(1L, 2L, "Dix-huit", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		BookImpl book3 = new BookImpl(1L, 3L, "Dx-sept", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		
		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);
		libraryImpl.addBook(book2, user);
		libraryImpl.addBook(book3, user);

		try {
			libraryImpl.searchByTitle(null);
			fail("title should not be null");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.searchByTitle("");
			fail("title should not be empty");
		} catch (IllegalArgumentException iae) {
		}
		Book[] books = libraryImpl.searchByTitle("Dix");
		List<Book> expectedList = new ArrayList<>();
		expectedList.add(book);
		expectedList.add(book2);
		List<Book> list = Arrays.asList(books);
		assertEquals(list.size(), expectedList.size());
		for (Book bookInList : list) {
			assertTrue(expectedList.contains(bookInList));
		}
	}

	@Test
	public void testSearchByAuthor() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Dix", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		BookImpl book2 = new BookImpl(1L, 2L, "Dix-huit", "Jules Vene",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);
		BookImpl book3 = new BookImpl(1L, 3L, "Dx-sept", "Julis Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);

		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);
		libraryImpl.addBook(book2, user);
		libraryImpl.addBook(book3, user);

		try {
			libraryImpl.searchByAuthor(null);
			fail("author should not be null");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.searchByAuthor("");
			fail("author should not be empty");
		} catch (IllegalArgumentException iae) {
		}
		Book[] books = libraryImpl.searchByAuthor("Jules");
		List<Book> expectedList = new ArrayList<>();
		expectedList.add(book);
		expectedList.add(book2);
		List<Book> list = Arrays.asList(books);
		assertEquals(list.size(), expectedList.size());
		for (Book bookInList : list) {
			assertTrue(expectedList.contains(bookInList));
		}
	}

	@Test
	public void testIsBuyable() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);

		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);

		try {
			libraryImpl.isBuyable(null);
			fail("Book should not be null.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			BookImpl bookNotRegistered = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.isBuyable(bookNotRegistered);
			fail("Not Registered Book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}

		assertFalse(libraryImpl.isBuyable(book));
	}

	@Test
	public void testBuyBook() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);

		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);

		try {
			libraryImpl.buyBook(null, user);
			fail("Null book should not be allowed");
		} catch (IllegalArgumentException iae) {
		}
		try {
			libraryImpl.buyBook(book, null);
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException iae) {
		}
		try {
			BookImpl bookNotRegistered = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.buyBook(bookNotRegistered, user);
			fail("Not registered book should not be allowed");
		} catch (IllegalArgumentException iae) {
		}
		try {
			UserImpl userNotRegistered = new UserImpl("Hacker", "hack@wanadoo.fr", "Pirate");
			libraryImpl.buyBook(book, userNotRegistered);
			fail("Not registered user should not be allowed");
		} catch (IllegalArgumentException iae) {
		}

		assertFalse(libraryImpl.buyBook(book, user));
	}

	@Test
	public void testGetCost() throws RemoteException {
		Users users = new Users();
		LibraryImpl libraryImpl = new LibraryImpl(users);

		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.", "Gründ",
				32.70, 2002, 03, 22);

		UserImpl user = new UserImpl("max", "max@test.fr", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.ADD_BOOK);
		libraryImpl.addBook(book, user);

		try {
			libraryImpl.getCost(null);
			fail("Null book should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		try {
			BookImpl bookNotRegistered = new BookImpl(2L, 2L, "Vingt mille lieues sous les mers", "Jules Verne",
					"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique.",
					"Gründ", 32.70, 2002, 03, 22);
			libraryImpl.getCost(bookNotRegistered);
			fail("Not registered should not be allowed.");
		} catch (IllegalArgumentException iae) {
		}
		assertEquals(libraryImpl.getCost(book), 32.70, 0.005);
	}
}
