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
import java.time.format.DateTimeFormatter;

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
    @GetMapping("/login")
    public String loginViaLink(
            @RequestParam("phone") String phone,
            @RequestParam("reference") String reference,
            HttpSession session
    ) {
        // Find reservation
        Reservation reservation = reservationRepository.findByCustomerPhoneAndReference(phone, reference);

        if (reservation != null) {
            // Store session info
            session.setAttribute("phone", reservation.getCustomer().getPhone());
            session.setAttribute("reference", reservation.getReference());
            session.setAttribute("reservationId", reservation.getId());

            // Redirect to reservation page
            return "redirect:/queue"; // your page to view/modify reservation
        } else {
            return "redirect:/loginpage?error=No+Reservation+Found";
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
            LocalDateTime completeDateTime = null;
            if (reservation.getReservationCompletetime() != null){
                completeDateTime = LocalDateTime.of(reservation.getDate(),reservation.getReservationCompletetime());
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy  hh:mm a");
            String readablePendingDateTime = pendingDateTime.format(formatter);

            model.addAttribute("customerName", reservation.getCustomer().getName());
            model.addAttribute("customerPhone", reservation.getCustomer().getPhone());
            model.addAttribute("reference", reservation.getReference());
            model.addAttribute("queueNumber", position);
            model.addAttribute("seatingCapacity", reservation.getPax());
            model.addAttribute("preferredSpace", reservation.getPrefer() != null ? reservation.getPrefer() : "");
            model.addAttribute("status", reservation.getStatus());
            model.addAttribute("pendingDateTime", pendingDateTime.toString());
            model.addAttribute("completeDateTime", reservation.getReservationCompletetime() != null ? completeDateTime.toString() : "") ;
            model.addAttribute("created", readablePendingDateTime);

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

    @PostMapping("/updateCustomer")
    public String updateCustomer(@RequestParam String customerName,@RequestParam String customerPhone,@RequestParam String seatingCapacity,@RequestParam String preferredSpace, HttpSession session) {
        String phone = (String) session.getAttribute("phone");
        String reference = (String) session.getAttribute("reference");

        if (phone == null || reference == null) {
            // session expired or user not logged in
            return "redirect:/loginpage";
        }

        Reservation reservation = reservationRepository.findByCustomerPhoneAndReference(phone, reference);
        if (reservation != null) {
            // Update the customer name
            reservation.getCustomer().setName(customerName);
            reservation.getCustomer().setPhone(customerPhone);
            reservation.setPax(Integer.parseInt(seatingCapacity));
            reservation.setPrefer(preferredSpace);
            reservationRepository.save(reservation); // save the reservation (cascades to customer if mapped)
            session.setAttribute("phone", customerPhone);

        }
        return "redirect:/queue"; // Redirect after successful update
    }


}