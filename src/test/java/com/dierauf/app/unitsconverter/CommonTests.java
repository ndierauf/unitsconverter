package com.dierauf.app.unitsconverter;

import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.jupiter.api.Test;

import com.dierauf.app.unitsconverter.ui.model.UnitConversion;
import com.dierauf.app.unitsconverter.ui.model.UnitConversions;

// Used for both unit and funtional testing.
public abstract class CommonTests {


	@Test
	void runBasics() {
		this.runPositiveTest(new BasicUnit("minute"));
		this.runPositiveTest(new BasicUnit("hour"));
		this.runPositiveTest(new BasicUnit("h"));
		this.runPositiveTest(new BasicUnit("day"));
		this.runPositiveTest(new BasicUnit("d"));
		this.runPositiveTest(new BasicUnit("degree"));
		this.runPositiveTest(new BasicUnit("°"));
		this.runPositiveTest(new BasicUnit("‘"));
		this.runPositiveTest(new BasicUnit("'"));
		this.runPositiveTest(new BasicUnit("“"));
		this.runPositiveTest(new BasicUnit("\""));
		this.runPositiveTest(new BasicUnit("hectare"));
		this.runPositiveTest(new BasicUnit("ha"));
		this.runPositiveTest(new BasicUnit("litre"));
		this.runPositiveTest(new BasicUnit("L"));
		this.runPositiveTest(new BasicUnit("tonne"));
		this.runPositiveTest(new BasicUnit("t"));
	}

	@Test
	void funWithParenthesis_HappyPaths() {
		double expectedConversionFactor = 0.00029088820866572d;
		String expectedConversionUnit = "rad/s";
		this.runPositiveTest("(min)", 60d, "s");
		this.runPositiveTest("degree/minute", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("(degree/minute)", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("((degree/minute))", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("((degree)/(minute))", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("((degree)/minute)", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("(degree/(minute))", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("degree/(minute)", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("(degree)/minute", expectedConversionFactor, expectedConversionUnit);
		this.runPositiveTest("(((degree/(minute))))", expectedConversionFactor, expectedConversionUnit);
	}


	@Test
	protected void generalTests() {
		// Division.
		this.runPositiveTest(new TestPacket("(degree)/(degree)", 1d, "rad/rad"));
		this.runPositiveTest(new TestPacket("minute/hour", .016666666666667d, "s/s"));
		this.runPositiveTest(new TestPacket("hour/minute", 60d, "s/s"));
		this.runPositiveTest(new TestPacket("degree/second", 3600d, "rad/rad"));
		this.runPositiveTest(new TestPacket("second/degree", .00027777777777778d, "rad/rad"));
		this.runPositiveTest(new TestPacket("degree/litre", 17.453292519943d, "rad/m^3"));
		this.runPositiveTest(new TestPacket("litre/degree", .057295779513082d, "m^3/rad"));
		this.runPositiveTest(new TestPacket("hectare/litre", 10000000d, "m^2/m^3"));
		this.runPositiveTest(new TestPacket("degree/litre", 17.453292519943d, "rad/m^3"));
		this.runPositiveTest(new TestPacket("'/degree", .016666666666667d, "rad/rad"));

		// Multiplication.
		this.runPositiveTest(new TestPacket("'*degree", .0000050769569964451d, "rad rad"));
		this.runPositiveTest(new TestPacket("'*degree*'*degree*'*degree", .00000000000000013086106619144d, "rad rad rad rad rad rad"));

		// Division and multiplication.
		this.runPositiveTest(new TestPacket("(minute*hour)/(minute*hour)", 1d, "s s/s s"));
		this.runPositiveTest(new TestPacket("(hour/minute)/(hour/minute)", 1d, "s s/s s"));
		this.runPositiveTest(new TestPacket("(hour/minute)/(minute/hour)", 3600d, "s s/s s"));
		this.runPositiveTest(new TestPacket("('/')*('/')*('/')", 1.0d, "rad rad rad/rad rad rad"));
		this.runPositiveTest(new TestPacket("(minute/min)*(hour/h)*(day/d)*(degree/°)*('/‘)*(\"/second)*(hectare/ha)*(litre/L)*(tonne/t)",
				1.0d, "s s s rad rad rad m^2 m^3 kg/s s s rad rad rad m^2 m^3 kg"));
	}

	@Test
	void funWithParenthesis_UnhappyPaths() {
		this.runNegativeTest("min)", "Unbalanced parenthesis");
		this.runNegativeTest("(degree/minute", "Unbalanced parenthesis");
		this.runNegativeTest("", "");
		this.runNegativeTest("()", "");
		this.runNegativeTest("(degree", "");
		this.runNegativeTest("degree)", "");
		this.runNegativeTest("degree/degree)", "");
		this.runNegativeTest_Calculate("degree()", "");
		this.runNegativeTest_Calculate("degree + degree", "");
	}


	protected abstract void runNegativeTest_Calculate(String name, String expected);

	private void runPositiveTest(final TestPacket testPacket) {
		this.runPositiveTest(testPacket.name, testPacket.expectedConversionFactor, testPacket.expectedConversionUnit);
	}

	protected void runPositiveTest(final BasicUnit basicUnit) {
		this.runPositiveTest(basicUnit.name_or_symbol, basicUnit.expectedConversionFactor, basicUnit.expectedConversionUnit);
	}


	protected abstract void runNegativeTest(final String name, final String expected);
	protected abstract void runPositiveTest(final String name, final double expectedConversionFactor,
	    final String expectedConversionUnit);


	class BasicUnit {
		String name_or_symbol;
		double expectedConversionFactor;
		String expectedConversionUnit;
		BasicUnit(final String name_or_symbol) {
			this.name_or_symbol = name_or_symbol;
			UnitConversion unitConversion = UnitConversions.getInstance().getUnitConversion(name_or_symbol);
			double multiplicationFactor = unitConversion.getConversionFactor();
			this.expectedConversionFactor = new BigDecimal(multiplicationFactor).round(new MathContext(14)).doubleValue();
			this.expectedConversionUnit = unitConversion.getConversionUnit();
		}
	}

	class TestPacket {
		String name;
		double expectedConversionFactor;
		String expectedConversionUnit;
		TestPacket(final String name, final double ecf, final String ecu) {
			this.name = name;
			this.expectedConversionFactor = ecf;
			this.expectedConversionUnit = ecu;
		}
	}

}
