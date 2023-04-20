package com.example.fundoonotes.service;

import com.example.fundoonotes.dto.LabelDTO;
import com.example.fundoonotes.exception.CustomException;
import com.example.fundoonotes.model.Label;
import com.example.fundoonotes.model.Note;
import com.example.fundoonotes.model.User;
import com.example.fundoonotes.repository.LabelRepository;
import com.example.fundoonotes.repository.NoteRepository;
import com.example.fundoonotes.repository.UserRepository;
import com.example.fundoonotes.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class LabelService implements LabelIService {
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    NoteRepository noteRepository;

    @Override
    public Label addLabel(LabelDTO labelDTO, String token) {
        boolean isVerified = tokenUtil.isValid(token);
        int userId = tokenUtil.verifyToken(token);
        User user = userRepository.findById(userId).get();
        Optional<Note> note = noteRepository.findById(labelDTO.getNoteId());
        if (isVerified) {
            Label label = new Label();
            label.setName(labelDTO.getName());
            if (user != null) {
                label.setUser(user);
                label.getNotes().add(note.get());

            }
            labelRepository.save(label);
            return label;
        } else {
            throw new CustomException("Access denied to the User");
        }

    }

    @Override
    public Optional<Label> updateLabel(LabelDTO labelDTO, String token, int id) {
        boolean isVerified = tokenUtil.isValid(token);
        int userId = tokenUtil.verifyToken(token);
        Optional<User> user = userRepository.findById(userId);
        Optional<Label> label = labelRepository.findById(id);
        if (isVerified) {
            if (label.isPresent()) {
                label.get().setName(labelDTO.getName());
                labelRepository.save(label.get());
                return label;
            } else throw new CustomException("No label present for the user");
        } else {
            throw new CustomException("Access denied to the user");
        }
    }

    @Override
    public void deleteLabel(int id, String token) {
        boolean isVerified = tokenUtil.isValid(token);
        if (isVerified) {
            Optional<Label> label = labelRepository.findById(id);
            if (label.isPresent()) {
                label.get().getNotes().remove(label.get().getNotes());
                labelRepository.deleteById(id);
            } else
                throw new CustomException("Access denied");
        }

    }

    @Override
    public List<Label> getAllLabel(String token) {
        boolean isVerified = tokenUtil.isValid(token);
        int userId = tokenUtil.verifyToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (isVerified) {
            List<Label> labels = labelRepository.findAllByUser(user.get());
            if (!labels.isEmpty()) {
                return labels;
            } else throw new CustomException("No label Present of the User");
        } else {
            throw new CustomException("Access denied");

        }
    }
}