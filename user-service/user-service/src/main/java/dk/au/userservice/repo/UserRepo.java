package dk.au.userservice.repo;

import dk.au.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    // Spring Data JPA will implement the basic CRUD operations
}