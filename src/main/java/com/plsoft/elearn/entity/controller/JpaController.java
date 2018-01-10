package com.plsoft.elearn.entity.controller;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

import net.sf.ehcache.CacheManager;

/**
 * Base class for JPA controller classes.  Handles the entity manager and an optional transaction manager.<p>
 * @author Ravikanth.Tangeti
 *
 */
public class JpaController {
  @Resource
  private UserTransaction utx = null;
  @PersistenceUnit(unitName = "schooladmin")
  protected EntityManagerFactory emf = null;

  private static CacheManager cacheManager = CacheManager.create();

  private static final long CACHE_TIMEOUT_SECS = 500000;
  private static final int CACHE_ITEMS = 1000;

  private JpaTransactionManager transactionManager;

  public JpaController() {}
  /**
   *
   * @param mgr
   */
  public JpaController( JpaTransactionManager mgr) {
    transactionManager = mgr;
  }

  public boolean hasTransactionManager() {
    return transactionManager != null;
  }

  protected EntityManager getEntityManager() {
    if( hasTransactionManager()) {
      return transactionManager.getEntityManager();
    }
    else {
      if (emf == null) {
        emf = Persistence.createEntityManagerFactory("elearn");
      }
      return emf.createEntityManager();
    }
  }

  protected void beginTransaction() {
    if( hasTransactionManager()) {

    }
    else {
      try {
        utx.begin();
      }
      catch( Exception e) {
        e.printStackTrace();
      }
    }
  }

  protected UserTransaction getTransaction() {
    try {
      if( hasTransactionManager()) {
        return transactionManager.getTransaction();
      }
      else {
        if( utx != null)
          utx = null;
        utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        utx.setTransactionTimeout( 600);
      }
    }
    catch (NamingException e) {
      e.printStackTrace();
    }
    catch( Exception e) {
     e.printStackTrace();
    }
    return utx;
  }

  protected void commitTransaction() throws Exception {
    if( hasTransactionManager())
      return;

    assert( utx != null);
    if( utx != null)
      utx.commit();
  }

  protected void rollbackTransaction() throws Exception {
    if( hasTransactionManager())
      return;

    assert( utx != null);
    if( utx != null)
      utx.rollback();
  }

}
