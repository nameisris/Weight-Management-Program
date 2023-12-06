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

public class WithdrawalFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	private JLabel lblTitle;
	private JLabel lblCheckPassword;
	private JLabel lblPassword;
	private JTextField tfPassword;
	private JButton cancelBtn;
	private JButton withdrawalBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WithdrawalFrame frame = new WithdrawalFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public WithdrawalFrame() {
		this(null);
	}

	public WithdrawalFrame(String userid) {
		this.userid = userid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setSize(400, 300);
		setLocationRelativeTo(null); //윈도우 창을 화면의 가운데에 띄워줌
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// 타이틀
		lblTitle = new JLabel("회원 탈퇴");
		Font f1 = new Font("돋움", Font.BOLD, 20);
		lblTitle.setFont(f1);
		lblTitle.setBounds(150, 30, 160, 30);
		contentPane.add(lblTitle);
		
		lblCheckPassword = new JLabel("비밀번호를 확인해주세요.");
		lblCheckPassword.setBounds(130, 80, 140, 20);
		contentPane.add(lblCheckPassword);
		
		lblPassword = new JLabel("비밀번호");
		lblPassword.setBounds(50, 120, 100, 20);
		contentPane.add(lblPassword);
		
		// 비밀번호
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(130, 115, 176, 35);
		contentPane.add(tfPassword);
		
		// 취소 액션
		cancelBtn = new JButton("취소");
		cancelBtn.setBounds(50, 180, 120, 30);
		contentPane.add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new UserInfoFrame(userid);
			}
			
		});
		
		// 탈퇴 액션
		withdrawalBtn = new JButton("탈퇴");
		withdrawalBtn.setBounds(200, 180, 120, 30);
		contentPane.add(withdrawalBtn);
		withdrawalBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MemberDao dao = MemberDao.getInstance();
				
				if(tfPassword.getText().equals("")) { // 비밀번호가 입력되지 않았다면
					JOptionPane.showMessageDialog(null,"비밀번호를 입력해주세요.");
					tfPassword.requestFocus(); // 포커스이동
				} else if(dao.checkPassword(tfPassword.getText())) { // 비밀번호가 맞다면
					//탈퇴할지 묻기
					int result = JOptionPane.showConfirmDialog(null, "탈퇴하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
					
					if (result == JOptionPane.CLOSED_OPTION) { // 탈퇴하지 않는다면
						
					} else if (result == JOptionPane.YES_OPTION) { // 탈퇴할 것이라면
						if(dao.deleteUser(userid)) { // 계정 삭제
							JOptionPane.showMessageDialog(null, "탈퇴를 완료하였습니다.");
							dispose();
							new LoginFrame();
						} else {
							JOptionPane.showMessageDialog(null, "탈퇴를 실패하였습니다.");
						}
					} else {
						
					}
				} else { // 비밀번호가 틀렸다면
					JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
					tfPassword.setText("");
					tfPassword.requestFocus(); // 포커스이동
				}
			}
			
		});
		
		setVisible(true);
	}

}
