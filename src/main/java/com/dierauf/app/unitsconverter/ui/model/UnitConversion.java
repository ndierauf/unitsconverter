package com.dierauf.app.unitsconverter.ui.model;

public class UnitConversion {

	private final String name;
	private final String symbol;
	private final String type;
	private final double conversionFactor;
	private final String conversionUnit;

	public UnitConversion(final String[] conversion) {
		this(conversion[0], conversion[1], conversion[2], conversion[3], conversion[4]);
	}

	public UnitConversion(final String name, final String symbol, final String type, final String conversionFactor,
	    final String conversionUnit) {
		this.name = name;
		this.symbol = symbol;
		this.type = type;
		this.conversionFactor = this.setConversionFactor(conversionFactor);
		this.conversionUnit = conversionUnit;
	}

	private double setConversionFactor(final String conversionFactor) {
		switch (conversionFactor) {
		case "60":
			return 60d;
		case "3600":
			return 3600d;
		case "86400":
			return 86400d;
		case "(π / 180)":
			return (Math.PI / 180);
		case "(π / 10800)":
			return (Math.PI / 10800);
		case "(π / 648000)":
			return (Math.PI / 648000);
		case "10000":
			return 10000d;
		case "0.001":
			return 0.001d;
		case "10^3":
			return Math.pow(10, 3);
		default:
			throw new RuntimeException("Unknown conversionFactor: '" + conversionFactor + "'. ");
		}
	}

	public String getName() {
		return this.name;
	}
	public String getSymbol() {
		return this.symbol;
	}
	public String getType() {
		return this.type;
	}
	public double getConversionFactor() {
		return this.conversionFactor;
	}
	public String getConversionUnit() {
		return this.conversionUnit;
	}

}
