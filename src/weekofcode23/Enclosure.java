package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class Enclosure {
	static long numberOfPoints;
	static long[] lengthOfSides = null;
	static MathContext mc = new MathContext(10);
	static BigDecimal epsilonForR = new BigDecimal(0.01, mc);
	static BigDecimal epsilonForStartR = new BigDecimal(0.1, mc);
	static long maxR = 0;
	static int precision = 10;

	public static void main(String[] args) throws FileNotFoundException {
		String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/Enclosure/EnclosureTestData00.txt";
		File file = new File(pathOfTestCaseFile);

		Scanner sc = new Scanner(file);
		numberOfPoints = sc.nextInt();
		lengthOfSides = new long[(int) numberOfPoints];
		for (int i = 0; i < numberOfPoints; i++) {
			lengthOfSides[i] = sc.nextLong();
			if (lengthOfSides[i] > maxR) {
				maxR = lengthOfSides[i];
			}
		}
		StringBuffer solution = solve();
		System.out.println(solution);

	}

	private static StringBuffer solve() {
		StringBuffer solution = new StringBuffer();
		BigDecimal radius = Enclosure.findRUsingNewton();
		// System.out.println("radius " + radius);
		// radius=1.11803;
		// calculate the points
		double winkelSum = 0;
		solution.append("0");
		solution.append("\n");
		solution.append("0");
		solution.append("\n");
		solution.append("\n");
		solution.append("0");
		solution.append("\n");
		solution.append(lengthOfSides[0]);
		solution.append("\n");
		solution.append("\n");

		// double mitteX = radius * radius - lengthOfSides[0] / 2.0 *
		// lengthOfSides[0] / 2.0;
		// mitteX = Math.sqrt(mitteX);
		// double mitteY = lengthOfSides[0] / 2.0;
		// winkelSum = 3 / 2.0 * Math.PI;
		// winkelSum += Math.asin(lengthOfSides[0] / (2 * radius));
		// // System.out.println(winkelSum);
		// for (int i = 1; i < numberOfPoints; i++) {
		// // System.out.println(radius);
		// double winkel = 2 * Math.asin(lengthOfSides[i] / (2 * radius));
		//
		// winkelSum += winkel;
		// if (winkelSum >= 2 * Math.PI) {
		// winkelSum -= 2 * Math.PI;
		// }
		// // System.out.println("winkelsum " + winkelSum);
		// double x = mitteX + radius * Math.sin(winkelSum);
		// double y = mitteY + radius * Math.cos(winkelSum);
		// if (i - 1 != numberOfPoints) {
		// solution.append(x);
		// solution.append("\n");
		// solution.append(y);
		// solution.append('\n');
		// solution.append('\n');
		// }
		// }

		return solution;
	}

	private static BigDecimal calcStartR() {
		BigDecimal startR = new BigDecimal(0, mc);
		BigDecimal sumR = new BigDecimal(0, mc);
		for (int i = 0; i < numberOfPoints; i++) {
			sumR = sumR.add(new BigDecimal(lengthOfSides[i], mc));
		}
		double d = Math.PI * 2;
		System.out.println(sumR.toPlainString());
		startR = sumR.divide(new BigDecimal(d, mc), precision, RoundingMode.HALF_UP);
		// ist das richtig?
		System.out.println(startR.toPlainString());
		while (calcSum(startR).compareTo(epsilonForStartR) == 1) {
			startR = startR.add(new BigDecimal(0.00001, mc));

		}
		return startR;
	}

	private static BigDecimal findRUsingNewton() {
		BigDecimal rn = calcStartR();
		System.out.println(rn.toPlainString());
		BigDecimal rnplus1 = rn;
		BigDecimal sumToCheckRNPLUS1;
		do {
			System.out.println(rnplus1.toPlainString());
			BigDecimal dummy = rnplus1.add(new BigDecimal(0));
			rnplus1 = rn.add(new BigDecimal(0), mc);
		rnplus1=	rnplus1.add(zahlerFuerNewton(rn).divide(nennerFuerNewton(rn), precision, RoundingMode.HALF_UP));
			// rnplus1 = rn + zahlerFuerNewton(rn) / nennerFuerNewton(rn);
			rn = dummy;

			sumToCheckRNPLUS1 = calcSum(rnplus1);
			System.out.println(sumToCheckRNPLUS1.toPlainString());

		} while (sumToCheckRNPLUS1.compareTo(epsilonForR) == 1);
		return rnplus1;
	}

	private static BigDecimal calcSum(BigDecimal rnplus1) {
		BigDecimal sum = new BigDecimal(0, mc);
		for (int i = 0; i < numberOfPoints; i++) {
			BigDecimal dummy = new BigDecimal(lengthOfSides[i], mc);
			dummy = dummy.divide(new BigDecimal(2.0), mc);
			dummy = dummy.divide(rnplus1, precision, RoundingMode.HALF_UP);
			// BigDecimal dummy = new BigDecimal( lengthOfSides[i] / (2.0 *
			// rnplus1),mc);
			if (dummy.compareTo(new BigDecimal(1, mc)) == 1) {
				return new BigDecimal(1, mc);
			}
			double asin = Math.asin(dummy.doubleValue());
			sum = sum.add(new BigDecimal(asin, mc));
		}
		sum = sum.subtract(new BigDecimal(Math.PI, mc));
		sum = sum.abs();

		return sum;
	}

	private static BigDecimal zahlerFuerNewton(BigDecimal rn) {
		BigDecimal zaehler = new BigDecimal(0, mc);
		for (int i = 0; i < numberOfPoints; i++) {
			double dummy = Math.asin(lengthOfSides[i] / (2 * rn.doubleValue()));
			zaehler = zaehler.add(new BigDecimal(dummy, mc));
		}
		zaehler = zaehler.subtract(new BigDecimal(Math.PI, mc));
		return zaehler;
	}

	private static BigDecimal nennerFuerNewton(BigDecimal rn) {
		BigDecimal nenner = new BigDecimal(0, mc);

		for (int i = 0; i < numberOfPoints; i++) {
			double dummy = 2 * rn.doubleValue()
					* Math.sqrt(4 * rn.doubleValue() * rn.doubleValue() - lengthOfSides[i] * lengthOfSides[i]);
			BigDecimal nennerOfNenner = new BigDecimal(dummy, mc);

			// System.out.println("nenner "+nenner);
			BigDecimal toAdd = new BigDecimal(lengthOfSides[i], mc);
			toAdd = toAdd.divide(nennerOfNenner, 10, RoundingMode.HALF_UP);
			nenner = nenner.add(toAdd);
		}
		return nenner;

	}
}
