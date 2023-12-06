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
import models.WeightRecord;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


public class ManageWeightFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	private JLabel lblTitle;
	private JLabel lblDate;
	private JLabel lblIntake;
	private JLabel lblActive;
	private JTextField tfIntake;
	private JTextField tfActive;
	private JButton intakeBtn;
	private JButton activeBtn;
	private JButton insertCompleteBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageWeightFrame frame = new ManageWeightFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ManageWeightFrame() {
		this(null, 0, 0);
	}
	
	public ManageWeightFrame(String userid, double totalEat, double totalActive) {
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
		lblTitle = new JLabel("체중 관리");
		Font f1 = new Font("돋움", Font.BOLD, 20); //궁서 바탕 돋움
		lblTitle.setFont(f1); 
		lblTitle.setBounds(159, 41, 101, 20); //위치와 크기 설정
		contentPane.add(lblTitle);
		
		lblDate = new JLabel("날짜");
		Font f2 = new Font("돋움", Font.BOLD, 14);
		lblDate.setFont(f2);
		lblDate.setBounds(69, 101, 59, 20);
		contentPane.add(lblDate);

		lblIntake = new JLabel("섭취량");
		lblIntake.setFont(f2);
		lblIntake.setBounds(69, 151, 59, 20);
		contentPane.add(lblIntake);
		
		lblActive = new JLabel("활동량");
		lblActive.setFont(f2);
		lblActive.setBounds(69, 201, 59, 20);
		contentPane.add(lblActive);
		
		// 섭취량
		tfIntake = new JTextField(totalEat + "kcal");
		tfIntake.setBounds(159, 148, 121, 30);
		contentPane.add(tfIntake);
		
		// 활동량
		tfActive = new JTextField(totalActive + "kcal");
		tfActive.setBounds(159, 198, 121, 30);
		contentPane.add(tfActive);
		
		// JDatePicker 라이브러리 사용
		//https://mvnrepository.com/artifact/net.sourceforge.jdatepicker/jdatepicker/1.3.2
		//https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(159, 99, 151, 30);
		contentPane.add(datePicker);
		
		// 섭취량 +(추가) 액션
		intakeBtn = new JButton("+");
		intakeBtn.setBounds(280, 148, 30, 29);
		contentPane.add(intakeBtn);
		intakeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new IntakeKcalFrame(userid, totalEat, totalActive);
			}
		});
		
		// 활동량 +(추가) 액션
		activeBtn = new JButton("+");
		activeBtn.setBounds(280, 198, 30, 29);
		contentPane.add(activeBtn);
		activeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BurnKcalFrame(userid, totalEat, totalActive);
			}
		});
		
		// 입력 액션
		insertCompleteBtn = new JButton("입력");
        insertCompleteBtn.setBounds(146, 400, 129, 29);
		contentPane.add(insertCompleteBtn);
		insertCompleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MemberDao dao = MemberDao.getInstance();
				WeightRecord weightRecord = new WeightRecord();
				
				weightRecord.setUserid(userid);
				weightRecord.setWdate(model.getYear() + "-" + (model.getMonth() + 1) + "-" + model.getDay());
				weightRecord.setIntakekcal(Double.parseDouble(tfIntake.getText().replaceAll("kcal", "")));
				weightRecord.setBurnkcal(Double.parseDouble(tfActive.getText().replaceAll("kcal", "")));
				
				if(dao.saveWeightRecord(weightRecord)) {
					JOptionPane.showMessageDialog(null, "칼로리 입력이 완료되었습니다.");
					dispose();
					new ManageResultFrame(userid, totalEat, totalActive, model.getYear() + "-" + (model.getMonth() + 1) + "-" + model.getDay());
				} else {
					JOptionPane.showMessageDialog(null, "칼로리 입력을 실패하였습니다.");
				}	
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
