package com.zhopy.authjwtservice.services;

import com.zhopy.authjwtservice.dto.JwtResponse;
import com.zhopy.authjwtservice.dto.UserDTO;
import com.zhopy.authjwtservice.entity.User;
import com.zhopy.authjwtservice.security.JwtIO;
import com.zhopy.authjwtservice.services.interfaces.IUserService;
import com.zhopy.authjwtservice.utils.DateUtils;
import com.zhopy.authjwtservice.utils.helpers.MapperHelper;
import com.zhopy.authjwtservice.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtIO jwtIO;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    UserValidator userValidator;

    @Autowired
    IUserService userService;

    @Value("${jms.jwt.token.expires-in}")
    private int EXPIRES_IN;

    public JwtResponse login(String email, String password) {
        JwtResponse jwtResponse = null;
        if (userValidator.validatorCredentials(email, password)) {
            User user = userService.getByEmail(email);
            UserDTO userDTO = MapperHelper.modelMapper().map(user, UserDTO.class);
            jwtResponse = JwtResponse.builder()
                    .tokenType("bearer")
                    .accessToken(jwtIO.generateToken(userDTO))
                    .issueAt(dateUtils.getDateMillis() + "")
                    .clientId(email)
                    .expiresIn(EXPIRES_IN)
                    .build();
        }
        return jwtResponse;
    }

}
