package edu.depaul.users.service;

import edu.depaul.users.model.User;
import edu.depaul.users.repos.UserRepository;
import edu.depaul.users.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll(Sort.by("id"));
    }

    public User get(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final User user) {
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final User user) {
        userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }


    public boolean fullNameExists(final String fullName) {
        return userRepository.existsByFullNameIgnoreCase(fullName);
    }

    public boolean userNameExists(final String userName) {
        return userRepository.existsByUserNameIgnoreCase(userName);
    }

    public boolean passwordHashExists(final String passwordHash) { return userRepository.existsByPasswordHashIgnoreCase(passwordHash);


    }

}
