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

public class ConfirmManagerFrame extends JFrame {
	
	private JPanel contentPane;
	private JLabel lblTitle;
	private JLabel lblCheckPassword;
	private JLabel lblPassword;
	private JTextField tfPassword;
	private JButton cancelBtn;
	private JButton confirmBtn;

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfirmManagerFrame frame = new ConfirmManagerFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ConfirmManagerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setSize(400, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// 타이틀
		lblTitle = new JLabel("관리자 모드");
		Font f1 = new Font("돋움", Font.BOLD, 20);
		lblTitle.setFont(f1);
		lblTitle.setBounds(150, 30, 160, 30);
		contentPane.add(lblTitle);
		
		lblCheckPassword = new JLabel("비밀번호를 입력해주세요.");
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
				new LoginFrame();
			}
			
		});
		
		// 확인 액션
		confirmBtn = new JButton("확인");
		confirmBtn.setBounds(200, 180, 120, 30);
		contentPane.add(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(tfPassword.getText().equals("")) { // 비밀번호가 입력되지 않았다면
					JOptionPane.showMessageDialog(null,"비밀번호를 입력해주세요.");
					tfPassword.requestFocus(); // 포커스이동
				} else if(tfPassword.getText().equals("1234")) { // 관리자 모드 비밀번호 1234
					dispose();
					new MemberListFrame();
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
