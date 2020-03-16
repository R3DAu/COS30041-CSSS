/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.swin.vehicle;

/**
 *
 * @author reyno
 */
public class VehicleType {

    private String code;
    private String description;
    private Integer seats;

    public VehicleType(String code, String description, Integer seats) {
        this.code = code;
        this.description = description;
        this.seats = seats;
    }

    public String getCode() {
        return code.toUpperCase();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
