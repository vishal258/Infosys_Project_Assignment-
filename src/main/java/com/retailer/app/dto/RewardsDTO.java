package com.retailer.app.dto;

import java.util.ArrayList;
import java.util.List;

public class RewardsDTO {
	private Long customerId;
	private String customerName;
	private List<MonthlyPointsDTO> monthlyPoints;
	private int totalPoints;
	private double totalAmounts;

	public RewardsDTO() {

	}

	public RewardsDTO(Long customerId, String customerName, List<MonthlyPointsDTO> monthlyPoints, int totalPoints,
			double totalAmounts) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.monthlyPoints = monthlyPoints != null ? monthlyPoints : new ArrayList<>();
		this.totalPoints = totalPoints;
		this.totalAmounts = totalAmounts;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<MonthlyPointsDTO> getMonthlyPoints() {
		return monthlyPoints;
	}

	public void setMonthlyPoints(List<MonthlyPointsDTO> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public double getTotalAmounts() {
		return totalAmounts;
	}

	public void setTotalAmounts(double totalAmounts) {
		this.totalAmounts = totalAmounts;
	}
}
