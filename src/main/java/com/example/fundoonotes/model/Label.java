package com.example.fundoonotes.model;

import com.example.fundoonotes.dto.LabelDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "label.data")
public class Label {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private LocalDateTime createdDate=LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;


    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Note> notes=new HashSet<>();




    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Label) {
            Label label = (Label) obj;
            if (this.name.equals(label.getName()))
                return true;
            else
                return false;
        }
        throw new IllegalArgumentException("Can't compare non-Label objects");
    }

    @Override
    public int hashCode() {
        return (name).hashCode();
    }
}

