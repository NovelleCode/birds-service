package se.iths.bird.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.bird.dtos.BirdDto;
import se.iths.bird.dtos.BirdWeight;
import se.iths.bird.entities.Bird;
import se.iths.bird.services.Service;

import java.util.List;

@RestController
public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/birds")
    public List<BirdDto> all() {
        return service.getAllBirds();
    }

    @GetMapping("/birds/{id}")
    public BirdDto one(@PathVariable Integer id) {
        return service.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "ISBN "+ id +" not found."));
    }

    @GetMapping(value = "/birds/search", params = "name")
    public List<BirdDto> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    @GetMapping(value = "/birds/search", params = "gender")
    public List<BirdDto> searchByGender(@RequestParam String gender) {
        return service.searchByGender(gender);
    }

    @PostMapping("/birds")
    @ResponseStatus(HttpStatus.CREATED)
    public BirdDto create(@RequestBody BirdDto birdDto) {
        return service.createBird(birdDto);
    }

    // This method should only be accessed by users with the role of 'admin'
    @DeleteMapping("/birds/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // This method should only be accessed by users with the role of 'admin'
    @PutMapping("birds/{id}")
    public BirdDto replace(@RequestBody BirdDto birdDto, @PathVariable Integer id) {
        return service.replace(id, birdDto);
    }

    // This method should only be accessed by users with the role of 'admin'
    @PatchMapping("/birds/{id}")
    public BirdDto update(@RequestBody BirdWeight birdWeight, @PathVariable Integer id) {
        return service.update(id, birdWeight);
    }
}
