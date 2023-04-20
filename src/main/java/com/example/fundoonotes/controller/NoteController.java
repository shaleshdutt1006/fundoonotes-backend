package com.example.fundoonotes.controller;
import com.example.fundoonotes.dto.LabelDTO;
import com.example.fundoonotes.dto.ResponseDTO;
import com.example.fundoonotes.dto.NoteDTO;
import com.example.fundoonotes.model.Label;
import com.example.fundoonotes.model.Note;
import com.example.fundoonotes.service.NoteIService;
import com.example.fundoonotes.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    NoteIService noteIService;

    //Method to create notes
    @PostMapping("/create/{token}")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody NoteDTO noteDTO,@PathVariable String token) {
        noteIService.insert(noteDTO,token);
        ResponseDTO responseDTO = new ResponseDTO("Details added Successfully", noteDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    //Method to get note by its Id
    @GetMapping("/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> getByNoteId(@PathVariable int noteId
                                                    ,@PathVariable String token) {
        Optional<Note> foundBook = noteIService.getByNoteId(noteId,token);
        ResponseDTO responseDTO = new ResponseDTO("Get call noteId successfully", foundBook);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }

    //Method to pin the note
    @PutMapping("/pin/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> pinNote(@PathVariable int noteId,
                                                @PathVariable String token) {
        Optional<Note> pinNote = noteIService.pinNote(noteId,token);
        ResponseDTO responseDTO = new ResponseDTO("pinned note successfully", pinNote);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }

    //Method to archive the note
    @PutMapping("/archive/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> archiveNote(@PathVariable int noteId,
                                                   @PathVariable String token) {
        Optional<Note> archiveNote = noteIService.archiveNote(noteId,token);
        ResponseDTO responseDTO = new ResponseDTO("archive note successfully", archiveNote);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }

    //Method to trash the note
    @PutMapping("/trash/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> trashNote(@PathVariable int noteId,
                                                 @PathVariable String token) {
        Optional<Note> trashNote = noteIService.trashNote(noteId,token);
        ResponseDTO responseDTO = new ResponseDTO("trash note successfully", trashNote);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //Method to set reminder the note
    @PutMapping("/reminder/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> setReminder(@PathVariable int noteId,
                                                   @RequestParam("reminder") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                                   LocalDateTime reminder,@PathVariable String token) {
        Optional<Note> note = noteIService.setReminder(noteId, reminder,token);
        ResponseDTO responseDTO = new ResponseDTO("Reminder added to note successfully", note);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }
    //Method to remove reminder from the note
    @DeleteMapping("/reminder/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> removeReminder(@PathVariable int noteId,
                                                      @PathVariable String token) {
        Optional<Note> note = noteIService.removeReminder(noteId,token);
        ResponseDTO responseDTO = new ResponseDTO("Reminder added to note successfully", note);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //Method to get all the notes in database
    @GetMapping("/get-all/{token}")
    public ResponseEntity<ResponseDTO> getAll(@PathVariable String token) {
         List<Note> noteList = noteIService.getAll(token);
        ResponseDTO responseDTO = new ResponseDTO("Get all Id successfully", noteList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //Method to update note details by using noteId
    @PutMapping("/update/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> updateNote(@Valid @PathVariable int noteId,
                                                  @RequestBody NoteDTO noteDTO,
                                                  @PathVariable String token) {
        Optional<Note> updatedNote = noteIService.updateNoteDetails(noteId, noteDTO,token);
        ResponseDTO responseDTO = new ResponseDTO("Note Updated successfully", updatedNote);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }

    //Method to delete note details by using bookId
    @DeleteMapping("/{noteId}/{token}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int noteId
                                                ,@PathVariable String token) {
        List<Note> updatedData = noteIService.deleteByNoteId(noteId,token);
        ResponseDTO responseDTO = new ResponseDTO("Id deleted successfully", updatedData);
        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }
//    //  Method add label to the note
//
//    @PostMapping("/{noteId}/label/{token}")
//    public ResponseEntity<ResponseDTO> addLabelToNote(@PathVariable int noteId,
//                                                      @RequestBody LabelDTO labelDTO,
//                                                       @PathVariable String token ) {
//       Label newLabel = noteIService.addLabel(labelDTO,noteId,token);
//        ResponseDTO responseDTO =new ResponseDTO("Label added",newLabel);
//             return new ResponseEntity<>(responseDTO,HttpStatus.OK);
//    }
}