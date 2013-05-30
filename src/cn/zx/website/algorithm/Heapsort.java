package cn.zx.website.algorithm;

import java.util.Arrays;

public class Heapsort extends Sort {

	public Heapsort(double[] array) {
		super(array);
	}

	private double[] maxHeapfy(int i, int n) {
		// left and right children don't exist
		if (i * 2 > n) {
			return a;
		}
		// only left child exsits
		if (i * 2 == n) {
			if (a[i - 1] < a[i * 2 - 1]) {
				swap(i - 1, i * 2 - 1);
			}
			return a;
		}
		// left and right children both exist
		if (a[i * 2 - 1] >= a[i * 2] && a[i * 2 - 1] >= a[i - 1]) {
			swap(i - 1, i * 2 - 1);
			maxHeapfy(i * 2, n);
		} else if (a[i * 2] >= a[i * 2 - 1] && a[i * 2] >= a[i - 1]) {
			swap(i - 1, i * 2);
			maxHeapfy(i * 2 + 1, n);
		} else {
			return a;
		}
		return null;
	}

	private double[] buildMaxHeap(int n) {
		for (int i = n / 2; i > 0; i--) {
			maxHeapfy(i, n);
		}
		return a;
	}

	public double[] sort() {
		buildMaxHeap(a.length);
		for (int i = a.length - 1; i > 0; i--) {
			swap(i, 0);
			buildMaxHeap(i);
		}
		return a;
	}

	public static void main(String[] args) {
		double[] d = new double[] { 1, 5, 3, 2, 8, 3, 0, 9, 10, -10 };
		System.out.println(Arrays.toString(new Heapsort(d).sort()));
	}
}