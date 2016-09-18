package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.stream.MemoryCacheImageInputStream;

public class SashaAndSwapSlow {
	static int n;
	static int numbers[];
	static long modulo = 1000000007;
	static long solution = 0;
	static HashMap<BigInteger, Byte> permutationsKey;
	static HashMap<BigInteger, Long> memory[] = null;
	static double timeSum = 0;
	static long solutionMemory[][];
	static long solutionFast[][];

	public static void main(String[] args) throws FileNotFoundException {
		int numberOfInstances = 20;
		solutionMemory = new long[numberOfInstances][numberOfInstances];
		double time1 = System.currentTimeMillis();
		for (int t = 3; t <= numberOfInstances; t++) {
			String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/SashaAndSwap/SashaAndSwapTestDataOwn"
					+ t + ".txt";
			File file = new File(pathOfTestCaseFile);

			Scanner sc = new Scanner(file);
			n = sc.nextInt();
			numbers = new int[n];
			for (int i = 0; i < n; i++) {
				numbers[i] = sc.nextInt();
			}
			System.out.print(t + " : ");
			// SashaAndSwapSlow.solve();
			SashaAndSwapSlow.solveForIFast();
			System.out.println();
		}
		double time2 = System.currentTimeMillis();
		System.out.println((time2 - time1) / 1000);
		System.out.println(timeSum / 1000);

	}

	private static void solve() {
		for (int i = 1; i < 6 && i < n; i++) {
			long solution = solveForI(i);
			solutionMemory[n][i] = solution;
			if (i != n - 1 && i > 3 && i > 1) {
				if (solutionMemory[n][i] != solutionMemory[n - 1][i] + (n - 1) * solutionMemory[n - 1][i - 1]) {
					System.out.println("fuck klappt doch nicht");
				}
			}
			long numOfPerm = (long) Math.pow(n * (n - 1) / 2, i);
			System.out.print((solution) + " ");
		}
	}

	private static void solveForIFast() {
		solutionFast = new long[n + 1][n + 1];
		long sum = 0;
		for (int i = 2; i <= n; i++) {
			sum += (i - 1);
			solutionFast[i][1] = sum;
		}
	
		// System.out.println(" n "+n);
		for (int i = 3; i <= n; i++) {
			for (int t = 2; t <= n; t++) {
				// System.out.println(i + " "+t);
				
				if (i == t + 1) {
					long a=solutionFast[i-1][t-1];
					solutionFast[i][t]=solutionFast[i-1][t-1]*(i);
				} else {
					solutionFast[i][t] = solutionFast[i - 1][t] + (i - 1) * solutionFast[i - 1][t - 1];
				}
			}
		}
		for (int a = 1; a < n; a++) {
			System.out.print(solutionFast[n][a] + " ");
		}

		// return solutionFast;
	}

	private static long solveForI(int k) {
		solution = 0;
		permutationsKey = new HashMap<>();
		memory = new HashMap[n];
		for (int i = 0; i < n; i++) {
			memory[i] = new HashMap<>();
		}
		BigInteger keyValue = keyValueForPermutation(numbers);
		long solution = countPermuation(k, numbers, keyValue);
		return solution;

	}

	private static long countPermuation(int numOfSwaps, int[] permutation, BigInteger keyValue) {
		if (numOfSwaps == 0) {

			if (permutationsKey.containsKey(keyValue)) {
				return 0;

			} else {
				permutationsKey.put(keyValue, (byte) 1);
				// solution++;
				return 1;
			}
		}
		long sum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int[] copy = copyArrayDeep(permutation);
				copy[i] = permutation[j];
				copy[j] = permutation[i];
				long a = 0;
				// BigInteger keyValue2 = keyValueForPermutation(copy);
				BigInteger updatedKeyValue = updateKeyValue(keyValue, i, j, permutation);
				// System.out.println(keyValue2);
				// System.out.println(updatedKeyValue);

				// System.out.println(keyValue);
				if (memory[numOfSwaps - 1].containsKey(updatedKeyValue)) {
					// a = memory[numOfSwaps - 1].containsKey(keyValue);
					a = memory[numOfSwaps - 1].get(updatedKeyValue);
				} else {

					a = countPermuation(numOfSwaps - 1, copy, updatedKeyValue);
					memory[numOfSwaps - 1].put(updatedKeyValue, a);
					sum += a;
				}
			}
		}
		return sum;
	}

	private static BigInteger updateKeyValue(BigInteger keyValue, int i, int j, int permutation[]) {
		double time1 = System.currentTimeMillis();
		BigInteger updatedKeyValue = keyValue.add(new BigInteger("0"));
		int diff1 = permutation[j] - permutation[i];
		BigInteger nplus1 = new BigInteger((n + 1) + "");
		updatedKeyValue = updatedKeyValue.add(nplus1.pow(i).multiply(new BigInteger("" + diff1)));

		int diff2 = permutation[i] - permutation[j];
		updatedKeyValue = updatedKeyValue.add(nplus1.pow(j).multiply(new BigInteger("" + diff2)));

		double time2 = System.currentTimeMillis();
		timeSum += (time2 - time1);
		return updatedKeyValue;
	}

	private static int[] copyArrayDeep(int[] permutation) {

		int[] copy = new int[n];
		for (int i = 0; i < n; i++) {
			copy[i] = permutation[i];
		}

		return copy;
	}

	private static BigInteger keyValueForPermutation(int[] permutation) {
		// double time1=System.currentTimeMillis();
		BigInteger keyValue = new BigInteger("0");
		for (int i = 0; i < n; i++) {
			BigInteger dummy = new BigInteger((n + 1) + "");
			dummy = dummy.pow(i).multiply(new BigInteger(permutation[i] + ""));
			keyValue = keyValue.add(dummy);
		}

		return keyValue;
	}

}
