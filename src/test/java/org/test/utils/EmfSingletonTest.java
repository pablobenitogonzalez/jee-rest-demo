package org.test.utils;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class EmfSingletonTest {
	@Test
	public void obtenerEntityManagerTest() {
		final EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		Assert.assertTrue(em.isOpen());
		em.getTransaction().commit();
		em.close();
		Assert.assertFalse(em.isOpen());
	}
}
