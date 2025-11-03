package org.itmo.isLab1.adminrequests.dto;

import lombok.Data;
import org.itmo.isLab1.adminrequests.Status;
import org.itmo.isLab1.common.framework.dto.ClientDto;
import org.itmo.isLab1.users.User;

import java.time.ZonedDateTime;

@Data
public class AdminRequestDto implements ClientDto{
    private int id;
    private User user;
    private Status status;
    private User approvedBy;
    private ZonedDateTime approvalDate;
    private ZonedDateTime createdAt;
}
