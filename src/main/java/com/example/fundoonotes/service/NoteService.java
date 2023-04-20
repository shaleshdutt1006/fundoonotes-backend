package com.example.fundoonotes.service;


import com.example.fundoonotes.dto.LabelDTO;
import com.example.fundoonotes.dto.NoteDTO;
import com.example.fundoonotes.exception.CustomException;
import com.example.fundoonotes.model.Label;
import com.example.fundoonotes.model.Note;
import com.example.fundoonotes.model.User;
import com.example.fundoonotes.repository.LabelRepository;
import com.example.fundoonotes.repository.NoteRepository;
import com.example.fundoonotes.repository.UserRepository;
import com.example.fundoonotes.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class NoteService implements NoteIService {

    @Autowired
    NoteRepository noteRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenUtil tokenUtil;

    // Method to register note details
    @Override
    public Note insert(NoteDTO noteDTO, String token) {
        int userId = tokenUtil.verifyToken(token);
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Note createNote = new Note(noteDTO);
            createNote.setUser(user.get());
            noteRepository.save(createNote);
            return createNote;
        } else throw new CustomException("User not present to add the notes");
    }

    // Method to get note by noteId
    @Override
    public Optional<Note> getByNoteId(int noteId, String token) {
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
            Optional<Note> note = noteRepository.findById(noteId);
            if (note.isPresent()) {
                return note;
            } else {
                throw new CustomException("Note with this noteId is not present");
            }
        } else {
            throw new CustomException("Access denied to the User");
        }

    }


    // Method to get all the notes in database
    @Override
    public List<Note> getAll(String token) {
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
            if (!noteRepository.findAll().isEmpty()) {
                return noteRepository.findAll();
            } else {
                throw new CustomException("No notes present in the database");
            }
        } else {
            throw new CustomException("Access denied to the User");
        }
    }


    // Method to update note details using noteId
    @Override
    public Optional<Note> updateNoteDetails(int noteId, NoteDTO noteDTO, String token) {
        boolean isVerfied = tokenUtil.isValid(token);
        Optional<Note> note=noteRepository.findById(noteId);
        if (isVerfied) {
            if (note.isPresent()) {
                Note updatedNote = new Note(noteId,noteDTO,note);
                noteRepository.save(updatedNote);
                return Optional.of(updatedNote);
            } else {
                throw new CustomException("Note with this noteId is not present");
            }
        } else throw new CustomException("Access denied to the User");

    }


    // Method to delete note details by using noteId
    @Override
    public List<Note> deleteByNoteId(int noteId, String token) {
        boolean isVerfied = tokenUtil.isValid(token);
        Optional<Note> note = noteRepository.findById(noteId);
        if (isVerfied) {
            if (note.isPresent()) {
                noteRepository.deleteById(noteId);
                return noteRepository.findAll();
            } else {
                throw new CustomException("Note with this noteId is not present");
            }
        } else {
            throw new CustomException("Access denied to the User");
        }

    }

    @Override
    public Optional<Note> pinNote(int noteId, String token) {
        int userId = tokenUtil.verifyToken(token);
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }
        Optional<User> user = userRepository.findById(userId);
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent() && user.isPresent()) {
            note.get().setPinned(true);
            Note updatedNote = new Note(noteId, note);
            noteRepository.save(updatedNote);
            return note;
        } else throw new CustomException("Note is not present to pin");

    }

    @Override
    public Optional<Note> archiveNote(int noteId, String token) {
        int userId = tokenUtil.verifyToken(token);
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }
        Optional<User> user = userRepository.findById(userId);
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent() && user.isPresent()) {
            note.get().setArchived(true);
            Note updatedNote = new Note(noteId, note);
            noteRepository.save(updatedNote);
            return note;
        } else throw new CustomException("Note is not present to archive");

    }

    @Override
    public Optional<Note> trashNote(int noteId, String token) {
        int userId = tokenUtil.verifyToken(token);
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }
        Optional<User> user = userRepository.findById(userId);
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent() && user.isPresent()) {
            note.get().setTrashed(true);
            Note updatedNote = new Note(noteId, note);
            noteRepository.save(updatedNote);
            return note;
        } else throw new CustomException("Note is not present to add in trash");

    }

    @Override
    public Optional<Note> setReminder(int noteId, LocalDateTime reminder, String token) {
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }
        int userId = tokenUtil.verifyToken(token);
        Optional<User> user = userRepository.findById(userId);
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isPresent() && user.isPresent()) {
            Note note = noteOptional.get();
            note.setReminder(reminder);
            noteRepository.save(note);
        } else throw new CustomException("Note is not present to update reminder");
        return noteOptional;
    }

    @Override
    public Optional<Note> removeReminder(int noteId, String token) {
        boolean isVerfied = tokenUtil.isValid(token);
        if (isVerfied) {
        } else {
            throw new CustomException("Access denied to the User");
        }
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        int userId = noteOptional.get().getUser().getUserID();
        Optional<User> user = userRepository.findById(userId);
        if (noteOptional.isPresent() && user.isPresent()) {
            Note note = noteOptional.get();
            note.setReminder(null);
            noteRepository.save(note);
        }
        return noteOptional;
    }

//    @Override
//    public Label addLabel(LabelDTO labelDTO, int noteId, String token) {
//        boolean isVerfied = tokenUtil.isValid(token);
//        if (isVerfied) {
//        } else {
//            throw new CustomException("Access denied to the User");
//        }
//         Optional<Note> note = noteRepository.findById(noteId);
//
//        Label label = new Label();
//        if (note.isPresent()) {
//            label.setName(labelDTO.getName());
//            label.getNotes().add(note.get());
//            labelRepository.save(label);
//            note.get().getLabels().add(label);
//            noteRepository.save(note.get());
//        } else {
//            throw new CustomException("Note is not Present to add label");
//        }
//        return label;
//    }
}







