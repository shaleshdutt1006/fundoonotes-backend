package com.example.fundoonotes.repository;


import com.example.fundoonotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    //Custom query to get details by NoteName
    @Query(value = "select * from Note where note_Name=:noteName", nativeQuery = true)
    Optional<Note> getByNoteName(String noteName);



    List<Note> findByReminder(LocalDateTime reminder);
}
