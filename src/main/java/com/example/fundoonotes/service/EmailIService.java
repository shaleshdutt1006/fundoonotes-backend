package com.example.fundoonotes.service;

import com.example.fundoonotes.dto.ResponseDTO;
import com.example.fundoonotes.model.Email;
import org.springframework.http.ResponseEntity;

public interface EmailIService {
    ResponseEntity<ResponseDTO> sendMail(Email email);
}
