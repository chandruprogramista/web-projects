package com.chandru.basic_crud_app.endpoints;

import com.chandru.basic_crud_app.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservation-api")
public class ReservationRestController {

    ConsumerService consumerService;

    @Autowired
    public ReservationRestController (ConsumerService consumerService) {
    this.consumerService = consumerService;
    }

    // book the ticket
    @PostMapping ("book")
    public void book () {

    }

    // supports the partial cancel
    @PutMapping ("cancel")
    public void cancel () {

    }

    // fully vanish the pnr, like "drop the table"
    @DeleteMapping("cancel/{pnrId}")
    public void cancel (@PathVariable(name = "pnrId") String id) {

    }
}

