package models;

public class WeightRecord {
	private String userid;       // 아이디 (외부키)
	private String wdate;        // 날짜
	private double intakekcal;   // 섭취 칼로리
	private double burnkcal;     // 소모 칼로리
	private String wcomment;     // 평가 문장
	private String wfeedback;    // 피드백
	
	public WeightRecord() {
		
	}
	
	public WeightRecord(String userid, String wdate, double intakekcal, double burnkcal, String wcomment, String wfeedback) {
		this.userid = userid;
		this.wdate = wdate;
		this.intakekcal = intakekcal;
		this.burnkcal = burnkcal;
		this.wcomment = wcomment;
		this.wfeedback = wfeedback;
//		this.setUserid(userid);
//		this.setWdate(wdate);
//		this.setIntakekcal(intakekcal);
//		this.setBurnkcal(burnkcal);
//		this.setWcomment(wcomment);
//		this.setWfeedback(wfeedback);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public double getIntakekcal() {
		return intakekcal;
	}

	public void setIntakekcal(double intakekcal) {
		this.intakekcal = intakekcal;
	}

	public double getBurnkcal() {
		return burnkcal;
	}

	public void setBurnkcal(double burnkcal) {
		this.burnkcal = burnkcal;
	}

	public String getWcomment() {
		return wcomment;
	}

	public void setWcomment(String wcomment) {
		this.wcomment = wcomment;
	}

	public String getWfeedback() {
		return wfeedback;
	}

	public void setWfeedback(String wfeedback) {
		this.wfeedback = wfeedback;
	}
}
