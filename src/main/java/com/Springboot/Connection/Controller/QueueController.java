package com.Springboot.Connection.Controller;

import com.Springboot.Connection.model.Reservation;
import com.Springboot.Connection.repository.ReservationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class QueueController {

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/login")
    public String login(@RequestParam String phone,@RequestParam String reference, HttpSession session) {
        Reservation reservation = reservationRepository.findByCustomerPhoneAndReference(phone, reference);

        if (reservation != null) {
            session.setAttribute("phone", reservation.getCustomer().getPhone());
            session.setAttribute("reference", reservation.getReference());
            return "redirect:/queue";
        } else {
            return "redirect:/loginpage?error=No Reservation Found";
        }

    }
    @GetMapping("/queue")
    public String queuePage(HttpSession session, Model model) {
        String phone = (String) session.getAttribute("phone");
        String reference =(String) session.getAttribute("reference");

        if (phone == null) {
            return "redirect:/loginpage";
        }

        Reservation reservation = reservationRepository.findByCustomerPhoneAndReference(phone,reference);

        if (reservation != null) {
            long position = reservationRepository.countAhead(reservation.getReservationPendingtime()) + 1;
            long Minutes = 10;
            long estimatedMinutes = Minutes * position;
            long hours = estimatedMinutes / 60;
            long minutes = estimatedMinutes % 60;
            String estimatedTimeStr = String.format("%02d:%02d", hours, minutes);
            LocalDate pendingDate = reservation.getDate();   // e.g., 2025-12-12
            LocalTime pendingTime = reservation.getReservationPendingtime();   // e.g., 23:58:00
            LocalDateTime pendingDateTime = LocalDateTime.of(pendingDate, pendingTime);

            model.addAttribute("customerName", reservation.getCustomer().getName());
            model.addAttribute("customerPhone", reservation.getCustomer().getPhone());
            model.addAttribute("reference", reservation.getReference());
            model.addAttribute("queueNumber", position);
            model.addAttribute("seatingCapacity", reservation.getPax());
            model.addAttribute("preferredSpace", reservation.getPrefer() != null ? reservation.getPrefer() : "");
            model.addAttribute("estimatedTime", estimatedTimeStr);
            model.addAttribute("pendingDateTime", pendingDateTime.toString());

        } else {
            // Default placeholders
            model.addAttribute("customerName", "Loading...");
            model.addAttribute("customerPhone", "Loading...");
            model.addAttribute("reference", "Loading...");
            model.addAttribute("queueNumber", "Loading...");
            model.addAttribute("seatingCapacity", "Loading...");
            model.addAttribute("preferredSpace", "Loading...");
            model.addAttribute("estimatedTime", "Loading...");
            }

        return "ReservationData";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session
        session.invalidate();

        // Redirect to login page
        return "redirect:/loginpage?logout=You have Successfully logged out";
    }


}