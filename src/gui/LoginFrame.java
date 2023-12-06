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

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfUserid, tfPassword;
	private JButton loginBtn;
	private JButton joinBtn;
	private JButton manageBtn;

	public static void main(String[] args) {
		//프레임 장식
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setSize(400, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("아이디");
		Font f2 = new Font("돋움", Font.BOLD, 14);
		lblLogin.setFont(f2);
		lblLogin.setBounds(41, 52, 69, 35);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("비밀번호");
		lblPassword.setFont(f2);
		lblPassword.setBounds(41, 103, 69, 35);
		contentPane.add(lblPassword);
		
		// 아이디
		tfUserid = new JTextField();
		tfUserid.setColumns(10);
		tfUserid.setBounds(157, 52, 176, 35);
		contentPane.add(tfUserid);
		
		// 비밀번호
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(157, 103, 176, 35);
		contentPane.add(tfPassword);
		
		// 회원가입 액션
		joinBtn = new JButton("회원가입");
		joinBtn.setBounds(229, 154, 104, 29);
		contentPane.add(joinBtn);
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new JoinFrame();
				dispose();
			}
		});
		
		
		// 로그인 액션
		loginBtn = new JButton("로그인");
		loginBtn.setBounds(108, 154, 106, 29);
		contentPane.add(loginBtn);
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userid = tfUserid.getText();
				String password = tfPassword.getText();
				MemberDao dao = MemberDao.getInstance();
				
				//각 입력항목 공백여부 확인한 뒤 로그인
				if(tfUserid.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"ID를 입력해주세요.");
		            tfUserid.requestFocus(); //포커스 이동
				} else if(tfPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"비밀번호를 입력해주세요.");
					tfPassword.requestFocus(); //포커스 이동
				} else {
					if (dao.findByUserIDAndPassword(userid, password)) { //로그인
						if (dao.checkFirst(userid, password)) { //처음 로그인했다면
							//로그인 성공 메시지
							JOptionPane.showMessageDialog(null, "로그인 성공");
							//회원 정보 리스트 화면 이동과 동시에 userid 세션값으로 넘김.
							new InsertUserInfoFrame(userid);
							//현재 화면 종료
							dispose();
						} else { //처음 로그인한 것이 아니라면
							JOptionPane.showMessageDialog(null, "로그인 성공");
				//회원 정보 리스트 화면 이동과 동시에 userid 세션값으로 넘김.
							//MemberListFrame mlf = new MemberListFrame(userid);
							new MenuFrame(userid);
							//현재 화면 종료
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "로그인 실패");
					}
				}
				
			}
		});
		
		// 관리자 모드 액션
		manageBtn = new JButton("관리자 모드");
		manageBtn.setBounds(229, 194, 106, 29);
		contentPane.add(manageBtn);
		manageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ConfirmManagerFrame();
				dispose();
			}
			
		});
		
		setVisible(true);
	}
}