package fr.mlv.school;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class Users {
	private static final String											   DIGEST_METHOD = "MD5";

	private final ConcurrentHashMap<String, User>						   users		 = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, byte[]>						   passwords	 = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Permission, CopyOnWriteArraySet<User>> permissions	 = new ConcurrentHashMap<>();

	public User findByUsername(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("username is not valid");
		}
		return users.entrySet().parallelStream().filter(entry -> entry.getKey().equals(username))
				.map(entry -> entry.getValue()).findAny().orElse(null);
	}

	public List<User> getUsersList() {
		return new ArrayList<User>(users.values());
	}

	public User findByEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("email is not valid");
		}

		return users.values().parallelStream().filter(user -> {
			try {
				return user.getEmail().equals(email);
			} catch (Exception e) {
				e.printStackTrace(System.err);
				return false;
			}
		}).findAny().orElse(null);
	}

	public boolean isRegistered(User user) throws RemoteException {
		if (user == null) {
			throw new IllegalArgumentException("user is not valid");
		}

		return passwords.containsKey(user.getUsername());
	}

	public boolean authenticate(User user, String password) throws RemoteException {
		if (user == null) {
			throw new IllegalArgumentException("user is not valid");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("password is not valid");
		}

		try {
			byte[] thedigest = getDigest(password);
			return Arrays.equals(passwords.get(user.getUsername()), thedigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.err);
			return false;
		}
	}

	private byte[] getDigest(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(DIGEST_METHOD);
		return md.digest(password.getBytes());
	}

	private boolean isValidPassword(String password) {
		return password != null && password.length() > 5;
	}

	public boolean register(User user, String password) throws RemoteException {
		if (user == null) {
			throw new IllegalArgumentException("user is not valid");
		}
		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("password is not valid");
		}
		if (passwords.containsKey(user)) {
			throw new IllegalArgumentException("The user is already registered");
		}
		if (findByEmail(user.getEmail()) != null) {
			throw new IllegalArgumentException("A user with this email is already registered");
		}
		try {
			byte[] thedigest = getDigest(password);
			users.put(user.getUsername(), user);
			passwords.put(user.getUsername(), thedigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.err);
			return false;
		}

		return true;
	}

	public boolean grantPermission(User user, Permission permission) throws RemoteException {
		if (user == null || !isRegistered(user)) {
			throw new IllegalArgumentException("User is not valid");
		}
		if (permission == null) {
			throw new IllegalArgumentException("Permission is not valid");
		}

		CopyOnWriteArraySet<User> permitedUsers = getOrCreatePermited(permission);

		if (permitedUsers.contains(user)) {
			return false;
		}

		return permitedUsers.add(user);
	}

	public boolean userCan(User user, Permission permission) throws RemoteException {
		if (user == null || !isRegistered(user)) {
			throw new IllegalArgumentException("User is not valid");
		}
		if (permission == null) {
			throw new IllegalArgumentException("Permission is not valid");
		}

		CopyOnWriteArraySet<User> permitedUsers = getOrCreatePermited(permission);

		return permitedUsers.contains(user);
	}

	public boolean revokePermission(User user, Permission permission) throws RemoteException {
		if (user == null || !isRegistered(user)) {
			throw new IllegalArgumentException("User is not valid");
		}
		if (permission == null) {
			throw new IllegalArgumentException("Permission is not valid");
		}

		CopyOnWriteArraySet<User> permitedUsers = getOrCreatePermited(permission);

		if (!permitedUsers.contains(user)) {
			return false;
		}

		return permitedUsers.remove(user);
	}

	public Set<User> getPermitedUsers(Permission permission) {
		Set<User> users = new HashSet<>();
		CopyOnWriteArraySet<User> permitedUsers = getOrCreatePermited(permission);

		users.addAll(permitedUsers);

		return users;
	}

	private CopyOnWriteArraySet<User> getOrCreatePermited(Permission permission) {
		if (!permissions.containsKey(permission)) {
			permissions.put(permission, new CopyOnWriteArraySet<>());
		}

		return permissions.get(permission);
	}

	public Set<Permission> getUserPermissions(User user) throws RemoteException {
		if (user == null || !isRegistered(user)) {
			throw new IllegalArgumentException("user is not valid");
		}
		return permissions.entrySet().parallelStream().filter(e -> e.getValue().contains(user)).map(e -> e.getKey())
				.collect(Collectors.toSet());
	}

	public boolean removeUser(User findByUsername) {
		// TODO Auto-generated method stub
		return false;
	}
}
