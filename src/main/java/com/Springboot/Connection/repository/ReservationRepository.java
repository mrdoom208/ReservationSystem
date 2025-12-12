package com.Springboot.Connection.repository;

import com.Springboot.Connection.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByCustomerPhoneAndReference(String phone, String reference);

    @Query("SELECT COUNT(r) FROM Reservation r " +
            "WHERE (r.status = 'Pending' OR r.status = 'Confirmed') " +
            "AND r.reservationPendingtime < :time")
    long countAhead(java.time.LocalTime time);

}
