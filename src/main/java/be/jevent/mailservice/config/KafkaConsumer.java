package be.jevent.mailservice.config;

import be.jevent.mailservice.dto.TicketEvent;
import be.jevent.mailservice.service.MailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private MailService mailService;

    private TicketEvent payload = null;

    /*@KafkaListener(topics = "ticket", groupId = "ticket")
    public void receive(ConsumerRecord<String, TicketEvent> consumerRecord) {
        LOGGER.info("received payload='{}'", consumerRecord.toString());
        TicketEvent ticket = consumerRecord.value();
        setPayload(ticket);
        mailService.sendEmail("robbyquintiens.rq@gmail.com");
    }*/

    @KafkaListener(topics = "ticket", groupId = "ticket")
    public void receive(TicketEvent ticketEvent) {
        LOGGER.info("received payload='{}'", ticketEvent.toString());
        setPayload(ticketEvent);
        mailService.sendEmail("robbyquintiens.rq@gmail.com");
    }

    public TicketEvent getPayload() {
        return payload;
    }

    private void setPayload(TicketEvent payload) {
        this.payload = payload;
    }

}
