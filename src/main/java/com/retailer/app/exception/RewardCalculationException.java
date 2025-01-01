package com.retailer.app.exception;

import java.io.Serializable;

public class RewardCalculationException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;

	public RewardCalculationException(String message) {
		super(message);
	}

	public RewardCalculationException(String message, Throwable cause) {
		super(message, cause);
	}
}
