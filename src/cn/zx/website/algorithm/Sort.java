package cn.zx.website.algorithm;

import java.util.Arrays;

public abstract class Sort {
	protected double[] a;

	public Sort(double[] array) {
		this.a = array;
	}

	public abstract double[] sort();

	protected void swap(int i, int j) {
		double temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	@Override
	public String toString() {
		return "Sort [a=" + Arrays.toString(a) + "]";
	}

}