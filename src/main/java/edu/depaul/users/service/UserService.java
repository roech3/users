package edu.depaul.users.service;

import edu.depaul.users.domain.User;
import edu.depaul.users.model.UserDTO;
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

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setUserName(user.getUserName());
        userDTO.setPasswordHash(user.getPasswordHash());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFullName(userDTO.getFullName());
        user.setUserName(userDTO.getUserName());
        user.setPasswordHash(userDTO.getPasswordHash());
        return user;
    }

    public boolean fullNameExists(final String fullName) {
        return userRepository.existsByFullNameIgnoreCase(fullName);
    }

    public boolean userNameExists(final String userName) {
        return userRepository.existsByUserNameIgnoreCase(userName);
    }

    public boolean passwordHashExists(final String passwordHash) {
        return userRepository.existsByPasswordHashIgnoreCase(passwordHash);
    }

}
