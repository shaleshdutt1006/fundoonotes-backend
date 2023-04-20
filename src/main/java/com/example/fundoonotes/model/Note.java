package com.example.fundoonotes.model;

import com.example.fundoonotes.dto.NoteDTO;


import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;


@Entity
@Table(name = "note_data")
@Data
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    @Lob
    private String description;
    private String color;
    private boolean isPinned;
    private boolean isArchived;
    private boolean isTrashed;

    private LocalDate createdDate = LocalDate.now();
    @FutureOrPresent
    private LocalDateTime reminder;


    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Label> labels;

    @ManyToOne
    private User user;
//Constructor to create the note
    public Note(NoteDTO noteDTO) {
        this.title = noteDTO.getTitle();
        this.description = noteDTO.getDescription();
    }

    public Note(int id, NoteDTO noteDTO) {
        this.id = id;
        this.title = noteDTO.getTitle();
        this.description = noteDTO.getDescription();

    }
    //Constructor to pin,archive,trash,add and remove reminder of the note

    public Note(int id, Optional<Note> note) {
        this.id = id;
        this.title = note.get().getTitle();
        this.description = note.get().getDescription();
        this.setArchived(note.get().isArchived());
        this.setPinned(note.get().isPinned);
        this.setTrashed(note.get().isTrashed);
        this.color = note.get().getColor();
        this.setUser(note.get().getUser());

    }


    //Constructor to update the note details
    public Note(int noteId, NoteDTO noteDTO, Optional<Note> note) {
        this.id = noteId;
        this.title = noteDTO.getTitle();
        this.description = noteDTO.getDescription();
        this.setArchived(note.get().isArchived());
        this.setPinned(note.get().isPinned);
        this.setTrashed(note.get().isTrashed);
        this.color = note.get().getColor();
        this.setUser(note.get().getUser());

    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", isPinned=" + isPinned +
                ", isArchived=" + isArchived +
                ", isTrashed=" + isTrashed +
                ", createdDate=" + createdDate +
                ", reminder='" + reminder + '\'' +
                ", user=" + user +
                '}';
    }


}
