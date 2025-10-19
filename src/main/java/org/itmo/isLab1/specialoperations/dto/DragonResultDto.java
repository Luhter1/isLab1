package org.itmo.isLab1.specialoperations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.itmo.isLab1.dragons.dto.DragonDto;

@Data
@AllArgsConstructor
public class DragonResultDto {
  private String errorMessage;
  private DragonDto dragon;
}
