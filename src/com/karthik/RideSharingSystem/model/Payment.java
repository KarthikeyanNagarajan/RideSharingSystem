package com.karthik.RideSharingSystem.model;

public class Payment
{
	private int id;
	private double amount;
	private Ride ride;
	private PaymentStatus paymentStatus;

	public Payment(int id, double amount, Ride ride, PaymentStatus paymentStatus)
	{
		this.id = id;
		this.amount = amount;
		this.ride = ride;
		this.paymentStatus = paymentStatus;
	}

	public int getId()
	{
		return id;
	}

	public double getAmount()
	{
		return amount;
	}

	public Ride getRide()
	{
		return ride;
	}

	public PaymentStatus getPaymentStatus()
	{
		return paymentStatus;
	}
}
