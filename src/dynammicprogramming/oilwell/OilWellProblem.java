package dynammicprogramming.oilwell;

public class OilWellProblem {
	
	// grid[i][j] is 1, if there is a OilWell on Position i,j
	boolean grid[][];
	byte r;
	byte c;

	public boolean[][] getGrid() {
		return grid;
	}

	public void setGrid(boolean[][] grid) {
		this.grid = grid;
	}

	public byte getR() {
		return r;
	}

	public void setR(byte r) {
		this.r = r;
	}

	public byte getC() {
		return c;
	}

	public void setC(byte c) {
		this.c = c;
	}
	
	public boolean getCeil(byte i, byte j){
		return grid[i][j];
	}

}
