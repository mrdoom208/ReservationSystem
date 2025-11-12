package com.Springboot.Connection.Controller;

import com.Springboot.Connection.model.CustomerReservation;
import com.Springboot.Connection.repository.CustomerReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReservationController {
    @Autowired
    private CustomerReservationRepository repository;

    @RequestMapping("/")
    public String showForm() {
        return "reservation.html"; // points to your HTML file
    }

    @PostMapping("/reserve")
    public String saveReservation(CustomerReservation reservation) {
        repository.save(reservation);
        return "redirect:/success.html"; // after saving, redirect to success page
    }
}
