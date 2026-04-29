package cs3220.repository;

import cs3220.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    // Used on login: match both email and password
    UserEntity findByEmailAndPassword(String email, String password);

    // Used on registration: check if email already exists
    UserEntity findByEmail(String email);
}
