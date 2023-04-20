package com.example.fundoonotes.service;


import com.example.fundoonotes.dto.LoginDTO;
import com.example.fundoonotes.dto.UserDTO;
import com.example.fundoonotes.model.User;

import java.util.List;
import java.util.Optional;

public interface UserIService {


    //Method to register user details
    String registerDetails(UserDTO userDTO);

    //Method to reset the password of user using emailId
    User resetPassword(LoginDTO loginDTO, String newPassword, String token);

    //Method to update password after forgetting
    User forgetPassword(String emailId, String newPassword, String token);

    void deleteUser(int userId, String token);

    //Method for login
    String login(LoginDTO loginDTO);
}
