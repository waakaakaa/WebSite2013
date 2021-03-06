package cn.zx.website.domain;

import java.sql.Timestamp;

public class Weather {
	private int id;
	private Timestamp createDate;;
	private int humidity;
	private int temperature;

	public Weather() {
	}

	public Weather(Timestamp createDate, int humidity, int temperature) {
		this.createDate = createDate;
		this.humidity = humidity;
		this.temperature = temperature;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", createDate=" + createDate
				+ ", humidity=" + humidity + ", temperature=" + temperature
				+ "]";
	}
}