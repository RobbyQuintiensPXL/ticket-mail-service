package be.jevent.mailservice.config;

import be.jevent.mailservice.dto.TicketEvent;
import be.jevent.mailservice.service.EmailService;
import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private final EmailService mailService;

    private TicketEvent payload = null;

    public KafkaConsumer(EmailService mailService){
        this.mailService = mailService;
    }

    @KafkaListener(topics = "ticket", groupId = "ticket")
    public void receive(@Payload TicketEvent ticketEvent) throws MessagingException, IOException, DocumentException {
        LOGGER.info("received payload='{}'", ticketEvent.toString());
        setPayload(ticketEvent);

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", ticketEvent.getFirstName() + " " + ticketEvent.getName());
        templateModel.put("eventName", ticketEvent.getEventName());
        templateModel.put("building", ticketEvent.getBuildingName());
        templateModel.put("date", ticketEvent.getEventDate() + " " + ticketEvent.getEventTime());
        mailService.sendMessageUsingThymeleafTemplate(ticketEvent.getEmail(), "Tickets: " + ticketEvent.getEventName(), templateModel, ticketEvent);
    }

    public TicketEvent getPayload() {
        return payload;
    }

    private void setPayload(TicketEvent payload) {
        this.payload = payload;
    }
}