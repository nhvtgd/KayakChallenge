package com.example.kayakchallenge.helper;

public class Temperature {
	private String high;
	private String low;
	public Temperature(String high, String low) {
		this.setHigh(high);
		this.setLow(low);
	}
	public String getHigh() {
		if (this.high.equals("null") || this.high.length() <= 0)
			this.setHigh("Unavailable");
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		if (this.low.equals("null") || this.low.length() <= 0)
			this.setLow("Unavailable");
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	
	@Override
	public String toString() {
		return "High: " + getHigh() +", " + "Low: " + getLow();
	}
	
}
