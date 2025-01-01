package com.retailer.app.service;

import com.retailer.app.dto.RewardsDTO;
import com.retailer.app.exception.RewardCalculationException;
import com.retailer.app.model.RewardsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardsServiceTest {

	private RewardsService rewardsService;

	@BeforeEach
	void setUp() {
		rewardsService = new RewardsService();
	}

	@Test
	void calculateRewardsForLastThreeMonths_success() {

		List<RewardsModel> mockData = rewardsService.generateMockData();

		List<RewardsDTO> rewards = rewardsService.calculateRewardsForLastThreeMonths(mockData);

		assertNotNull(rewards);
		assertEquals(2, rewards.size());
		assertEquals(1L, rewards.get(0).getCustomerId());
		assertEquals(250.0, rewards.get(0).getTotalAmounts());
	}

	@Test
	void calculateRewardsForLastThreeMonths_noTransactions() {

		List<RewardsModel> emptyData = List.of();

		RewardCalculationException exception = assertThrows(RewardCalculationException.class,
				() -> rewardsService.calculateRewardsForLastThreeMonths(emptyData));
		assertEquals("No transactions found for the last three months.", exception.getMessage());
	}

	@Test
	void calculateRewardsForCustomMonths_success() {

		String startDate = "2024-01-01";
		String endDate = "2024-01-31";
		List<RewardsModel> mockData = rewardsService.generateMockData();

		List<RewardsDTO> rewards = rewardsService.calculateRewardsForCustomMonths(startDate, endDate, mockData);

		assertNotNull(rewards);
		assertEquals(2, rewards.size());
	}

	@Test
	void calculateRewardsForCustomMonths_invalidDateRange() {

		String startDate = "2024-02-01";
		String endDate = "2024-01-01";
		List<RewardsModel> mockData = rewardsService.generateMockData();

		RewardCalculationException exception = assertThrows(RewardCalculationException.class,
				() -> rewardsService.calculateRewardsForCustomMonths(startDate, endDate, mockData));
		assertEquals("Start date cannot be after end date.", exception.getMessage());
	}
}
