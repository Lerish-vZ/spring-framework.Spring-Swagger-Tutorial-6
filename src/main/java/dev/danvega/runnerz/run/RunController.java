package dev.danvega.runnerz.run;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Tag(name = "Run APIs", description = "Run management APIs")
@RestController
@RequestMapping("/api/runs")
class RunController {

    private final JdbcRunRepository runRepository;

    RunController(JdbcRunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @Operation(
            summary = "Get all the runs",
            description = "Get all existing runs",
            tags = {"API", "GET"}
    )
    @GetMapping
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @Operation(
            summary = "Get all the runs by a specific id",
            description = "Get all existing runs with specific id",
            tags = {"API", "GET"}
    )
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if(run.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found.");
        }
        return run.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@Valid @RequestBody Run run) {
        runRepository.create(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.update(run,id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runRepository.delete(id);
    }

    List<Run> findByLocation(@RequestParam String location) {
        return runRepository.findByLocation(location);
    }
}
