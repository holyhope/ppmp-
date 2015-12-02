package fr.mlv.school;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class UsersImpl implements Users {
	private final ConcurrentHashMap<User, byte[]> users = new ConcurrentHashMap<>();

	@Override
	public User findByUsername(String username) throws RemoteException {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("username is not valid");
		}
		return users.keySet().parallelStream().filter(u -> {
			try {
				return u.getUsername().equals(username);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			return false;
		}).findAny().orElse(null);
	}

	@Override
	public User findByEmail(String email) throws RemoteException {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("email is not valid");
		}
		return users.keySet().parallelStream().filter(u -> {
			try {
				return u.getEmail().equals(email);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			return false;
		}).findAny().orElse(null);
	}

	@Override
	public boolean authenticate(User user, String password) throws RemoteException {
		if (user == null || !users.containsKey(user)) {
			return false;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(password.getBytes());
			return Arrays.equals(users.get(user), thedigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.err);
			return false;
		}
	}

	private boolean isValidPassword(String password) {
		return password != null && password.length() > 5;
	}

	@Override
	public boolean register(User user, String password) throws RemoteException {
		if (user == null) {
			throw new IllegalArgumentException("user is not valid");
		}
		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("password is not valid");
		}
		if (users.containsKey(user)) {
			throw new IllegalArgumentException("The user is already registered");
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(password.getBytes());
			users.put(user, thedigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.err);
			return false;
		}
		return true;
	}

}
