package cs3220.repository;

import cs3220.model.GuestBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestBookEntityRepository extends JpaRepository<GuestBookEntity, Long> {

    // All messages ordered by date descending (newest first)
    List<GuestBookEntity> findAllByOrderByDateDesc();
}
