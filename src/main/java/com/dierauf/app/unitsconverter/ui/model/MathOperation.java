package com.dierauf.app.unitsconverter.ui.model;

public abstract class MathOperation {
	protected MathOperation leftMathOperation;
	protected MathOperation rightMathOperation;
	protected final String mathOperationString;
	protected String mathOperator;


	// Factory pattern for creating appropriate sub-class (with or without parenthesis).
	public static MathOperation createMathOperation(final String mathOperationString) {

		if (mathOperationString.length() == 0)
			throw new RuntimeException("No units provided. Please provide units information, ie: /units/si?units=degree");

		verifyBalancedParenthesis(mathOperationString);
		MathOperation mathOperation = (mathOperationString.startsWith("("))
		    ? new MathOperation_WithParenthesis(mathOperationString)
		    : new MathOperation_NoParenthesis(mathOperationString);

		return mathOperation;
	}


	protected MathOperation(final String mathOperationString) {
		this.mathOperationString = mathOperationString;
		this.setLeftRight_MathOperations();
	}


	protected abstract void setLeftRight_MathOperations();
	protected abstract boolean isSingleOperation(final int index);
	protected abstract String extractMathOperationString_Right(final int index);
	protected abstract String extractMathOperationString_Left(final int index);
	protected abstract String extractMathOperator(final int index);


	public MathResult calculate() {

		if (this.leftMathOperation == null)
			// No left or right nodes, thus we are at a leaf node. End recursion. Return result for this node.
			return this.createMathResult_ThisNode();

		// Recursively calculate results from left and right nodes.
		MathResult leftMathResult = this.leftMathOperation.calculate();
		MathResult rightMathResult = (this.rightMathOperation == null) ? null : this.rightMathOperation.calculate();

		// For this particular node ...
		return this.createMathResult_ThisNode(leftMathResult, rightMathResult);
	}


	private MathResult createMathResult_ThisNode() {
		UnitConversion unitConversion = UnitConversions.getInstance().getUnitConversion(this.mathOperationString);
		MathResult mathResult = new MathResult(unitConversion.getConversionFactor(), unitConversion.getConversionUnit());
		return mathResult;
	}


	private MathResult createMathResult_ThisNode(final MathResult leftMathResult, final MathResult rightMathResult) {

		if (rightMathResult == null)
			// No right node -> left is a single node. Return it.
			return leftMathResult;

		String units = this.getUnits(leftMathResult.getUnits(), rightMathResult.getUnits());
		if (this.mathOperator.equals("*"))
			return new MathResult(leftMathResult.getMultiplicationFactor() * rightMathResult.getMultiplicationFactor(), units);
		else
			return new MathResult(leftMathResult.getMultiplicationFactor() / rightMathResult.getMultiplicationFactor(), units);
	}

	private String getUnits(final String leftUnits, final String rightUnits) {
		return (this.mathOperator.equals("*")) ?
				// Multiplication:
				this.getMultiplicationUnits(leftUnits, rightUnits) :
				// Division:
		    this.getDivisionUnits(leftUnits, rightUnits);
	}


	private String getMultiplicationUnits(final String leftUnits, final String rightUnits) {
		String units = "";
		if (leftUnits.contains("/") && rightUnits.contains("/")) {
			String[] lUnits = leftUnits.split("/");
			String[] rUnits = rightUnits.split("/");
			units = lUnits[0] + " " + rUnits[0] + "/" + lUnits[1] + " " + rUnits[1];
		}
		else if (leftUnits.contains("/") && !rightUnits.contains("/")) {
			String[] lUnits = leftUnits.split("/");
			units = lUnits[0] + " " + rightUnits + "/" + lUnits[1];
		}
		else if (!leftUnits.contains("/") && rightUnits.contains("/")) {
			String[] rUnits = rightUnits.split("/");
			units = leftUnits + " " + rUnits[0] + "/" + rUnits[1];
		}
		else {
			units = leftUnits + " " + rightUnits;
		}
		return units.trim();
	}


	private String getDivisionUnits(final String leftUnits, final String rightUnits) {
		String units = "";
		if (leftUnits.contains("/") && rightUnits.contains("/")) {
			String[] lUnits = leftUnits.split("/");
			String[] rUnits = rightUnits.split("/");
			units = lUnits[0] + " " + rUnits[1] + "/" + lUnits[1] + " " + rUnits[0];
		}
		else if (leftUnits.contains("/") && !rightUnits.contains("/")) {
			String[] lUnits = leftUnits.split("/");
			units = lUnits[0] + "/" + lUnits[1] + " " + rightUnits;
		}
		else if (!leftUnits.contains("/") && rightUnits.contains("/")) {
			String[] rUnits = rightUnits.split("/");
			units = leftUnits + " " + rUnits[1] + "/" + rUnits[0];
		}
		else {
			units = leftUnits + "/" + rightUnits;
		}
		return units.trim();
	}


	protected String extractMathOperator(final int beginIndex, final int endIndex) {
		return this.mathOperationString.substring(beginIndex, endIndex);
	}

	protected String extractMathOperationString(final int beginIndex) {
		return this.mathOperationString.substring(beginIndex);
	}

	protected String extractMathOperationString(final int beginIndex, final int endIndex) {
		return this.mathOperationString.substring(beginIndex, endIndex);
	}

	protected int getIndexOfMathOperator() {
		int indexOfMult = this.mathOperationString.indexOf('*');
		int indexOfDivi = this.mathOperationString.indexOf("/");
		if (this.isSingleOperation(indexOfMult) && this.isSingleOperation(indexOfDivi))
			return -1;
		if (this.isSingleOperation(indexOfMult))
			return indexOfDivi;
		if (this.isSingleOperation(indexOfDivi))
			return indexOfMult;
		return Math.min(indexOfMult, indexOfDivi);
	}


	private static void verifyBalancedParenthesis(final String mathOperationString) {
		int countOfParenthesis = 0;
		int index = 0;
		while (index < mathOperationString.length()) {
			char charAt = mathOperationString.charAt(index++);
			if (charAt == '(')
				countOfParenthesis++;
			else if (charAt == ')')
				countOfParenthesis--;
		}
		if (countOfParenthesis != 0)
			throw new RuntimeException("Unbalanced parenthesis found in operation: '" + mathOperationString + "'. ");
	}


}
