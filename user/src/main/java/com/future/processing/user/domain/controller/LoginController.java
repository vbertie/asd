package com.future.processing.user.domain.controller;

import com.future.processing.user.domain.UserFacade;
import com.future.processing.user.domain.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:4200" ) /** add your's */
@RequestMapping("/login")
class LoginController {

    private final UserFacade userFacade;

    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getToken(HttpSession session, HttpServletRequest request) {

        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();

        String username = new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];;

        UserDto user = userFacade.findByUsername(username);

        return Collections.singletonMap("id", String.valueOf(user.getId()));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {

        SecurityContextHolder.clearContext();

        return new ResponseEntity(HttpStatus.OK);
    }
}
