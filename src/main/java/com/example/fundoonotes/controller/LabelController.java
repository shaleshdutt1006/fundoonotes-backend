package com.example.fundoonotes.controller;


import com.example.fundoonotes.dto.LabelDTO;
import com.example.fundoonotes.dto.ResponseDTO;
import com.example.fundoonotes.model.Label;
import com.example.fundoonotes.service.LabelIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired

    private LabelIService labelService;

    @PostMapping("/add/{token}")
    public ResponseEntity<ResponseDTO> addLabel(@Valid @RequestBody LabelDTO labelDTO,
                                                           @PathVariable String token) {
        Label label = labelService.addLabel(labelDTO,token);
        ResponseDTO responseDTO = new ResponseDTO("Label added successfully", label);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{token}")
    public ResponseEntity<ResponseDTO> updateLabel(@Valid @RequestBody LabelDTO labelDTO,
                                                   @PathVariable int id,
                                                   @PathVariable String token) {
        Optional<Label> label = labelService.updateLabel(labelDTO,token,id);
        ResponseDTO responseDTO = new ResponseDTO("Label updated successfully", label);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<ResponseDTO> getAllLabel(@Valid @PathVariable String token) {
        List<Label> label = labelService.getAllLabel(token);
        ResponseDTO responseDTO = new ResponseDTO("All Labels called successfully", label);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/id/{token}")
    public ResponseEntity<ResponseDTO> deleteLabel(@PathVariable int id,
                                                   @PathVariable String token) {
        labelService.deleteLabel(id,token);
        ResponseDTO responseDTO = new ResponseDTO("Label updated successfully", id);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
