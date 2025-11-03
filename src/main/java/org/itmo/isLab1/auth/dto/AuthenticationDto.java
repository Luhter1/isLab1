package org.itmo.isLab1.auth.dto;

import lombok.*;

import org.itmo.isLab1.common.framework.dto.ClientDto;
import org.itmo.isLab1.users.User;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto implements ClientDto{
    private String token;
    private User user;
}