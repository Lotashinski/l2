package by.grsu.les2.repository;

import by.grsu.les2.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmailIgnoreCase(@NonNull String email);

    boolean existsByEmailIgnoreCase(@NonNull String email);
}
