package fr.mlv.school;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class UserImpl extends UnicastRemoteObject implements User {
	private static final long				  serialVersionUID = 1L;
	private static final Pattern			  emailValidator   = Pattern.compile("^.+@.+\\..+$");

	private final String					  userName;
	private final String					  email;
	private final String					  role;

	private ArrayList<Notification>			  notifications	   = new ArrayList<>();

	private ArrayList<Consumer<Notification>> observers;

	public UserImpl(String userName, String email, String role) throws RemoteException {
		if (userName == null || userName.length() == 0) {
			throw new IllegalArgumentException("username is not valid");
		}
		if (email == null || !emailValidator.matcher(email).find()) {
			throw new IllegalArgumentException("email is not valid");
		}
		if (role == null) {
			throw new IllegalArgumentException("role is not valid");
		}
		this.userName = userName;
		this.email = email;
		this.role = role;
	}

	@Override
	public String toString() {
		return userName + " (" + email + ")";
	}

	public String getEmail() throws RemoteException {
		return email;
	}

	public String getUsername() throws RemoteException {
		return userName;
	}

	public String getRole() throws RemoteException {
		return role;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserImpl)) {
			return false;
		}

		UserImpl user = (UserImpl) obj;
		return userName.equals(user.userName);
	}

	@Override
	public boolean consumeNotification(Notification notification) throws RemoteException {
		return notifications.remove(Objects.requireNonNull(notification));
	}

	@Override
	public boolean addNotification(Notification notification) throws RemoteException {
		boolean success = notifications.add(Objects.requireNonNull(notification));
		if (!success) {
			return false;
		}
		observers.parallelStream().forEach(o -> o.accept(notification));
		return true;
	}

	@Override
	public List<Notification> getNotifications() throws RemoteException {
		return new LinkedList<>(notifications);
	}

	@Override
	public boolean addObserver(Consumer<Notification> consumer) throws RemoteException {
		return observers.add(Objects.requireNonNull(consumer));
	}
}
