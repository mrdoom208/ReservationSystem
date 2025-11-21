package com.Springboot.Connection.Config;

public class CustomerReservationDTO {
    private String name;
    private int tableCapacity;
    private String phone;

    // getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getTableCapacity() { return tableCapacity; }
    public void setTableCapacity(int tableCapacity) { this.tableCapacity = tableCapacity; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "CustomerReservationDTO{name='" + name + "', phone='" + phone + "', tableCapacity=" + tableCapacity + "}";
    }
}
