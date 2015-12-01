package test.fr.mlv.school;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import fr.mlv.school.UserImpl;

public class UserTest {
	@Test
	public void testUser() throws RemoteException {
		new UserImpl("holyhope", "email.test@domain.com", "admin");
	}

	@Test
	public void testGetEmail() throws RemoteException {
		UserImpl userImpl = new UserImpl("administrator", "email.test@domain.com", "admin");
		assertEquals("email.test@domain.com", userImpl.getEmail());
		try {
			userImpl = new UserImpl("administrator", "email de test", "admin");
			fail("« email de test » is not a valid email");
		} catch (IllegalArgumentException e) {
		}
		try {
			userImpl = new UserImpl("administrator", null, "admin");
			fail("Null email should not be allowed");
		} catch (IllegalArgumentException e) {
		}
		try {
			userImpl = new UserImpl("administrator", "", "admin");
			fail("Empty email should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testGetUsername() throws RemoteException {
		UserImpl userImpl = new UserImpl("administrator", "email.test@domain.com", "admin");
		assertEquals("administrator", userImpl.getUsername());
		try {
			userImpl = new UserImpl(null, "email.test@domain.com", "admin");
			fail("Null username should not be allowed");
		} catch (IllegalArgumentException e) {
		}
		try {
			userImpl = new UserImpl("", "email.test@domain.com", "admin");
			fail("Empty username should not be allowed");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testGetRole() throws RemoteException {
		fail("Not yet implemented");
	}
}
