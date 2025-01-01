package com.retailer.app.service;

import org.springframework.stereotype.Service;
import com.retailer.app.dto.*;
import com.retailer.app.model.*;
import com.retailer.app.exception.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {

	private String getMonth(LocalDate date) {
		return date.getMonth().toString();
	}

	public List<RewardsDTO> calculateRewards(List<RewardsModel> transactions) {
		Map<Long, List<RewardsModel>> transactionsByCustomer = transactions.stream()
				.collect(Collectors.groupingBy(RewardsModel::getCustomerId));

		List<RewardsDTO> rewardsList = new ArrayList<>();

		for (Map.Entry<Long, List<RewardsModel>> entry : transactionsByCustomer.entrySet()) {
			Long customerId = entry.getKey();
			List<RewardsModel> customerTransactions = entry.getValue();

			RewardsDTO rewards = new RewardsDTO();
			rewards.setCustomerId(customerId);
			rewards.setCustomerName(customerTransactions.get(0).getCustomerName());

			Map<String, MonthlyPointsDTO> monthlyData = new HashMap<>();
			int totalPoints = 0;
			double totalAmounts = 0;

			for (RewardsModel transaction : customerTransactions) {
				double amount = transaction.getAmount();
				int points = calculatePoints(amount);

				totalPoints += points;
				totalAmounts += amount;

				String month = getMonth(transaction.getTransactionDate());

				MonthlyPointsDTO monthlyPoints = monthlyData.getOrDefault(month, new MonthlyPointsDTO());
				monthlyPoints.setMonth(month);
				monthlyPoints.setPoints(monthlyPoints.getPoints() + points);
				monthlyPoints.setAmount(monthlyPoints.getAmount() + amount);

				monthlyData.put(month, monthlyPoints);
			}

			rewards.setMonthlyPoints(new ArrayList<>(monthlyData.values()));
			rewards.setTotalPoints(totalPoints);
			rewards.setTotalAmounts(totalAmounts);

			rewardsList.add(rewards);
		}

		return rewardsList;
	}

	public List<RewardsDTO> calculateRewardsForLastThreeMonths(List<RewardsModel> allTransactions) {
		try {
			LocalDate endDate = LocalDate.now();
			LocalDate startDate = endDate.minusMonths(3);

			List<RewardsModel> filteredTransactions = allTransactions.stream().filter(transaction -> {
				LocalDate transactionDate = transaction.getTransactionDate();
				return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
			}).collect(Collectors.toList());

			if (filteredTransactions.isEmpty()) {
				throw new RewardCalculationException("No transactions found for the last three months.");
			}

			return calculateRewards(filteredTransactions);

		} catch (Exception e) {
			throw new RewardCalculationException("Error occurred while calculating rewards: " + e.getMessage(), e);
		}
	}

	public List<RewardsDTO> calculateRewardsForCustomMonths(String startDate, String endDate,
			List<RewardsModel> allTransactions) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate start = LocalDate.parse(startDate, formatter);
			LocalDate end = LocalDate.parse(endDate, formatter);

			if (start.isAfter(end)) {
				throw new RewardCalculationException("Start date cannot be after end date.");
			}

			List<RewardsModel> filteredTransactions = allTransactions.stream().filter(transaction -> {
				LocalDate transactionDate = transaction.getTransactionDate();
				return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
			}).collect(Collectors.toList());

			if (filteredTransactions.isEmpty()) {
				throw new RewardCalculationException("No transactions found for the specified date range.");
			}

			return calculateRewards(filteredTransactions);

		} catch (Exception e) {
			throw new RewardCalculationException("Error occurred while calculating rewards: " + e.getMessage(), e);
		}
	}

	private int calculatePoints(double amount) {
		if (amount <= 50) {
			return 0;
		} else if (amount <= 100) {
			return (int) (amount - 50);
		} else {
			return (int) ((amount - 100) * 2 + 50);
		}
	}

	public List<RewardsModel> generateMockData() {
		return Arrays.asList(new RewardsModel(1L, "John Doe", LocalDate.of(2024, 1, 15), 120.0),
				new RewardsModel(2L, "Jane Smith", LocalDate.of(2024, 1, 18), 50.0),
				new RewardsModel(2L, "Jane Smith", LocalDate.of(2024, 12, 2), 100.0),
				new RewardsModel(2L, "Jane Smith", LocalDate.of(2024, 12, 10), 150.0),
				new RewardsModel(1L, "John Doe", LocalDate.of(2024, 11, 30), 250.0));
	}

}
