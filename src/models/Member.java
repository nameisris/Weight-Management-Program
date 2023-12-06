package models;

import java.sql.Timestamp;

public class Member {
	private String userid;          // 아이디
	private String password;        // 비밀번호
	private String name;            // 이름
	private String email;           // 이메일
	private String phone;           // 전화번호
	private String gender;          // 성별
	private int age;                // 나이
	private double weight;          // 몸무게
	private double height;          // 키
	private double bmi;             // 비만도
	private double tWeight;         // 목표체중
	private int tTerm;              // 목표기간
	private String active;          // 활동정도
	private Timestamp createDate;   // 생성일

	public Member() {
	
	}
	
	public Member(String userid, String password, String name, String email, String phone, String gender,
			int age, double weight, double height, double bmi, double tWeight, int tTerm, String active, Timestamp createDate) {	
		this.userid = userid;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.age = age;
		this.weight = weight;
		this.bmi = bmi;
		this.tWeight = tWeight;
		this.tTerm = tTerm;
		this.active = active;
		this.createDate = createDate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}
	
	public double getTWeight() {
		return tWeight;
	}

	public void setTWeight(double tWeight) {
		this.tWeight = tWeight;
	}
	
	public int getTTerm() {
		return tTerm;
	}

	public void setTTerm(int tTerm) {
		this.tTerm = tTerm;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}

