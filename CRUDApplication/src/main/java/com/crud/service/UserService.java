package com.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.Exception.UserNotFoundException;
import com.crud.entity.User;
import com.crud.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public User saveUser(User user) {
		return repository.save(user);
	}

	// all data
	public List<User> getAllUsers() {

		return repository.findAll();
	}

	// get one user
	public User getById(Integer id) {

		return repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with this id:" + id));
	}

	// delete one user
	public void deleteUser(Integer id) {

		repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with this id:" + id));
		repository.deleteById(id);

	}

	// update user

	public User updateUser(Integer id, User user) {

		User existingUser = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with this id:" + id));

		existingUser.setName(user.getName());
		existingUser.setAddress(user.getAddress());
		existingUser.setEmail(user.getEmail());
		existingUser.setAge(user.getAge());

		return repository.save(existingUser);
	}

}
