package fr.mlv.school;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class UsersImpl implements Users {
	private static final String											   DIGEST_METHOD = "MD5";

	private final ConcurrentHashMap<User, byte[]>						   users		 = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Permission, CopyOnWriteArraySet<User>> permissions	 = new ConcurrentHashMap<>();

	@Override
	public User findByUsername(String username) {
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
	public User findByEmail(String email) {
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
	public boolean isRegistered(User user) {
		if (user == null) {
			throw new IllegalArgumentException("user is not valid");
		}

		return users.containsKey(user);
	}

	@Override
	public boolean authenticate(User user, String password) {
		if (user == null) {
			throw new IllegalArgumentException("user is not valid");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("password is not valid");
		}

		try {
			byte[] thedigest = getDigest(password);
			return Arrays.equals(users.get(user), thedigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.err);
			return false;
		}
	}

	private byte[] getDigest(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(DIGEST_METHOD);
		byte[] thedigest = md.digest(password.getBytes());
		return thedigest;
	}

	private boolean isValidPassword(String password) {
		return password != null && password.length() > 5;
	}

	@Override
	public boolean register(User user, String password) throws RemoteException {
		if (user == null || isRegistered(user)) {
			throw new IllegalArgumentException("user is not valid");
		}
		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("password is not valid");
		}
		if (users.containsKey(user)) {
			throw new IllegalArgumentException("The user is already registered");
		}
		if (findByEmail(user.getEmail()) != null) {
			throw new IllegalArgumentException("A user with this email is already registered");
		}
		try {
			byte[] thedigest = getDigest(password);
			users.put(user, thedigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.err);
			return false;
		}
		return true;
	}

	@Override
	public boolean grantPermission(User user, Permission permission) {
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

	@Override
	public boolean userCan(User user, Permission permission) {
		if (user == null || !isRegistered(user)) {
			throw new IllegalArgumentException("User is not valid");
		}
		if (permission == null) {
			throw new IllegalArgumentException("Permission is not valid");
		}

		CopyOnWriteArraySet<User> permitedUsers = getOrCreatePermited(permission);

		return permitedUsers.contains(user);
	}

	@Override
	public boolean revokePermission(User user, Permission permission) {
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

	@Override
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

	@Override
	public Set<Permission> getUserPermissions(User user) {
		if (user == null || !isRegistered(user)) {
			throw new IllegalArgumentException("user is not valid");
		}
		return permissions.entrySet().parallelStream().filter(e -> e.getValue().contains(user)).map(e -> e.getKey())
				.collect(Collectors.toSet());
	}
}
