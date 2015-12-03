package fr.mlv.school;

import java.rmi.RemoteException;
import java.util.Set;

public interface Users {
	public User findByUsername(String username);

	public User findByEmail(String email);

	public boolean authenticate(User user, String password);

	public boolean isRegistered(User user);

	public boolean register(User user, String password) throws RemoteException;

	public boolean grantPermission(User user, Permission permission);

	public boolean revokePermission(User user, Permission permission);

	boolean userCan(User user, Permission permission);

	Set<User> getPermitedUsers(Permission permission);

	Set<Permission> getUserPermissions(User user);
}
