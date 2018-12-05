package com.dierauf.app.unitsconverter;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.Assert;

import com.dierauf.app.unitsconverter.ui.model.MathOperation;
import com.dierauf.app.unitsconverter.ui.model.MathResult;


public class MathOperationsTest extends CommonTests {


	@Override
	protected void runPositiveTest(final String mathOperationString, final double expectedConversionFactor,
	    final String expectedConversionUnit) {
		MathOperation mathOperation = MathOperation.createMathOperation(mathOperationString);
		MathResult mathResult = mathOperation.calculate();
		assertNotNull(mathResult);
		double multiplicationFactor = mathResult.getMultiplicationFactor();
		double value = new BigDecimal(multiplicationFactor).round(new MathContext(14)).doubleValue();
		Assert.assertEquals(expectedConversionFactor, value, .00000000000001d);
		String units = mathResult.getUnits();
		assertNotNull(units);
		Assert.assertEquals(expectedConversionUnit, units);
	}


	@Override
	protected void runNegativeTest(final String name, final String expected) {
		assertThrows(RuntimeException.class, () -> {MathOperation.createMathOperation(name);});
	}


	@Override
	protected void runNegativeTest_Calculate(final String name, final String expected) {
		assertThrows(RuntimeException.class, () -> {MathOperation.createMathOperation(name).calculate();});
	}


}
