package dynammicprogramming.oilwell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reader for the OilWell Problem
 * 
 * @author Christoph
 * 
 */
public class OilWellReader {
	static Scanner sc;

	static protected OilWellProblem readInput(String pathOfTestCaseFile) {

		createScannerForTestCaseFile(pathOfTestCaseFile);
		OilWellProblem oilWellProblem = createOilWellProblem();

		return oilWellProblem;

	}

	private static OilWellProblem createOilWellProblem() {
		
		OilWellProblem oilWellProblem = new OilWellProblem();
		byte r = sc.nextByte();
		byte c = sc.nextByte();
		oilWellProblem.setR(r);
		oilWellProblem.setC(c);
		//oilWellProblem.setMemoryMinimumCostForSetUpAllOilWellsOutOfBorder(new int[r][r][c][c]);

		boolean grid[][] = readGrid(r, c);
		oilWellProblem.setGrid(grid);
		
		return oilWellProblem;
	}

	private static void createScannerForTestCaseFile(String pathOfTestCaseFile) {
		File file = new File(pathOfTestCaseFile);
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static boolean[][] readGrid(byte r, byte c) {
		boolean grid[][] = new boolean[r][c];
		for (int i = 0; i < r; i++) {
			for (byte j = 0; j < c; j++) {
				int a = sc.nextByte();
				if (a == 1) {
					grid[i][j] = true;
				}
			}
		}
		return grid;
	}
}
