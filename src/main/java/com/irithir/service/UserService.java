package com.irithir.service;

import com.irithir.dto.RegistrationDto;
import com.irithir.model.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
