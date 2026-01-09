package com.Springboot.Connection.repository;

import com.Springboot.Connection.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByAccountAndSentFalse(String account);

}
