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

public class JoinFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblTitle;
	private JButton checkIDBtn;
	private JButton cancelBtn;
	private JButton joinCompleteBtn;
	private JTextField tfUserid;
	private JTextField tfPassword;
	private JTextField tfName;
	private JTextField tfEmail;
	private JTextField tfPhone;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinFrame frame = new JoinFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JoinFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 윈도우 창 종료 시 프로세스가 끝나도록 해줌
		setTitle("Weight Management Program"); // 제목 지정
		setSize(430, 490); // 크기 지정
		setLocationRelativeTo(null); // 윈도우 창을 화면의 가운데에 띄워줌
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 여백
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 타티틀
		lblTitle = new JLabel("회원가입");
		Font f1 = new Font("돋움", Font.BOLD, 20); // 궁서 바탕 돋움
		lblTitle.setFont(f1); 
		lblTitle.setBounds(159, 41, 101, 20); // 위치와 크기 설정
		contentPane.add(lblTitle);
		
		JLabel lblUserid = new JLabel("아이디");
		Font f2 = new Font("돋움", Font.BOLD, 14); // 궁서 바탕 돋움
		lblUserid.setFont(f2);
		lblUserid.setBounds(69, 113, 69, 20);
		contentPane.add(lblUserid);
		
		JLabel lblPassword = new JLabel("비밀번호");
		lblPassword.setFont(f2);
		lblPassword.setBounds(69, 163, 69, 20);
		contentPane.add(lblPassword);
		
		JLabel lblName = new JLabel("이름");
		lblName.setFont(f2);
		lblName.setBounds(69, 210, 69, 20);
		contentPane.add(lblName);
		
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setFont(f2);
		lblEmail.setBounds(69, 257, 69, 20);
		contentPane.add(lblEmail);
		
		JLabel lblPhone = new JLabel("전화번호");
		lblPhone.setFont(f2);
		lblPhone.setBounds(69, 304, 69, 20);
		contentPane.add(lblPhone);
		
		// 아이디
		tfUserid = new JTextField();
		tfUserid.setColumns(10);
		tfUserid.setBounds(159, 106, 101, 35);
		contentPane.add(tfUserid);
		
		// 비밀번호
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(159, 156, 186, 35);
		contentPane.add(tfPassword);
		
		// 이름
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(159, 203, 186, 35);
		contentPane.add(tfName);
		
		// 이름
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(159, 250, 186, 35);
		contentPane.add(tfEmail);
		
		// 전화번호
		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(159, 297, 186, 35);
		contentPane.add(tfPhone);
		
		// 중복 확인 액션
		checkIDBtn = new JButton("중복 확인");
		checkIDBtn.setBounds(260, 106, 85, 35);
		contentPane.add(checkIDBtn);
		checkIDBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemberDao dao = MemberDao.getInstance();
				
				if (tfUserid.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"ID를 입력해주세요.");
		            tfUserid.requestFocus(); // 포커스 이동
				} else {
					if (dao.checkID(tfUserid.getText())) {
						JOptionPane.showMessageDialog(null, tfUserid.getText()+"는 사용가능합니다.");  
					} else {
						JOptionPane.showMessageDialog(null, tfUserid.getText()+"는 중복입니다.");
						tfUserid.setText(""); // tfUserid의 text박스지우기
		                tfUserid.requestFocus(); // 포커스 이동
					}
				}
			}
			
		});
		
		// 회원가입 액션
		joinCompleteBtn = new JButton("회원가입");
		joinCompleteBtn.setBounds(225, 363, 120, 29);
		contentPane.add(joinCompleteBtn);
		joinCompleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Member member = new Member();
				member.setUserid(tfUserid.getText());
				member.setPassword(tfPassword.getText());
				member.setName(tfName.getText());
				member.setEmail(tfEmail.getText());
				member.setPhone(tfPhone.getText());
				
				MemberDao dao = MemberDao.getInstance();
				
				// 각 입력항목 공백여부 확인 및 아이디 중복여부 확인한 뒤 회원가입
				if(tfUserid.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"ID를 입력해주세요.");
		            tfUserid.requestFocus(); // 포커스 이동
				} else if(dao.checkID(tfUserid.getText()) == false) {
					JOptionPane.showMessageDialog(null, tfUserid.getText()+"는 중복입니다.");
					tfUserid.setText(""); // tfUserid의 text박스지우기
	                tfUserid.requestFocus(); // 포커스 이동
				} else if(tfPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"비밀번호를 입력해주세요.");
					tfPassword.requestFocus(); // 포커스 이동
				} else if(tfName.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"이름을 입력해주세요.");
					tfName.requestFocus(); // 포커스 이동
				} else if(tfEmail.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"이메일을 입력해주세요.");
					tfEmail.requestFocus(); // 포커스 이동
				} else if(tfPhone.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"전화번호를 입력해주세요.");
					tfPhone.requestFocus(); // 포커스 이동
				} else {
					if (dao.saveUserInfo(member)) {
						JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
						dispose();
						new LoginFrame();
					} else {
						JOptionPane.showMessageDialog(null, "기입된 내용이 잘못되었습니다.");
					}
				}
			}
		});
		
		cancelBtn = new JButton("취소");
		cancelBtn.setBounds(69, 363, 120, 29);
		contentPane.add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginFrame();
			}
			
		});

		setVisible(true);
	}
}
