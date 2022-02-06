package com.zhopy.authjwtservice.controller;

import com.zhopy.authjwtservice.dto.TokenDTO;
import com.zhopy.authjwtservice.exceptions.ApiUnauthorized;
import com.zhopy.authjwtservice.security.JwtIO;
import com.zhopy.authjwtservice.services.AuthService;
import com.zhopy.authjwtservice.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/oauth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthValidator validator;

    @Autowired
    private JwtIO jwtIO;

    @PostMapping(path = "/client_credential/accesstoken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestBody MultiValueMap<String, String> paramMap, @RequestParam("grant_type") String grantType) throws ApiUnauthorized {
        validator.validate(paramMap, grantType);
        return ResponseEntity.ok(authService.login(paramMap.getFirst("email"), paramMap.getFirst("password")));
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<Object> validate(@RequestParam String token) throws ApiUnauthorized {
        boolean validate = !jwtIO.validateToke(token);

        if (!validate){
            return ResponseEntity.badRequest().build();
        }

        TokenDTO tokenDTO = new TokenDTO();

        tokenDTO.setToken(token);

        return ResponseEntity.ok(tokenDTO);
    }


}
