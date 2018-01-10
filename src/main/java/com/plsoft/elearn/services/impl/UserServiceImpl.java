/**
 *
 */
package com.plsoft.elearn.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plsoft.elearn.entity.Users;
import com.plsoft.elearn.entity.controller.UsersJpaController;
import com.plsoft.elearn.entity.controller.exceptions.PreexistingEntityException;
import com.plsoft.elearn.entity.controller.exceptions.RollbackFailureException;
import com.plsoft.elearn.services.UserService;

/**
 * @author Ravikanth.Tangeti
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersJpaController uc;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.plsoft.elearn.services.UserServiceInterface#findUserByName(java
	 * .lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public Users findUserByName(String username) {
		Users user = uc.findUserByName(username);
		return user;
	}

	@Transactional(readOnly = true)
	@Override
	public Users getUserById(Integer id) {
		return uc.findUsers(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Users> findAllUsers() {
		return uc.findAllUsers();
	}

	@Transactional(readOnly = true)
	public void createUser(Users user) {
		try {
			uc.create(user);
		} catch (PreexistingEntityException e) {
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	public void updateUser(Users user) {
		try {
			uc.update(user);
		} catch (PreexistingEntityException e) {
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
