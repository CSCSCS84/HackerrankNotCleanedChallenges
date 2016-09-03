package dynammicprogramming.oilwell;

import java.util.LinkedList;

import Util.UtilCS;



/**
 * Algorithm for solving the https://www.hackerrank.com/challenges/oil-well
 * 
 * Problem. This Recursive algorithm works as follows: A current border is given
 * and all OilWells in this border are set up. The algorithm chose then a not
 * set up OilWell to open. Note, that not all not set up OilWells have to be
 * checked. We check the region above, below, left, right, left above, left
 * below, right above, right below the current border. For above/below/left/
 * right the following statement proofs: If a OilWell above the current corner
 * is set up, only the OilWells with the minimum vertical distance to the
 * current upperBorder of the Border are interisting. If there are more than one
 * OilWell above the current corner, the minimum cost will be for all the same.
 * So for above/below/left/ right at most one OilWell is interisting to set up.
 * For the other 4 cases see the method searchDiagonal for more Details.
 * 
 * If the Border is updated all OilWells will be set up if not already done. In
 * an optimal solutions this has to be done because the cost for setting up a
 * OilWell increase if the border increase.
 * 
 * @author Christoph
 * 
 */
public class OilWellProblemRecursiveAlgorithm {

	private OilWellProblem oilWellProblem;
	// for a given border, memory stores the minimum cost we need to open the
	// OilWells that are not in the Border where [i][j][k][l] stores the cost
	// for border i,j,k,l
	int[][][][] memoryMinimumCostForSetUpAllOilWellsOutOfBorder;

	public static void main(String[] args) {

		int testCases[] = { 0, 2, 4, 6, 7, 8, 9, 10, 11 };
		int solutions[] = { 3, 22, 177, 1030, 7919, 3012, 639, 39952, 50086 };
		int i = 0;
		for (Integer testCase : testCases) {
			String pathOfTestCaseFile = "/home/christoph/Development2/HackerRank2/TestData/OilWell/OilWellTest"
					+ testCase + ".txt";
			OilWellProblem oilWellProblem = OilWellReader.readInput(pathOfTestCaseFile);
			OilWellProblemRecursiveAlgorithm ol = new OilWellProblemRecursiveAlgorithm();
			int sol = ol.solve(oilWellProblem);
			System.out.println(sol);
			if (sol != solutions[i]) {
				System.out.println("Falsche Lösung");
				return;
			}

			i++;
		}

	}

	public int solve(OilWellProblem oilWellProblem) {
		setMemoryMinimumCostForSetUpAllOilWellsOutOfBorder(new int[oilWellProblem.r][oilWellProblem.r][oilWellProblem.c][oilWellProblem.c]);
		this.oilWellProblem = oilWellProblem;
		int minSolutionValue = Integer.MAX_VALUE;

		// readInput(pathOfTestCaseFile);

		for (byte i = 0; i < oilWellProblem.getR(); i++) {
			for (byte j = 0; j < oilWellProblem.getC(); j++) {
				if (oilWellProblem.grid[i][j]) {
					byte[] borderOfSetUpWells = { i, i, j, j };

					int solution = minimumCostForSetUpAllOilWellsOutOfBorder(borderOfSetUpWells);

					if (solution < minSolutionValue) {
						minSolutionValue = solution;
					}
				}
			}
		}
		if (minSolutionValue == Integer.MAX_VALUE) {
			return 0;
		} else {
			return minSolutionValue;
		}
	}

	private int minimumCostForSetUpAllOilWellsOutOfBorder(byte[] borderOfSetUpWells) {

		int minnimumCostForSetUpAllOilWellsOutOfBorder = Integer.MAX_VALUE;

		LinkedList<OilWell> possibleOilWellsForNextSetUp = possibleOilWellsForNextSetUp(borderOfSetUpWells);

		if (possibleOilWellsForNextSetUp.size() == 0) {
			return 0;
		}
		for (OilWell oilWell : possibleOilWellsForNextSetUp) {

			int costSetUpNewOilWells = costSetUpOilWell(borderOfSetUpWells, oilWell.getVerticalPos(),
					oilWell.getHorizontalPos());
			byte[] updatedBorder = updateBorder(borderOfSetUpWells, oilWell.getVerticalPos(),
					oilWell.getHorizontalPos());
			costSetUpNewOilWells += costSetUpAllNewOilWellsInBorder(updatedBorder, borderOfSetUpWells,
					oilWell.getVerticalPos(), oilWell.getHorizontalPos(), oilWell.getPositionToCurrentBorder());

			int memoryCost = memoryCostForBorder(updatedBorder);
			int a = 0;
			if (0 == memoryCost) {
				a = minimumCostForSetUpAllOilWellsOutOfBorder(updatedBorder);
				setMemoryCostForBorder(updatedBorder, a);

			} else {
				a = memoryCost;
			}

			costSetUpNewOilWells += a;
			if (costSetUpNewOilWells < minnimumCostForSetUpAllOilWellsOutOfBorder) {
				minnimumCostForSetUpAllOilWellsOutOfBorder = costSetUpNewOilWells;
			}
		}

		return minnimumCostForSetUpAllOilWellsOutOfBorder;
	}

	private void setMemoryCostForBorder(byte[] updatedBorder, int a) {
		memoryMinimumCostForSetUpAllOilWellsOutOfBorder[updatedBorder[0]][updatedBorder[1]][updatedBorder[2]][updatedBorder[3]] = a;
	}

	private int memoryCostForBorder(byte[] border) {
		return memoryMinimumCostForSetUpAllOilWellsOutOfBorder[border[0]][border[1]][border[2]][border[3]];
	}

	/**
	 * Calculates the cost for setting up all OilWells the are in outerBorder
	 * but not in innerBorder and that are not on position
	 * (oilWellVerticalPos,oilWellHorizontalPos)
	 * 
	 * @param outerBorder
	 * @param innerBorder
	 * @param oilWellVerticalPos
	 * @param oilWellHorizontalPos
	 * @param positionOfOilWellToCurrentBorder
	 * @return
	 */
	private int costSetUpAllNewOilWellsInBorder(byte[] outerBorder, byte[] innerBorder, byte oilWellVerticalPos,
			byte oilWellHorizontalPos, byte positionOfOilWellToCurrentBorder) {

		int costSetUpNewOilWells = 0;
		if (positionOfOilWellToCurrentBorder <= 3) {
			costSetUpNewOilWells = costSetUpOilWellsNonDiagonal(outerBorder, oilWellVerticalPos, oilWellHorizontalPos,
					positionOfOilWellToCurrentBorder);
		} else {
			costSetUpNewOilWells = costSetUpOilWellsDiagonal(outerBorder, innerBorder, oilWellVerticalPos,
					oilWellHorizontalPos, positionOfOilWellToCurrentBorder);
		}

		return costSetUpNewOilWells;

	}

	/**
	 * SetUp all OilWells set are not inside border but inside border a,b,c,d
	 * and that are not on position (i,j)
	 * 
	 */
	private int setUpWellsInRange(byte a, byte b, byte c, byte d, byte cordI, byte cordJ, byte[] border) {
		int sumCostForSetUpOilWells = 0;
		for (byte i = a; i <= b; i++) {
			for (byte j = c; j <= d; j++) {
				if (oilWellProblem.getCeil(i, j)) {
					if (!(cordI == i && cordJ == j)) {
						byte cost = costSetUpOilWell(border, i, j);
						sumCostForSetUpOilWells += cost;
					}
				}
			}
		}
		return sumCostForSetUpOilWells;
	}

	private int costSetUpOilWellsDiagonal(byte[] border, byte[] oldBorder, byte cordI, byte cordJ, byte posWellAdded) {

		if (posWellAdded == 4) {
			return setUpWellsAboveLeft(border, oldBorder, cordI, cordJ);
		}
		if (posWellAdded == 5) {
			return setUpWellsBelowLeft(border, oldBorder, cordI, cordJ);
		}
		if (posWellAdded == 6) {
			return setUpWellsRightAbove(border, oldBorder, cordI, cordJ);
		}
		if (posWellAdded == 7) {
			return setUpWellsBelowRight(border, oldBorder, cordI, cordJ);
		}
		return 0;
	}

	private short setUpWellsBelowRight(byte[] border, byte[] oldBorder, byte cordI, byte cordJ) {
		short sumCostForSetUpOilWells = (short) setUpWellsInRange((byte) (oldBorder[1] + 1), border[1],
				(byte) (border[2]), oldBorder[3], cordI, cordJ, border);

		// right
		sumCostForSetUpOilWells += setUpWellsInRange((byte) (border[0]), border[1], (byte) (oldBorder[3] + 1),
				border[3], cordI, cordJ, border);
		return sumCostForSetUpOilWells;
	}

	private short setUpWellsRightAbove(byte[] border, byte[] oldBorder, byte cordI, byte cordJ) {
		short sumCostForSetUpOilWells = (short) setUpWellsInRange((byte) border[0], oldBorder[0], oldBorder[2],
				border[3], cordI, cordJ, border);

		// right
		sumCostForSetUpOilWells += setUpWellsInRange((byte) border[0], border[1], (byte) (oldBorder[3] + 1), border[3],
				cordI, cordJ, border);
		return sumCostForSetUpOilWells;
	}

	private short setUpWellsBelowLeft(byte[] border, byte[] oldBorder, byte cordI, byte cordJ) {
		short sumCostForSetUpOilWells = (short) setUpWellsInRange(border[0], border[1], border[2],
				(byte) (oldBorder[2] - 1), cordI, cordJ, border);

		sumCostForSetUpOilWells += setUpWellsInRange((byte) (oldBorder[1] + 1), border[1], oldBorder[2],
				(byte) border[3], cordI, cordJ, border);
		return sumCostForSetUpOilWells;
	}

	private short setUpWellsAboveLeft(byte[] border, byte[] oldBorder, byte cordI, byte cordJ) {
		// left
		short sumCostForSetUpOilWells = (short) setUpWellsInRange(border[0], border[1], border[2],
				(byte) (border[3] - 1), cordI, cordJ, border);

		// above
		sumCostForSetUpOilWells += setUpWellsInRange(border[0], (byte) (oldBorder[0] - 1), oldBorder[2],
				(byte) (border[3]), cordI, cordJ, border);
		return sumCostForSetUpOilWells;
	}

	public int costSetUpOilWellsNonDiagonal(byte[] border, byte cordI, byte cordJ, byte posWellAdded) {
		// above
		short sumOfCost = 0;
		if (posWellAdded == 0) {
			byte i = border[0];
			byte maxVer = (byte) (border[1] - border[0]);
			byte hor1 = 0;
			byte hor2 = (byte) (border[3] - border[2]);
			for (byte j = border[2]; j <= border[3]; j++) {
				if (oilWellProblem.getCeil(i, j)) {

					if (cordJ != j) {
						byte maxHor = UtilCS.max(hor1, hor2);
						byte cost = UtilCS.max(maxVer, maxHor);
						sumOfCost += cost;

					}

				}
				hor1++;
				hor2--;
			}
			return sumOfCost;
		}
		// below
		if (posWellAdded == 1) {
			byte i = border[1];
			byte maxVer = (byte) (border[1] - border[0]);
			byte hor1 = 0;
			byte hor2 = (byte) (border[3] - border[2]);
			for (byte j = border[2]; j <= border[3]; j++) {
				if (oilWellProblem.getCeil(i, j)) {
					if (cordJ != j) {
						byte maxHor = UtilCS.max(hor1, hor2);
						// int cost = costForAddingWell(border, i, j);
						byte cost = UtilCS.max(maxVer, maxHor);
						sumOfCost += cost;
					}
				}
				hor1++;
				hor2--;
			}
			return sumOfCost;
		}

		// left
		if (posWellAdded == 2) {
			byte j = border[2];
			byte maxHor = (byte) (border[3] - border[2]);
			byte vert1 = 0;
			byte vert2 = (byte) (border[1] - border[0]);
			for (byte i = border[0]; i <= border[1]; i++) {
				if (oilWellProblem.getCeil(i, j)) {
					if (cordI != i) {
						byte maxVer = UtilCS.max(vert1, vert2);
						// int cost = costForAddingWell(border, i, j);
						byte cost = UtilCS.max(maxVer, maxHor);
						sumOfCost += cost;
					}
				}
				vert1++;
				vert2--;
			}
			return sumOfCost;
		}

		// right
		if (posWellAdded == 3) {

			byte j = border[3];
			byte maxHor = (byte) (border[3] - border[2]);
			byte vert1 = 0;
			byte vert2 = (byte) (border[1] - border[0]);
			for (byte i = border[0]; i <= border[1]; i++) {
				if (oilWellProblem.getCeil(i, j)) {
					if (cordI != i) {
						byte maxVer = UtilCS.max(vert1, vert2);
						// int cost = costForAddingWell(border, i, j);
						byte cost = UtilCS.max(maxVer, maxHor);
						sumOfCost += cost;
					}
				}
				vert1++;
				vert2--;
			}
			return sumOfCost;
		}
		return sumOfCost;
	}

	// ca. 2.6 s
	private byte costSetUpOilWell(byte[] border, byte cordI, byte cordJ) {

		byte maxHor = UtilCS.max(UtilCS.absolute((byte) (cordI - (border[0]))),
				UtilCS.absolute((byte) (cordI - (border[1]))));
		byte maxVer = UtilCS.max(UtilCS.absolute((byte) (cordJ - (border[2]))),
				UtilCS.absolute((byte) (cordJ - (border[3]))));

		return UtilCS.max(maxHor, maxVer);
	}

	// wahrscheinlich ziemlich optimal
	// 0.3 s für Test11
	private byte[] updateBorder(byte[] border, byte pos1, byte pos2) {

		byte[] borderUpdate = new byte[4];

		if (pos1 < border[0]) {
			borderUpdate[0] = pos1;
		} else {
			borderUpdate[0] = border[0];
		}
		if (pos1 > border[1]) {
			borderUpdate[1] = pos1;
		} else {
			borderUpdate[1] = border[1];
		}
		if (pos2 < border[2]) {
			borderUpdate[2] = pos2;
		} else {
			borderUpdate[2] = border[2];
		}
		if (pos2 > border[3]) {
			borderUpdate[3] = pos2;
		} else {
			borderUpdate[3] = border[3];
		}

		return borderUpdate;
	}

	// für oben, unten, rechts links wird nur höchstens eine well geaddet
	// ca. 0.3 s für Test11
	private LinkedList<OilWell> possibleOilWellsForNextSetUp(byte[] border) {

		LinkedList<OilWell> wellsNotInSol = new LinkedList<OilWell>();
		byte[] diagonoalBorder = searchAboveBelowLeftRight(border, wellsNotInSol);
		searchDiagonal(border, wellsNotInSol, diagonoalBorder);
		return wellsNotInSol;

	}

	private byte searchVertical(byte a, byte b, byte c, byte d, LinkedList<OilWell> wellsNotInSol, byte steps,
			byte positionToBorder) {

		for (byte i = a; i * steps <= steps * b; i += steps) {
			for (byte j = c; j <= d; j++) {
				if (oilWellProblem.getCeil(i, j)) {
					OilWell well = new OilWell(i, j, positionToBorder);
					wellsNotInSol.add(well);
					return i;
				}
			}
		}
		return (byte) (b + steps);
	}

	private byte searchHoriconal(byte a, byte b, byte c, byte d, LinkedList<OilWell> wellsNotInSol, byte steps,
			byte positionToBorder) {

		for (byte j = (byte) c; j * steps <= d * steps; j += steps) {
			for (byte i = a; i <= b; i++) {
				if (oilWellProblem.getCeil(i, j)) {
					OilWell well = new OilWell(i, j, (byte) positionToBorder);
					wellsNotInSol.add(well);
					return j;
				}
			}
		}
		return (byte) (d + steps);
	}

	private byte[] searchAboveBelowLeftRight(byte[] border, LinkedList<OilWell> wellsNotInSol) {
		// look above the current border
		byte[] diagonoalBorder = new byte[4];
		diagonoalBorder[0] = searchVertical((byte) (border[0] - 1), (byte) 0, border[2], border[3], wellsNotInSol,
				(byte) -1, (byte) 0);

		// look below the current border
		diagonoalBorder[1] = searchVertical((byte) (border[1] + 1), (byte) (oilWellProblem.getR() - 1), border[2],
				border[3], wellsNotInSol, (byte) 1, (byte) 1);
		// look left the current border
		diagonoalBorder[2] = searchHoriconal(border[0], border[1], (byte) (border[2] - 1), (byte) 0, wellsNotInSol,
				(byte) -1, (byte) 2);
		// look right the current border
		diagonoalBorder[3] = searchHoriconal(border[0], border[1], (byte) (border[3] + 1),
				(byte) (oilWellProblem.getC() - 1), wellsNotInSol, (byte) 1, (byte) 3);

		return diagonoalBorder;
	}

	private void searchDiagonalHelper(LinkedList<OilWell> wellsNotInSol, byte startPos1, byte startPos2,
			byte upperBorderCopy, byte leftBorderCopy, byte step1, byte step2) {
		while (startPos1 > upperBorderCopy && startPos2 > leftBorderCopy) {
			boolean wellFound = false;
			for (byte i = startPos1; step1 * i < step1 * upperBorderCopy && !wellFound; i += step1) {
				if (oilWellProblem.getCeil(i, startPos2)) {
					OilWell well = new OilWell(i, startPos2, (byte) 4);
					wellsNotInSol.add(well);
					wellFound = true;

					upperBorderCopy = (byte) (i + 1);

				}
			}

			wellFound = false;
			for (byte j = startPos2;  j <  leftBorderCopy && !wellFound; j += 1) {

				if (oilWellProblem.getCeil(startPos1, j)) {
					OilWell well = new OilWell(startPos1, j, (byte) 4);
					wellsNotInSol.add(well);
					wellFound = true;
					leftBorderCopy = (byte) (j + 1);

				}

			}
			startPos1 += step1;
			startPos2 += step2;
		}
	}

	// 0.06 für Test11
	public void searchDiagonal(byte[] border, LinkedList<OilWell> wellsNotInSol, byte[] diagonalBorder) {
		byte startPos1 = (byte) (border[0] - 1);
		byte startPos2 = (byte) (border[2] - 1);

		// upper left corner
		byte upperBorderCopy = diagonalBorder[0];
		byte leftBorderCopy = diagonalBorder[2];
		searchDiagonalHelper(wellsNotInSol, startPos1, startPos2, upperBorderCopy, leftBorderCopy, (byte) -1, (byte) 1);

		// lower left corner
		startPos1 = (byte) (border[1] + 1);
		startPos2 = (byte) (border[2] - 1);
		leftBorderCopy = diagonalBorder[2];
		byte lowerBorderCopy = diagonalBorder[1];
		searchDiagonalHelper(wellsNotInSol, startPos1, startPos2, lowerBorderCopy, leftBorderCopy, (byte) 1,(byte) -1);

		// while (startPos1 < lowerBorderCopy && startPos2 > leftBorderCopy) {
		// boolean wellFound = false;
		// for (byte i = startPos1; i < lowerBorderCopy && !wellFound; i++) {
		// if (oilWellProblem.getCeil(i, startPos2)) {
		// OilWell well = new OilWell(i, startPos2, (byte) 5);
		// wellsNotInSol.add(well);
		// wellFound = true;
		// lowerBorderCopy = (byte) (i - 1);
		// }
		// }
		// wellFound = false;
		// for (byte j = startPos2; j > leftBorderCopy && !wellFound; j--) {
		// if (oilWellProblem.getCeil(startPos1, j)) {
		// OilWell well = new OilWell(startPos1, j, (byte) 5);
		// wellsNotInSol.add(well);
		// wellFound = true;
		// leftBorderCopy = (byte) (j + 1);
		// }
		// }
		// startPos1++;
		// startPos2--;
		// }
		//
		// // upper right corner
		startPos1 = (byte) (border[0] - 1);
		startPos2 = (byte) (border[3] + 1);
		upperBorderCopy = diagonalBorder[0];
		;
		byte rightBorderCopy = diagonalBorder[3];
		;
		while (startPos1 > upperBorderCopy && startPos2 < rightBorderCopy) {
			boolean wellFound = false;
			for (byte i = startPos1; i > upperBorderCopy && !wellFound; i--) {
				if (oilWellProblem.getCeil(i, startPos2)) {
					OilWell well = new OilWell(i, startPos2, (byte) 6);
					wellsNotInSol.add(well);
					wellFound = true;
					upperBorderCopy = (byte) (i + 1);
				}
			}
			wellFound = false;
			for (byte j = startPos2; j < rightBorderCopy && !wellFound; j++) {
				if (oilWellProblem.getCeil(startPos1, j)) {
					OilWell well = new OilWell(startPos1, j, (byte) 6);
					wellsNotInSol.add(well);
					wellFound = true;
					rightBorderCopy = (byte) (j - 1);
				}
			}
			startPos1--;
			startPos2++;
		}
		//
		// // lower right corner
		startPos1 = (byte) (border[1] + 1);
		startPos2 = (byte) (border[3] + 1);
		lowerBorderCopy = diagonalBorder[1];
		;
		rightBorderCopy = diagonalBorder[3];
		;
		while (startPos1 < lowerBorderCopy && startPos2 < diagonalBorder[3]) {
			boolean wellFound = false;
			for (byte i = startPos1; i < lowerBorderCopy && !wellFound; i++) {
				if (oilWellProblem.getCeil(i, startPos2)) {
					OilWell well = new OilWell(i, startPos2, (byte) 7);
					wellsNotInSol.add(well);
					wellFound = true;
					lowerBorderCopy = (byte) (i - 1);
				}
			}
			wellFound = false;
			for (byte j = startPos2; j < rightBorderCopy && !wellFound; j++) {
				if (oilWellProblem.getCeil(startPos1, j)) {
					OilWell well = new OilWell(startPos1, j, (byte) 7);
					wellsNotInSol.add(well);
					wellFound = true;
					rightBorderCopy = (byte) (j - 1);
				}
			}
			startPos1++;
			startPos2++;
		}

	}

	private void setMemoryMinimumCostForSetUpAllOilWellsOutOfBorder(
			int[][][][] memoryMinimumCostForSetUpAllOilWellsOutOfBorder) {
		this.memoryMinimumCostForSetUpAllOilWellsOutOfBorder = memoryMinimumCostForSetUpAllOilWellsOutOfBorder;
	}

}
