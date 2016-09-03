package dynammicprogramming.oilwell;


public class OilWell {

	byte verticalPos;
	byte horizontalPos;
	byte positionToCurrentBorder;

	public OilWell(byte verticalPos, byte horizontalPos, byte positionToCurrentBorder) {
		super();
		this.verticalPos = verticalPos;
		this.horizontalPos = horizontalPos;
		this.positionToCurrentBorder = positionToCurrentBorder;
	}

	public byte getVerticalPos() {
		return verticalPos;
	}

	public byte getHorizontalPos() {
		return horizontalPos;
	}

	public byte getPositionToCurrentBorder() {
		return positionToCurrentBorder;
	}

}