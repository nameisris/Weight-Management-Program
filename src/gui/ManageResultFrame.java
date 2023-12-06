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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.MemberDao;
import models.Member;
import models.WeightRecord;
import util.BodyCalculator;

public class ManageResultFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	
	private JLabel lblTitle;
	private JLabel lblComment1;
	private JLabel lblComment2;
	private JLabel lblFeedback;
	
	private JTextField tfFeedback;
	
	private JButton insertCompleteBtn;
	private JButton stopBtn;
	private JButton continueBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageResultFrame frame = new ManageResultFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ManageResultFrame() {
		this(null, 0, 0, null);
	}
	
	public ManageResultFrame(String userid, double totalEat, double totalActive, String wdate) {
		this.userid = userid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setSize(430, 490);
		setLocationRelativeTo(null); //윈도우 창을 화면의 가운데에 띄워줌
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// 타이틀
		lblTitle = new JLabel("관리 결과");
		Font f1 = new Font("돋움", Font.BOLD, 20); //궁서 바탕 돋움
		lblTitle.setFont(f1); 
		lblTitle.setBounds(159, 41, 101, 20); //위치와 크기 설정
		contentPane.add(lblTitle);
		
		// 현재 사용자 정보 가져오기
		MemberDao dao = MemberDao.getInstance();
		Member member = new Member();
		member = dao.getUserInfo(member, userid);
		
		// 사용자 정보로 각종 수치 계산
		BodyCalculator bc = new BodyCalculator();
		
		double bmr = bc.calculateBMR(member.getGender() + "", member.getWeight(), member.getHeight(), member.getAge()); //기초대사량
		double rmr = bc.calculateRMR(member.getActive(), bmr); //활동대사량
		double tef = bc.calculateTEF(bmr, rmr); //음식소화흡수열량
		double neededK = bmr + rmr + tef; //일 필요열량
		
		int tTerm = member.getTTerm(); //목표 기간
		double tWeight = member.getWeight() - member.getTWeight(); //목표체중
		double dateK = 7200 * (tWeight / tTerm); //일 목표소모 열량
		
		double targetK = neededK - dateK; //최종적인 하루 섭취 칼로리
		double resultK = totalEat - totalActive; //더먹으면 양수, 운동많이했음 음수

		lblComment1 = new JLabel();
		if(targetK - resultK >= 0) {
			lblComment1.setText("일 목표소모 열량인 " + String.format("%.1f", dateK) + "kcal보다");
		} else if(targetK - resultK < 0) {
			lblComment1.setText("일 목표소모 열량인 " + String.format("%.1f", dateK) + "kcal보다");
		}
		lblComment1.setBounds(100, 80, 200, 20);
		contentPane.add(lblComment1);

		lblComment2 = new JLabel();
		if(targetK - resultK >= 0) {
			if (resultK < 0) {
				lblComment2.setText(String.format("%.1f", targetK + resultK) + "kcal만큼 덜 먹었습니다.");
			} else if(resultK >= 0){
				lblComment2.setText(String.format("%.1f", targetK - resultK) + "kcal만큼 덜 먹었습니다.");
			}
		} else if(targetK - resultK < 0) {
			lblComment2.setText(String.format("%.1f", resultK - targetK) + "kcal만큼 더 먹었습니다.");
		}
		lblComment2.setBounds(100, 100, 200, 20);
		contentPane.add(lblComment2);
		
		lblFeedback = new JLabel("피드백 입력");
		lblFeedback.setFont(f1); 
		lblFeedback.setBounds(30, 160, 200, 20);
		contentPane.add(lblFeedback);
		
		// 피드백
		tfFeedback = new JTextField();
		tfFeedback.setBounds(30, 200, 370, 30);
		contentPane.add(tfFeedback);
		
		// 입력 액션
		insertCompleteBtn = new JButton("입력");
        insertCompleteBtn.setBounds(150, 250, 110, 30);
		contentPane.add(insertCompleteBtn);
		insertCompleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WeightRecord weightRecord = new WeightRecord();
				
				weightRecord.setWcomment(lblComment1.getText() + " " + lblComment2.getText());
				weightRecord.setWfeedback(tfFeedback.getText() + "");
				
				if (dao.saveWeightRecordComment(weightRecord, userid, wdate)) {
					JOptionPane.showMessageDialog(null, "피드백이 입력되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "피트백 입력을 실패하였습니다.");
				}	
			}
        	
        });
		
		// 그만하기 액션
		stopBtn = new JButton("그만하기");
		stopBtn.setBounds(30, 400, 120, 30);
		contentPane.add(stopBtn);
		stopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MenuFrame(userid);
			}
        	
        });
		
		// 계속하기 액션
		continueBtn = new JButton("계속하기");
		continueBtn.setBounds(260, 400, 120, 30);
		contentPane.add(continueBtn);
		continueBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ManageWeightFrame(userid, 0, 0);
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
