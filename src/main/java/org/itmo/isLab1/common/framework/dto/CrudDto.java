package org.itmo.isLab1.common.framework.dto;

import lombok.Getter;
import lombok.Setter;
import org.itmo.isLab1.users.User;

import java.time.ZonedDateTime;

@Getter
@Setter
public abstract class CrudDto {
  private User createdBy;
  private ZonedDateTime createdAt;
  private User updatedBy;
  private ZonedDateTime updatedAt;
}