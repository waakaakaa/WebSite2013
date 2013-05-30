package cn.zx.website.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Quicksort extends Sort {
	private static Random rand = new Random();;

	public Quicksort(double[] a) {
		super(a);
	}

	@Override
	public double[] sort() {
		quicksort(1, a.length);
		return a;
	}

	private void quicksort(int p, int r) {
		if (p < r) {
			int q = ranromizedPartition(p, r);
			quicksort(p, q - 1);
			quicksort(q + 1, r);
		}
	}

	private int partition(int p, int r) {
		double x = a[r - 1];
		int i = p - 2;
		for (int j = p - 1; j < r - 1; j++) {
			if (x >= a[j]) {
				i++;
				swap(i, j);
			}
		}
		swap(i + 1, r - 1);
		return i + 2;
	}

	private int ranromizedPartition(int p, int r) {
		int i = rand.nextInt(r - p) + p;
		swap(r - 1, i - 1);
		return partition(p, r);
	}

	public static void main(String[] args) {
		Sort s = new Quicksort(new double[] { 2, 8, 1000, 7, 1, 3, 5, 6, 4, 12,
				99, 5, 11 });
		System.out.println(Arrays.toString(s.sort()));
	}
}