package org.itmo.isLab1.users;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.itmo.isLab1.common.errors.UserWithThisUsernameAlreadyExists;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;

    public User save(User user) {
    return repository.save(user);
    }

    @Transactional
    public User create(User user) {
    if (repository.existsByUsername(user.getUsername())) {
        throw new UserWithThisUsernameAlreadyExists("Пользователь с таким именем уже существует");
    }

    if (repository.count() == 0) {
        logger.info("Creating first user with ADMIN role");
        user.setRole(Role.ADMIN);
    } else {
        user.setRole(Role.USER);
    }
    return save(user);
    }
}
