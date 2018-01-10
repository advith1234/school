package com.plsoft.elearn.entity.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.springframework.stereotype.Service;

import com.plsoft.elearn.entity.Users;
import com.plsoft.elearn.entity.controller.exceptions.PreexistingEntityException;
import com.plsoft.elearn.entity.controller.exceptions.RollbackFailureException;

/**
 * Controller class to handle CRUD operations on a JPA entity object as well as
 * some default and custom named and native queries.
 * <p>
 *
 * @author Ravikanth.Tangeti
 */
@Service
public class UsersJpaController {
	private static final Logger logger = Logger
			.getLogger(UsersJpaController.class);

	public UsersJpaController() {
	}

	@PersistenceContext
	private EntityManager em;

	private EntityManager getEntityManager() {
		return em;
	}

	/**
	 * Returns list of users based on the staff flag
	 * <p>
	 * The cache is not checked.
	 * <p>
	 *
	 * @param flag
	 *            T for staff users, F for employer users
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Users> findStaffUsers(String flag) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createNamedQuery("Users.findByStaffFlag");
			q.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
			q.setHint(QueryHints.REFRESH, HintValues.TRUE);

			q.setParameter("staffFlag", flag);

			return q.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Users> findUsersEntities() {
		return findUsersEntities(true, -1, -1);
	}

	public List<Users> findUsersEntities(int maxResults, int firstResult) {
		return findUsersEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Users> findUsersEntities(boolean all, int maxResults,
			int firstResult) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select object(o) from Users as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public Users findUsers(Integer id) {
		return findUsers(id, false);
	}

	public void create(Users user) throws PreexistingEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.persist(user);
		} catch (Exception ex) {
			try {
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			if (findUsers(user.getUserseqnum()) != null) {
				throw new PreexistingEntityException("User " + user
						+ " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void update(Users user) throws PreexistingEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			Users userPersist = em.find(Users.class, user.getUserseqnum());
			if (userPersist != null)
				em.merge(user);
		} catch (Exception ex) {
			try {
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Find a single Users object based on the key id
	 *
	 * @param id
	 * @return
	 */
	public Users findUsers(Integer id, boolean useCache) {
		EntityManager em = getEntityManager();
		Users user = null;

		try {
			Query q = em.createNamedQuery("Users.findByUserseqnum");
			q.setParameter("userseqnum", id);
			q.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
			q.setHint(QueryHints.REFRESH, HintValues.TRUE);

			user = (Users) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;
	}

	/**
	 *
	 * @param employerId
	 * @return
	 */
	public List<Users> findUsersByEmployer(Integer employerId) {

		return null;
	}

	public Users findUserByName(String id) {
		return findUserByName(id, false);
	}

	/**
	 * Find a single Users object based on the key id
	 *
	 * @param id
	 * @return
	 */
	public Users findUserByName(String id, boolean useCache) {
		EntityManager em = getEntityManager();
		Users user = null;

		try {
			Query q = em.createNamedQuery("Users.findByUsername");
			q.setParameter("username", id);
			q.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
			q.setHint(QueryHints.REFRESH, HintValues.TRUE);

			user = (Users) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public List<Users> findAllUsers() {
		EntityManager em = getEntityManager();

		Query q = em.createNamedQuery("Users.findAllUsers");
		return q.getResultList();
	}

	public Integer getNextUserId() {
		EntityManager em = getEntityManager();
		try {
			Query q = em
					.createNativeQuery("select users_id_seq.nextval from dual");
			return ((BigDecimal) q.getSingleResult()).intValue();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 *
	 * @return
	 */
	public int getUsersCount() {
		EntityManager em = getEntityManager();
		try {
			return ((Long) em.createQuery("select count(o) from Users as o")
					.getSingleResult()).intValue();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
