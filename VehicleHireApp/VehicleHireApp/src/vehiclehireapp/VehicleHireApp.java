/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiclehireapp;

import au.edu.swin.vehicle.Vehicle;
import au.edu.swin.vehicle.VehicleType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author reyno
 */
public class VehicleHireApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Let's create some vehicle types.
        VehicleType sedan = new VehicleType("SEDAN", "A standard sedan", 4);
        VehicleType limo6 = new VehicleType("LIMO6", "A six seater limo", 6);
        VehicleType limo8 = new VehicleType("LIMO8", "An eight seater limo", 8);

        //create the vehicles
        ArrayList<Vehicle> vehicles = new ArrayList();
        vehicles.add(new Vehicle("Ed's Holden Caprice", "Silver", sedan, 2002));
        vehicles.add(new Vehicle("John's Mercedes C200", "Black", sedan, 2005));
        vehicles.add(new Vehicle("Guy's Volvo 244 DL", "Blue", sedan, 1976));
        vehicles.add(new Vehicle("Sasco's Ford Limo", "White", limo6, 2014));
        vehicles.add(new Vehicle("Peters's Ford Limo", "White", limo6, 2004));
        vehicles.add(new Vehicle("Robert's Ford Limo", "White", limo8, 2003));

        System.out.println("\n\nList of vehicles in system:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }

        String typeCode = args[0];
        System.out.println("\n\nList of vehicle of type " + typeCode);
        ArrayList<Vehicle> xResults = getVehiclesFromType(typeCode, vehicles);
        for (Vehicle result : xResults) {
            System.out.println(result);
        }

        Integer option = 0;

        while (option != 4) {
            System.out.println("\n\n Please select from the following list:");
            System.out.println("1: SEDEAN");
            System.out.println("2: LIMO6");
            System.out.println("3: LIMO8");
            System.out.println("4: Exit");

            //let's keep this simple..
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
           
            try{
                System.out.print("\nOption: ");
                option = Integer.parseInt(in.readLine());
            }catch(IOException e){
                e.printStackTrace();
            }

            if (option < 1 || option > 4) {
                System.out.println("Please enter a valid number between 1 and 4");
                continue;
            }
            
            String optionString = "";

            switch(option){
                case 1:
                    optionString = "SEDAN";
                    break;
                case 2:
                    optionString = "LIMO6";
                    break;
                case 3:
                    optionString = "LIMO8";
                    break;
                case 4:
                    continue;
                default:
                    System.out.println("\n\n Cannot find that vehicle type!");
            }
            
            ArrayList<Vehicle> results = getVehiclesFromType(optionString, vehicles);

            for (Vehicle result : results) {
                System.out.println(result);
            }
        }
    }

    private static ArrayList<Vehicle> getVehiclesFromType(String typeCode, ArrayList<Vehicle> vehicles) {
        ArrayList<Vehicle> myVehicles = new ArrayList();

        System.out.println("\n\nList of vehicle of type " + typeCode);
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType().getCode().equals(typeCode)) {
                myVehicles.add(vehicle);
            }
        }

        return myVehicles;
    }

}
