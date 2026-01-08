package com.goldenleaf.shop.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.UserRepository;

/**
 * Service class for managing {@link User} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, deleting, and registering users.
 * Supports operations by user ID, name, or login. Handles password encoding via {@link PasswordEncoder}.
 * Acts as an intermediary between controllers and the {@link UserRepository}.
 * </p>
 *
 * @see User
 * @see UserRepository
 * @see PasswordEncoder
 */
@Service
public class UserService {

    /**
     * Repository used for performing CRUD operations on {@link User} entities.
     */
    private final UserRepository userRepository;

    /**
     * Encoder used to hash user passwords before saving to the database.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new {@code UserService} with the provided repository and password encoder.
     *
     * @param repo the repository used to perform operations on users
     * @param encoder the password encoder used to hash passwords
     * @throws IllegalArgumentException if {@code repo} or {@code encoder} is {@code null}
     *
     * @see UserRepository
     * @see PasswordEncoder
     */
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        if (repo == null || encoder == null) {
            throw new IllegalArgumentException("UserRepository and PasswordEncoder cannot be null");
        }
        this.userRepository = repo;
        this.passwordEncoder = encoder;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a {@link List} of all {@link User} entities
     *
     * @see UserRepository#findAll()
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a {@link User} by their unique ID.
     *
     * @param id the ID of the user
     * @return the {@link User} with the specified ID
     * @throws RuntimeException if no user with the given ID exists
     *
     * @see UserRepository#findById(Object)
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    /**
     * Retrieves a {@link User} by their name.
     *
     * @param name the name of the user
     * @return the {@link User} with the specified name
     * @throws RuntimeException if no user with the given name exists
     *
     * @see UserRepository#findByName(String)
     */
    public User getUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found by name: " + name));
    }

    /**
     * Retrieves a {@link User} by their login.
     *
     * @param login the login of the user
     * @return the {@link User} with the specified login
     * @throws RuntimeException if no user with the given login exists
     *
     * @see UserRepository#findByLogin(String)
     */
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found by login: " + login));
    }

    /**
     * Adds a new {@link User} to the database.
     *
     * @param user the user to add
     *
     * @see UserRepository#save(Object)
     */
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * Removes an existing {@link User} from the database.
     *
     * <p>If the user exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param user the user to remove
     *
     * @see UserRepository#delete(Object)
     * @see UserRepository#existsById(Object)
     */
    public void removeUser(User user) {
        if (user != null && userRepository.existsById(user.getId())) {
            userRepository.delete(user);
        }
    }

    /**
     * Removes a {@link User} by their ID.
     *
     * @param id the ID of the user to delete
     *
     * @see UserRepository#deleteById(Object)
     */
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Removes a {@link User} by their login.
     *
     * @param login the login of the user to delete
     * @throws RuntimeException if no user with the given login exists
     *
     * @see UserRepository#findByLogin(String)
     * @see UserRepository#delete(Object)
     */
    public void removeUserByLogin(String login) {
        userRepository.findByLogin(login)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> { throw new RuntimeException("User not found with login: " + login); }
                );
    }

    /**
     * Updates an existing {@link User}.
     *
     * <p>The user must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param user the user to update
     * @throws RuntimeException if the user does not exist or ID is null
     *
     * @see UserRepository#save(Object)
     * @see UserRepository#existsById(Object)
     */
    public void editUser(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }
        userRepository.save(user);
    }

    /**
     * Registers a new {@link User} with the given password.
     *
     * <p>The password is hashed using {@link PasswordEncoder} before saving to the database.</p>
     *
     * @param user the user to register
     * @param password the plain-text password to encode and save
     *
     * @see UserRepository#save(Object)
     * @see PasswordEncoder#encode(CharSequence)
     */
    public void register(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

	
}
