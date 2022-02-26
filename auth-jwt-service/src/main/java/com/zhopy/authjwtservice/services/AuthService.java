package com.zhopy.authjwtservice.services;

import com.zhopy.authjwtservice.dto.JwtResponse;
import com.zhopy.authjwtservice.feignclients.UserFeignClient;
import com.zhopy.authjwtservice.model.User;
import com.zhopy.authjwtservice.model.UserValidate;
import com.zhopy.authjwtservice.security.JwtIO;
import com.zhopy.authjwtservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    private JwtIO jwtIO;
    @Autowired
    private DateUtils dateUtils;
    @Value("${jms.jwt.token.expires-in}")
    private int EXPIRES_IN;

    public JwtResponse login(String email, String password) {
        JwtResponse jwtResponse = null;
        UserValidate userValidate = new UserValidate(email, password);
        User user = userFeignClient.findByEmail(userValidate);
        jwtResponse = JwtResponse.builder()
                .tokenType("bearer")
                .accessToken(jwtIO.generateToken(user))
                .issueAt(dateUtils.getDateMillis() + "")
                .clientId(email)
                .expiresIn(EXPIRES_IN)
                .build();

        return jwtResponse;
    }

}
