package fr.mlv.school.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.GridLayout;

import javax.swing.BoxLayout;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import fr.mlv.school.BookImpl;
import fr.mlv.school.LibraryImpl;
import fr.mlv.school.Permission;
import fr.mlv.school.UserImpl;
import fr.mlv.school.Users;

import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class BiblioGUI {

	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private LibraryImpl lib;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BiblioGUI window = new BiblioGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws RemoteException 
	 */
	public BiblioGUI() throws RemoteException {
		lib = new LibraryImpl();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws RemoteException 
	 */
	private void initialize() throws RemoteException {
		
		int frameWidth = 900;
		int frameHeight = 600;
		Color myColor = new Color(218,165,32);
		
		BookImpl book = new BookImpl(1L, 1L, "Vingt mille lieues sous les mers", "Jules Verne",
				"L'apparition d'une bête monstrueuse en 1866 aux quatre coins des mers défraie la chronique. L'animal, rapide, fusiforme et phosphorescent, est responsable de plusieurs naufrages, brisant le bois des navires avec une force colossale. De retour d'une expédition dans le Nebraska, Pierre Aronnax, professeur suppléant au Muséum d'histoire naturelle de Paris, émet l'hypothèse d'un narval géant. "
						+ "Les compagnies d'assurances maritimes menacent d'augmenter leurs prix et demandent que le monstre soit éliminé. Une grande chasse est alors organisée à bord de l’Abraham-Lincoln, fleuron de la marine américaine, mené par le commandant Farragut. Aronnax reçoit une lettre du secrétaire de la Marine lui demandant de rejoindre l’expédition pour représenter la France. Le scientifique embarque avec son fidèle domestique flamand, Conseil. A bord, ils font la connaissance de Ned Land, harponneur originaire de Québec. Après des mois de navigation, la confrontation avec le monstre a enfin lieu, et l'’Abraham-Lincoln est endommagé. Un choc entre le monstre et la frégate projette Aronnax, Conseil et Ned par dessus bord. Ils échouent finalement sur le dos du monstre, qui n'est autre qu'un sous-marin en tôle armée. Les naufragés sont faits prisonniers et se retrouvent à bord du mystérieux appareil. Ils font alors connaissance du capitaine Nemo, qui refuse de leur rendre la liberté. "
						+ "« Vous êtes venus surprendre un secret que nul homme au monde ne doit pénétrer, le secret de toute mon existence ! Et vous croyez que je vais vous renvoyer sur cette terre qui ne doit plus me connaître ! Jamais ! En vous retenant, ce n’est pas vous que je garde, c’est moi-même1 ! ». "
						+ "Alors que Ned et Conseil ne cherchent qu'à s'évader, Aronnax éprouve une certaine curiosité pour Nemo, cet homme qui a fui le monde de la surface et la société. Le capitaine consent à révéler au savant les secrets des mers. Il lui fait découvrir le fonctionnement de son sous-marin, le Nautilus, et décide d’entreprendre un tour du monde des profondeurs. Nos héros découvrent des trésors engloutis, comme l'Atlantide et des épaves d'anciens navires, s'aventurent sur les îles du Pacifique et la banquise du Pôle Sud, chassent dans les forêts sous-marines et combattent des calmars géants. Aronnax finit par découvrir que Nemo utilise le Nautilus comme une machine de guerre, un instrument de vengeance contre les navires appartenant à une « nation maudite » à laquelle il voue une terrible haine. "
						+ "« Je suis le droit, je suis la justice ! me dit-il. Je suis l’opprimé, et voilà l’oppresseur ! C’est par lui que tout ce que j’ai aimé, chéri, vénéré, patrie, femme, enfants, mon père, ma mère, j’ai vu tout périr ! Tout ce que je hais est là2 ! ». "
						+ "Aronnax, Ned et Conseil parviennent à s’échapper. Ils s’embarquent à bord d'une chaloupe et accosteront sur une des îles Lofoten. Ils ne sauront jamais ce qu’est devenu le Nautilus, peut-être englouti dans un maelstrom.",
				"Gründ", 32.70, 2002, 03, 22);
		
		Users users = lib.getUsers();

		UserImpl user = new UserImpl("holyhope", "email.test@domain.com", "user");
		users.register(user, "AZERTY");
		users.grantPermission(user, Permission.REMOVE_BOOK);
		
		lib.addBook(book, user);

		
		//Fenetre principale
		frame = new JFrame();
		frame.setTitle("Biblioteque MLV");
		//frame.setSize(frameWidth, frameHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		//Up
		JPanel panelTop = new JPanel();
		frame.getContentPane().add(panelTop, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		ImageIcon imageBiblio = new ImageIcon(new ImageIcon(BiblioGUI.class.getResource("/fr/mlv/school/gui/biblio.jpg")).getImage().getScaledInstance(frame.getWidth(), 150, Image.SCALE_DEFAULT));
		panelTop.setLayout(new GridLayout(0, 1, 0, 300));
		
		lblNewLabel.setIcon(imageBiblio);
		panelTop.add(lblNewLabel);
		
	
		//Center
		JSplitPane splitPaneCenter = new JSplitPane();
		splitPaneCenter.setResizeWeight(0.05);
		frame.getContentPane().add(splitPaneCenter, BorderLayout.CENTER);
		
		JPanel panelRight = new JPanel();
		splitPaneCenter.setRightComponent(panelRight);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(myColor);
		panelRight.add(scrollPane);
	
		Object data[][] = {{"Test1","Test2","Test2","Test4","Test5","Test6","Ajouter au panier"}};
		String headers[]= {"Isbn","Title","Author","Summary","Publisher","Format","+"};
		
		DefaultTableModel model = new DefaultTableModel(data,headers);
		table = new JTable(model);
		
		ButtonEditor buttonAddPanier = new ButtonEditor(new JCheckBox());
		buttonAddPanier.addTableModel(model);
		
		table.getColumn("+").setCellRenderer(new ButtonRenderer());
		    table.getColumn("+").setCellEditor(buttonAddPanier);
		scrollPane.setViewportView(table);
		
		JDesktopPane desktopPaneLeft = new JDesktopPane();
		desktopPaneLeft.setBackground(new Color(218, 165, 32));
		splitPaneCenter.setLeftComponent(desktopPaneLeft);
		GridBagLayout gbl_desktopPaneLeft = new GridBagLayout();
		gbl_desktopPaneLeft.columnWidths = new int[]{183, 0};
		gbl_desktopPaneLeft.rowHeights = new int[]{22, 23, 23, 160, 16, 28, 1, 29, 0};
		gbl_desktopPaneLeft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_desktopPaneLeft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		desktopPaneLeft.setLayout(gbl_desktopPaneLeft);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnexionGUI connexionGUI = new ConnexionGUI();
			}
		});
		GridBagConstraints gbc_btnConnexion = new GridBagConstraints();
		gbc_btnConnexion.anchor = GridBagConstraints.NORTH;
		gbc_btnConnexion.insets = new Insets(0, 0, 5, 0);
		gbc_btnConnexion.gridx = 0;
		gbc_btnConnexion.gridy = 1;
		desktopPaneLeft.add(btnConnexion, gbc_btnConnexion);
		
		JButton btnPanier = new JButton("Mon panier");
		btnPanier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanierGUI panierGUI = new PanierGUI();
			}
		});
		GridBagConstraints gbc_btnPanier = new GridBagConstraints();
		gbc_btnPanier.anchor = GridBagConstraints.NORTH;
		gbc_btnPanier.insets = new Insets(0, 0, 5, 0);
		gbc_btnPanier.gridx = 0;
		gbc_btnPanier.gridy = 2;
		desktopPaneLeft.add(btnPanier, gbc_btnPanier);
		
		JLabel lblFindLivre = new JLabel("Rechercher un livre :");
		GridBagConstraints gbc_lblFindLivre = new GridBagConstraints();
		gbc_lblFindLivre.fill = GridBagConstraints.VERTICAL;
		gbc_lblFindLivre.insets = new Insets(0, 0, 5, 0);
		gbc_lblFindLivre.gridx = 0;
		gbc_lblFindLivre.gridy = 4;
		desktopPaneLeft.add(lblFindLivre, gbc_lblFindLivre);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 5;
		desktopPaneLeft.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnFindBook = new JButton("Rechercher");
		btnFindBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean find = false;
				table.setRowSelectionAllowed(true);
				String bookInfo = textField.getText();
				System.out.println(bookInfo);
				
				for (int row = 0; row <= table.getRowCount() - 1; row++) {

	                for (int col = 0; col <= table.getColumnCount() - 1; col++) {

	                    if (bookInfo.equals(table.getValueAt(row, col))) {
	                    	find = true;
	                    	
	                    	System.out.println("trouvé en " + row +" "+col);
	                    	
	                        // this will automatically set the view of the scroll in the location of the value
	                        table.scrollRectToVisible(table.getCellRect(row, 0, true));

	                        // this will automatically set the focus of the searched/selected row/value
	                        table.setRowSelectionInterval(row, row);

	                    }
	                }
	            }
				
				if(!find){
					table.setRowSelectionAllowed(false);
				}
			}
		});
		
		GridBagConstraints gbc_btnFindBook = new GridBagConstraints();
		gbc_btnFindBook.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindBook.anchor = GridBagConstraints.SOUTH;
		gbc_btnFindBook.gridx = 0;
		gbc_btnFindBook.gridy = 6;
		desktopPaneLeft.add(btnFindBook, gbc_btnFindBook);
		
		//Bottom
		JPanel panelDown = new JPanel();
		frame.getContentPane().add(panelDown, BorderLayout.SOUTH);
	}
}

