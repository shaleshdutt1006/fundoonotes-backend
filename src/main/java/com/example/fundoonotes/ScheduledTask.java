package com.example.fundoonotes;

import java.time.LocalDateTime;
import java.util.List;

import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.fundoonotes.model.Note;
import com.example.fundoonotes.repository.NoteRepository;

@Component
@Slf4j
public class ScheduledTask {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private NoteRepository noteRepository;

    @Scheduled(cron = "0 * * * * *")

    public void sendReminders() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Note> notes = noteRepository.findByReminder(currentDateTime); // get notes with reminder before current datetime
        if (!notes.isEmpty()) {
            for (Note note : notes) {
                if(note.getReminder().isEqual(currentDateTime)){
                // send email reminder
                String recipientEmail = note.getUser().getEmailId();
//            String recipientEmail = "shaleshdutt1006@gmail.com";// get recipient email from note's user
                String subject = "Reminder: " + note.getTitle();
                String message = note.getDescription();
                try {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    log.info("mimemessage is " + mimeMessage.getDescription());
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setTo(recipientEmail);
                    helper.setSubject(subject);
                    helper.setText(message);
                    javaMailSender.send(mimeMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }}
    }
}