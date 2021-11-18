package com.egil.pts.main;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		// read inputs
		// int maxSeries = scan.nextInt();
		int prev = 0;
		for (int i = 0; i <= 5; i++) {

			System.out.print(prev);
			System.out.println(prev + i);
			prev = i;

		}

	}
}
