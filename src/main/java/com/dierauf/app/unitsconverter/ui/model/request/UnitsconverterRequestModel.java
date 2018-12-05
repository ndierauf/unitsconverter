package com.dierauf.app.unitsconverter.ui.model.request;

import java.math.BigDecimal;
import java.math.MathContext;

import com.dierauf.app.unitsconverter.ui.model.MathOperation;
import com.dierauf.app.unitsconverter.ui.model.MathResult;
import com.dierauf.app.unitsconverter.ui.model.response.UnitsconverterResponseModel;

public class UnitsconverterRequestModel {

	private final String units;

	public UnitsconverterRequestModel(final String units) {
		this.units = units;
	}

	public String getUnits() {
		return this.units;
	}

	public UnitsconverterResponseModel getUnitsRest() {
		String unitsNormalized = this.units.replaceAll("\\s", ""); // No need for space characters. Will make parsing easier.
		MathOperation mathOperation = MathOperation.createMathOperation(unitsNormalized);
		MathResult results = mathOperation.calculate();
		Double multiplicationFactor = results.getMultiplicationFactor();
		double value = new BigDecimal(multiplicationFactor).round(new MathContext(14)).doubleValue();
		return new UnitsconverterResponseModel(value, results.getUnits());
	}

}
