/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Springboot.Connection.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author formentera
 */
@Entity
public class CustomerReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Name;
    private int TableCapacity;
    private String Prefer;
    private String Phone;
    private String Email;
    private String Reference;
    

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return Name; }
    public void setName(String Name) { this.Name = Name; }

    public int getTableCapacity() { return TableCapacity; }
    public void setTableCapacity(int TableCapacity) { this.TableCapacity = TableCapacity; }

    public String getPrefer() { return Prefer; }
    public void setPrefer(String Prefer) { this.Prefer = Prefer; }

    public String getPhone() { return Phone; }
    public void setPhone(String Phone) { this.Phone = Phone; }

    public String getEmail() { return Email; }
    public void setEmail(String Email) { this.Email = Email; }

    public String getReference() { return Reference; }
    public void setReference(String Reference) { this.Reference = Reference; }
}
