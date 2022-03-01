package com.zhopy.authjwtservice.controller;

import com.zhopy.authjwtservice.dto.TokenDTO;
import com.zhopy.authjwtservice.exceptions.ApiUnauthorized;
import com.zhopy.authjwtservice.security.JwtIO;
import com.zhopy.authjwtservice.services.AuthService;
import com.zhopy.authjwtservice.validator.AuthValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackLogin")
    @PostMapping(path = "/client_credential/accesstoken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestBody MultiValueMap<String, String> paramMap, @RequestParam("grant_type") String grantType) throws ApiUnauthorized {
        validator.validate(paramMap, grantType);
        return ResponseEntity.ok(authService.login(paramMap.getFirst("email"), paramMap.getFirst("password")));
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<Object> validate(@RequestParam String token) {
        boolean validate = !jwtIO.validateToke(token);

        TokenDTO tokenDTO = new TokenDTO();

        if (!validate) {
            tokenDTO.setToken(token);
        } else {
            tokenDTO.setToken("");
        }
        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping(path = "/getPayload")
    public ResponseEntity<Object> getPayload(@RequestParam String token) {
        boolean validate = !jwtIO.validateToke(token);

        if (!validate) {
            return ResponseEntity.badRequest().build();
        }

        String payload = jwtIO.getPayload(token);

        return ResponseEntity.ok(payload);
    }

    private ResponseEntity<Object> fallBackLogin(@RequestBody MultiValueMap<String, String> paramMap, @RequestParam("grant_type") String grantType, RuntimeException e) throws ApiUnauthorized {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem");
    }


}
