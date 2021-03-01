package se.iths.bird.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.bird.dtos.BirdDto;
import se.iths.bird.dtos.BirdWeight;
import se.iths.bird.entities.Bird;
import se.iths.bird.mappers.BirdMapper;
import se.iths.bird.repositories.BirdRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BirdService implements se.iths.bird.services.Service {

    private final BirdMapper birdMapper;
    private final BirdRepository birdRepository;

    public BirdService(BirdMapper birdMapper, BirdRepository birdRepository) {
        this.birdMapper = birdMapper;
        this.birdRepository = birdRepository;
    }

    @Override
    public List<BirdDto> getAllBirds() {
        return birdMapper.mapp(birdRepository.findAll());
    }

    @Override
    public Optional<BirdDto> getOne(Integer id) {
        return birdMapper.mapp(birdRepository.findById(id));
    }

    // TODO: 2021-02-25
    // Add validations
    @Override
    public BirdDto createBird(BirdDto birdDto) {
        if(birdDto.getName().isEmpty() || birdDto.getGender().isEmpty() || birdDto.getType().isEmpty())
            throw new RuntimeException();
        return birdMapper.mapp(birdRepository.save(birdMapper.mapp(birdDto)));
    }
    
    @Override
    public void delete(Integer id) {
        birdRepository.deleteById(id);
    }
    
    @Override
    public BirdDto replace(Integer id, BirdDto birdDto) {
        Optional<Bird> bird = birdRepository.findById(id);
        if(bird.isPresent()) {
            Bird updatedBird = bird.get();
            updatedBird.setName(birdDto.getName());
            updatedBird.setType(birdDto.getType());
            updatedBird.setWeight(birdDto.getWeight());
            updatedBird.setGender(birdDto.getGender());
            return birdMapper.mapp(birdRepository.save(updatedBird));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id "+ id +" not found.");
        }
    }

    @Override
    public BirdDto update(Integer id, BirdWeight birdWeight) {
        Optional<Bird> bird = birdRepository.findById(id);
        if(bird.isPresent()) {
            Bird updatedBird = bird.get();
            if(birdWeight.weight > 0.0)
                updatedBird.setWeight(birdWeight.weight);
            return birdMapper.mapp(birdRepository.save(updatedBird));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found.");
        }
    }

    @Override
    public List<BirdDto> search(String name) {
        return birdMapper.mapp(birdRepository.findAllByNameContains(name));
    }
}
