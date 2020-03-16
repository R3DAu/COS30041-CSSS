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
public class Vehicle {
    private String name;
    private String colour;
    private VehicleType type;
    private Integer year;
    
    /** Creates a new instance of Vehicle */
    public Vehicle(String name, String colour, VehicleType type, Integer year){
        this.name = name;
        this.colour = colour;
        this.type = type;
        this.year = year;
    }
    
    public String getColour(){
        return colour;
    }
    
    public void setColour(String colour)
    {
        this.colour = colour;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public VehicleType getType(){
        return this.type;
    }
    
    public void setType(VehicleType type){
        this.type = type;
    }
    
     public Integer getYear(){
        return this.year;
    }
    
    public void setYear(Integer year){
        this.year = year;
    }
    
    public String toString(){
        String objString = String.format("%s %s (%s) %d", this.getName(), this.getColour(), this.getType().getDescription(), this.getYear());
        return objString;
    }
}
