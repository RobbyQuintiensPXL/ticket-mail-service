package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(TicketEvent ticketEvent){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(ticketEvent.getEmail());
        mail.setSubject(ticketEvent.getEventName());
        mail.setText("Test tekst");
        javaMailSender.send(mail);
    }

}
