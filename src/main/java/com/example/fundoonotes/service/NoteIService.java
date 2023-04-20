package com.example.fundoonotes.service;


import com.example.fundoonotes.dto.LabelDTO;
import com.example.fundoonotes.dto.NoteDTO;
import com.example.fundoonotes.model.Label;
import com.example.fundoonotes.model.Note;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoteIService {


    //Method to register note details
    Note insert(NoteDTO noteDTO,String token);

    //Method to get note by noteId
    Optional<Note> getByNoteId(int noteId,String token);

Optional<Note> pinNote(int noteId,String token);
Optional<Note> archiveNote(int noteId,String token);
    Optional<Note> trashNote(int noteId,String token);
    Optional<Note> setReminder(int noteId, LocalDateTime reminder,String token);
Optional<Note> removeReminder(int noteId,String token);

    //Method to get all the note in database
    List<Note> getAll(String token);

    // Method to update note details using noteId
    Optional<Note> updateNoteDetails(int noteId, NoteDTO noteDTO,String token);

    //Method to delete book details by using bookId
    List<Note> deleteByNoteId(int bookId,String token);

   // Label addLabel(LabelDTO labelDTO,int noteId,String token);





}
