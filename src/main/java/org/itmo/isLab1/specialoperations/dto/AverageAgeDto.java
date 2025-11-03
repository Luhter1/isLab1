package org.itmo.isLab1.specialoperations.dto;

import org.itmo.isLab1.common.framework.dto.ClientDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageAgeDto implements ClientDto{
  private String errorMessage;
  private Double averageAge;
}
