package cn.zx.website.qwi;

import static cn.zx.website.qwi.Constants.STEP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.special.Erf;

public class QuantumWell {
	private double wellWidth;
	private Semiconductor well;
	private double barrierWidth;
	private Semiconductor barrier;

	public QuantumWell(double wellWidth, Semiconductor well, double barrierWidth, Semiconductor barrier) {
		if (wellWidth <= 0 || barrierWidth <= 0) {
			throw new RuntimeException("Only positive width allowed!");
		}
		this.well = well;
		this.barrier = barrier;
		this.wellWidth = wellWidth;
		this.barrierWidth = barrierWidth;
	}

	public double[][] diffuse(double diffusionLengthIII, double diffusionLengthV) {
		List<Double> listX = new ArrayList<>();
		List<Double> listIII = new ArrayList<>();
		List<Double> listV = new ArrayList<>();
		double z = -wellWidth / 2 - barrierWidth;
		while (z <= wellWidth / 2 + barrierWidth) {
			listX.add(z);
			listIII.add(well.getX() + ((barrier.getX() - well.getX()) / 2) * (2 - Erf.erf((wellWidth / 2 - z) / 2 / diffusionLengthIII) - Erf.erf((wellWidth / 2 + z) / 2 / diffusionLengthIII)));
			listV.add(well.getY() + ((barrier.getY() - well.getY()) / 2) * (2 - Erf.erf((wellWidth / 2 - z) / 2 / diffusionLengthV) - Erf.erf((wellWidth / 2 + z) / 2 / diffusionLengthV)));
			z += STEP;
		}
		double[] arrX = new double[listX.size()];
		double[] arrIII = new double[listX.size()];
		double[] arrV = new double[listX.size()];
		for (int i = 0; i < arrX.length; i++) {
			arrX[i] = listX.get(i);
			arrIII[i] = listIII.get(i);
			arrV[i] = listV.get(i);
		}
		return new double[][] { arrX, arrIII, arrV };
	}

	public static void main(String[] args) {
		QuantumWell qw = new QuantumWell(5.5e-9, new InGaAsP(0.8, 0.8), 10e-9, new InGaAsP(0.74, 1));
		System.out.println(Arrays.deepToString(qw.diffuse(0, 0)));
		System.out.println(Arrays.deepToString(qw.diffuse(1e-9, 0)));
		System.out.println(Arrays.deepToString(qw.diffuse(2e-9, 0)));
	}
}