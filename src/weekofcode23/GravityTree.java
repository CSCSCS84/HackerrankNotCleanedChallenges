package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class GravityTree {
	static int numberOfNodes;
	static Node[] nodes;

	public static void main(String[] args) throws FileNotFoundException {
		double time1 = System.currentTimeMillis();
		String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/GravityTree/GravityTreeTestDataOwn1b.txt";
		File file = new File(pathOfTestCaseFile);

		Scanner sc = new Scanner(file);
		numberOfNodes = sc.nextInt();
		// leave 0 entry empty
		nodes = new Node[numberOfNodes + 1];
		for (int i = 1; i <= numberOfNodes; i++) {
			nodes[i] = new Node();
		}

		for (int i = 2; i <= numberOfNodes; i++) {
			int parentOfNode = sc.nextInt();
			nodes[i].setParent(parentOfNode);
			// nodes[i].setLevel(nodes[parentOfNode].getLevel() + 1);
			nodes[parentOfNode].childs.add(i);
		}
		GravityTree.calcLevels();

		// for(int i=2;i<=numberOfNodes;i++){
		// System.out.println(nodes[i].getLevel());
		// }
		int q = sc.nextInt();
		for (int i = 1; i <= q; i++) {
			int v = sc.nextInt();
			int u = sc.nextInt();
			long solution = solve(v, u);

			System.out.println(solution);
		}
		double time2 = System.currentTimeMillis();
		sc.close();

	}

	private static void calcLevels() {
		Stack<Integer> stackOfChild = new Stack<>();
		stackOfChild.add(1);
		while (!stackOfChild.empty()) {
			int child = stackOfChild.pop();
			if (child != 1) {
				int parent = nodes[child].getParent();
				nodes[child].setLevel(nodes[parent].getLevel() + 1);
			}
			// long distance=nodes[child].getLevel()-levelOfU+distanceVU;
			// solution+=distance*distance;

			for (Integer childOfChild : nodes[child].getChilds()) {
				stackOfChild.add(childOfChild);
			}
		}

	}

	private static long solve(int v, int u) {
		long solution = 0;
		if (!isChild(u, v)) {

			long distanceVU = distance(v, u);
			solution = sumOfDistanceForTree(u, 0, distanceVU);

		} else {
			solution += sumOfDistanceForTree(v, 0, 0);
			solution += sumOfParent(v, 0, u);
		}
		// System.out.println();
		return solution;
	}

	private static long sumOfParent(int v, int depth, int u) {

		int parent = nodes[v].getParent();
		if (parent == 0) {
			return 0;
		}

		long solution = 0;
		solution += (depth + 1) * (depth + 1);
		if (parent != u) {
			solution += sumOfParent(parent, depth + 1, u);
		}
		for (Integer child : nodes[(int) parent].getChilds()) {
			if (child != v) {
				//solution += (depth + 2) * (depth + 2);
				solution += sumOfDistanceForTree(child, 0, depth+2);
			}
		}

		return solution;
	}

	private static long sumOfDistanceForTree(int u, int depth, long distanceVU) {
		long solution = 0;
		int levelOfU = nodes[u].getLevel();
		Stack<Integer> stackOfChild = new Stack<>();
		stackOfChild.add(u);
		while (!stackOfChild.empty()) {
			int child = stackOfChild.pop();
			long distance = nodes[child].getLevel() - levelOfU + distanceVU;
			solution += distance * distance;

			for (Integer childOfChild : nodes[child].getChilds()) {
				stackOfChild.add(childOfChild);
			}
		}
		return solution;
	}

	private static long sumOfDistance(LinkedList<Integer> allChildsAndV, long distanceVU, int u) {

		long solution = 0;
		// System.out.println("Diese Kinder sind interessant");
		for (Integer child : allChildsAndV) {
			// System.out.println(child);
			long qDist = (nodes[child].getLevel() - nodes[u].getLevel() + distanceVU);
			// System.out.println(qDist);
			qDist = qDist * qDist;
			solution += qDist;
		}
		return solution;
	}

	private static long distance(int v, int u) {
		long distance = 0;
		if (nodes[v].getLevel() > nodes[u].getLevel()) {
			while (nodes[v].getLevel() > nodes[u].getLevel()) {
				distance++;
				v = nodes[v].getParent();
			}
		}
		if (nodes[v].getLevel() < nodes[u].getLevel()) {
			while (nodes[v].getLevel() < nodes[u].getLevel()) {
				distance++;
				u = nodes[u].getParent();
			}
		}
		while (v != u) {
			distance += 2;
			v = nodes[v].getParent();
			u = nodes[u].getParent();

		}

		return distance;
	}

	private static void allChilds(int u, LinkedList<Integer> allChilds) {
		for (Integer child : nodes[u].getChilds()) {
			allChilds.add(child);
			allChilds(child, allChilds);
		}

	}

	// kontrolle, ob u child von v ist
	static boolean isChild(int v, int u) {
		if (nodes[u].getLevel() <= nodes[v].getLevel()) {
			return false;
		}
		int levelV = nodes[v].getLevel();

		while (nodes[u].getLevel() != levelV) {
			u = nodes[u].getParent();
		}
		if (u == v) {
			return true;
		} else {
			return false;
		}
	}

	private static class Node {
		public LinkedList<Integer> childs = new LinkedList<>();
		public int parent;
		public int level;

		public LinkedList<Integer> getChilds() {
			return childs;
		}

		public void setChilds(LinkedList<Integer> childs) {
			this.childs = childs;
		}

		public int getParent() {
			return parent;
		}

		public void setParent(int parent) {
			this.parent = parent;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

	}

}
