package weekofcode23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class GravityTree {
	static int numberOfNodes;
	static Node[] nodes;

	public static void main(String[] args) throws FileNotFoundException {
		double time1 = System.currentTimeMillis();
		String pathOfTestCaseFile = "/home/christoph/Development2/HackerrankNotCleanedChallenges/TestData/GravityTree/GravityTreeTestData1000a.txt";
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
			nodes[i].setLevel(nodes[parentOfNode].getLevel() + 1);
			nodes[parentOfNode].childs.add(i);
		}

		int q = sc.nextInt();
		for (int i = 1; i <= q; i++) {
			int v = sc.nextInt();
			int u = sc.nextInt();
			long solution = solve(v, u);

			System.out.println(solution);
		}
		double time2 = System.currentTimeMillis();
		// System.out.println((time2 - time1) / 1000);
		sc.close();

	}

	private static long solve(int v, int u) {
		long solution = 0;
		// TODO recursion hier rausnehmen
		if (!isChild(u, v)) {
			// System.out.println(u + " ist ein kind von " + v);
			// alle distancen von v zu seinen kindern berechnen
			LinkedList<Integer> allChildsAndV = new LinkedList<>();
			// recursion hier rausnehmen
			allChilds(u, allChildsAndV);
			allChildsAndV.add(u);
			long distanceVU = distance(v, u);
			// System.out.println("distanceVU "+distanceVU);
			solution = sumOfDistance(allChildsAndV, distanceVU, u);

		} else {
			solution += sumOfDistanceForTree(v, 0);
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
				solution += (depth + 2) * (depth + 2);
				solution += sumOfDistanceForTree(child, depth + 2);
			}
		}

		return solution;
	}

	private static long sumOfDistanceForTree(int u, int depth) {
		long solution = 0;
		for (Integer child : nodes[u].getChilds()) {
			solution += (depth + 1) * (depth + 1);
			solution += sumOfDistanceForTree(child, depth + 1);

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
		if(nodes[u].getLevel()<=nodes[v].getLevel()){
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
