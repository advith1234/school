package com.plsoft.elearn.util;

import org.apache.log4j.Logger;

import com.plsoft.elearn.entity.controller.JpaTransactionManager;

/**
 * Utility class for persistence-related functionality
 *
 * @author Ravikanth.Tangeti
 */
public class PersistenceUtil {

	public static final Logger log = Logger.getLogger(PersistenceUtil.class);

	public static void rollbackTransaction(JpaTransactionManager transactionManager) {

		try {
			transactionManager.rollbackTransaction();
		} catch( Exception re) {
			log.error("Failed to rollback transaction", re);
		}

	}
}
