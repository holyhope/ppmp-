package test.fr.mlv.school;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import org.junit.Test;

import fr.mlv.school.Permission;
import fr.mlv.school.UserImpl;
import fr.mlv.school.Users;

public class UsersTest {
	@Test
	public void testFindByUsername() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user = new UserImpl("administrator", "admin@upem.fr", "admin");
		usersImpl.register(user, "password");
		assertNull(usersImpl.findByUsername("badusername"));
		try {
			usersImpl.findByUsername(null);
			fail("Null username should not be allowed");
		} catch (IllegalArgumentException e) {
		}
		try {
			usersImpl.findByUsername("");
			fail("Empty username should not be allowed");
		} catch (IllegalArgumentException e) {
		}
		assertEquals(user, usersImpl.findByUsername("administrator"));
	}

	@Test
	public void testFindByEmail() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user = new UserImpl("administrator", "admin@upem.fr", "admin");
		usersImpl.register(user, "password");
		assertNull(usersImpl.findByEmail("bad.email@upem.fr"));
		try {
			usersImpl.findByEmail(null);
			fail("Null email should not be allowed");
		} catch (IllegalArgumentException e) {
		}
		try {
			usersImpl.findByEmail("");
			fail("Empty email should not be allowed");
		} catch (IllegalArgumentException e) {
		}
		assertEquals(user, usersImpl.findByEmail("admin@upem.fr"));
	}

	@Test
	public void testAuthenticate() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user = new UserImpl("administrator", "admin@upem.fr", "admin");
		usersImpl.register(user, "password");
		assertFalse(usersImpl.authenticate(user, "badpassword"));
		assertTrue(usersImpl.authenticate(user, "password"));
		assertTrue(usersImpl.authenticate(user, "password"));

		try {
			usersImpl.authenticate(null, "password");
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.authenticate(user, null);
			fail("Null password should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.authenticate(user, "");
			fail("Empty password should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testIsRegistered() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user1 = new UserImpl("administrator", "admin@upem.fr", "admin");
		UserImpl user2 = new UserImpl("administrator2", "admin2@upem.fr", "admin");
		UserImpl user3 = new UserImpl("administrator", "admin3@upem.fr", "admin");
		UserImpl user4 = new UserImpl("administrator4", "admin@upem.fr", "admin");

		assertFalse(usersImpl.isRegistered(user1));
		assertTrue(usersImpl.register(user1, "password"));
		assertTrue(usersImpl.isRegistered(user1));
		assertTrue(usersImpl.isRegistered(user3));
		assertFalse(usersImpl.isRegistered(user4));

		assertFalse(usersImpl.isRegistered(user2));

		try {
			usersImpl.isRegistered(null);
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRegister() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user1 = new UserImpl("administrator", "admin@upem.fr", "admin");
		UserImpl user2 = new UserImpl("administrator2", "admin2@upem.fr", "admin");
		UserImpl user3 = new UserImpl("administrator", "admin3@upem.fr", "admin");
		UserImpl user4 = new UserImpl("administrator4", "admin@upem.fr", "admin");

		assertTrue(usersImpl.register(user1, "password"));
		assertTrue(usersImpl.register(user2, "password"));

		try {
			usersImpl.register(user3, "password");
			fail("Duplicate username should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.register(user4, "password");
			fail("Duplicate email should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.register(null, "password");
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.register(user1, null);
			fail("Null password should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.register(user1, "");
			fail("Empty password should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testGrantPermission() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user = new UserImpl("administrator", "admin@upem.fr", "admin");

		try {
			usersImpl.grantPermission(user, Permission.ADD_BOOK);
			fail("Only registered user should be allowed");
		} catch (IllegalArgumentException e) {
		}

		usersImpl.register(user, "password");

		assertTrue(usersImpl.grantPermission(user, Permission.ADD_BOOK));
		assertFalse(usersImpl.grantPermission(user, Permission.ADD_BOOK));

		assertTrue(usersImpl.grantPermission(user, Permission.REMOVE_BOOK));
		assertFalse(usersImpl.grantPermission(user, Permission.REMOVE_BOOK));

		try {
			usersImpl.grantPermission(user, null);
			fail("Null permission should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.grantPermission(null, Permission.ADD_BOOK);
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRevokePermission() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user = new UserImpl("administrator", "admin@upem.fr", "admin");

		try {
			usersImpl.revokePermission(user, Permission.ADD_BOOK);
			fail("Only registered user should be allowed");
		} catch (IllegalArgumentException e) {
		}

		usersImpl.register(user, "password");

		assertFalse(usersImpl.revokePermission(user, Permission.ADD_BOOK));

		usersImpl.grantPermission(user, Permission.ADD_BOOK);

		assertTrue(usersImpl.revokePermission(user, Permission.ADD_BOOK));

		try {
			usersImpl.revokePermission(user, null);
			fail("Null permission should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.revokePermission(null, Permission.ADD_BOOK);
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testUserCan() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user1 = new UserImpl("administrator", "admin@upem.fr", "admin");
		UserImpl user2 = new UserImpl("administrator2", "admin2@upem.fr", "admin");

		try {
			usersImpl.userCan(user1, Permission.ADD_BOOK);
			fail("Only registered user should be allowed");
		} catch (IllegalArgumentException e) {
		}

		usersImpl.register(user1, "password");
		usersImpl.register(user2, "password");

		assertFalse(usersImpl.userCan(user1, Permission.ADD_BOOK));

		usersImpl.grantPermission(user1, Permission.ADD_BOOK);

		assertTrue(usersImpl.userCan(user1, Permission.ADD_BOOK));
		assertFalse(usersImpl.userCan(user2, Permission.ADD_BOOK));

		usersImpl.revokePermission(user1, Permission.ADD_BOOK);

		assertFalse(usersImpl.userCan(user1, Permission.ADD_BOOK));
		assertFalse(usersImpl.userCan(user2, Permission.ADD_BOOK));

		try {
			usersImpl.userCan(user1, null);
			fail("Null permission should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.userCan(null, Permission.ADD_BOOK);
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testGetUserPermissions() throws RemoteException {
		Users usersImpl = new Users();
		UserImpl user1 = new UserImpl("administrator", "admin@upem.fr", "admin");
		UserImpl user2 = new UserImpl("administrator2", "admin2@upem.fr", "admin");

		try {
			usersImpl.getUserPermissions(user1);
			fail("Only registered user should be allowed");
		} catch (IllegalArgumentException e) {
		}

		usersImpl.register(user1, "password");
		usersImpl.register(user2, "password");
		assertEquals(0, usersImpl.getUserPermissions(user1).size());

		usersImpl.grantPermission(user1, Permission.ADD_BOOK);
		assertTrue(usersImpl.getUserPermissions(user1).contains(Permission.ADD_BOOK));
		assertEquals(1, usersImpl.getUserPermissions(user1).size());
		assertEquals(0, usersImpl.getUserPermissions(user2).size());

		usersImpl.grantPermission(user1, Permission.REMOVE_BOOK);
		assertTrue(usersImpl.getUserPermissions(user1).contains(Permission.ADD_BOOK));
		assertTrue(usersImpl.getUserPermissions(user1).contains(Permission.REMOVE_BOOK));
		assertEquals(2, usersImpl.getUserPermissions(user1).size());
		assertEquals(0, usersImpl.getUserPermissions(user2).size());

		usersImpl.grantPermission(user2, Permission.REMOVE_BOOK);
		assertTrue(usersImpl.getUserPermissions(user2).contains(Permission.REMOVE_BOOK));
		assertEquals(1, usersImpl.getUserPermissions(user2).size());
		assertEquals(2, usersImpl.getUserPermissions(user1).size());
	}
}
