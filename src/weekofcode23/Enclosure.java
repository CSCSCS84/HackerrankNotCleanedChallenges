package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Enclosure {
	static long numberOfPoints;
	static long[] lengthOfSides = null;
	static double epsilonForR = 0.001;
	static double epsilonForStartR = 0.01;
	static long maxR = 0;

	public static void main(String[] args) throws FileNotFoundException {
		String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/Enclosure/EnclosureTestDataOwn10.txt";
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
		double radius = Enclosure.findRUsingNewton();
	//	System.out.println("radius " + radius);
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

		double mitteX = radius * radius - lengthOfSides[0] / 2.0 * lengthOfSides[0] / 2.0;
		mitteX = Math.sqrt(mitteX);
		double mitteY = lengthOfSides[0] / 2.0;
		winkelSum = 3 / 2.0 * Math.PI;
		winkelSum += Math.asin(lengthOfSides[0] / (2 * radius));
		//System.out.println(winkelSum);
		for (int i = 1; i < numberOfPoints; i++) {
			// System.out.println(radius);
			double winkel = 2 * Math.asin(lengthOfSides[i] / (2 * radius));

			winkelSum += winkel;
			if (winkelSum >= 2 * Math.PI) {
				winkelSum -= 2 * Math.PI;
			}
		//	System.out.println("winkelsum " + winkelSum);
			double x = mitteX + radius * Math.sin(winkelSum);
			double y = mitteY + radius * Math.cos(winkelSum);
			if (i - 1 != numberOfPoints) {
				solution.append(x);
				solution.append("\n");
				solution.append(y);
				solution.append('\n');
				solution.append('\n');
			}
		}

		return solution;
	}

	private static double calcStartR() {
		double startR = 0;
		double sumR = 0;
		for (int i = 0; i < numberOfPoints; i++) {
			sumR += lengthOfSides[i];
		}
		startR = sumR / (2 * Math.PI);
		// ist das richtig?

		while (calcSum(startR) > epsilonForStartR) {
			startR += 0.00001;

		}
	//	System.out.println("StartR " + startR);
		return startR;
	}

	private static double findRUsingNewton() {
		double sol = 0;
		double rn = calcStartR();
		//System.out.println("start r: " + rn);
		// double rn = 1.05;
		double rnplus1 = rn;
		double sumToCheckRNPLUS1;
		do {
			double dummy = rnplus1;
			rnplus1 = rn + zahlerFuerNewton(rn) / nennerFuerNewton(rn);
			rn = dummy;
			
			sumToCheckRNPLUS1 = calcSum(rnplus1);

		} while (sumToCheckRNPLUS1 > epsilonForR);
		return rnplus1;
	}

	private static double calcSum(double rnplus1) {
		double sum = 0;
		for (int i = 0; i < numberOfPoints; i++) {
			double dummy = lengthOfSides[i] / (2.0 * rnplus1);
			if (Math.abs(dummy) > 1) {
				return 1;
			}
			sum += Math.asin(dummy);
		}
		sum -= Math.PI;
		sum = Math.abs(sum);
		return sum;
	}

	private static double zahlerFuerNewton(double rn) {
		double zaehler = 0;
		for (int i = 0; i < numberOfPoints; i++) {
			zaehler = zaehler += Math.asin(lengthOfSides[i] / (2 * rn));
		}
		zaehler -= Math.PI;
		return zaehler;
	}

	private static double nennerFuerNewton(double rn) {
		double nenner = 0;

		for (int i = 0; i < numberOfPoints; i++) {
			double nennerOfNenner = 2 * rn * Math.sqrt(4 * rn * rn - lengthOfSides[i] * lengthOfSides[i]);
			// System.out.println("nenner "+nenner);
			nenner += lengthOfSides[i] / nennerOfNenner;
		}
		return nenner;

	}
}
