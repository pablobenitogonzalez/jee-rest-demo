package org.test.persistence.jpa;

import org.test.model.Login;
import org.test.persistence.interfaces.LoginDao;
import org.test.utils.ApplicationUtils;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.test.utils.ApplicationStrings.*;

@Jpa
public class LoginJpaDao extends JpaDao<Login, Long> implements LoginDao {

	@SuppressWarnings("unused")
	public LoginJpaDao() {}

	public LoginJpaDao(EntityManager em) {
		super(em);
	}

	@Override
	public Login find(Long id) {
		EntityManager em = this.getEntityManager();
		return em.find(Login.class, id);
	}

	@Override
	public Boolean existsNaturalKey(String email) {
		EntityManager em = this.getEntityManager();
		return (em.createNamedQuery(LOGIN_FIND_FILTER_BY_EMAIL)
				.setParameter(EMAIL, email))
				.getResultList().size() != 0;
	}

	@Override
	public Login findByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException {
		EntityManager em = this.getEntityManager();
		List result = em.createNamedQuery(LOGIN_FIND_BY_EMAIL_AND_PASSWORD)
				.setParameter(EMAIL, email)
				.setParameter(PASSWORD, ApplicationUtils.getPasswordToStore(password))
				.getResultList();
		return (result.size() > 0)? (Login) result.get(0) : null;
	}
}
