package cn.zx.website.qwi;

public class QuantumWell {
	private double wellWidth;
	private Semiconductor well;
	private double barrierWidth;
	private Semiconductor barrier;
	private double diffusionLengthIII;
	private double diffusionLengthV;

	public QuantumWell(double wellWidth, Semiconductor well,
			double barrierWidth, Semiconductor barrier) {
		if (wellWidth <= 0 || barrierWidth <= 0) {
			throw new RuntimeException("Only positive width allowed!");
		}
		this.well = well;
		this.barrier = barrier;
		this.wellWidth = wellWidth;
		this.barrierWidth = barrierWidth;
	}

	public QuantumWell(double wellWidth, Semiconductor well,
			double barrierWidth, Semiconductor barrier,
			double diffusionLengthIII, double diffusionLengthV) {
		this(wellWidth, well, barrierWidth, barrier);
		this.diffusionLengthIII = diffusionLengthIII;
		this.diffusionLengthV = diffusionLengthV;
	}

	public double getWellWidth() {
		return wellWidth;
	}

	public Semiconductor getWell() {
		return well;
	}

	public double getBarrierWidth() {
		return barrierWidth;
	}

	public Semiconductor getBarrier() {
		return barrier;
	}

	public double getDiffusionLengthIII() {
		return diffusionLengthIII;
	}

	public double getDiffusionLengthV() {
		return diffusionLengthV;
	}

	@Override
	public String toString() {
		return "QuantumWell [wellWidth=" + wellWidth + ", well=" + well
				+ ", barrierWidth=" + barrierWidth + ", barrier=" + barrier
				+ ", diffusionLengthIII=" + diffusionLengthIII
				+ ", diffusionLengthV=" + diffusionLengthV + "]";
	}

	public static void main(String[] args) {
		QuantumWell qw = new QuantumWell(5.5e-9, new InGaAsP(0.8, 0.8), 10e-9,
				new InGaAsP(0.74, 1));
		System.out.println(qw);
	}
}