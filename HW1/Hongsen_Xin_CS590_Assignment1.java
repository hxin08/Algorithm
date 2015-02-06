package edu.stevens.cs590.assignment;

import java.util.ArrayList;
import java.util.Stack;

public class IntegerNumber {

	public String integerNumber;
	public static String intializeInteger = "0";

	public IntegerNumber() {
		integerNumber = intializeInteger;
	}

	private IntegerNumber(String stringNumber) {
		integerNumber = stringNumber;
	}

	public IntegerNumber(String integerNumber, String addNum) {

	}

	private void setIntegerNumber(String strNumber) {
		integerNumber = strNumber;
	}

	// Define the maxLength of two number for operation loop
	public static int maxLength(String integerNumber, String addNum) {
		int maxL = 0;
		// Get the maxLength of two numbers
		if (integerNumber.charAt(0) == '-') {
			integerNumber = integerNumber.substring(1);
		}
		if (addNum.charAt(0) == '-') {
			addNum = addNum.substring(1);
		}
		if (integerNumber.length() >= addNum.length()) {
			maxL = integerNumber.length();
		} else {
			maxL = addNum.length();
		}
		return maxL;
	}

	// If integerNumberAbsolute >= addNumAbsolute.
	@SuppressWarnings("unused")
	public static boolean compareAbsolute(String integerNumber, String addNum) {
		String integerNumberAbsolute = null;
		String addNumAbsolute = null;
		boolean isEqual = false;
		String csDigit = null;
		String bsDigit = null;
		int ciDigit = 0;
		int biDigit = 0;
		if (integerNumber.charAt(0) == '-') {
			integerNumberAbsolute = integerNumber.substring(1);
		} else {
			integerNumberAbsolute = integerNumber;
		}
		if (addNum.charAt(0) == '-') {
			addNumAbsolute = addNum.substring(1);
		} else {
			addNumAbsolute = addNum;
		}
		if (integerNumberAbsolute.length() > addNumAbsolute.length()) {
			return true;
		} else if (integerNumberAbsolute.length() < addNumAbsolute.length()) {
			return false;
		}
		// IntegerNumber.length() == addNum.length()
		else {
			// Loop to compare every digit from begin to end
			for (int index = 0; index <= integerNumber.length(); index++) {
				csDigit = integerNumberAbsolute.substring(index, index + 1);
				bsDigit = addNumAbsolute.substring(index, index + 1);
				if (csDigit == "") {
					ciDigit = 0;
				}
				if (bsDigit == "") {
					biDigit = 0;
				}

				if ((csDigit != "") && (bsDigit != "") && (ciDigit == biDigit)) {
					isEqual = true;
				} else if ((csDigit != "") && (bsDigit != "")
						&& (ciDigit < biDigit)) {
					isEqual = false;
					return false;
				}
				return isEqual;
			}// for
		}
		return false;
	}

	// Add Absolute operation
	public static String addAbsolute(String integerNumber, String addNum) {
		String subResult = "";
		int digitSum = 0;
		int digitCarry = 0;
		boolean resultFlag = true;
		String csLast = integerNumber.substring((integerNumber.length() - 1));
		String bsLast = addNum.substring((addNum.length() - 1));
		int ciLast = 0;
		int biLast = 0;
		// Get the maxLength of these two numbers.
		int maxDigits = maxLength(integerNumber, addNum);
		// Create Integer Stack to store countResult
		Stack<Integer> countResult = new Stack<Integer>();

		// Loop to adding each digit
		for (int i = 0; i <= maxDigits - 1; i++) {
			// Check the length of two operators

			if (integerNumber.length() > 1) {
				// The last char of C.
				csLast = integerNumber.substring(integerNumber.length() - 1);
				if (csLast == "") {
					ciLast = 0;
				} else {
					ciLast = Integer.parseInt(csLast);
				}
			} else {
				csLast = "";
				ciLast = 0;
			}

			if (addNum.length() >= 1) {
				// The last char of B.
				bsLast = addNum.substring(addNum.length() - 1);
				if (bsLast == "") {
					biLast = 0;
				} else {
					biLast = Integer.parseInt(bsLast);
				}
			} else {
				bsLast = "";
				biLast = 0;
			}

			digitSum = digitCarry + ((ciLast + biLast) % 10);
			// Set digitCarry
			if (ciLast + biLast >= 10) {
				digitCarry = 1;
			} else {
				digitCarry = 0;
			}
			// Put digitSum into Stack.
			countResult.push(digitSum);
			if (integerNumber.length() >= 1)
				integerNumber = integerNumber.substring(0,
						integerNumber.length() - 1);
			if (addNum.length() >= 1)
				addNum = addNum.substring(0, addNum.length() - 1);
		}// for
		if (!countResult.empty()) {
			// Convert Stack to ArrayList
			ArrayList<Integer> arrayResult = new ArrayList<Integer>(countResult);
			// Convert ArrayList to String
			for (int i = arrayResult.size() - 1; i >= 0; i--) {
				if (resultFlag) {
					subResult = subResult
							+ arrayResult.get(i).toString();
				} else {
					if (arrayResult.get(i) != 0) {
						subResult = subResult
								+ arrayResult.get(i).toString();
						resultFlag = true;
					} else {
						subResult = "";
					}
				}

			}
			resultFlag = false;
			if (subResult.length() == 0) {
				integerNumber = "0";
			} else {
				integerNumber = subResult;
			}
		}
		return integerNumber;
	}// addAbsolute

	// Add operation
	public static String addDigit(String integerNumber, String addNum) {
		// Two positive integerNumber add or two negative number add
		// First digit of C
		char fCharc1 = integerNumber.charAt(0);
		// First digit of B
		char fCharc2 = addNum.charAt(0);
		boolean sameSign = ((fCharc1 == '-' && fCharc2 == '-') || (fCharc1 != '-' && fCharc2 != '-'));
		// Judge if C and B are both empty.
		if ((integerNumber.isEmpty()) && (addNum.isEmpty())) {
			// C equals zero.
			integerNumber = intializeInteger;
		}
		// If C and B are not both empty
		else {
			// If C and B with same sign.
			if (sameSign) {
				// If C and B are both negative number.
				if (fCharc1 == '-' && fCharc2 == '-') {
					integerNumber = integerNumber.substring(1);
					addNum = addNum.substring(1);

					// Loop to adding each digit
					// add sign '-'
					integerNumber = "-" + addAbsolute(integerNumber, addNum);

				}
				// If C and B are positive number or zero(not both empty)
				if (fCharc1 != '-' && fCharc2 != '-') {

					// C is zero, B is positive.
					if ((integerNumber.length() == 0) && (addNum.length() != 0)) {
						// C is zero, C equals B.
						integerNumber = addNum;
					}
					// C is positive, B is zero.
					else if ((integerNumber.length() != 0)
							&& (addNum.length() == 0)) {
						// B is zero, C equals itself.
						integerNumber = integerNumber.toString();
					}
					// C and B are both positive number
					else if ((integerNumber.length() != 0)
							&& (addNum.length() != 0)) {
						// addDigit
						integerNumber = addAbsolute(integerNumber, addNum);
					}
				}
			}
			// If C and B with different sign.
			else {
				// Different sign
				if (fCharc1 == '-' && fCharc2 != '-') {
					integerNumber = integerNumber.substring(1);
					// If C < 0 , B >= 0, |C| > |B|
					if (!compareAbsolute(addNum, integerNumber)) {
						// |C| - |B|, add "-"
						integerNumber = "-"
								+ subtractDigit(integerNumber, addNum);
					}
					// If C < 0 , B >= 0, |C| < |B|
					else if (!compareAbsolute(integerNumber, addNum)) {
						// |B| - |C|
						integerNumber = subtractDigit(addNum, integerNumber);
					} else if (compareAbsolute(addNum, integerNumber)
							&& compareAbsolute(integerNumber, addNum)) {
						integerNumber = "0";
					}
				}
				if (fCharc1 != '-' && fCharc2 == '-') {
					addNum = addNum.substring(1);
					// If C >= 0 , B < 0, |C| > |B|
					if (!compareAbsolute(addNum, integerNumber)) {
						// |C| - |B|
						integerNumber = subtractDigit(integerNumber, addNum);
					}
					// If C >= 0 , B < 0, |C| < |B|
					else if (!compareAbsolute(integerNumber, addNum)) {
						// |B| - |C|
						integerNumber = "-"
								+ subtractDigit(addNum, integerNumber);
					} else if (compareAbsolute(addNum, integerNumber)
							&& compareAbsolute(integerNumber, addNum)) {
						integerNumber = "0";
					}
				}
			}
		}
		return integerNumber;
	}

	// Subtract operation
	public static String subtractDigit(String integerNumber, String addNum) {
		String subResult = "";
		int digitSubtract = 0;
		int borrow = 0;
		boolean resultFlag = false;
		// First digit of C
		char fCharc1 = integerNumber.charAt(0);
		// First digit of B
		char fCharc2 = addNum.charAt(0);
		// The last char of C.
		String csLast = integerNumber.substring(integerNumber.length() - 1);
		// The last char of B.
		String bsLast = addNum.substring(addNum.length() - 1);
		int ciLast = Integer.parseInt(csLast);
		int biLast = Integer.parseInt(bsLast);
		// Create Integer Stack to store countResult
		Stack<Integer> countResult = new Stack<Integer>();
		int maxDigits = maxLength(integerNumber, addNum);

		if (fCharc1 == '-' && fCharc2 == '-') {
			integerNumber = integerNumber.substring(1);
			addNum = addNum.substring(1);
			// Loop to subtract each digit
			if (compareAbsolute(integerNumber, addNum)) {
				for (int i = 0; i <= maxDigits - 1; i++) {
					// Check the length of two operators
					if (integerNumber.length() >= 1) {
						// The last char of C.
						csLast = integerNumber
								.substring(integerNumber.length() - 1);
						// ciLast = Integer.parseInt(csLast.toString());
						ciLast = Integer.parseInt(csLast.toString());
					} else {
						csLast = "";
						ciLast = 0;
					}

					if (addNum.length() >= 1) {
						// The last char of B.
						bsLast = addNum.substring(addNum.length() - 1);
						biLast = Integer.parseInt(bsLast.toString());
					} else {
						bsLast = "";
						biLast = 0;
					}

					if (borrow == 0) {
						if (ciLast >= biLast) {
							borrow = 0;
							digitSubtract = borrow + ciLast - biLast;
						} else {
							borrow = 10;
							digitSubtract = borrow + ciLast - biLast;
						}
					} else {
						ciLast = ciLast - 1;
						if (ciLast >= biLast) {
							borrow = 0;
							digitSubtract = borrow + ciLast - biLast;
						} else {
							borrow = 10;
							digitSubtract = borrow + ciLast - biLast;
						}
					}
					// Put digitSubtract into Stack.
					countResult.push(digitSubtract);
					// Iteration two operations
					if (integerNumber.length() > 1) {
						// Remove last char of C
						integerNumber = integerNumber.substring(0,
								integerNumber.length() - 1);
						csLast = integerNumber
								.substring(integerNumber.length() - 1);
						ciLast = Integer.parseInt(csLast);
					} else if (integerNumber.length() == 1) {
						// Remove last char of C
						integerNumber = integerNumber.substring(0,
								integerNumber.length() - 1);
						csLast = "";
						ciLast = 0;
					}
					if (addNum.length() > 1) {
						// Remove last char of B
						addNum = addNum.substring(0, addNum.length() - 1);
						bsLast = addNum.substring(addNum.length() - 1);
						biLast = Integer.parseInt(bsLast);
					} else if (addNum.length() == 1) {
						// Remove last char of B
						addNum = addNum.substring(0, addNum.length() - 1);
						bsLast = "";
						biLast = 0;
					}
				}// for

				if (!countResult.empty()) {
					// Convert Stack to ArrayList
					ArrayList<Integer> arrayResult = new ArrayList<Integer>(
							countResult);
					// Convert ArrayList to String
					for (int i = arrayResult.size() - 1; i >= 0; i--) {
						if (resultFlag) {
							subResult = subResult
									+ arrayResult.get(i).toString();
						} else {
							if (arrayResult.get(i) != 0) {
								subResult = subResult
										+ arrayResult.get(i).toString();
								resultFlag = true;
							} else {
								subResult = "";
							}
						}

					}
					resultFlag = false;
					if (subResult.length() == 0) {
						integerNumber = "0";
					} else {
						integerNumber = "-" + subResult;
					}

				}
			}
		}// if
			// For two negative operations subtract
		else if (fCharc1 != '-' && fCharc2 != '-') {
			// Loop to subtract each digit
			// If integerNumber > addNum
			if (compareAbsolute(integerNumber, addNum)) {
				for (int i = 0; i <= maxDigits - 1; i++) {
					if (borrow == 0) {
						if (ciLast >= biLast) {
							borrow = 0;
							digitSubtract = borrow + ciLast - biLast;
						} else {
							borrow = 10;
							digitSubtract = borrow + ciLast - biLast;
						}
					} else {
						ciLast = ciLast - 1;
						if (ciLast >= biLast) {
							borrow = 0;
							digitSubtract = borrow + ciLast - biLast;
						} else {
							borrow = 10;
							digitSubtract = borrow + ciLast - biLast;
						}
					}
					// Put digitSubtract into Stack.
					countResult.push(digitSubtract);
					// Iteration two operations
					if (integerNumber.length() > 1) {
						// Remove last char of C
						integerNumber = integerNumber.substring(0,
								integerNumber.length() - 1);
						csLast = integerNumber
								.substring(integerNumber.length() - 1);
						ciLast = Integer.parseInt(csLast);
					} else if (integerNumber.length() == 1) {
						// Remove last char of C
						integerNumber = integerNumber.substring(0,
								integerNumber.length() - 1);
						csLast = "";
						ciLast = 0;
					}
					if (addNum.length() > 1) {
						// Remove last char of B
						addNum = addNum.substring(0, addNum.length() - 1);
						bsLast = addNum.substring(addNum.length() - 1);
						biLast = Integer.parseInt(bsLast);
					} else if (addNum.length() == 1) {
						// Remove last char of B
						addNum = addNum.substring(0, addNum.length() - 1);
						bsLast = "";
						biLast = 0;
					}
				}// for
				if (!countResult.empty()) {
					// Convert Stack to ArrayList
					ArrayList<Integer> arrayResult = new ArrayList<Integer>(
							countResult);
					// Convert ArrayList to String
					for (int i = arrayResult.size() - 1; i >= 0; i--) {
						if (resultFlag) {
							subResult = subResult
									+ arrayResult.get(i).toString();
						} else {
							if (arrayResult.get(i) != 0) {
								subResult = subResult
										+ arrayResult.get(i).toString();
								resultFlag = true;
							} else {
								subResult = "";
							}
						}

					}
					resultFlag = false;
					if (subResult.length() == 0) {
						integerNumber = "0";
					} else {
						integerNumber = subResult;
					}
				}
			} else {
				for (int i = 0; i <= maxDigits - 1; i++) {
					if (borrow == 0) {
						if (biLast >= ciLast) {
							borrow = 0;
							digitSubtract = borrow + biLast - ciLast;
						} else {
							borrow = 10;
							digitSubtract = borrow + biLast - ciLast;
						}
					} else {
						biLast = biLast - 1;
						if (biLast >= ciLast) {
							borrow = 0;
							digitSubtract = borrow + biLast - ciLast;
						} else {
							borrow = 10;
							digitSubtract = borrow + biLast - ciLast;
						}
					}
					// Put digitSubtract into Stack.
					countResult.push(digitSubtract);

					// Iteration two operations
					if (integerNumber.length() > 1) {
						// Remove last char of C
						integerNumber = integerNumber.substring(0,
								integerNumber.length() - 1);
						csLast = integerNumber
								.substring(integerNumber.length() - 1);
						ciLast = Integer.parseInt(csLast);
					} else if (integerNumber.length() == 1) {
						// Remove last char of C
						integerNumber = integerNumber.substring(0,
								integerNumber.length() - 1);
						csLast = "";
						ciLast = 0;
					}
					if (addNum.length() > 1) {
						// Remove last char of B
						addNum = addNum.substring(0, addNum.length() - 1);
						bsLast = addNum.substring(addNum.length() - 1);
						biLast = Integer.parseInt(bsLast);
					} else if (addNum.length() == 1) {
						// Remove last char of B
						addNum = addNum.substring(0, addNum.length() - 1);
						bsLast = "";
						biLast = 0;
					}
				}// for
				if (!countResult.empty()) {
					// Convert Stack to ArrayList
					ArrayList<Integer> arrayResult = new ArrayList<Integer>(
							countResult);
					// Convert ArrayList to String
					for (int i = arrayResult.size() - 1; i >= 0; i--) {
						if (resultFlag) {
							subResult = subResult
									+ arrayResult.get(i).toString();
						} else {
							if (arrayResult.get(i) != 0) {
								subResult = subResult
										+ arrayResult.get(i).toString();
								resultFlag = true;
							} else {
								subResult = "";
							}
						}

					}
					resultFlag = false;
					if (subResult.length() == 0) {
						integerNumber = "0";
					} else {
						integerNumber = "-" + subResult;
					}

				}
			}
		} else if (fCharc1 != '-' && fCharc2 == '-') {
			// Loop to subtract each digit
			addNum = addNum.substring(1);
			integerNumber = addDigit(integerNumber, addNum);
		} else if (fCharc1 == '-' && fCharc2 != '-') {
			// Loop to subtract each digit
			integerNumber = integerNumber.substring(1);
			integerNumber = "-" + addDigit(integerNumber, addNum);
		}
		return integerNumber;
	}

	public static void main(String[] args) {
		// IntegerNumber object A is created
		IntegerNumber A = new IntegerNumber();
		// And A contains the integer 0
		//System.out.println(A.integerNumber);
		// IntegerNumber object B is created
		IntegerNumber B = new IntegerNumber("-12345678954688709764347890");
		// B contains the negative number shown within the quotes ""
		//System.out.println(B.integerNumber);
		// IntegerNumber object C is created
		IntegerNumber C = new IntegerNumber();
		// And C contains the positive number shown within the quotes ""
		C.integerNumber = "5678954688709764347890";
		//System.out.println(C.integerNumber);
		// IntegerNumber object D is created and D contains the number that B
		// contains
		IntegerNumber D = new IntegerNumber(B.integerNumber);
		//System.out.println(D.integerNumber);
		// IntegerNumber object E is created and E contains the number that B
		// contains
		IntegerNumber E = new IntegerNumber(B.integerNumber);
		//System.out.println(E.integerNumber);
		// Assigns the value of A to that of B
		((IntegerNumber) A).setIntegerNumber(B.integerNumber);
		// Cout << A << " " << B << endl; output to screen the integers in A and
		// B
		System.out.println(A.integerNumber + " " + B.integerNumber);

		// C = B + C; cout << C << endl; // output to screen the sum of the
		// numbers B and C
		C.integerNumber = addDigit(B.integerNumber, C.integerNumber);
		System.out.println(C.integerNumber);

		// D = B - C; cout << D << endl; // output to screen the number B - C
		D.integerNumber = subtractDigit(B.integerNumber, C.integerNumber);
		System.out.println(D.integerNumber);

		// If ( A < B ) { E += B; cout << E << endl; // output to screen the sum
		// of E and B }
		// A < 0, B >= 0
		if ((A.integerNumber.charAt(0) == '-')
				&& (B.integerNumber.charAt(0) != '-')) {
			E.integerNumber = addDigit(E.integerNumber, B.integerNumber);
			System.out.print("A < B 1:");
			System.out.println(E.integerNumber);
		}
		// A < 0, B < 0, |A| > |B|
		if ((A.integerNumber.charAt(0) == '-')
				&& (B.integerNumber.charAt(0) == '-')
				&& !compareAbsolute(B.integerNumber, A.integerNumber)) {
			E.integerNumber = addDigit(E.integerNumber, B.integerNumber);
			System.out.print("A < B 2:");
			System.out.println(E.integerNumber);
		}
		// A > 0, B > 0, |A| < |B|
		if ((A.integerNumber.charAt(0) != '-')
				&& (B.integerNumber.charAt(0) != '-')
				&& !compareAbsolute(A.integerNumber, B.integerNumber)) {
			E.integerNumber = addDigit(E.integerNumber, B.integerNumber);
			System.out.print("A < B 3:");
			System.out.println(E.integerNumber);
		}

		// If ( A >= B ) { E -= B; cout << E << endl; // output to screen the
		// number E - B }
		// A >=0 , B < 0
		if ((A.integerNumber.charAt(0) != '-')
				&& (B.integerNumber.charAt(0) == '-')) {
			E.integerNumber = subtractDigit(E.integerNumber, B.integerNumber);

		}

		// A > = 0 ,B >=0 £¬|A| > |B|
		if ((A.integerNumber.charAt(0) != '-')
				&& (B.integerNumber.charAt(0) != '-')
				&& !compareAbsolute(B.integerNumber, A.integerNumber)) {
			E.integerNumber = subtractDigit(E.integerNumber, B.integerNumber);
		}

		// A > =0 ,B >=0 £¬|A| = |B|
		if ((A.integerNumber.charAt(0) != '-')
				&& (B.integerNumber.charAt(0) != '-')
				&& compareAbsolute(B.integerNumber, A.integerNumber)
				&& compareAbsolute(A.integerNumber, B.integerNumber)) {
			E.integerNumber = subtractDigit(E.integerNumber, B.integerNumber);

		}

		// A < 0, B < 0,|A| < |B|
		if ((A.integerNumber.charAt(0) == '-')
				&& (B.integerNumber.charAt(0) == '-')
				&& !compareAbsolute(A.integerNumber, B.integerNumber)) {
			E.integerNumber = subtractDigit(E.integerNumber, B.integerNumber);
		}

		// A < 0, B < 0,|A| = |B|
		if ((A.integerNumber.charAt(0) == '-')
				&& (B.integerNumber.charAt(0) == '-')
				&& compareAbsolute(B.integerNumber, A.integerNumber)
				&& compareAbsolute(A.integerNumber, B.integerNumber)) {
			E.integerNumber = subtractDigit(E.integerNumber, B.integerNumber);
			System.out.println(E.integerNumber);
		}

		// If (A == B || C != D) { cout << A << E << D << endl; output to screen
		// the numbers A, E, and D}
		if ((A.integerNumber == B.integerNumber)
				|| (C.integerNumber != D.integerNumber)) {
			// TODO result
			System.out.println(A.integerNumber + E.integerNumber
					+ D.integerNumber);
		}
	}
}
