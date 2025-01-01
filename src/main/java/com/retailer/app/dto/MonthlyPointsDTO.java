package com.retailer.app.dto;

public class MonthlyPointsDTO {
	private String month;
	private int points;
	private double amount;

	public MonthlyPointsDTO() {

	}

	public MonthlyPointsDTO(String month, int points, double amount) {
		this.month = month;
		this.points = points;
		this.amount = amount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}