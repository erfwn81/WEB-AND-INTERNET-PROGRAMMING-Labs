package cs3220.repository;

import cs3220.model.MessageEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageEntryRepository extends JpaRepository<MessageEntry, Long> {
    List<MessageEntry> findAllByOrderByCreatedAtDesc();
}