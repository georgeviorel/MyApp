package ro.myapp.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.myapp.myapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}