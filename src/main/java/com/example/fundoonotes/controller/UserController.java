package com.example.fundoonotes.controller;

import com.example.fundoonotes.dto.LoginDTO;
import com.example.fundoonotes.dto.ResponseDTO;
import com.example.fundoonotes.dto.UserDTO;
import com.example.fundoonotes.model.User;
import com.example.fundoonotes.service.UserIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController

@RequestMapping("/user")

public class UserController {
    @Autowired
    UserIService userIService;

    //Method to register user details
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerDetails(@Valid @RequestBody UserDTO userDTO ) {
      String token=  userIService.registerDetails(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("Details registered Successfully", token);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    //Method to reset the password of user using emailId
    @PutMapping("/reset-password/{token}")
    public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody LoginDTO loginDTO,
                                                     @RequestParam String newPassword ,
                                                     @PathVariable String token) {
        User searchByEmailId = userIService.resetPassword(loginDTO, newPassword,token);
        ResponseDTO responseDTO = new ResponseDTO("Password reset successfully", searchByEmailId);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }

    @PutMapping("/forget-password/{token}")
    public ResponseEntity<ResponseDTO> forgetPassword(@Valid @RequestParam String emailId,
                                                      @RequestParam String newPassword ,
                                                      @PathVariable String token) {
        User user = userIService.forgetPassword(emailId, newPassword,token);
        ResponseDTO responseDTO = new ResponseDTO("Password reset successfully", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }

    @DeleteMapping("/{userId}/{token}")
    public ResponseEntity<ResponseDTO> deleteUser(@Valid @PathVariable int userId,
                                                         @PathVariable String token) {
         userIService.deleteUser(userId,token);
        ResponseDTO responseDTO = new ResponseDTO("User deleted successfully", userId);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }


    //Method to login by user using its emailId and password
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        String loginUser = userIService.login(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login successfully", loginUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
