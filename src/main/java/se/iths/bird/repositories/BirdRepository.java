package se.iths.bird.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.bird.entities.Bird;

import java.util.List;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Integer> {
    List<Bird> findAllByNameContains(String name);
    List<Bird> findAllByGender(String gender);
}
