package com.future.processing.user.domain.controller;

import com.future.processing.user.domain.UserFacade;
import com.future.processing.user.domain.dto.post.PostUserDto;
import com.future.processing.user.domain.dto.put.PutUserDto;
import com.future.processing.user.domain.dto.stream.SaleDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "https://localhost:4200") /** add your's */
@RequiredArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Validated PostUserDto dto) {

        userFacade.createUser(dto);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userFacade.findById(id), HttpStatus.OK);
    }

    @PutMapping("/sale")
    public ResponseEntity<?> processSale(@RequestBody SaleDto dto) {

        userFacade.processSale(dto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Validated PutUserDto dto) {
        return new ResponseEntity<>(userFacade.updateUser(dto), HttpStatus.OK);
    }

}
