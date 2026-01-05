package com.Springboot.Connection.repository;

import com.Springboot.Connection.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByCustomerPhoneAndReference(String phone, String reference);

    @Query("SELECT COUNT(r) FROM Reservation r " +
            "WHERE (r.status = 'Pending' OR r.status = 'Confirmed') " +
            "AND r.reservationPendingtime < :time")
    long countAhead(java.time.LocalTime time);

    List<Reservation> findAllByStatus(String Status);
    void deleteByDateBefore(LocalDate cutoffDate);
}
