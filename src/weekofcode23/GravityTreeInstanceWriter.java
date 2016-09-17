package weekofcode23;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;

public class GravityTreeInstanceWriter {
	static int numberOfNodes = 500000;
	static int q = 500000;

	public static void main(String[] args) {
		Writer writer = null;

		try {
			String filename = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/GravityTree/NeueInstanzen/GravityTreeTestData500000a2.txt";
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
			String input = "";
			input = "" + numberOfNodes;
			input += "\n";
			input += "" + inputA();
			input += "\n";
			input += "" + q;
			input += "\n";
			input += calcQs(q);
			writer.write(input);
		} catch (IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {/* ignore */
			}
		}
	}

	// input where u is a child of v
	private static StringBuffer inputA() {
		StringBuffer str = new StringBuffer();
		int[] parents = new int[numberOfNodes - 1];
		for (int i = 2; i <= parents.length + 1; i++) {
			double a = Math.random() * i;
			parents[i - 2] = (int) a;
			if (parents[i - 2] == 0) {
				parents[i - 2] = 1;
			}
		}
		Arrays.sort(parents);
		for (int i = 0; i < parents.length; i++) {
			if (parents[i] != 0) {
				str.append(parents[i] + " ");
			}
		}
		return str;
	}

	private static StringBuffer calcQs(int q) {
		StringBuffer str = new StringBuffer();
		for (int i = 1; i <= q; i++) {
			int a = (int) (Math.random() * numberOfNodes);
			if (a == 0) {
				a = 1;
			}
			int b = (int) (Math.random() * numberOfNodes);
			if (b == 0) {
				b = 1;
			}
			if (a < b) {
				str.append( a + " " + b);
				str.append( "\n");

			} else if (b < a) {
				str.append( b + " " + a);
				str.append( "\n");
			}
			if (b == a) {
				i--;
			}
		}
		return str;
	}

}
