package com.dierauf.app.unitsconverter.ui.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnitsconverterResponseModel {

	private final String unitName;
	private final double multiplicationFactor;


	public UnitsconverterResponseModel(final double multiplicationFactor, final String unitName) {
		this.unitName = unitName;
		this.multiplicationFactor = multiplicationFactor;
	}

	@JsonProperty("unit_name")
	public String getUnits() {
		return this.unitName;
	}

	@JsonProperty("multiplication_factor")
	public double getMultiplicationFactor() {
		return this.multiplicationFactor;
	}

}
