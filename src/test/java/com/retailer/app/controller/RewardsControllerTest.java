package com.retailer.app.controller;

import com.retailer.app.dto.*;
import com.retailer.app.model.RewardsModel;
import com.retailer.app.exception.*;
import com.retailer.app.service.RewardsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RewardsControllerTest {

	private MockMvc mockMvc;

	@Mock
	private RewardsService rewardsService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		RewardsController rewardsController = new RewardsController(rewardsService);
		mockMvc = MockMvcBuilders.standaloneSetup(rewardsController).build();
	}

	@Test
	void getRewardsForLastThreeMonths_success() throws Exception {

		List<RewardsDTO> mockRewards = Arrays.asList(
				new RewardsDTO(1L, "John Doe",
						Arrays.asList(new MonthlyPointsDTO("JANUARY", 100, 120.0),
								new MonthlyPointsDTO("FEBRUARY", 150, 130.0)),
						30, 250.0),
				new RewardsDTO(2L, "Jane Smith", Arrays.asList(new MonthlyPointsDTO("JANUARY", 50, 50.0),
						new MonthlyPointsDTO("MARCH", 150, 100.0)), 20, 150.0));

		when(rewardsService.calculateRewardsForLastThreeMonths(anyList())).thenReturn(mockRewards);

		mockMvc.perform(get("/api/rewards/lastThreeMonths")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].customerId").value(1L)).andExpect(jsonPath("$[0].totalPoints").value(30));
	}

	@Test
	void getRewardsForLastThreeMonths_noTransactions() throws Exception {

		when(rewardsService.calculateRewardsForLastThreeMonths(anyList()))
				.thenThrow(new RewardCalculationException("No transactions found for the last three months."));

		mockMvc.perform(get("/api/rewards/lastThreeMonths")).andExpect(status().isBadRequest());
	}

	@Test
	void getRewardsForCustomMonths_success() throws Exception {

		List<RewardsDTO> mockRewards = Arrays.asList(new RewardsDTO(1L, "John Doe", null, 350, 250.0));

		when(rewardsService.calculateRewardsForCustomMonths(eq("2024-01-01"), eq("2024-01-31"), anyList()))
				.thenReturn(mockRewards);

		mockMvc.perform(get("/api/rewards/custom").param("startDate", "2024-01-01").param("endDate", "2024-01-31"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].customerName").value("John Doe"));
	}

	@Test
	void getRewardsForCustomMonths_invalidDateRange() throws Exception {

		when(rewardsService.calculateRewardsForCustomMonths(eq("2024-02-01"), eq("2024-01-01"), anyList()))
				.thenThrow(new RewardCalculationException("Start date cannot be after end date."));

		mockMvc.perform(get("/api/rewards/custom").param("startDate", "2024-02-01").param("endDate", "2024-01-01"))
				.andExpect(status().isBadRequest());
	}
}
