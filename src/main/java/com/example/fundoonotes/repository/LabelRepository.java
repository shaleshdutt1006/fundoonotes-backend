package com.example.fundoonotes.repository;

import com.example.fundoonotes.model.Label;
import com.example.fundoonotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {

   List<Label> findAllByUser(User user);
}
