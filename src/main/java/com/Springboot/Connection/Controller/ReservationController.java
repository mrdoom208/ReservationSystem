package com.Springboot.Connection.Controller;

import com.Springboot.Connection.Config.CustomerReservationDTO;
import com.Springboot.Connection.model.CustomerReservation;
import com.Springboot.Connection.repository.CustomerReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class ReservationController {
    @Autowired
    private CustomerReservationRepository repository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/")
    public String showForm() {

        return "walkin";
    }



    @PostMapping("/reserve")
    public String saveReservation(CustomerReservation reservation) {

        reservation.setReference(String.format("RSV-%05d", repository.count()+1));
        reservation.setDate(LocalDate.now());
        reservation.setStatus("Pending");
        reservation.setReservationPendingtime(LocalTime.now());
        repository.save(reservation);
        System.out.println(reservation);


        CustomerReservationDTO dto = new CustomerReservationDTO();
        dto.setName(reservation.getName());
        dto.setPhone(reservation.getPhone());
        dto.setTableCapacity(reservation.getPax());

        System.out.println("Sending WebSocket message for reservation: " + dto);


        messagingTemplate.convertAndSend("/topic/forms", dto);


        return "redirect:/success.html"; // after saving, redirect to success page
    }
}
