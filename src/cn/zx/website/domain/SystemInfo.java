package cn.zx.website.domain;

public class SystemInfo {
	private int memoryTotal;
	private int memoryUsed;
	private int swapTotal;
	private int swapUsed;
	private float cpuRate;
	private float temperature;

	public SystemInfo() {
	}

	public SystemInfo(int memoryTotal, int memoryUsed, int swapTotal,
			int swapUsed, float cpuRate, float temperature) {
		this.memoryTotal = memoryTotal;
		this.memoryUsed = memoryUsed;
		this.swapTotal = swapTotal;
		this.swapUsed = swapUsed;
		this.cpuRate = cpuRate;
		this.temperature = temperature;
	}

	public int getMemoryTotal() {
		return memoryTotal;
	}

	public void setMemoryTotal(int memoryTotal) {
		this.memoryTotal = memoryTotal;
	}

	public int getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(int memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public int getSwapTotal() {
		return swapTotal;
	}

	public void setSwapTotal(int swapTotal) {
		this.swapTotal = swapTotal;
	}

	public int getSwapUsed() {
		return swapUsed;
	}

	public void setSwapUsed(int swapUsed) {
		this.swapUsed = swapUsed;
	}

	public float getCpuRate() {
		return cpuRate;
	}

	public void setCpuRate(float cpuRate) {
		this.cpuRate = cpuRate;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "SystemInfo [memoryTotal=" + memoryTotal + ", memoryUsed="
				+ memoryUsed + ", swapTotal=" + swapTotal + ", swapUsed="
				+ swapUsed + ", cpuRate=" + cpuRate + ", temperature="
				+ temperature + "]";
	}

}