package cs3220.repository;

import cs3220.model.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserEntryRepository extends JpaRepository<UserEntry, Long> {
    Optional<UserEntry> findByEmail(String email);
    boolean existsByEmail(String email);
}