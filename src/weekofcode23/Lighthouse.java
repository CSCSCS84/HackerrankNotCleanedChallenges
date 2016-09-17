package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Lighthouse {

	static byte[][] grid;
	static int n;
	static LinkedList<Point> rocks = new LinkedList<Point>();

	public static void main(String[] args) throws FileNotFoundException {
		String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/Lighthouse/LighthouseTestData50c.txt";
		File file = new File(pathOfTestCaseFile);

		Scanner sc = new Scanner(file);
		n = sc.nextInt();
		sc.nextLine();
		grid = new byte[n][n];

		// read input
		for (byte i = 0; i < n; i++) {
			String line = sc.nextLine();
			for (byte j = 0; j < n; j++) {
				char s = line.charAt(j);
				if (s == '*') {
					grid[i][j] = 1;
					Point p = new Point(i, j);
					rocks.add(p);
				} else {
					grid[i][j] = 0;
				}

			}
		}

		int solution = Lighthouse.solve();
		System.out.println(solution);

	}

	private static int solve() {
		int maxRadius = 0;
		if (rocks.size() > 0) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (grid[i][j] == 0) {
						int radius = calcMaxRadiusForPoint(i, j);
						if (radius > maxRadius) {
							maxRadius = radius;
						}
					}
				}
			}
		} else {
			if (n % 2 != 0) {

				maxRadius = (n - 1) / 2;
			} else {
				maxRadius = (n - 2) / 2;
			}
		}
		return maxRadius;
	}

	private static int calcMaxRadiusForPoint(int i, int j) {
		int minRad = Integer.MAX_VALUE;
		minRad = minDistanceToBorder(i, j);

		for (Point p : rocks) {
			int dist = calcDistance(i, j, p);
			if (dist < minRad) {
				minRad = dist;
			}
		}

		return calcFirstSmallerInteger(minRad);
	}

	static int minDistanceToBorder(int i, int j) {
		int distLeft = (i+1) * (i+1);
		int distRight = (n - i) * (n - i);

		int distAbove = (j+1) * (j+1);
		int distBelow = (n - j) * (n - j);

		int min = Math.min(Math.min(distLeft, distRight), Math.min(distAbove, distBelow));

		return min;
	}

	static int calcFirstSmallerInteger(int number) {
		int max = 0;
		while ((max + 1) * (max + 1) < number) {
			max++;
		}
		return max;

	}

	static int calcDistance(int i, int j, Point p) {
		int distance = (i - p.getX()) * (i - p.getX()) + (j - p.getY()) * (j - p.getY());
		return distance;
	}

	static private class Point {
		byte x;
		byte y;

		public Point(byte i, byte j) {
			// TODO Auto-generated constructor stub
			this.x = i;
			this.y = j;
		}

		public byte getX() {
			return x;
		}

		public byte getY() {
			return y;
		}

	}

}
