package cn.zx.website.qwi;

public interface Semiconductor {
	double bandOffsetSplittingRatioForX();

	double energyBandGap();

	double spinOrbitSplitting();

	double electronEffectiveMass();

	double heavyHoleEffectiveMassPerpendicularToQwLayer();

	double lightHoleEffectiveMassParallelToQwLayer();

	double heavyHoleEffectiveMassParallelToQwLayer();

	double lightHoleEffectiveMassPerpendicularToQwLayer();

	double latticeConstant();

	double elasticStiffnessConstantC11();

	double elasticStiffnessConstantC12();

	double hydrostaticPressureCoefficient();

	double shearDeformationPotential();

	double dielectricFunction();

	double renewedStrengthTransitions();

	double nondispersionContribution();
	
	public double getX();
	
	public double getY();
};