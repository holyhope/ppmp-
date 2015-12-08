package fr.mlv.school;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LibraryServer {
	private final Users		  users;
	private final LibraryImpl library;

	private static Scanner	  scannerSystem;

	public LibraryServer(Users users, LibraryImpl library) {
		this.users = users;
		this.library = library;
	}

	public static void main(String[] args) {
		try {
			Users users = new Users();
			LibraryImpl library = new LibraryImpl(users);

			LibraryServer libraryServer = new LibraryServer(users, library);

			libraryServer.bind("rmi://localhost:1099/LibraryService");

			System.out.println("Server is Ready.");

			try (Scanner scanner = new Scanner(System.in)) {
				scannerSystem = scanner;
				libraryServer.listenCommand(scanner, System.out);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		}
	}

	private static enum Command {
		QUIT, HELP, ADDUSER, REMOVEUSER, ADDPERMISSION, REMOVEPERMISSION, SAVE, LISTUSERS, ADDBOOK, REMOVEBOOK, LOAD;

		public String toString() {
			switch (this) {
				case QUIT:
					return "quit";
				case SAVE:
					return "save";
				case HELP:
					return "help";
				case ADDBOOK:
					return "addbook";
				case REMOVEBOOK:
					return "removebook";
				case ADDUSER:
					return "adduser";
				case REMOVEUSER:
					return "removeuser";
				case LISTUSERS:
					return "listusers";
				case ADDPERMISSION:
					return "addpermission";
				case REMOVEPERMISSION:
					return "removepermission";
				case LOAD:
					return "load";
				default:
					throw new IllegalArgumentException("Invalid command");
			}
		};

		public static Command fromString(String command) {
			switch (command) {
				case "quit":
					return QUIT;
				case "save":
					return SAVE;
				case "help":
					return HELP;
				case "adduser":
					return ADDUSER;
				case "removeuser":
					return REMOVEUSER;
				case "listusers":
					return Command.LISTUSERS;
				case "addpermission":
					return ADDPERMISSION;
				case "removepermission":
					return REMOVEPERMISSION;
				case "load":
					return LOAD;
				default:
					throw new IllegalArgumentException("Invalid command (" + command + ")");
			}
		}
	}

	private void listenCommand(Scanner scanner, PrintStream out) {
		helpCommand(out, scanner);
		while (scanner.hasNextLine()) {
			try {
				String command = scanner.nextLine();
				switch (Command.fromString(command)) {
					case QUIT:
						System.exit(0);
						break;
					case HELP:
						if (!helpCommand(out, scanner)) {
							System.err.println("An error occured");
						}
						break;
					case ADDBOOK:
						if (addBookCommand(out, scanner)) {
							System.out.println("Book added.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case REMOVEBOOK:
						if (removeBookCommand(out, scanner)) {
							System.out.println("Book removed.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case ADDUSER:
						if (addUserCommand(out, scanner)) {
							System.out.println("User added.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case REMOVEUSER:
						if (removeUserCommand(out, scanner)) {
							System.out.println("User removed.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case ADDPERMISSION:
						if (addPermissionCommand(out, scanner)) {
							System.out.println("Permission added.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case REMOVEPERMISSION:
						if (removePermissionCommand(out, scanner)) {
							System.out.println("Permission removed.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case SAVE:
						if (saveCommand(out, scanner)) {
							System.out.println("File saved.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case LOAD:
						if (loadCommad(out, scanner)) {
							System.out.println("File loaded.");
						} else {
							System.err.println("An error occured");
						}
						break;
					case LISTUSERS:
						if (!listUsersCommand(out, scanner)) {
							System.err.println("An error occured");
						}
						break;
				}
			} catch (Exception e) {
				System.err.println("An error occured");
				e.printStackTrace(System.err);
			}
		}
	}

	private boolean loadCommad(PrintStream out, Scanner scanner) throws IOException {
		if (out != null) {
			out.print("Nom du fichier :");
		}
		String filename = scanner.nextLine();
		try (FileInputStream in = new FileInputStream(filename); Scanner scannerFile = new Scanner(in)) {
			listenCommand(scannerFile, null);
		}
		return true;
	}

	private boolean removeUserCommand(PrintStream out, Scanner scanner) {
		if (out != null) {
			out.print("Username :");
		}
		String username = scanner.nextLine();
		return users.removeUser(users.findByUsername(username));
	}

	private boolean removeBookCommand(PrintStream out, Scanner scanner) throws RemoteException {
		if (out != null) {
			out.print("Code barre :");
		}
		Long barCode = scanner.nextLong();
		return library.forceDeleteBook(library.searchByBarCode(barCode));
	}

	private boolean listUsersCommand(PrintStream out, Scanner scanner) throws RemoteException {
		if (out == null) {
			return false;
		}
		List<User> list = users.getUsersList();
		for (User user : list) {
			out.println(user.getUsername() + "\t" + user.getEmail() + "\t(" + user.getRole() + ")");
		}
		return true;
	}

	private boolean helpCommand(PrintStream out, Scanner scanner) {
		if (out == null) {
			return false;
		}
		out.println("Commands:");
		out.println(" - " + Command.HELP);
		out.println(" - " + Command.ADDBOOK);
		out.println(" - " + Command.REMOVEBOOK);
		out.println(" - " + Command.ADDUSER);
		out.println(" - " + Command.REMOVEUSER);
		out.println(" - " + Command.ADDPERMISSION);
		out.println(" - " + Command.REMOVEPERMISSION);
		out.println(" - " + Command.SAVE);
		out.println(" - " + Command.QUIT);
		return true;
	}

	private boolean saveCommand(PrintStream out, Scanner scanner) throws IOException {
		if (out != null) {
			out.print("Nom du fichier :");
		}
		String filename = scanner.nextLine();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
			List<User> list = users.getUsersList();
			for (User user : list) {
				writer.write("adduser\n");
				writer.write(user.getUsername() + "\n");
				writer.write(user.getEmail() + "\n");
				writer.write(user.getRole() + "\n");
				writer.write("\n"); // Password

				Set<Permission> permissions = users.getUserPermissions(user);
				for (Permission permission : permissions) {
					writer.write("addpermission\n");
					writer.write(user.getUsername() + "\n");
					writer.write(permission.toString() + "\n");
				}
			}
			return true;
		}
	}

	private boolean addPermissionCommand(PrintStream out, Scanner scanner) throws RemoteException {
		if (out != null) {
			out.print("Nom de l'utilisateur :");
		}
		String username = scanner.nextLine();
		if (out != null) {
			out.print("Permission :");
		}
		String type = scanner.nextLine();
		User user = users.findByUsername(username);
		return users.grantPermission(user, Permission.fromString(type));
	}

	private boolean removePermissionCommand(PrintStream out, Scanner scanner) throws RemoteException {
		if (out != null) {
			out.print("Nom de l'utilisateur :");
		}
		String username = scanner.nextLine();
		if (out != null) {
			out.print("Permission :");
		}
		String type = scanner.nextLine();
		User user = users.findByUsername(username);
		return users.revokePermission(user, Permission.fromString(type));
	}

	private boolean addBookCommand(PrintStream out, Scanner scanner) throws RemoteException {
		if (out != null) {
			out.print("Nom de l'utilisateur :");
		}
		String username = scanner.nextLine();
		if (out != null) {
			out.print("ISBN :");
		}
		Long isbn = scanner.nextLong();
		if (out != null) {
			out.print("Code bar :");
		}
		Long barCode = scanner.nextLong();
		if (out != null) {
			out.print("Titre :");
		}
		String title = scanner.nextLine();
		if (out != null) {
			out.print("Auteur :");
		}
		String author = scanner.nextLine();
		if (out != null) {
			out.print("Résumé :");
		}
		StringBuilder summaryBuilder = new StringBuilder();
		boolean isFinished = false;
		while (!isFinished) {
			String nextLine = scanner.nextLine();
			isFinished = nextLine.isEmpty();
			summaryBuilder.append(nextLine);
		}
		String summary = summaryBuilder.toString();
		if (out != null) {
			out.print("Publisher :");
		}
		String publisher = scanner.nextLine();
		if (out != null) {
			out.print("Coût :");
		}
		float cost = scanner.nextFloat();
		if (out != null) {
			out.print("Date de publication (jj/mm/aaaa) :");
		}
		int dayPublish = scanner.nextInt();
		int monthPublish = scanner.nextInt();
		int yearPublish = scanner.nextInt();
		BookImpl book = new BookImpl(isbn, barCode, title, author, summary, publisher, cost, yearPublish, monthPublish,
				dayPublish);
		User user = users.findByUsername(username);

		return library.addBook(book, user);
	}

	private boolean addUserCommand(PrintStream out, Scanner scanner) throws RemoteException {
		if (out != null) {
			out.print("Nom d'utilisateur :");
		}
		String username = scanner.nextLine();
		if (out != null) {
			out.print("Email :");
		}
		String email = scanner.nextLine();
		if (out != null) {
			out.print("Rôle :");
		}
		String role = scanner.nextLine();
		UserImpl user = new UserImpl(username, email, role);

		if (out != null) {
			out.print("Mot de passe :");
		}
		String password = scanner.nextLine();
		if (password.isEmpty()) {
			System.out.print("Mot de passe pour l'uilisateur \"" + user.getUsername() + "\" :");
			password = scannerSystem.nextLine();
		}

		return users.register(user, password);
	}

	private void bind(String serviceName) throws RemoteException, MalformedURLException {
		// String codebase =
		// "file:///Users/Maxime/git/ppmp-master/MLV_School_RMI/src";
		// System.setProperty("java.rmi.server.codebase", codebase);

		System.setProperty("java.security.policy", "sec.policy");
		System.setSecurityManager(new RMISecurityManager());
		Naming.rebind(serviceName, library);
	}
}
