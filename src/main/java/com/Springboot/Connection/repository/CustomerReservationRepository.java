package com.Springboot.Connection.repository;

import com.Springboot.Connection.model.CustomerReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReservationRepository extends JpaRepository<CustomerReservation, Long> {

}
