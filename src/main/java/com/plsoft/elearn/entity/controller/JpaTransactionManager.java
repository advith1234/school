package com.plsoft.elearn.entity.controller;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import com.plsoft.elearn.entity.controller.exceptions.RollbackFailureException;

/**
 * Transaction manager that can be assigned to a controller for transaction management when multiple object edits need to be part of a single transaction.<p>
 * @author Ravikanth.Tangeti
 *
 */
public class JpaTransactionManager extends JpaController {
  private static final Logger logger = Logger.getLogger( JpaTransactionManager.class);

  private EntityManager em = null;

  public void flushEntityManager() {
    if( em != null && em.isOpen()) {
      em.flush();
    }
  }

  @Override
  public void beginTransaction() {
    super.beginTransaction();
  }

  /* (non-Javadoc)
   * @see com.plsoft.indiana.elearn.entity.controller.JpaController#commitTransaction()
   */
  @Override
  public void commitTransaction() throws Exception {
    try {
      super.commitTransaction();
    }
    catch( Exception ex) {
      try {
        rollbackTransaction();
      }
      catch( Exception re) {
        throw new RollbackFailureException( "An error occurred attempting to roll back the transaction.", re);
      }

      throw ex;
    }
    finally {
      if( em != null) {
        em.close();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.plsoft.indiana.elearn.entity.controller.JpaController#rollbackTransaction()
   */
  @Override
  public void rollbackTransaction() throws Exception {
    super.rollbackTransaction();
  }

  @Override
  protected EntityManager getEntityManager() {
    if (emf == null) {
      emf = Persistence.createEntityManagerFactory("schooladmin");
    }

    if( em == null)
      em = emf.createEntityManager();

    if( !em.isOpen()) {
      em = emf.createEntityManager();
    }

    return em;
  }

  @Override
  public UserTransaction getTransaction() {
    return super.getTransaction();
  }
}
