package edu.depaul.users.repos;

import edu.depaul.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByFullNameIgnoreCase(String fullName);

    boolean existsByUserNameIgnoreCase(String userName);

    boolean existsByPasswordHashIgnoreCase(String passwordHash);

}
