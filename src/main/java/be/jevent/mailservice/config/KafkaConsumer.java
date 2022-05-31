package be.jevent.mailservice.config;

import be.jevent.mailservice.dto.TicketEvent;
import be.jevent.mailservice.service.MailService;
import be.jevent.mailservice.service.PDFGenerator;
import com.itextpdf.text.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private MailService mailService;

    private TicketEvent payload = null;

    @KafkaListener(topics = "ticket", groupId = "ticket")
    public void receive(@Payload TicketEvent ticketEvent) throws MessagingException, IOException {
        LOGGER.info("received payload='{}'", ticketEvent.toString());
        setPayload(ticketEvent);
        mailService.sendEmail(ticketEvent);
    }

    public TicketEvent getPayload() {
        return payload;
    }

    private void setPayload(TicketEvent payload) {
        this.payload = payload;
    }
}