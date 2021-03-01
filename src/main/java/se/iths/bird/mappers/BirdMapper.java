package se.iths.bird.mappers;

import org.springframework.stereotype.Component;
import se.iths.bird.dtos.BirdDto;
import se.iths.bird.entities.Bird;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BirdMapper {
    
    public BirdMapper() {
    }

    public BirdDto mapp(Bird bird) {
        return new BirdDto(bird.getId(), bird.getName(), bird.getType(), bird.getWeight(), bird.getGender());
    }

    public Bird mapp(BirdDto birdDto) {
        return new Bird(birdDto.getId(), birdDto.getName(), birdDto.getType(), birdDto.getWeight(), birdDto.getGender());
    }

    public Optional<BirdDto> mapp(Optional<Bird> optionalBird) {
        if (optionalBird.isEmpty())
            return Optional.empty();
        return Optional.of(mapp(optionalBird.get()));
    }

    public List<BirdDto> mapp(List<Bird> birdList) {
        return birdList
                .stream()
                .map(this::mapp)
                .collect(Collectors.toList());

//        List<BirdDto> birdDtoList = new ArrayList<>();
//        for(var bird : birdList) {
//            birdDtoList.add(mapp(bird));
//        }
//        return birdDtoList;
    }
    
    
}
