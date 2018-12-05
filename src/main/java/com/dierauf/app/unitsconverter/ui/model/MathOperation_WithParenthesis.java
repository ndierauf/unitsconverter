package com.dierauf.app.unitsconverter.ui.model;

public class MathOperation_WithParenthesis extends MathOperation {

	public MathOperation_WithParenthesis(final String mathOperationString) {
		super(mathOperationString);
	}

	@Override
	protected void setLeftRight_MathOperations() {
		int indexOfClosingParenthesis = this.getIndexOfClosingParenthesis();
		String mathOperationString_Left = this.extractMathOperationString_Left(indexOfClosingParenthesis);
		this.leftMathOperation = createMathOperation(mathOperationString_Left);
		if (this.isSingleOperation(indexOfClosingParenthesis)) // ie, this.mathOperationString looks like: "(degree)"
			this.rightMathOperation = null;
		else { // ie, this.mathOperationString looks like: "(degree)/min"
			String mathOperationString_Right = this.extractMathOperationString_Right(indexOfClosingParenthesis);
			String mathOperator = this.extractMathOperator(indexOfClosingParenthesis);
			this.rightMathOperation = createMathOperation(mathOperationString_Right);
			this.mathOperator = mathOperator;
		}
	}

	//Returns true for "(degree)", false for "(degree)/min"
	@Override
	protected boolean isSingleOperation(final int indexOfClosingParenthesis) {
		return indexOfClosingParenthesis + 1 >= this.mathOperationString.length();
	}

	@Override
	protected String extractMathOperationString_Right(final int indexOfClosingParenthesis) {
		int beginIndex = indexOfClosingParenthesis + 2;
		return this.extractMathOperationString(beginIndex);
	}

	@Override
	protected String extractMathOperationString_Left(final int indexOfClosingParenthesis) {
		int beginIndex = 1;
		int endIndex = indexOfClosingParenthesis;
		return this.extractMathOperationString(beginIndex, endIndex);
	}

	@Override
	protected String extractMathOperator(final int indexOfClosingParenthesis) {
		int beginIndex = indexOfClosingParenthesis + 1;
		int endIndex = indexOfClosingParenthesis + 2;
		return this.extractMathOperationString(beginIndex, endIndex);
	}

	private int getIndexOfClosingParenthesis() {
		int countOfLeftParenthesis = 1;
		int index = 1;
		while (countOfLeftParenthesis != 0 && index + 1 <= this.mathOperationString.length()) {
			char charAt = this.mathOperationString.charAt(index++);
			if (charAt == '(')
				countOfLeftParenthesis++;
			else if (charAt == ')')
				countOfLeftParenthesis--;
		}
		if (countOfLeftParenthesis == 0)
			return index - 1;
		else
			throw new RuntimeException("Non-closing parenthesis found in operation: '" + this.mathOperationString + "'. ");
	}

}
