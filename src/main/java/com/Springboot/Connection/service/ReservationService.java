package com.Springboot.Connection.service;

import com.Springboot.Connection.dto.WebUpdateDTO;
import com.Springboot.Connection.model.Reservation;
import com.Springboot.Connection.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SettingsService settingsService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              SettingsService settingsService) {
        this.reservationRepository = reservationRepository;
        this.settingsService = settingsService;
    }

    /*   =----------- AUTO DELETE ----------------
    @Scheduled(fixedRate = 60000)  // Runs every 1 minute
    public void checkAndUpdateReservationStatuses() {
        long autoCancelMinutes = settingsService.getAutoCancelMinutes(); // dynamic

        System.out.println(autoCancelMinutes);
        System.out.println(settingsService.getAutoDeleteMonths());
        List<Reservation> reservations = reservationRepository.findAllByStatus("Pending");

        for (Reservation reservation : reservations) {
            if (Duration.between(reservation.getReservationPendingtime(), LocalTime.now()).toMinutes() > autoCancelMinutes) {
                reservation.setStatus("Cancelled");
                reservation.setReservationCancelledtime(LocalTime.now());
                reservation.setRevenue(BigDecimal.ZERO);
                reservationRepository.save(reservation);


                WebUpdateDTO dto = new WebUpdateDTO();
                dto.setCode("CANCELLED_RESERVATION");
                dto.setMessage(
                        "Reservation from " + reservation.getCustomer().getName()
                                + " (" + reservation.getPax() + " pax)"
                                + " | Ref: " + reservation.getReference()
                                + " has been cancelled"
                );
                dto.setPhone(reservation.getCustomer().getPhone());
                dto.setReference(reservation.getReference());
                System.out.println(dto.getCode());

                messagingTemplate.convertAndSend("/topic/forms",dto);
                System.out.println(dto.getCode());

            }
        }
    }*/


    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteOldReservations() {
        int months = settingsService.getAutoDeleteMonths();
        LocalDate cutoffDate = LocalDate.now().minusMonths(months);
        reservationRepository.deleteByDateBefore(cutoffDate);
    }
}
