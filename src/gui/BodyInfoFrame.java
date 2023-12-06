package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.MemberDao;
import models.Member;
import util.BodyCalculator;

public class BodyInfoFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;

	private JLabel lblBodyTitle;
	private JLabel lblGender;
	private JLabel lblHeight;
	private JLabel lblWeight;
	private JLabel lblAge;
	private JLabel lblActive;
	
	private JLabel lblGenderIs;
	private JLabel lblHeightIs;
	private JLabel lblWeightIs;
	private JLabel lblAgeIs;
	private JLabel lblActiveIs;
	
	private JLabel lblBmiTitle;
	
	private JLabel lblBmi;
	private JLabel lblBmiIs;
	private JLabel lblBmiMeasure;
	
	private JLabel lblKcalTitle;
	
	private JLabel lblKcalTitleIs;
	
	private JLabel lblBmr;
	private JLabel lblBmrIs;
	private JLabel lblTef;
	private JLabel lblTefIs;
	private JLabel lblRmr;
	private JLabel lblRmrIs;
	
	private JLabel lblTKcal;
	private JLabel lblTKcalIs;
	
	private JLabel lblTActive;
	private JLabel lblTActiveIs;
	
	private JLabel lblTEat;
	private JLabel lblTEatIs;
	
	private JLabel lblTComment1;
	private JLabel lblTComment2;
	private JLabel lblTComment3;
	
	private JLabel lblResultTitle;
	private JLabel lblResultComment1;
	private JLabel lblResultComment2;
	private JLabel lblResultComment3;
	
	private JButton undoBtn;
	

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BodyInfoFrame frame = new BodyInfoFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BodyInfoFrame() {
		this(null);
	}
	
	public BodyInfoFrame(String userid) {
		this.userid = userid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setSize(430, 890);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// 신체 정보
		lblBodyTitle = new JLabel("신체 정보");
		Font f1 = new Font("돋움", Font.BOLD, 19);
		lblBodyTitle.setFont(f1);
		lblBodyTitle.setBounds(30, 20, 120, 30);
		contentPane.add(lblBodyTitle);
		
		lblGender = new JLabel("성별");
		Font f2 = new Font("돋움", Font.BOLD, 14);
		lblGender.setFont(f2);
		lblGender.setBounds(30, 60, 35, 30);
		contentPane.add(lblGender);
		
		lblHeight = new JLabel("키");
		lblHeight.setFont(f2);
		lblHeight.setBounds(140, 60, 35, 30);
		contentPane.add(lblHeight);
		
	    lblWeight = new JLabel("체중");
	    lblWeight.setFont(f2);
	    lblWeight.setBounds(270, 60, 35, 30);
		contentPane.add(lblWeight);

		lblAge = new JLabel("나이");
		lblAge.setFont(f2);
		lblAge.setBounds(30, 110, 35, 30);
		contentPane.add(lblAge);
		
		lblActive = new JLabel("활동량");
		lblActive.setFont(f2);
		lblActive.setBounds(140, 110, 45, 30);
		contentPane.add(lblActive);
		
		MemberDao dao = MemberDao.getInstance();
		Member member = new Member();
		member = dao.getUserInfo(member, userid);
		
		lblGenderIs = new JLabel(member.getGender());
		lblGenderIs.setFont(f2);
		lblGenderIs.setBounds(70, 60, 35, 30);
		contentPane.add(lblGenderIs);
		
		lblHeightIs = new JLabel(member.getHeight() + "cm");
		lblHeightIs.setFont(f2);
		lblHeightIs.setBounds(180, 60, 70, 30);
		contentPane.add(lblHeightIs);
		
	    lblWeightIs = new JLabel(member.getWeight() + "kg");
	    lblWeightIs.setFont(f2);
	    lblWeightIs.setBounds(310, 60, 70, 30);
		contentPane.add(lblWeightIs);

		lblAgeIs = new JLabel(member.getAge() + "");
		lblAgeIs.setFont(f2);
		lblAgeIs.setBounds(70, 110, 35, 30);
		contentPane.add(lblAgeIs);
		
		lblActiveIs = new JLabel(member.getActive());
		lblActiveIs.setFont(f2);
		lblActiveIs.setBounds(200, 110, 100, 30);
		contentPane.add(lblActiveIs);
		
		// BMI
		lblBmiTitle = new JLabel("비만도(BMI) 검사 결과");
		lblBmiTitle.setFont(f1);
		lblBmiTitle.setBounds(30, 160, 350, 30);
		contentPane.add(lblBmiTitle);
		
		lblBmi = new JLabel("BMI");
		lblBmi.setFont(f2);
		lblBmi.setBounds(30, 200, 350, 30);
		contentPane.add(lblBmi);
		
		lblBmiIs = new JLabel(member.getBmi() +"");
		lblBmiIs.setFont(f2);
		lblBmiIs.setBounds(100, 200, 350, 30);
		contentPane.add(lblBmiIs);
		
		BodyCalculator bc = new BodyCalculator();
		
		lblBmiMeasure = new JLabel(bc.measureBMI(member.getBmi()));
		lblBmiMeasure.setFont(f2);
		lblBmiMeasure.setBounds(170, 200, 350, 30);
		contentPane.add(lblBmiMeasure);
		
		// 일 필요열량
		lblKcalTitle = new JLabel("일 필요 열량");
		lblKcalTitle.setFont(f1);
		lblKcalTitle.setBounds(30, 250, 350, 30);
		contentPane.add(lblKcalTitle);
		
		lblBmr = new JLabel("기초 대사량");
		lblBmr.setFont(f2);
		lblBmr.setBounds(30, 290, 150, 30);
		contentPane.add(lblBmr);
		
		lblTef = new JLabel("음식소화흡수열량");
		lblTef.setFont(f2);
		lblTef.setBounds(30, 340, 150, 30);
		contentPane.add(lblTef);
		
		lblRmr = new JLabel("활동 대사량");
		lblRmr.setFont(f2);
		lblRmr.setBounds(30, 390, 150, 30);
		contentPane.add(lblRmr);
		
		double bmr = bc.calculateBMR(member.getGender() + "", member.getWeight(), member.getHeight(), member.getAge());
		double rmr = bc.calculateRMR(member.getActive(), bmr);
		double tef = bc.calculateTEF(bmr, rmr);
		double neededK = bmr + rmr + tef;
		
		lblKcalTitleIs = new JLabel(String.format("%.1f", neededK) + "kcal");
		lblKcalTitleIs.setFont(f1);
		lblKcalTitleIs.setBounds(160, 250, 350, 30);
		contentPane.add(lblKcalTitleIs);
		
		lblBmrIs = new JLabel(String.format("%.1f", bmr) + "kcal");
		lblBmrIs.setFont(f2);
		lblBmrIs.setBounds(120, 290, 150, 30);
		contentPane.add(lblBmrIs);
		
		lblTefIs = new JLabel(String.format("%.1f", tef) + "kcal");
		lblTefIs.setFont(f2);
		lblTefIs.setBounds(160, 340, 150, 30);
		contentPane.add(lblTefIs);
		
		lblRmrIs = new JLabel(String.format("%.1f", rmr) + "kcal");
		lblRmrIs.setFont(f2);
		lblRmrIs.setBounds(120, 390, 150, 30);
		contentPane.add(lblRmrIs);
		
		// 일 목표소모 열량
		lblTKcal = new JLabel("일 목표소모 열량");
		lblTKcal.setFont(f1);
		lblTKcal.setBounds(30, 440, 250, 30);
		contentPane.add(lblTKcal);
		
		lblTActive = new JLabel("운동으로 소모해야 할 칼로리");
		lblTActive.setFont(f2);
		lblTActive.setBounds(30, 480, 250, 30);
		contentPane.add(lblTActive);
		
		lblTEat = new JLabel("식사 조절로 줄여야 할 칼로리");
		lblTEat.setFont(f2);
		lblTEat.setBounds(30, 530, 250, 30);
		contentPane.add(lblTEat);
		
		int tTerm = member.getTTerm();
		double tWeight = member.getWeight() - member.getTWeight();
		double dateK = 7200 * (tWeight / tTerm);
		double activeK = dateK / 2;
		double eatK = dateK / 2;
		
		lblTKcalIs = new JLabel(String.format("%.1f", dateK) + "kcal");
		lblTKcalIs.setFont(f1);
		lblTKcalIs.setBounds(200, 440, 250, 30);
		contentPane.add(lblTKcalIs);
		
		lblTActiveIs = new JLabel(String.format("%.1f", activeK) + "kcal");
		lblTActiveIs.setFont(f2);
		lblTActiveIs.setBounds(230, 480, 250, 30);
		contentPane.add(lblTActiveIs);
		
		lblTEatIs = new JLabel(String.format("%.1f", eatK) + "kcal");
		lblTEatIs.setFont(f2);
		lblTEatIs.setBounds(230, 530, 250, 30);
		contentPane.add(lblTEatIs);
		
		// 목표 기준
		lblTComment1 = new JLabel("지금부터 " + tTerm + "일간 " + tWeight + "kg을 줄이기 위해서는");
		lblTComment1.setFont(f2);
		lblTComment1.setBounds(30, 580, 400, 30);
		contentPane.add(lblTComment1);
		
		lblTComment2 = new JLabel("매일 운동으로 " + String.format("%.1f", activeK) + "kcal를 소모해야 하고,");
		lblTComment2.setFont(f2);
		lblTComment2.setBounds(30, 600, 400, 30);
		contentPane.add(lblTComment2);
		
		lblTComment3 = new JLabel("하루에 " + String.format("%.1f", neededK - eatK) + "kcal 를 섭취하면 됩니다.");
		lblTComment3.setFont(f2);
		lblTComment3.setBounds(30, 620, 400, 30);
		contentPane.add(lblTComment3);
		
		lblResultTitle = new JLabel("종합 진단 결과");
		lblResultTitle.setFont(f1);
		lblResultTitle.setBounds(30, 670, 400, 30);
		contentPane.add(lblResultTitle);
		
		// 종합 진단
		lblResultComment1 = new JLabel("회원님의 현재 체중은 " + member.getWeight() + "kg이며");
		lblResultComment1.setFont(f2);
		lblResultComment1.setBounds(30, 710, 400, 30);
		contentPane.add(lblResultComment1);
		
		lblResultComment2 = new JLabel("비만도(BMI)는 " + member.getBmi() + "으로 " + bc.measureBMI(member.getBmi()) + "입니다.");
		lblResultComment2.setFont(f2);
		lblResultComment2.setBounds(30, 730, 400, 30);
		contentPane.add(lblResultComment2);

		lblResultComment3 = new JLabel("표준 체중은 " + String.format("%.1f", (member.getHeight() - 100) * 0.9) + "kg 입니다.");
		lblResultComment3.setFont(f2);
		lblResultComment3.setBounds(30, 750, 400, 30);
		contentPane.add(lblResultComment3);
		
		// 돌아가기 액션
		undoBtn = new JButton("돌아가기");
		undoBtn.setBounds(130, 800, 150, 30);
		contentPane.add(undoBtn);
		undoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new UserInfoFrame(userid);
			}
			
		});
		
		if (userid == null) {
			JOptionPane.showMessageDialog(null, "인증되지 않은 사용자입니다.");
			dispose();
		} else {
			setVisible(true);
		}
	}
}
