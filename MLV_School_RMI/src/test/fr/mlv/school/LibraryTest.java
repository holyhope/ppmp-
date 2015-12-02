package test.fr.mlv.school;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import fr.mlv.school.BookImpl;
import fr.mlv.school.LibraryImpl;
import fr.mlv.school.UserImpl;

public class LibraryTest {

	@Test
	public void testGetName() throws RemoteException {
		LibraryImpl libraryImpl = new LibraryImpl();
		assertEquals("MLV-School", libraryImpl.getName());
	}

	@Test
	public void testAddBook() throws RemoteException {
		LibraryImpl libraryImpl = new LibraryImpl();
		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
						+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
						+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
						+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
						+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
						+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
				"Gründ", 32.70, 2002, 03, 22);
		UserImpl user = new UserImpl("holyhope", "email.test@domain.com", "user");
		assertFalse(libraryImpl.addBook(book, user));
		user = new UserImpl("holyhope", "email.test@domain.com", "teacher");
		assertFalse(libraryImpl.addBook(book, user));
	}

	@Test
	public void testDeleteBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsBookAvailable() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testRestoreBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubscribeToWaitingList() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnsubscribeToWaitingList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchByBarCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchByISBN() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchByTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchByAuthor() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsBuyable() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuyBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCost() {
		fail("Not yet implemented");
	}

}
