package cn.zx.website.qwi;

import static cn.zx.website.qwi.Constants.ELECTRON_CHARGE;
import static cn.zx.website.qwi.Constants.H_BAR;
import static cn.zx.website.qwi.Constants.STEP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.special.Erf;

public class FdmSolver {
	private QuantumWell qw;
	private double step = STEP;
	private int n;
	private Semiconductor[] composition;

	public FdmSolver(QuantumWell qw) {
		this.qw = qw;
		init();
	}

	public FdmSolver(QuantumWell qw, double step) {
		this.qw = qw;
		this.step = step;
		init();
	}

	private void init() {
		List<Semiconductor> list = new ArrayList<>();
		double z = -qw.getWellWidth() / 2 - qw.getBarrierWidth() / 2;
		while (z <= qw.getWellWidth() / 2 + qw.getBarrierWidth() / 2) {
			double iii = qw.getWell().getX()//
					+ ((qw.getBarrier().getX() - qw.getWell().getX()) / 2)//
					* (2 - Erf.erf((qw.getWellWidth() / 2 - z) / 2
							/ qw.getDiffusionLengthIII()) - Erf.erf((qw
							.getWellWidth() / 2 + z)
							/ 2
							/ qw.getDiffusionLengthIII()));
			double v = qw.getWell().getY()//
					+ ((qw.getBarrier().getY() - qw.getWell().getY()) / 2)//
					* (2 - Erf.erf((qw.getWellWidth() / 2 - z) / 2
							/ qw.getDiffusionLengthV()) - Erf.erf((qw
							.getWellWidth() / 2 + z)
							/ 2
							/ qw.getDiffusionLengthV()));
			list.add(new InGaAsP(iii, v));
			z += step;
		}
		composition = new Semiconductor[list.size()];
		for (int i = 0; i < composition.length; i++) {
			composition[i] = list.get(i);
		}
		n = composition.length;
	}

	public Semiconductor[] getComposition() {
		return composition;
	}

	public double[] getPotentialC() {
		double[] t = new double[n];
		for (int i = 0; i < n; i++) {
			t[i] = composition[i].bandOffsetSplittingRatioForX()
					* composition[i].energyBandGap()//
					- (1 - composition[i].bandOffsetSplittingRatioForX())
					* getSPerpendicular()[i];
		}
		return t;
	}

	public double[] getPotentialHH() {
		double[] t = new double[n];
		for (int i = 0; i < n; i++) {
			t[i] = (1 - composition[i].bandOffsetSplittingRatioForX())
					* (composition[i].energyBandGap() - getSPerpendicular()[i])//
					+ getSParallel()[i];
		}
		return t;
	}

	public double[] getPotentialLH() {
		double[] t = new double[n];
		for (int i = 0; i < n; i++) {
			t[i] = (1 - composition[i].bandOffsetSplittingRatioForX())
					* (composition[i].energyBandGap() - getSPerpendicular()[i])//
					+ 0.5
					* (getSParallel()[i] + composition[i].spinOrbitSplitting())//
					- 0.5
					* Math.sqrt(composition[i].spinOrbitSplitting()
							* composition[i].spinOrbitSplitting() - 2
							* getSParallel()[i]
							* composition[i].spinOrbitSplitting() + 9
							* getSParallel()[i] * getSParallel()[i]);
		}
		return t;
	}

	private double[] getEpsilongxx() {
		double[] t = new double[n];
		for (int i = 0; i < n; i++) {
			t[i] = new InGaAsP(1, 0).latticeConstant()
					/ composition[i].latticeConstant() - 1;
		}
		return t;
	}

	private double[] getAv() {
		double[] t = new double[n];
		for (int i = 0; i < n; i++) {
			t[i] = -1.0
					/ 3.0
					* (composition[i].elasticStiffnessConstantC11() - 2 * composition[i]
							.elasticStiffnessConstantC12())
					* composition[i].hydrostaticPressureCoefficient();
		}
		return t;
	}

	private double[] getSPerpendicular() {
		double[] t = new double[n];
		for (int i = 0; i < n; i++) {
			t[i] = -2
					* getAv()[i]
					* (1 - composition[i].elasticStiffnessConstantC12()
							/ composition[i].elasticStiffnessConstantC11())
					* getEpsilongxx()[i];
		}
		return t;
	}

	private double[] getSParallel() {
		double[] t = new double[n];
		for (int i = 0; i < n; i++) {
			t[i] = -composition[i].shearDeformationPotential()
					* (1 + 2 * composition[i].elasticStiffnessConstantC12()
							/ composition[i].elasticStiffnessConstantC11())
					* getEpsilongxx()[i];
		}
		return t;
	}

	public double[] getEnergyLevelC() {
		return getEnergyLevel(getPotentialC());
	}

	public double[] getEnergyLevelHH() {
		return getEnergyLevel(getPotentialHH());
	}

	public double[] getEnergyLevelLH() {
		return getEnergyLevel(getPotentialLH());
	}

	private double[] getA() {
		double[] t = new double[n];
		for (int i = 0; i < n - 1; i++) {
			t[i] = H_BAR
					* H_BAR
					/ STEP
					/ STEP
					/ (composition[i].electronEffectiveMass() + composition[i + 1]
							.electronEffectiveMass());
		}
		t[n - 1] = H_BAR * H_BAR / STEP / STEP / 2
				/ composition[n - 1].electronEffectiveMass();
		return t;
	}

	private double[] getB() {
		double[] t = new double[n];
		for (int i = 1; i < n; i++) {
			t[i] = H_BAR
					* H_BAR
					/ STEP
					/ STEP
					/ (composition[i].electronEffectiveMass() + composition[i - 1]
							.electronEffectiveMass());
		}
		t[1] = H_BAR * H_BAR / STEP / STEP / 2
				/ composition[0].electronEffectiveMass();
		return t;
	}

	private double[] getEnergyLevel(double[] potential) {
		double[][] arr = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					arr[i][j] = getA()[i] + getB()[i] + potential[i];
					arr[i][j] /= ELECTRON_CHARGE;
				} else if (i - j == 1) {
					arr[i][j] = -getA()[i];
					arr[i][j] /= ELECTRON_CHARGE;
				} else if (j - i == 1) {
					arr[i][j] = -getB()[i];
					arr[i][j] /= ELECTRON_CHARGE;
				} else {
					arr[i][j] = 0;
				}
			}
		}
		RealMatrix mat = new Array2DRowRealMatrix(arr);
		EigenDecomposition ed = new EigenDecomposition(mat);
		double[] ev = ed.getRealEigenvalues();
		for (int i = 0; i < n; i++) {
			ev[i] *= ELECTRON_CHARGE;
		}
		return ev;
	}

	public static void main(String[] args) {
		QuantumWell qw = new QuantumWell(5.5e-9, new InGaAsP(0.8, 0.8), 10e-9,
				new InGaAsP(0.74, 1));
		FdmSolver solver = new FdmSolver(qw, 0.1e-9);
		// System.out.println(Arrays.toString(solver.getComposition()));
		// System.out.println(Arrays.toString(solver.getPotentialC()));
		// System.out.println(Arrays.toString(solver.getPotentialHH()));
		// System.out.println(Arrays.toString(solver.getPotentialLH()));
		long start = System.currentTimeMillis();
		System.out.println(Arrays.toString(solver.getEnergyLevelC()));
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}
}