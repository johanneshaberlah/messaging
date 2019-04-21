package net.plaria.messaging.service.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

  Optional<Message> findById(Long aLong);

  Optional<Message> findByKey(String key);
}
