package com.goldenleaf.shop.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.UserRepository;

@Service
public class UserService {
	
private final UserRepository userRepository;

public UserService(UserRepository repo)
{
	this.userRepository = repo;
}

public List<User> getAllUsers()
{
	return userRepository.findAll();
}

public User getUserById(Long id)
{
	 return userRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
}


public User getUserByName(String name)
{
	return userRepository.findByName(name)
			.orElseThrow(() -> new RuntimeException("User not found by name: " + name));
}

public User getUserByLogin(String login)
{
	return userRepository.findByLogin(login)
			.orElseThrow(() -> new RuntimeException("User not found by login: " + login));
}

public void addUser(User user)
{
	userRepository.save(user);
}


public void removeUser(User user)
{
    if (user != null && userRepository.existsById(user.getId())) {
        userRepository.delete(user);
    }
}

public void removeUserById(Long id)
{

	userRepository.deleteById(id);
}

public void removeUserByLogin(String login)
{

	 userRepository.findByLogin(login)
     .ifPresentOrElse(
         userRepository::delete,
         () -> { throw new RuntimeException("User not found with login: " + login); }
     );
}


public void editUser(User user)
{
   if (user.getId() == null || !userRepository.existsById(user.getId())) {
        throw new RuntimeException("User not found");
    }
    userRepository.save(user); 
}

}
