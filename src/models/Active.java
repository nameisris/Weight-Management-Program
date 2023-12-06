package models;

public class Active {
	private String activeName; // 활동명
	private double met; // MET 수치
	
	public Active() {
		
	}
	
	public Active(String activeName, double met) {
		this.activeName = activeName;
		this.met = met;
	}

	public double getMet() {
		return met;
	}

	public void setMet(double met) {
		this.met = met;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
}
