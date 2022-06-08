package be.jevent.mailservice.controller;

import be.jevent.mailservice.dto.TicketEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("pdf-ticket")
public class PDFController {

    private final ServletContext servletContext;
    private final TemplateEngine templateEngine;

    public PDFController(ServletContext servletContext, TemplateEngine templateEngine){
        this.servletContext = servletContext;
        this.templateEngine = templateEngine;
    }

/*    @RequestMapping(path = "pdf")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response){
        TicketEvent ticketEvent =
    }*/
}
