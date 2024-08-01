package com.karthik.RideSharingSystem.model;

public class Ride
{
	private int id;
	private Customer passenger;
	private Driver driver;
	private Location source;
	private Location destination;
	private RideStatus status;
	private double fare;

	public Ride(int id, Customer passenger, Driver driver, Location source, Location destination, RideStatus status,
			double fare)
	{
		this.id = id;
		this.passenger = passenger;
		this.driver = driver;
		this.source = source;
		this.destination = destination;
		this.status = status;
		this.fare = fare;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Customer getPassenger()
	{
		return passenger;
	}

	public void setPassenger(Customer passenger)
	{
		this.passenger = passenger;
	}

	public Driver getDriver()
	{
		return driver;
	}

	public void setDriver(Driver driver)
	{
		this.driver = driver;
	}

	public Location getSource()
	{
		return source;
	}

	public void setSource(Location source)
	{
		this.source = source;
	}

	public Location getDestination()
	{
		return destination;
	}

	public void setDestination(Location destination)
	{
		this.destination = destination;
	}

	public RideStatus getStatus()
	{
		return status;
	}

	public void setStatus(RideStatus status)
	{
		this.status = status;
	}

	public double getFare()
	{
		return fare;
	}

	public void setFare(double fare)
	{
		this.fare = fare;
	}
}
