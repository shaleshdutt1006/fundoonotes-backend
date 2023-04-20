package com.example.fundoonotes.repository;


import com.example.fundoonotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //Custom query to get user by its emailId
    @Query(value = "select * from User where email_Id=:emailId", nativeQuery = true)
    Optional<User> getByEmailId(String emailId);

    @Query(value = "select * from User where email_Id=:emailId", nativeQuery = true)
    User getEmailId(String emailId);

    //Custom query to get user by its emailId and password
    @Query(value = "select * from User where email_Id=:emailId && password=:password", nativeQuery = true)
    Optional<User> getLoginDetails(String emailId, String password);

}
