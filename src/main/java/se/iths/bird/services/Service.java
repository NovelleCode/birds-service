package se.iths.bird.services;

import se.iths.bird.dtos.BirdDto;
import se.iths.bird.dtos.BirdWeight;

import java.util.List;
import java.util.Optional;

public interface Service {
    List<BirdDto> getAllBirds();

    Optional<BirdDto> getOne(Integer id);

    BirdDto createBird(BirdDto birdDto);

    void delete(Integer id);

    BirdDto replace(Integer id, BirdDto birdDto);

    BirdDto update(Integer id, BirdWeight birdWeight);

    List<BirdDto> searchByName(String name);

    List<BirdDto> searchByGender(String gender);
}
