package util;

public class BodyCalculator {
	
	// 비만도지수 계산
	public double calculateBMI(double weight, double height) {
		double bmi = 0;
		
		bmi = (double) weight/((height/100)*(height/100));
		
		return bmi;
	}
	
	// 비만도지수 평가 계산
	public String measureBMI(double bmi) {
		String bmiResult = null;
		if(bmi >= 30) {
			bmiResult = "고도비만";
		} else if(bmi >= 25 && bmi < 30){
			bmiResult = "비만";
		} else if(bmi >= 23 && bmi < 25) {
			bmiResult = "과체중";
		} else if(bmi >= 18.5 && bmi < 23) {
			bmiResult = "정상체중";
		} else if(bmi < 18.5) {
			bmiResult = "저체중";
		}
		
		return bmiResult;
	}
	
	// 기초대사량 계산
	public double calculateBMR(String gender, double weight, double height, int age) {
		double bmr = 0;
		
		if(gender.equals("남")) {
			bmr = (66.47 + (13.75*weight) + (5*height) - (6.76*(double)age));
		} else if(gender.equals("여")) {
			bmr = (655.1 + (9.56*weight) + (1.85*height) - (4.68*(double)age));
		}
		
		return bmr;
	}
	
	// 몸무게별 칼로리 소모 계산
	public double calculateMET(double met, double weight, int min) {
		double kcal = 0;
		
		kcal = met * (3.5 * weight * min) / 1000 * 5;
		
		return kcal;
	}
	
	// 활동대사량 계산
	public double calculateRMR(String active, double bmr) {
		double rmr = 0;
		
		if(active.equals("활동 안함")) {
			rmr = (double) bmr * 0.2;
		} else if(active.equals("가벼운 활동")) {
			rmr = (double) bmr * 0.375;
		} else if(active.equals("보통 활동")) {
			rmr = (double) bmr * 0.555;
		} else if(active.equals("매우 활동")) {
			rmr = (double) bmr * 0.725;
		} else if(active.equals("심한 활동")) {
			rmr = (double) bmr * 0.9;
		}
		
		return rmr;
	}
	
	// 음식소화흡수열량 계산
	public double calculateTEF(double bmr, double rmr) {
		double tef = 0;
		
		tef = (bmr + rmr) / 9;
		
		return tef;
	}
	
}
