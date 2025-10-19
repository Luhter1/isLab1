package org.itmo.isLab1.specialoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.itmo.isLab1.specialoperations.dto.*;


@RestController
@RequestMapping(value = "/api/special-operations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SpecialOperationController {

  private final SpecialOperationService service;

  @GetMapping("/average-age")
  public ResponseEntity<AverageAgeDto> getAverageDragonAge() {
    var averageAge = service.getAverageDragonAge();
    return ResponseEntity.ok(averageAge);
  }

  @GetMapping("/deepest-cave-dragon")
  public ResponseEntity<DragonResultDto> getDragonInDeepestCave() {
    var dragon = service.getDragonInDeepestCave();
    return ResponseEntity.ok(dragon);
  }
}