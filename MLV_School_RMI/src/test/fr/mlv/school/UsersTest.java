package test.fr.mlv.school;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import org.junit.Test;

import fr.mlv.school.UserImpl;
import fr.mlv.school.UsersImpl;

public class UsersTest {
	@Test
	public void testFindByUsername() throws RemoteException {
		UsersImpl usersImpl = new UsersImpl();
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
		UsersImpl usersImpl = new UsersImpl();
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
		UsersImpl usersImpl = new UsersImpl();
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
		UsersImpl usersImpl = new UsersImpl();
		UserImpl user1 = new UserImpl("administrator", "admin@upem.fr", "admin");
		UserImpl user2 = new UserImpl("administrator", "admin@upem.fr", "admin");

		assertFalse(usersImpl.isRegistered(user1));
		assertTrue(usersImpl.register(user1, "password"));
		assertTrue(usersImpl.isRegistered(user1));

		assertFalse(usersImpl.isRegistered(user2));

		try {
			usersImpl.isRegistered(null);
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRegister() throws RemoteException {
		UsersImpl usersImpl = new UsersImpl();
		UserImpl user = new UserImpl("administrator", "admin@upem.fr", "admin");
		assertTrue(usersImpl.register(user, "password"));

		try {
			usersImpl.register(null, "password");
			fail("Null user should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.register(user, null);
			fail("Null password should not be allowed");
		} catch (IllegalArgumentException e) {
		}

		try {
			usersImpl.register(user, "");
			fail("Empty password should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}
}
