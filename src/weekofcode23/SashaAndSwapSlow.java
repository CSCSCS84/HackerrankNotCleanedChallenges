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

	public static void main(String[] args) throws FileNotFoundException {
		int numberOfInstances = 8;
		for (int t = 3; t <= 8; t++) {
			String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/SashaAndSwap/SashaAndSwapTestDataOwn"
					+ t + ".txt";
			File file = new File(pathOfTestCaseFile);

			Scanner sc = new Scanner(file);
			n = sc.nextInt();
			numbers = new int[n];
			for (int i = 0; i < n; i++) {
				numbers[i] = sc.nextInt();
			}
			SashaAndSwapSlow.solve();
			System.out.println();
		}

	}

	private static void solve() {
		for (int i = 1; i < n; i++) {
			long solution = solveForI(i);
			System.out.print(solution + " ");
		}
	}

	private static long solveForI(int k) {
		solution = 0;
		permutationsKey = new HashMap<>();
		memory = new HashMap[n];
		for (int i = 0; i < n; i++) {
			memory[i] = new HashMap<>();
		}
		long solution = countPermuation(k, numbers);
		return solution;

	}

	private static long countPermuation(int numOfSwaps, int[] permutation) {
		if (numOfSwaps == 0) {
			BigInteger keyValue = keyValueForPermutation(permutation);
			// hier die kontrolle ob permutation schon existiert
			// System.out.println(keyValue);
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
				BigInteger keyValue = keyValueForPermutation(copy);
				//System.out.println(keyValue);
				if (memory[numOfSwaps - 1].containsKey(keyValue)) {
					//a = memory[numOfSwaps - 1].containsKey(keyValue);
					a = memory[numOfSwaps - 1].get(keyValue);
				} else {

					a=countPermuation(numOfSwaps - 1, copy);
					memory[numOfSwaps - 1].put(keyValue, a);
					sum += a;
				}
			}
		}
		return sum;
	}

	private static int[] copyArrayDeep(int[] permutation) {
		int[] copy = new int[n];
		for (int i = 0; i < n; i++) {
			copy[i] = permutation[i];
		}
		return copy;
	}

	private static BigInteger keyValueForPermutation(int[] permutation) {
		BigInteger keyValue = new BigInteger("0");
		for (int i = 0; i < n; i++) {
			BigInteger dummy = new BigInteger((n + 1) + "");
			dummy = dummy.pow(i).multiply(new BigInteger(permutation[i] + ""));
			keyValue = keyValue.add(dummy);
		}
		return keyValue;
	}

}
