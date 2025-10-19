package org.itmo.isLab1.specialoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.itmo.isLab1.dragons.*;
import org.itmo.isLab1.dragons.mapper.DragonMapper;
import org.itmo.isLab1.specialoperations.dto.*;

@Service
@RequiredArgsConstructor
public class SpecialOperationService {
  private final DragonRepository repository;
  private final DragonMapper mapper;

  public AverageAgeDto getAverageDragonAge() {
    var result = repository.getAverageAge();
    return result
      .map(averageAge -> new AverageAgeDto(null, averageAge))
      .orElseGet(() -> new AverageAgeDto("No one dragons with filled age found", null));
  }

  public DragonResultDto getDragonInDeepestCave() {
    var result = repository.findDragonInDeepestCave();
    return result
      .map(dragon -> new DragonResultDto(null, mapper.map(dragon)))
      .orElseGet(() -> new DragonResultDto("No one dragons with cave found", null));
  }
}
