package fr.mlv.school;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Pattern;

public class UserImpl extends UnicastRemoteObject implements User {
	private static final long	 serialVersionUID = 1L;
	private static final Pattern emailValidator	  = Pattern.compile("^.+@.+\\..+$");

	private final String		 userName;
	private final String		 email;
	private final String		 role;

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
}
