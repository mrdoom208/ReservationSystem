package com.Springboot.Connection.Controller;

import com.Springboot.Connection.dto.CustomerReservationDTO;
import com.Springboot.Connection.model.Customer;
import com.Springboot.Connection.model.Reservation;
import com.Springboot.Connection.repository.CustomerRepository;
import com.Springboot.Connection.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CustomerRepository customerRepository;



    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/")
    public String showForm() {

        return "walkin";
    }


    @RequestMapping("/loginpage")
    public String showLogin(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            @RequestParam(required = false) String newreservation, Model model){
        if (error != null) {
            model.addAttribute("errorMessage", error);
            model.addAttribute("status","error");
        }
        if(logout != null){
            model.addAttribute("errorMessage",logout);
            model.addAttribute("status","success");
        }
        if(newreservation != null){
            model.addAttribute("errorMessage", newreservation);
            model.addAttribute("status","success");
        }

        return  "Login";
    }


    @ PostMapping("/reserve")
    public String saveReservation(CustomerReservationDTO reservationDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setEmail(reservationDTO.getEmail());
        newCustomer.setName(reservationDTO.getName());
        newCustomer.setPhone(reservationDTO.getPhone());
        customerRepository.save(newCustomer);

        Reservation reservation = new Reservation();
        reservation.setPrefer(reservationDTO.getPrefer());
        reservation.setReference(String.format("RSV-%05d", reservationRepository.count()+1));
        reservation.setPax(reservationDTO.getPax());
        reservation.setDate(LocalDate.now());
        reservation.setStatus("Pending");
        reservation.setReservationPendingtime(LocalTime.now());
        reservation.setCustomer(newCustomer);
        reservationRepository.save(reservation);


        CustomerReservationDTO dto = new CustomerReservationDTO();
        dto.setName(dto.getName());
        dto.setPhone(dto.getPhone());
        dto.setPax(dto.getPax());

        System.out.println("Sending WebSocket message for reservation: " + dto);


        messagingTemplate.convertAndSend("/topic/forms", dto);


        return "redirect:/loginpage?newreservation=New Reservation Created Successfully";
    }
}
