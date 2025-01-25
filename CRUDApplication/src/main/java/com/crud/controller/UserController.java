package com.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.dto.UserDto;
import com.crud.entity.User;
import com.crud.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto dto, BindingResult result) {

		if (result.hasErrors()) {

			Map<String, String> errors = new HashMap<String, String>();

			for (FieldError error : result.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(dto.getName());
		user.setAddress(dto.getAddress());
		user.setAge(dto.getAge());
		user.setEmail(dto.getEmail());

		userService.saveUser(user);

		return new ResponseEntity<>("user is valid & saved Successfully...", HttpStatus.OK);
	}

	// All data
	@GetMapping()
	public List<User> getAll() {

		return userService.getAllUsers();
	}

	// Get one user

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Integer id) {

		return userService.getById(id);
	}

	// delete one user
	@DeleteMapping("/{id}")
	public String deleteUserById(@PathVariable Integer id) {

		userService.deleteUser(id);
		String str = "deleted successfully";
		return str;
	}

	@PutMapping("/{id}")
	public User updateUserById(@PathVariable Integer id, @RequestBody User user) {

		return userService.updateUser(id, user);
	}

}
