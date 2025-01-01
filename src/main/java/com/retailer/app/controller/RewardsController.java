package com.retailer.app.controller;

import com.retailer.app.dto.RewardsDTO;
import com.retailer.app.exception.RewardCalculationException;
import com.retailer.app.model.RewardsModel;
import com.retailer.app.service.RewardsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

	private final RewardsService rewardsService;

	public RewardsController(RewardsService rewardsService) {
		this.rewardsService = rewardsService;
	}

	@GetMapping("/lastThreeMonths")
	public ResponseEntity<List<RewardsDTO>> getRewardsForLastThreeMonths() {
		try {
			List<RewardsModel> transactions = rewardsService.generateMockData();
			List<RewardsDTO> rewards = rewardsService.calculateRewardsForLastThreeMonths(transactions);
			return ResponseEntity.ok(rewards);
		} catch (RewardCalculationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping("/custom")
	public ResponseEntity<List<RewardsDTO>> getRewardsForCustomMonths(@RequestParam String startDate,
			@RequestParam String endDate) {
		try {
			List<RewardsModel> transactions = rewardsService.generateMockData();
			List<RewardsDTO> rewards = rewardsService.calculateRewardsForCustomMonths(startDate, endDate, transactions);
			return ResponseEntity.ok(rewards);
		} catch (RewardCalculationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}
