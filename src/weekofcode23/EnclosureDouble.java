package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EnclosureDouble {
	static long numberOfPoints;
	static long[] lengthOfSides = null;
	static double epsilonForR = 0.0000001;
	static double epsilonForStartR = 0.1;
	static long maxR = 0;
	static private long maxIteration = 100000;

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
		StringBuffer solution1 = new StringBuffer();
		StringBuffer solution2 = new StringBuffer();
		double radius = EnclosureDouble.findRByLogSearch();

		double distToZeroSol1 = 0;
		double distToZeroSol2 = 0;

		distToZeroSol1 = solution1(solution1, radius, distToZeroSol1);
		distToZeroSol2 = solution2(solution2, radius, distToZeroSol2);

		if(distToZeroSol1<distToZeroSol1){
			
			return solution1;
		}
		else{
			return solution2;
		}
	}

	private static double solution2(StringBuffer solution2, double radius, double distToZeroSol2) {
		double winkelSum = 0;
		solution2.append("0");
		solution2.append("\n");
		solution2.append("0");
		solution2.append("\n");
		solution2.append("\n");
		solution2.append("0");
		solution2.append("\n");
		solution2.append(lengthOfSides[0]);
		solution2.append("\n");
		solution2.append("\n");

		double mitteX = radius * radius - lengthOfSides[0] / 2.0 * lengthOfSides[0] / 2.0;
		mitteX =- Math.sqrt(mitteX);
		double mitteY = lengthOfSides[0] / 2.0;
		double winkelStart=Math.asin(mitteY/ (radius));
		
		winkelStart=Math.PI/2 -winkelStart;
		winkelSum=winkelStart;
		// System.out.println(winkelSum);
		for (int i = 1; i < numberOfPoints; i++) {
			// System.out.println(radius);
			double winkel = 2 * Math.asin(lengthOfSides[i] / (2 * radius));

			winkelSum += winkel;
			if (winkelSum >= 2 * Math.PI) {
				winkelSum -= 2 * Math.PI;
			}
			// System.out.println("winkelsum " + winkelSum);
			double x = mitteX + radius * Math.sin(winkelSum);
			double y = mitteY + radius * Math.cos(winkelSum);
			if (i + 1 == numberOfPoints) {
				distToZeroSol2 += (x * x + y * y);
			}
			if (i + 1 != numberOfPoints) {
				solution2.append(x);
				solution2.append("\n");
				solution2.append(y);
				solution2.append('\n');
				solution2.append('\n');
			}
		}
		return distToZeroSol2;
	}

	private static double solution1(StringBuffer solution1, double radius, double distToZeroSol1) {
		double winkelSum = 0;
		solution1.append("0");
		solution1.append("\n");
		solution1.append("0");
		solution1.append("\n");
		solution1.append("\n");
		solution1.append("0");
		solution1.append("\n");
		solution1.append(lengthOfSides[0]);
		solution1.append("\n");
		solution1.append("\n");

		double mitteX = radius * radius - lengthOfSides[0] / 2.0 * lengthOfSides[0] / 2.0;
		mitteX = Math.sqrt(mitteX);
		double mitteY = lengthOfSides[0] / 2.0;
		winkelSum = 3 / 2.0 * Math.PI;
		winkelSum += Math.asin(lengthOfSides[0] / (2 * radius));
		// System.out.println(winkelSum);
		for (int i = 1; i < numberOfPoints; i++) {
			// System.out.println(radius);
			double winkel = 2 * Math.asin(lengthOfSides[i] / (2 * radius));

			winkelSum += winkel;
			if (winkelSum >= 2 * Math.PI) {
				winkelSum -= 2 * Math.PI;
			}
			// System.out.println("winkelsum " + winkelSum);
			double x = mitteX + radius * Math.sin(winkelSum);
			double y = mitteY + radius * Math.cos(winkelSum);
			if (i + 1 == numberOfPoints) {
				distToZeroSol1 += (x * x + y * y);
			}
			if (i + 1 != numberOfPoints) {
				solution1.append(x);
				solution1.append("\n");
				solution1.append(y);
				solution1.append('\n');
				solution1.append('\n');
			}
		}
		return distToZeroSol1;
	}

	private static double findRByLogSearch() {
		double radius = 0;

		double leftBound = 0;
		double rightBound = 0;
		double sumR = 0;
		for (int i = 0; i < numberOfPoints; i++) {
			sumR += lengthOfSides[i];
		}
		rightBound = sumR;
		long count = 0;

		while (count <= maxIteration) {
			radius = leftBound + (rightBound - leftBound) / 2.0;
			double calcSum = calcSum(radius);
			if (Math.abs(calcSum) < epsilonForR) {
				return radius;
			}
			if (Math.signum(calcSum(radius)) == Math.signum(calcSum(leftBound))) {
				leftBound = radius;
			} else {
				rightBound = radius;
			}
			count++;
		}
		return radius;

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
		// sum = Math.abs(sum);
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