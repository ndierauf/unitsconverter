package com.dierauf.app.unitsconverter.ui.model;

public class MathResult {

	private final double multiplicationFactor;
	private final String units;

	public MathResult(final double result, final String units) {
		this.multiplicationFactor = result;
		this.units = units;
	}

	public double getMultiplicationFactor() {
		return this.multiplicationFactor;
	}

	public String getUnits() {
		return this.units;
	}
}
