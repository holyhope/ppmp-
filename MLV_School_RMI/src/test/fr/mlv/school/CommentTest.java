package test.fr.mlv.school;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import fr.mlv.school.CommentImpl;

public class CommentTest {
	@Test
	public void testCommentImpl() throws RemoteException {
		new CommentImpl("CCRIDER", 8,
				"« Un kimono pour linceul » est un authentique thriller dans la mesure où les cadavres s’accumulent au fil de cette sombre et douloureuse histoire. Il a néanmoins la particularité d’être atypique tout d’abord pour son cadre exotique (l’auteur semble avoir une connaissance approfondie de la société japonaise en général et de la mafia en particulier) et ensuite pour son personnage principal, un basque, ancien complice des terroristes de l’ETA, qui ressort broyé de la machine répressive espagnole et auquel la vie n’a pas fait de cadeau. D’où une empathie immédiate pour son destin tragique. L’intrigue de qualité est menée habilement. On y trouve du suspens, des rebondissements et des questions pendantes qui ménagent l’intérêt tout au long du livre et jusqu’à une fin assez surprenante. Le style est souvent descriptif et parfois un peu lent mais ce n’est pas vraiment un défaut vu que cela permet d’apprendre tant de choses sur un Japon peu connu et une réalité yakusa plutôt terrifiante.");
	}

	@Test
	public void testGetAuthor() throws RemoteException {
		CommentImpl commentImpl = new CommentImpl("CCRIDER", 8,
				"« Un kimono pour linceul » est un authentique thriller dans la mesure où les cadavres s’accumulent au fil de cette sombre et douloureuse histoire. Il a néanmoins la particularité d’être atypique tout d’abord pour son cadre exotique (l’auteur semble avoir une connaissance approfondie de la société japonaise en général et de la mafia en particulier) et ensuite pour son personnage principal, un basque, ancien complice des terroristes de l’ETA, qui ressort broyé de la machine répressive espagnole et auquel la vie n’a pas fait de cadeau. D’où une empathie immédiate pour son destin tragique. L’intrigue de qualité est menée habilement. On y trouve du suspens, des rebondissements et des questions pendantes qui ménagent l’intérêt tout au long du livre et jusqu’à une fin assez surprenante. Le style est souvent descriptif et parfois un peu lent mais ce n’est pas vraiment un défaut vu que cela permet d’apprendre tant de choses sur un Japon peu connu et une réalité yakusa plutôt terrifiante.");
		assertEquals("CCRIDER", commentImpl.getAuthor());
	}

	@Test
	public void testGetMark() throws RemoteException {
		CommentImpl commentImpl = new CommentImpl("CCRIDER", 8,
				"« Un kimono pour linceul » est un authentique thriller dans la mesure où les cadavres s’accumulent au fil de cette sombre et douloureuse histoire. Il a néanmoins la particularité d’être atypique tout d’abord pour son cadre exotique (l’auteur semble avoir une connaissance approfondie de la société japonaise en général et de la mafia en particulier) et ensuite pour son personnage principal, un basque, ancien complice des terroristes de l’ETA, qui ressort broyé de la machine répressive espagnole et auquel la vie n’a pas fait de cadeau. D’où une empathie immédiate pour son destin tragique. L’intrigue de qualité est menée habilement. On y trouve du suspens, des rebondissements et des questions pendantes qui ménagent l’intérêt tout au long du livre et jusqu’à une fin assez surprenante. Le style est souvent descriptif et parfois un peu lent mais ce n’est pas vraiment un défaut vu que cela permet d’apprendre tant de choses sur un Japon peu connu et une réalité yakusa plutôt terrifiante.");
		assertEquals(8, commentImpl.getMark());
	}

	@Test
	public void testGetContent() throws RemoteException {
		CommentImpl commentImpl = new CommentImpl("CCRIDER", 8,
				"« Un kimono pour linceul » est un authentique thriller dans la mesure où les cadavres s’accumulent au fil de cette sombre et douloureuse histoire. Il a néanmoins la particularité d’être atypique tout d’abord pour son cadre exotique (l’auteur semble avoir une connaissance approfondie de la société japonaise en général et de la mafia en particulier) et ensuite pour son personnage principal, un basque, ancien complice des terroristes de l’ETA, qui ressort broyé de la machine répressive espagnole et auquel la vie n’a pas fait de cadeau. D’où une empathie immédiate pour son destin tragique. L’intrigue de qualité est menée habilement. On y trouve du suspens, des rebondissements et des questions pendantes qui ménagent l’intérêt tout au long du livre et jusqu’à une fin assez surprenante. Le style est souvent descriptif et parfois un peu lent mais ce n’est pas vraiment un défaut vu que cela permet d’apprendre tant de choses sur un Japon peu connu et une réalité yakusa plutôt terrifiante.");
		assertEquals(
				"« Un kimono pour linceul » est un authentique thriller dans la mesure où les cadavres s’accumulent au fil de cette sombre et douloureuse histoire. Il a néanmoins la particularité d’être atypique tout d’abord pour son cadre exotique (l’auteur semble avoir une connaissance approfondie de la société japonaise en général et de la mafia en particulier) et ensuite pour son personnage principal, un basque, ancien complice des terroristes de l’ETA, qui ressort broyé de la machine répressive espagnole et auquel la vie n’a pas fait de cadeau. D’où une empathie immédiate pour son destin tragique. L’intrigue de qualité est menée habilement. On y trouve du suspens, des rebondissements et des questions pendantes qui ménagent l’intérêt tout au long du livre et jusqu’à une fin assez surprenante. Le style est souvent descriptif et parfois un peu lent mais ce n’est pas vraiment un défaut vu que cela permet d’apprendre tant de choses sur un Japon peu connu et une réalité yakusa plutôt terrifiante.",
				commentImpl.getContent());
	}

	@Test
	public void testGetDate() throws RemoteException {
		CommentImpl commentImpl = new CommentImpl("CCRIDER", 8,
				"« Un kimono pour linceul » est un authentique thriller dans la mesure où les cadavres s’accumulent au fil de cette sombre et douloureuse histoire. Il a néanmoins la particularité d’être atypique tout d’abord pour son cadre exotique (l’auteur semble avoir une connaissance approfondie de la société japonaise en général et de la mafia en particulier) et ensuite pour son personnage principal, un basque, ancien complice des terroristes de l’ETA, qui ressort broyé de la machine répressive espagnole et auquel la vie n’a pas fait de cadeau. D’où une empathie immédiate pour son destin tragique. L’intrigue de qualité est menée habilement. On y trouve du suspens, des rebondissements et des questions pendantes qui ménagent l’intérêt tout au long du livre et jusqu’à une fin assez surprenante. Le style est souvent descriptif et parfois un peu lent mais ce n’est pas vraiment un défaut vu que cela permet d’apprendre tant de choses sur un Japon peu connu et une réalité yakusa plutôt terrifiante.");
		assertEquals(System.currentTimeMillis(), commentImpl.getTime(), 1000);
	}

}
