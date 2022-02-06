package com.zhopy.authjwtservice.validator;

import com.zhopy.authjwtservice.services.interfaces.IUserService;
import com.zhopy.authjwtservice.entity.User;
import com.zhopy.authjwtservice.utils.hash.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImplement implements UserValidator {

    @Autowired
    IUserService userService;

    @Override
    public boolean validatorCredentials(String email, String password) {
        boolean auth = false;

        if (userService.existsByEmail(email)){
            User user = userService.getByEmail(email);

            if (BCrypt.checkpw(password, user.getPassword())){
                auth = true;
            }
        }

        return auth;
    }

}
