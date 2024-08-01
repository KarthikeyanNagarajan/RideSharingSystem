package com.karthik.RideSharingSystem.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import com.karthik.RideSharingSystem.model.Customer;
import com.karthik.RideSharingSystem.model.Driver;
import com.karthik.RideSharingSystem.model.DriverStatus;
import com.karthik.RideSharingSystem.model.Location;
import com.karthik.RideSharingSystem.model.Ride;
import com.karthik.RideSharingSystem.model.RideStatus;

public class RideSharingService
{
	private static RideSharingService instance;
	private final Map<Integer, Customer> passengers;
	private final Map<Integer, Driver> drivers;
	private final Map<Integer, Ride> rides;
	private final Queue<Ride> requestedRides;

	private RideSharingService()
	{
		passengers = new HashMap<>();
		drivers = new HashMap<>();
		rides = new HashMap<>();
		requestedRides = new LinkedList<>();
	}

	public static synchronized RideSharingService getInstance()
	{
		if (instance == null)
		{
			instance = new RideSharingService();
		}
		return instance;
	}

	public void addPassenger(Customer passenger)
	{
		passengers.put(passenger.getId(), passenger);
	}

	public void addDriver(Driver driver)
	{
		drivers.put(driver.getId(), driver);
	}

	public void requestRide(Customer passenger, Location destination)
	{
		Ride ride = new Ride(generateRideId(), passenger, null, passenger.getLocation(), destination,
				RideStatus.REQUESTED, 0.0);
		requestedRides.offer(ride);
		findDrivers(ride);
	}

	private void findDrivers(Ride ride)
	{
		for (Driver driver : drivers.values())
		{
			if (driver.getStatus() == DriverStatus.AVAILABLE)
			{
				double distance = calculateDistance(driver.getLocation(), ride.getSource());
				if (distance <= 5.0)
				{ // Notify drivers within 5 km radius
					// Send notification to the driver
					System.out
							.println("Notifying driver: " + driver.getName() + " about ride request: " + ride.getId());
				}
			}
		}
	}

	public double calculateDistance(Location source, Location Destination)
	{
		// For simplicity, distance is 10 km
		return 10.0;
	}

	public void acceptRide(Driver driver, Ride ride)
	{
		if (ride.getStatus() == RideStatus.REQUESTED)
		{
			ride.setDriver(driver);
			ride.setStatus(RideStatus.ACCEPTED);
			driver.setStatus(DriverStatus.BUSY);
			notifyPassenger(ride);
		}
	}

	public void startRide(Ride ride)
	{
		if (ride.getStatus() == RideStatus.ACCEPTED)
		{
			ride.setStatus(RideStatus.INPROGRESS);
			notifyPassenger(ride);
		}
	}

	public void completeRide(Ride ride)
	{
		if (ride.getStatus() == RideStatus.INPROGRESS)
		{
			ride.setStatus(RideStatus.COMPLETED);
			ride.getDriver().setStatus(DriverStatus.AVAILABLE);
			double fare = calculateFare(ride);
			ride.setFare(fare);
			processPayment(ride, fare);
			notifyPassenger(ride);
			notifyDriver(ride);
		}
	}

	public void cancelRide(Ride ride)
	{
		if (ride.getStatus() == RideStatus.REQUESTED || ride.getStatus() == RideStatus.ACCEPTED)
		{
			ride.setStatus(RideStatus.CANCELLED);
			if (ride.getDriver() != null)
			{
				ride.getDriver().setStatus(DriverStatus.AVAILABLE);
			}
			notifyPassenger(ride);
			notifyDriver(ride);
		}
	}

	private void notifyPassenger(Ride ride)
	{
		// Notify the passenger about ride status updates
		// ...
		Customer passenger = ride.getPassenger();
		String message = "";
		switch (ride.getStatus())
		{
		case ACCEPTED:
			message = "Your ride has been accepted by driver: " + ride.getDriver().getName();
			break;
		case INPROGRESS:
			message = "Your ride is in progress";
			break;
		case COMPLETED:
			message = "Your ride has been completed. Fare: $" + ride.getFare();
			break;
		case CANCELLED:
			message = "Your ride has been cancelled";
			break;
		default:
			break;
		}
		// Send notification to the passenger
		System.out.println("Notifying passenger: " + passenger.getName() + " - " + message);
	}

	private void notifyDriver(Ride ride)
	{
		Driver driver = ride.getDriver();
		if (driver != null)
		{
			String message = "";
			switch (ride.getStatus())
			{
			case COMPLETED:
				message = "Ride completed. Fare: $" + ride.getFare();
				break;
			case CANCELLED:
				message = "Ride cancelled by passenger";
				break;
			default:
				break;
			}
			// Send notification to the driver
			System.out.println("Notifying driver: " + driver.getName() + " - " + message);
		}
	}

	private double calculateFare(Ride ride)
	{
		double baseFare = 2.0;
		double perKmFare = 1.5;
		double perMinuteFare = 0.25;

		double distance = calculateDistance(ride.getSource(), ride.getDestination());
		double duration = calculateDuration(ride.getSource(), ride.getDestination());

		double fare = baseFare + (distance * perKmFare) + (duration * perMinuteFare);
		return Math.round(fare * 100.0) / 100.0; // Round to 2 decimal places
	}

	private double calculateDuration(Location source, Location destination)
	{
		// Calculate the estimated duration between two locations based on distance and
		// average speed
		// For simplicity, let's assume an average speed of 30 km/h
		double distance = calculateDistance(source, destination);
		return (distance / 30) * 60; // Convert hours to minutes
	}

	private void processPayment(Ride ride, double amount)
	{
		// Process the payment for the ride
		// ...
	}

	private int generateRideId()
	{
		return (int) (System.currentTimeMillis() / 1000);
	}

	public Map<Integer, Ride> getRides()
	{
		return rides;
	}

	public Queue<Ride> getRequestedRides()
	{
		return requestedRides;
	}
}
