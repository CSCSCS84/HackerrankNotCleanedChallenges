package Util;

public class UtilCS {

	private long binomialCoefficient(int n, int k) {

		if (k == 0) {
			return 1;
		}
		if (k == 1) {
			return n;
		}
		if (n == 0 || k > n) {
			return 0;
		}
		long factorial1, factorial2, factorial3;
		factorial1 = factorial(n);
		factorial2 = factorial(k);
		factorial3 = factorial(n - k);

		return (factorial1 / (factorial2 * factorial3));
	}

	private long factorial(int k) {
		long factorial = 1;
		for (int i = 2; i <= k; i++) {
			factorial = factorial * i;
		}
		return factorial;
	}

	private long binomialCoefficientModulo(long n, long k, long modulo) {

		if (k == 0) {
			return 1;
		}
		if (k == 1) {
			return n;
		}
		if (n == 0 || k > n) {
			return 0;
		}

		long numerator = factorialNdividedByFactorialKModulo(n, k, modulo);
		long denominator = factorialModulo(k, modulo);
		long moduloInverse = moduloInverse(denominator, modulo);

		return (numerator * moduloInverse) % modulo;
	}

	private long factorialNdividedByFactorialKModulo(long n, long k, long modulo) {
		long factorial = 1;
		for (int i = 0; i < k; i++) {
			factorial = (factorial * (n - i)) % modulo;
		}
		return factorial;
	}

	private long factorialModulo(long k, long modulo) {
		long factorial = 1;
		for (int i = 2; i <= k; i++) {
			factorial = (factorial * i) % modulo;
		}
		return factorial;
	}

	private long moduloInverse(long number, long modulo) {
		return moduloPow(number, modulo - 2, modulo);
	}

	public long moduloPow(long x, long n, long modulo) {
		long result = 1;

		while (n > 0) {
			if (n % 2 != 0) {
				result = (result * x) % modulo;
			}

			x = (x * x) % modulo;
			n = n / 2;
		}

		return result;
	}

	private long moduloDivision(long numerator, long denominator, long modulo) {

		long moduloInverse = moduloInverse(denominator, modulo);
		return (numerator * moduloInverse) % modulo;

	}

	public static byte absolute(byte i) {
		if (i > 0) {
			return i;
		} else {
			return (byte) (i * (-1));
		}
	}

	public static byte max(byte a, byte b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	// calculates number of Primes smaller or equal to number
	private static long numOfPrim(long number) {

		long numOfPrime = 0;
		for (int i = 2; i <= number; i++) {
			if (isPrim(i)) {
				numOfPrime++;
			}
		}
		return numOfPrime;

	}

	private static boolean isPrim(int number) {
		if (number == 1 || number == 2) {
			return true;
		}

		boolean isPrim = true;
		long divisor = 2;

		while (divisor <= Math.sqrt(number)) {
			if (number % divisor == 0) {
				return false;
			}
			divisor += 1;
		}
		return isPrim;
	}
}
