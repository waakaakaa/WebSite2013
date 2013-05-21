package cn.zx.website.qwi;

public interface Semiconductor {
	double bandOffsetSplittingRatio(double x, double y);

	double energyBandGap(double x, double y);

	double spinOrbitSplitting(double x, double y);

	double electronEffectiveMass(double x, double y);

	double heavyHoleEffectiveMassPerpendicularToQwLayer(double x, double y);

	double lightHoleEffectiveMassParallelToQwLayer(double x, double y);

	double heavyHoleEffectiveMassParallelToQwLayer(double x, double y);

	double lightHoleEffectiveMassPerpendicularToQwLayer(double x, double y);

	double latticeConstant(double x, double y);

	double elasticStiffnessConstantC11(double x, double y);

	double elasticStiffnessConstantC12(double x, double y);

	double hydrostaticPressureCoefficient(double x, double y);

	double shearDeformationPotential(double x, double y);

	double dielectricFunction(double x, double y);

	double renewedStrengthTransitions(double x, double y);

	double nondispersionContribution(double x, double y);
	
	public double getX();
	
	public double getY();
};