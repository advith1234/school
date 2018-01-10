package com.plsoft.elearn.services;

import java.util.List;

import com.plsoft.elearn.entity.Users;

public interface UserService {

  public Users findUserByName( String username);

  public Users getUserById( Integer id);

  public List<Users> findAllUsers();

  public void createUser(Users user);

  public void updateUser(Users user);

}
