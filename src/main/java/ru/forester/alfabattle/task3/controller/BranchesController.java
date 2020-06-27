package ru.forester.alfabattle.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.forester.alfabattle.task3.entity.BranchesEntity;
import ru.forester.alfabattle.task3.model.BranchesWithDistance;
import ru.forester.alfabattle.task3.model.Status;
import ru.forester.alfabattle.task3.service.BranchesService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class BranchesController {

    @Autowired
    private BranchesService branchesService;

    @GetMapping("/branches/{id}")
    public ResponseEntity getById(@PathVariable int id) {
        Optional<BranchesEntity> optionalBranchesEntity = branchesService.getById(id);
        return optionalBranchesEntity.isPresent() ?
                new ResponseEntity<>(optionalBranchesEntity.get(), OK) :
                new ResponseEntity<>(new Status("branch not found"), NOT_FOUND);
    }

    @GetMapping("/branches")
    public BranchesWithDistance findNearest(@RequestParam BigDecimal lat, @RequestParam BigDecimal lon) {
        return branchesService.findNearest(lat, lon);
    }

    @GetMapping("/branches/{id}/predict")
    public ResponseEntity predict(@PathVariable int id,
                                  @RequestParam int dayOfWeek,
                                  @RequestParam int hourOfDay) {
        try {
            return new ResponseEntity<>(branchesService.calculatePrediction(id, dayOfWeek, hourOfDay), OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Status("branch not found"), NOT_FOUND);
        }
    }
}
