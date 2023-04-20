package com.example.fundoonotes.service;

import com.example.fundoonotes.dto.LoginDTO;
import com.example.fundoonotes.dto.UserDTO;
import com.example.fundoonotes.exception.CustomException;
import com.example.fundoonotes.model.Email;
import com.example.fundoonotes.model.User;
import com.example.fundoonotes.repository.UserRepository;
import com.example.fundoonotes.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserIService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailIService emailIService;
    @Autowired
    TokenUtil tokenUtil;

    //Method to register user details
    @Override
    public String registerDetails(UserDTO userDTO) {
        User registeredUser = new User(userDTO);
        userRepository.save(registeredUser);
        String token = tokenUtil.createToken(registeredUser.getUserID());
        return token;
    }


    //Method to reset the password of user using emailId
    @Override
    public User resetPassword(LoginDTO loginDTO, String newPassword, String token) {
        int userId = tokenUtil.verifyToken(token);
        Optional<User> foundUser = userRepository.findById(userId);
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the user");
        }
        if (foundUser.isPresent()) {
            User user = userRepository.getByEmailId(loginDTO.getEmailId()).get();
            user.setPassword(newPassword);
            Email email = new Email(loginDTO.getEmailId(), "Password is Reset Successfully", "New password is : " + loginDTO.getPassword());
            emailIService.sendMail(email);
            userRepository.save(user);
            return user;
        } else throw new CustomException("No User present of this emailId for resetting Password");
    }

    @Override
    public User forgetPassword(String emailId, String newPassword, String token) {
        int userId = tokenUtil.verifyToken(token);
        Optional<User> founduser = userRepository.findById(userId);
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }
        if (founduser.isPresent()) {
            User user = userRepository.getByEmailId(emailId).get();
            user.setPassword(newPassword);
            Email email = new Email(emailId, "Changed Password ", "New Password is" + newPassword);
            emailIService.sendMail(email);
            userRepository.save(user);
            return user;
        } else throw new CustomException("This user is not present in database");
    }

    @Override
    public void deleteUser(int userId, String token) {
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && isVerfied) {
            userRepository.deleteById(userId);

        } else throw new CustomException("User not found ");
    }


    //Method to login using its emailId and password
    @Override
    public String login(LoginDTO loginDTO) {
        Optional<User> user = userRepository.getByEmailId(loginDTO.getEmailId());
        if (user.get().getEmailId().equals(loginDTO.getEmailId())
                && user.get().getPassword().equals(loginDTO.getPassword())) {
            String token = tokenUtil.createToken(user.get().getUserID());
            return token;
        } else throw new CustomException("Invalid login details try again");
    }


}

