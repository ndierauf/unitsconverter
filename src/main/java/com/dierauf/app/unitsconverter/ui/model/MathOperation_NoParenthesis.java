package com.dierauf.app.unitsconverter.ui.model;

public class MathOperation_NoParenthesis extends MathOperation {

	public MathOperation_NoParenthesis(final String mathOperationString) {
		super(mathOperationString);
	}

	@Override
	protected void setLeftRight_MathOperations() {
		int indexOfOperator = this.getIndexOfMathOperator();
		if (this.isSingleOperation(indexOfOperator)) // ie: this.mathOperationString looks like: "degree"
			return;
		else { // ie, this.mathOperationString looks like: "degree/min"
			String mathOperationString_Left = this.extractMathOperationString_Left(indexOfOperator);
			String mathOperationString_Right = this.extractMathOperationString_Right(indexOfOperator);
			String mathOperator = this.extractMathOperator(indexOfOperator);
			this.leftMathOperation = createMathOperation(mathOperationString_Left);
			this.rightMathOperation = createMathOperation(mathOperationString_Right);
			this.mathOperator = mathOperator;
		}
	}

	//Returns true for "degree", false for "degree/min"
	@Override
	protected boolean isSingleOperation(final int indexOfOperator) {
		return indexOfOperator < 0;
	}

	@Override
	protected String extractMathOperationString_Right(final int indexOfOperator) {
		int beginIndex = indexOfOperator + 1;
		return this.extractMathOperationString(beginIndex);
	}

	@Override
	protected String extractMathOperationString_Left(final int indexOfOperator) {
		int beginIndex = 0;
		int endIndex = indexOfOperator;
		return this.extractMathOperationString(beginIndex, endIndex);
	}

	@Override
	protected String extractMathOperator(final int indexOfOperator) {
		int beginIndex = indexOfOperator;
		int endIndex = indexOfOperator + 1;
		return this.extractMathOperator(beginIndex, endIndex);
	}

}
