package com.retailer.app.model;

import java.time.LocalDate;

public class RewardsModel {
	private Long customerId;
	private String customerName; 
	private LocalDate transactionDate;
	private double amount;

	public RewardsModel() {
	}

	public RewardsModel(Long customerId, String customerName, LocalDate transactionDate, double amount) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.transactionDate = transactionDate;
		this.amount = amount;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
