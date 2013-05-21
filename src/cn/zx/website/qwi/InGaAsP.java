package cn.zx.website.qwi;

import static cn.zx.website.qwi.Constants.ELECTRON_CHARGE;
import static cn.zx.website.qwi.Constants.ELECTRON_MASS;
import static cn.zx.website.qwi.Constants.VACUUM_PERMITTIVITY;

/**
 * All the parameters come from this paper: Djie, H. S., Ng, S. L., Gunawan, O.,
 * Dowd, P., Aimez, V., Beauvais, J., & Beerens, J. (2002). Analysis of
 * strain-induced polarisation-insensitive integrated waveguides fabricated
 * using ion-implantation-induced intermixing. IEE Proceedings-Optoelectronics,
 * 149(4), 138-144.
 * 
 * @author Administrator
 * 
 */
public class InGaAsP implements Semiconductor {
	private double x;
	private double y;

	public InGaAsP(double x, double y) {
		if (x < 0 || x > 1 || y < 0 || y > 1) {
			throw new RuntimeException("x or y out of range!");
		}
		this.x = x;
		this.y = y;
	}

	public InGaAsP(double q) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "InGaAsP [x=" + x + ", y=" + y + "]";
	}

	@Override
	public double bandOffsetSplittingRatio(double x, double y) {
		return 1.5;
	}

	@Override
	public double energyBandGap(double x, double y) {
		double val = 1.35 - 1.17 * y + 0.668 * (1 - x) - 0.069 * y * (1 - x) + 0.18 * y * y + 0.03 * (1 - x) * y * y + 0.758 * (1 - x) * (1 - x) - 0.322 * y * (1 - x) * (1 - x);
		return val * ELECTRON_CHARGE;
	}

	@Override
	public double spinOrbitSplitting(double x, double y) {
		return ELECTRON_CHARGE * playWithXY(0.34, 0.43, 0.1, 0.1, x, y);
	}

	@Override
	public double electronEffectiveMass(double x, double y) {
		return ELECTRON_MASS * playWithXY(0.0632, 0.0213, 0.17, 0.077, x, y);
	}

	@Override
	public double heavyHoleEffectiveMassPerpendicularToQwLayer(double x, double y) {
		return ELECTRON_MASS * playWithXY(0.5, 0.41, 0.54, 0.12, x, y);
	}

	@Override
	public double lightHoleEffectiveMassParallelToQwLayer(double x, double y) {
		return ELECTRON_MASS * playWithXY(0.23, 0.082, 0.34, 0.29, x, y);
	}

	@Override
	public double heavyHoleEffectiveMassParallelToQwLayer(double x, double y) {
		return ELECTRON_MASS * playWithXY(0.11, 0.031, 0.19, 0.15, x, y);
	}

	@Override
	public double lightHoleEffectiveMassPerpendicularToQwLayer(double x, double y) {
		return ELECTRON_MASS * playWithXY(0.088, 0.024, 0.16, 0.12, x, y);
	}

	@Override
	public double latticeConstant(double x, double y) {
		double val = playWithXY(5.6533, 6.0584, 5.4512, 5.8688, x, y);
		return val / 1e-10;// A --> m
	}

	@Override
	public double elasticStiffnessConstantC11(double x, double y) {
		double val = playWithXY(11.8, 8.329, 14.12, 10.22, x, y);
		return val * 10e11 * 10e-5 / 1e-4;// 10^11dyn/cm^2 --> N/m^2
	}

	@Override
	public double elasticStiffnessConstantC12(double x, double y) {
		double val = playWithXY(5.38, 4.526, 6.253, 5.76, x, y);
		return val * 10e11 * 10e-5 / 1e-4;// 10^11dyn/cm^2 --> N/m^2;
	}

	@Override
	public double hydrostaticPressureCoefficient(double x, double y) {
		double val = playWithXY(11.5, 10, 11, 8.5, x, y);
		return val * 10e-6 * ELECTRON_CHARGE / 10e5;// 10^-6eV/bar --> J/Pa;
	}

	@Override
	public double shearDeformationPotential(double x, double y) {
		return ELECTRON_CHARGE * playWithXY(-1.7, -1.8, -1.5, -2, x, y);
	}

	@Override
	public double dielectricFunction(double x, double y) {
		return VACUUM_PERMITTIVITY * playWithXY(0.11, 0.031, 0.19, 0.15, x, y);
	}

	@Override
	public double renewedStrengthTransitions(double x, double y) {
		return 8.4 - 3.4 * y;
	}

	@Override
	public double nondispersionContribution(double x, double y) {
		return 6.6 + 3.4 * y;
	}

	private double playWithXY(double a, double b, double c, double d, double x, double y) {
		return a * (1 - x) * y + b * x * y + c * (1 - x) * (1 - y) + d * x * (1 - y);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}
}