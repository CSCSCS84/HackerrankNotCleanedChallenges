package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SashaAndSwap {

	static int n;
	static int numbers[];
	static int modulo = 1000000007;
	static int solution = 0;

	static int solutionFast[][];

	public static void main(String[] args) throws FileNotFoundException {
		double time1 = System.currentTimeMillis();
		String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/SashaAndSwap/SashaAndSwapTestDataOwn"
				+ 10000 + ".txt";
		File file = new File(pathOfTestCaseFile);

		Scanner sc = new Scanner(file);
		n = sc.nextInt();
		numbers = new int[n];
		for (int i = 0; i < n; i++) {
			numbers[i] = sc.nextInt();
		}

		SashaAndSwap.solveFaster();
		System.out.println();
		double time2 = System.currentTimeMillis();
		System.out.println((time2 - time1) / 1000);

	}

	private static void solveFaster() {
		StringBuffer sol = new StringBuffer();
		int[] solution = new int[n + 1];
		int sum = 0;
		for (int i = 2; i <= n; i++) {
			sum = (sum + (i - 1)) % modulo;
			solution[i] = sum;
		}
		int start = 1;
		sol.append(solution[n] + " ");
		int current = start;
		for (int t = 2; t < n-1; t++) {
			start = (start * (t + 1)) % modulo;
			current = start;
			int memory = solution[t+1];
			solution[t+1]=start;
			int nextValue=0;
			for (int i = t + 2; i <= n; i++) {
				 nextValue = ((memory * (i - 1)) % modulo + solution[i - 1]) % modulo;
				memory = solution[i];
				solution[i] = nextValue;

			}
			solution[n] = nextValue;
			sol.append(solution[n] + " ");

		}
		long lastTerm=1;
		for(int i=3;i<=n;i++){
			lastTerm=(lastTerm*(i))%modulo;
		}
		sol.append(lastTerm);

		System.out.println(sol);

	}


}
