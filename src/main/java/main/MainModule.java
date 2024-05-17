package main;

import com.java.dao.*;
import com.java.entity.*;
import com.java.exception.*;
import com.java.util.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainModule 
{

    private static AdminDAOImpl adminDAO;
    private static CustomerDAOImpl customerDAO;
    private static VehicleDAOImpl  vehicleDAO;
    private static ReservationDAOImpl reserveDAO;

    public static void main(String[] args) throws DatabaseConnectionException, IOException 
    {
        Connection connection = null;
        try 
        {
            connection = DBConnUtil.getConnection();

            adminDAO = new AdminDAOImpl(connection);
            customerDAO = new CustomerDAOImpl(connection);
            vehicleDAO = new VehicleDAOImpl(connection);
            reserveDAO = new ReservationDAOImpl(connection);

            Scanner scanner = new Scanner(System.in);

            boolean exit = false;
            boolean isAuthenticated = false; 
            while (!exit) 
            {
                
                if (!isAuthenticated) 
                {
                    try 
                    {
                       
                        authenticateUser(scanner);
                        isAuthenticated = true;
                    } 
                    catch (AuthenticationException e) 
                    {
                        
                        System.out.println(e.getMessage());
                    }
                } 
                else 
                {
                    
                	System.out.println(" ");
                	System.out.println("__________________________________________________________");
                	System.out.println(" ");
                    System.out.println("Welcome to the Car Rental System");
                    System.out.println(" ");
                    System.out.println("1. Customer Registration");
                    System.out.println("2. Update User Details");
                    System.out.println("3. Remove User");
                    System.out.println("4. Add Vehicle");
                    System.out.println("5. Update Vehicle");
                    System.out.println("6. Remove Vehicle");
                    System.out.println("7. Create Reservation");
                    System.out.println("8. Update Reservation");
                    System.out.println("9. Cancel Reservation");
                    System.out.println("10. Show Reports");
                    System.out.println("11. Show Vehicle Utilization");
                    System.out.println("12. Exit");
                    System.out.println("__________________________________________________________");
                    System.out.println(" ");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            registerCustomer(scanner);
                            break;
                        case 2:
                            updateCustomerDetails(scanner);
                            break;
                        case 3:
                            removeUser(scanner);
                            break;
                        case 4:
                            addVehicle(scanner);
                            break;
                        case 5:
                            updateVehicle(scanner);
                            break;
                        case 6:
                            removeVehicle(scanner);
                            break;
                        case 7:
                            createReservation(scanner);
                            break;
                        case 8:
                            updateReservation(scanner);
                            break;
                        case 9:
                            cancelReservation(scanner);
                            break;
                        case 10:
                            generateReports();
                            break;
                        case 11:
                            generateVehicleUtilizationReport();
                            break;
                        case 12:
                            exit = true;
                            System.out.println("Exiting the Car Rental System. Goodbye!");
                            System.out.println(" ");
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a number from 1 to 12.");
                            System.out.println(" ");
                    }
                }
            }

            scanner.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            if (connection != null) 
            {
                try 
                {
                    connection.close();
                } 
                catch (SQLException e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void generateReports() 
    {
        
        try 
        {
            int totalAdmins = adminDAO.getTotalAdmins();
            int totalCustomers = customerDAO.getTotalCustomers();
            int totalVehicles = vehicleDAO.getTotalVehicles();
            int totalReservations = reserveDAO.getTotalReservations();

            System.out.println("General Reports:");
            System.out.println("Total Admins: " + totalAdmins);
            System.out.println("Total Customers: " + totalCustomers);
            System.out.println("Total Vehicles: " + totalVehicles);
            System.out.println("Total Reservations: " + totalReservations);
            System.out.println(" ");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void generateVehicleUtilizationReport() {
        try {
            List<Vehicle> vehicles = vehicleDAO.getAllVehicles();

            System.out.println("Vehicle Utilization Report:");
            for (Vehicle vehicle : vehicles) {
                System.out.println("Vehicle ID: " + vehicle.getVehicleID());
                System.out.println("Model: " + vehicle.getModel());
                System.out.println("Make: " + vehicle.getMake());
                System.out.println("Year: " + vehicle.getYear());
                System.out.println("Color: " + vehicle.getColor());
                System.out.println("Registration Number: " + vehicle.getRegistrationNumber());
                System.out.println("Availability: " + (vehicle.isAvailability() ? "Available" : "Not Available"));
                System.out.println("Daily Rate: " + vehicle.getDailyRate());
                System.out.println(" ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cancelReservation(Scanner scanner) {
		
    	System.out.println("Enter reservation ID to remove:");
        int reservationID = scanner.nextInt();
        scanner.nextLine(); 

        Reservation reservation = reserveDAO.getReservationById(reservationID);
        if (reservation != null) {
            
        	reserveDAO.cancelReservation(reservationID);
            System.out.println("Reservation canclled successfully.");
            System.out.println(" ");
        } else {
            System.out.println("Reservation ID not found.");
            System.out.println(" ");
        }
		
	}

	private static void updateReservation(Scanner scanner) {
		// TODO Auto-generated method stub
    	System.out.println("Enter reservation ID to update:");
        int reservationID = scanner.nextInt();
        scanner.nextLine(); 

        Reservation reservation = reserveDAO.getReservationById(reservationID);

        if (reservation != null) {
        	
            
        	System.out.print("Customer ID: ");
            int customerID = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Vehicle ID: ");
            int vehicleID = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Start Date: ");
            String startDate = scanner.nextLine();

            System.out.print("End Date: ");
            String endDate = scanner.nextLine();

            System.out.print("Total Cost: ");
            double totalcost = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Status: ");
            String status = scanner.nextLine(); 

            reservation.setCustomerID(customerID);
            reservation.setVehicleID(vehicleID);
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setTotalCost(totalcost);
            reservation.setStatus(status);

            reserveDAO.updateReservation(reservation);
            System.out.println("Reservation updated successfully.");
            System.out.println(" ");
        } else {
            System.out.println("Reservation ID not found.");
            System.out.println(" ");
        }
		
	}

	private static void createReservation(Scanner scanner) {
		// TODO Auto-generated method stub
    	System.out.println("Enter reservation details:");
        
        System.out.print("Customer ID: ");
        int customerID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Vehicle ID: ");
        int vehicleID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Start Date: ");
        String startDate = scanner.nextLine();

        System.out.print("End Date: ");
        String endDate = scanner.nextLine();

        System.out.print("Total Cost: ");
        double totalcost = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Status: ");
        String status = scanner.nextLine(); 

        Reservation reservation = new Reservation();
        reservation.setCustomerID(customerID);
        reservation.setVehicleID(vehicleID);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotalCost(totalcost);
        reservation.setStatus(status);
        
        
        reserveDAO.createReservation(reservation);
        System.out.println("Reservation Created successfully.");
        System.out.println(" ");
		
	}

	private static void removeVehicle(Scanner scanner) {
        System.out.println("Enter vehicle ID to remove:");
        int vehicleId = scanner.nextInt();

        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
        if (vehicle != null) {
            
            vehicleDAO.removeVehicle(vehicleId);
            System.out.println("Vehicle removed successfully.");
            System.out.println(" ");
        } else {
            System.out.println("Vehicle not found.");
            System.out.println(" ");
        }
    }


	private static void updateVehicle(Scanner scanner) {
        System.out.println("Enter vehicle ID to update:");
        int vehicleId = scanner.nextInt();
        scanner.nextLine(); 

        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

        if (vehicle != null) {
        	
            
            System.out.print("Model: ");
            String model = scanner.nextLine();

            System.out.print("Make: ");
            String make = scanner.nextLine();

            System.out.print("Year: ");
            int year = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Colour: ");
            String colour = scanner.nextLine();

            System.out.print("Registration Number: ");
            String registrationNumber = scanner.nextLine();

            System.out.print("Availability (true/false): ");
            boolean availability = scanner.nextBoolean();

            System.out.print("Daily Rate: ");
            double dailyRate = scanner.nextDouble(); 

            vehicle.setModel(model);
            vehicle.setMake(make);
            vehicle.setYear(year);
            vehicle.setColor(colour);
            vehicle.setRegistrationNumber(registrationNumber);
            vehicle.setAvailability(availability);
            vehicle.setDailyRate(dailyRate);

            vehicleDAO.updateVehicle(vehicle);
            System.out.println("Vehicle updated successfully.");
            System.out.println(" ");
        } else {
            System.out.println("Vehicle not found.");
            System.out.println(" ");
        }
    }


    private static void addVehicle(Scanner scanner) {
        System.out.println("Enter vehicle details:");
 
        System.out.print("Model: ");
        String model = scanner.nextLine();
        scanner.nextLine();

        System.out.print("Make: ");
        String make = scanner.nextLine();

        System.out.print("Year: ");
        int year = scanner.nextInt();

        System.out.print("Colour: ");
        String colour = scanner.nextLine();
        scanner.nextLine();

        System.out.print("RegistrationNumber: ");
        String registrationNumber = scanner.nextLine();

        System.out.print("Availability (true/false): ");
        boolean availability = scanner.nextBoolean();

        System.out.print("DailyRate: ");
        double dailyRate = scanner.nextDouble();

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(model);
        vehicle.setMake(make);
        vehicle.setYear(year);
        vehicle.setColor(colour);
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setAvailability(availability);
        vehicle.setDailyRate(dailyRate);

        vehicleDAO.addVehicle(vehicle);
        System.out.println("Vehicle addded successfully.");
        System.out.println(" ");
        
    }


	private static void removeUser(Scanner scanner) {
		// TODO Auto-generated method stub
    	System.out.println("Enter username of the customer to remove:");
        String username = scanner.nextLine();

        Customer customer = customerDAO.getCustomerByUsername(username);
        if (customer != null) {

            customerDAO.deleteCustomer(customer.getCustomerID());
            System.out.println("Customer removed successfully.");
            System.out.println(" ");
        } else {
            System.out.println("Customer not found.");
            System.out.println(" ");
        }
		
	}

	private static void updateCustomerDetails(Scanner scanner) {
		// TODO Auto-generated method stub
    	System.out.println("Enter username of the customer to update:");
        String username = scanner.nextLine();
        

        Customer customer = customerDAO.getCustomerByUsername(username);
        if (customer != null) {
            System.out.println("Enter updated details:");
            
            System.out.print("FirstName: ");
            String firstName = scanner.nextLine();
            
            System.out.print("LastName: ");
            String lastName = scanner.nextLine();
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            System.out.print("PhoneNumber: ");
            String phoneNumber = scanner.nextLine();
            
            System.out.print("Address: ");
            String address = scanner.nextLine();
            
            System.out.print("UserName: ");
            String userName = scanner.nextLine();
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            System.out.print("RegistrationDate: ");
            String registrationDate = scanner.nextLine();
            
            
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            customer.setUsername(userName);
            customer.setPassword(password);
            customer.setRegistrationDate(registrationDate);
            

            customerDAO.updateCustomer(customer);
            System.out.println("Customer details updated successfully.");
            System.out.println(" ");
        } else {
            System.out.println("Customer not found.");
            System.out.println(" ");
        }
		
	}

	private static void registerCustomer(Scanner scanner) {
		// TODO Auto-generated method stub
    	System.out.println("Enter customer details:");
    	
        System.out.print("FirstName: ");
        String firstName = scanner.nextLine();
        
        System.out.print("LastName: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        
        System.out.print("PhoneNumber: ");
        String phoneNumber = scanner.nextLine();
        
        System.out.print("Address: ");
        String address = scanner.nextLine();
        
        System.out.print("UserName: ");
        String userName = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        System.out.print("RegistrationDate: ");
        String registrationDate = scanner.nextLine();
        

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customer.setUsername(userName);
        customer.setPassword(password);
        customer.setRegistrationDate(registrationDate);
        

        customerDAO.registerCustomer(customer);
        System.out.println("Customer registered successfully.");
        System.out.println(" ");
		
	}

	private static void authenticateUser(Scanner scanner) throws AuthenticationException {
	    System.out.println("Login with your Username and Password");
	    System.out.println("Enter username:");
	    String username = scanner.nextLine();
	    
	    System.out.println("Enter password:");
	    String password = scanner.nextLine();

	    Admin admin = adminDAO.getAdminByUsername(username);
	    if (admin == null || !admin.getPassword().equals(password)) 
	    {
	        throw new AuthenticationException("Invalid username or password.");
	    } 
	    else 
	    {
	        System.out.println("Authentication successful. Welcome, " + admin.getFirstName() + "!");
	        System.out.println("");
	    }
	}

}
