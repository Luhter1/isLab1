package org.itmo.isLab1.specialoperations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.itmo.isLab1.common.framework.dto.ClientDto;
import org.itmo.isLab1.dragons.dto.DragonDto;

@Data
@AllArgsConstructor
public class DragonResultDto implements ClientDto{
  private String errorMessage;
  private DragonDto dragon;
}
