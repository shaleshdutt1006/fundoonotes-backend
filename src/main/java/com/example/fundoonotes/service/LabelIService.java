package com.example.fundoonotes.service;

import com.example.fundoonotes.dto.LabelDTO;
import com.example.fundoonotes.model.Label;

import java.util.List;
import java.util.Optional;

public interface LabelIService {

   Label addLabel(LabelDTO labelDTO,String token);

   Optional<Label> updateLabel(LabelDTO labelDTO, String token,int Id);

   void deleteLabel(int id,String token);

   List<Label> getAllLabel(String token);
}
