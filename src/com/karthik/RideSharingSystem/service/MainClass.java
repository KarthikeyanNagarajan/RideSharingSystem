package com.karthik.RideSharingSystem.service;

import com.karthik.RideSharingSystem.model.Customer;
import com.karthik.RideSharingSystem.model.Driver;
import com.karthik.RideSharingSystem.model.DriverStatus;
import com.karthik.RideSharingSystem.model.Location;
import com.karthik.RideSharingSystem.model.Ride;

public class MainClass
{

	public static void main(String[] args)
	{
		RideSharingService rideSharingService = RideSharingService.getInstance();

		// Create passengers
		Customer passenger1 = new Customer(1, "CustKarthik-1", "1234567890", new Location(37.7749, -122.4194));
		Customer passenger2 = new Customer(2, "CustKarthik-2", "9876543210", new Location(37.7860, -122.4070));
		rideSharingService.addPassenger(passenger1);
		rideSharingService.addPassenger(passenger2);

		// Create drivers
		Driver driver1 = new Driver(1, "DriveKarthik-1", "4567890123", "ABC123", new Location(37.7749, -122.4194),
				DriverStatus.AVAILABLE);
		Driver driver2 = new Driver(2, "DriveKarthik-2", "7890123456", "XYZ789", new Location(37.7860, -122.4070),
				DriverStatus.AVAILABLE);
		rideSharingService.addDriver(driver1);
		rideSharingService.addDriver(driver2);

		// Passenger 1 requests a ride
		rideSharingService.requestRide(passenger1, new Location(37.7887, -122.4098));

		// Driver 1 accepts the ride
		Ride ride1 = rideSharingService.getRequestedRides().poll();
		rideSharingService.acceptRide(driver1, ride1);

		// Start the ride
		rideSharingService.startRide(ride1);

		// Complete the ride
		rideSharingService.completeRide(ride1);

		// Passenger 2 requests a ride
		rideSharingService.requestRide(passenger2, new Location(37.7749, -122.4194));

		// Driver 2 accepts the ride
		Ride ride2 = rideSharingService.getRequestedRides().poll();
		rideSharingService.acceptRide(driver2, ride2);

		// Passenger 2 cancels the ride
		rideSharingService.cancelRide(ride2);
	}

}
