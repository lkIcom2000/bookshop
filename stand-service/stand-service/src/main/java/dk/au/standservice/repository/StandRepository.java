package dk.au.standservice.repository;

import dk.au.standservice.model.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandRepository extends JpaRepository<Stand, Long> {
} 