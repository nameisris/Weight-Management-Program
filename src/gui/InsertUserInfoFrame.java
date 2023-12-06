package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.MemberDao;
import models.Member;
import util.BodyCalculator;

public class InsertUserInfoFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	private JLabel lblTitle;
	private JLabel lblGender;
	private JLabel lblAge;
	private JLabel lblHeight;
	private JLabel lblWeight;
	private JTextField tfAge;
	private JTextField tfWeight;
	private JTextField tfHeight;
	private JRadioButton maleBtn;
    private JRadioButton femaleBtn;
    private ButtonGroup genderGroupBtn;
	private JButton insertCompleteBtn;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertUserInfoFrame frame = new InsertUserInfoFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public InsertUserInfoFrame() {
		this(null);
	}
	
	public InsertUserInfoFrame(String userid) {
		this.userid = userid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setSize(430, 490);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// 타이틀
		lblTitle = new JLabel("신체 정보 입력");
		Font f1 = new Font("돋움", Font.BOLD, 20);
		lblTitle.setFont(f1); 
		lblTitle.setBounds(135, 50, 150, 20);
		contentPane.add(lblTitle);
		
		lblGender = new JLabel("성별");
		Font f2 = new Font("돋움", Font.BOLD, 14);
		lblGender.setFont(f2);
		lblGender.setBounds(69, 115, 69, 20);
        contentPane.add(lblGender);
		
		lblAge = new JLabel("나이");
		lblAge.setFont(f2);
		lblAge.setBounds(69, 163, 69, 20);
		contentPane.add(lblAge);
		
		lblHeight = new JLabel("키");
		lblHeight.setFont(f2);
		lblHeight.setBounds(69, 210, 69, 20);
		contentPane.add(lblHeight);
		
		lblWeight = new JLabel("몸무게");
		lblWeight.setFont(f2);
		lblWeight.setBounds(69, 257, 69, 20);
		contentPane.add(lblWeight);
        
		// 나이
        tfAge = new JTextField();
        tfAge.setColumns(10);
        tfAge.setBounds(159, 156, 146, 35);
		contentPane.add(tfAge);
		
		// 키
		tfHeight = new JTextField();
		tfHeight.setColumns(10);
		tfHeight.setBounds(159, 203, 146, 35);
		contentPane.add(tfHeight);
		
		// 몸무게
		tfWeight = new JTextField();
		tfWeight.setColumns(10);
		tfWeight.setBounds(159, 250, 146, 35);
		contentPane.add(tfWeight);
		
		// 성별
		maleBtn = new JRadioButton("남자");
        femaleBtn = new JRadioButton("여자");
        genderGroupBtn = new ButtonGroup();
        genderGroupBtn.add(maleBtn);
        genderGroupBtn.add(femaleBtn);
        maleBtn.setBounds(160, 109, 95, 30);
        femaleBtn.setBounds(250, 109, 95, 30);
        contentPane.add(maleBtn);
        contentPane.add(femaleBtn);
        
        // 입력 액션
        insertCompleteBtn = new JButton("입력");
        insertCompleteBtn.setBounds(146, 324, 129, 29);
		contentPane.add(insertCompleteBtn);
		insertCompleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Member member = new Member();
				
				int age = Integer.parseInt(tfAge.getText());
				double weight = Double.parseDouble(tfWeight.getText());
				double height = Double.parseDouble(tfHeight.getText());
				
				BodyCalculator bc = new BodyCalculator();

				String gender = null;

				if(maleBtn.isSelected()) {
					gender = "남";
				}else if(femaleBtn.isSelected()) {
					gender = "여";
				}
		
				member.setGender(gender);
				member.setAge(age);
				member.setWeight(weight);
				member.setHeight(height);
				member.setBmi(bc.calculateBMI(weight, height));
				
				MemberDao dao = MemberDao.getInstance();
				
				// 각 입력 항목 공백 여부 확인한 뒤 사용자 정보 입력
				if(tfAge.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"나이를 입력해주세요.");
					tfAge.requestFocus();
				} else if(tfHeight.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"키를 입력해주세요.");
					tfHeight.requestFocus();
				} else if(tfWeight.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"몸무게를 입력해주세요.");
					tfHeight.requestFocus();
				} else if(maleBtn.isSelected() == false && femaleBtn.isSelected() == false) {
					JOptionPane.showMessageDialog(null,"성별을 선택해주세요.");
				} else {
					if (dao.saveBodyInfo(member, userid)) {
						JOptionPane.showMessageDialog(null, "신체 정보 입력이 완료되었습니다.");
						dispose();
						new InsertTargetFrame(userid);
					} else {
						JOptionPane.showMessageDialog(null, "신체 정보 입력을 실패하였습니다.");
					}	
				}
			}
        	
        });
        
        maleBtn.setOpaque(false);
        femaleBtn.setOpaque(false);
        
		if (userid == null) {
			JOptionPane.showMessageDialog(null, "인증되지 않은 사용자입니다.");
			dispose();
		} else {
			setVisible(true);
		}
	}
}
