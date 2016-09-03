/**
 * https://www.hackerrank.com/challenges/dynamic-programming-classics-the-longest-common-subsequence
 */
package dynammicprogramming;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TheLongestCommonSubsequence {

	private static String[][] memory = null;

	public static void main(String[] args) throws FileNotFoundException {
		Path currentRelativePath = Paths.get("");
		String absolutePath = currentRelativePath.toAbsolutePath().toString();
		
		String pathOfTestCaseFile = absolutePath + "/TestData/TheLongestCommonSubsequence/TheLongestCommonSubsequenceTestData03"
				+ ".txt";
		File file = new File(pathOfTestCaseFile);
		Scanner sc = new Scanner(file);
		
		int n = sc.nextInt();
		int m = sc.nextInt();
		memory = new String[n + 1][m + 1];
		
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
		}
		int b[] = new int[m];
		for (int i = 0; i < m; i++) {
			b[i] = sc.nextInt();
		}
		String solution = TheLongestCommonSubsequence.solve(a, b);
		System.out.println(solution);
		sc.close();

	}

	static String solve(int[] a, int[] b) {
		String solution = "";
		solution = longestCommonSubsequence(0, 0, a, b);
		return solution;
	}

	static String longestCommonSubsequence(int currentIndexOfa, int currentIndexOfb, int a[], int b[]) {
		if (currentIndexOfa >= a.length || currentIndexOfb >= b.length || currentIndexOfb == -1) {
			return " ";
		}

		int nextValidIndexOfb = calcNextValidIndex(b, currentIndexOfb, a[currentIndexOfa]);
		String solutionWithCurrentIndex = "";
		if (nextValidIndexOfb != -1) {
			solutionWithCurrentIndex = a[currentIndexOfa] + "";
			String subSolution = null;
			if (memory[currentIndexOfa + 1][nextValidIndexOfb + 1] != null) {
				subSolution = memory[currentIndexOfa + 1][nextValidIndexOfb + 1];
			} else {
				subSolution = longestCommonSubsequence(currentIndexOfa + 1, nextValidIndexOfb + 1, a, b);
				memory[currentIndexOfa + 1][nextValidIndexOfb + 1] = subSolution;
			}

			solutionWithCurrentIndex = solutionWithCurrentIndex + " " + subSolution;
		}

		String solutionWithoutCurrentIndex = null;
		if (memory[currentIndexOfa + 1][currentIndexOfb] != null) {
			solutionWithoutCurrentIndex = memory[currentIndexOfa + 1][currentIndexOfb];
		} else {
			solutionWithoutCurrentIndex = longestCommonSubsequence(currentIndexOfa + 1, currentIndexOfb, a, b);
			memory[currentIndexOfa + 1][currentIndexOfb] = solutionWithoutCurrentIndex;
		}
		if (solutionWithCurrentIndex.length() > solutionWithoutCurrentIndex.length()) {
			return solutionWithCurrentIndex;
		} else {
			return solutionWithoutCurrentIndex;
		}

	}

	static int calcNextValidIndex(int b[], int currentIndexOfb, int number) {
		for (int i = currentIndexOfb; i < b.length; i++) {
			if (b[i] == number) {
				return i;
			}
		}
		return -1;
	}

}
