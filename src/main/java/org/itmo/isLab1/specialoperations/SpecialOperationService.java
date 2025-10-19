package org.itmo.isLab1.specialoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.itmo.isLab1.dragons.*;
import org.itmo.isLab1.dragons.mapper.DragonMapper;
import org.itmo.isLab1.specialoperations.dto.*;

@Service
@RequiredArgsConstructor
public class SpecialOperationService {
  private static final String NO_DRAGONS_FOUND = "No one dragons found";

  private final DragonRepository repository;
  private final DragonMapper mapper;

  public AverageAgeDto getAverageDragonAge() {
    var result = repository.getAverageAge();
    return result
      .map(averageAge -> new AverageAgeDto(null, averageAge))
      .orElseGet(() -> new AverageAgeDto(NO_DRAGONS_FOUND, null));
  }

  public DragonResultDto getOldestDragon() {
    var result = repository.findTopAgeByAgeIsNotNullOrderByAgeDesc();
    return result
      .map(dragon -> new DragonResultDto(null, mapper.map(dragon)))
      .orElseGet(() -> new DragonResultDto(NO_DRAGONS_FOUND, null));
  }

  public DragonResultDto getDragonInDeepestCave() {
    var result = repository.findDragonInDeepestCave();
    return result
      .map(dragon -> new DragonResultDto(null, mapper.map(dragon)))
      .orElseGet(() -> new DragonResultDto(NO_DRAGONS_FOUND, null));
  }
}
