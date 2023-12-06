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

public class MenuFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	private JLabel lblTitle;
	private JButton weightManagementStartBtn;
	private JButton weightManagementListBtn;
	private JButton userInfoBtn;
	private JButton logoutBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame frame = new MenuFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MenuFrame() {
		this(null);
	}
	
	public MenuFrame(String userid) {
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
		lblTitle = new JLabel("체중 관리 프로그램");
		Font f1 = new Font("돋움", Font.BOLD, 30);
		lblTitle.setFont(f1); 
		lblTitle.setBounds(70, 50, 275, 40);
		contentPane.add(lblTitle);
		
		// 체중 관리 액션
		weightManagementStartBtn = new JButton("체중 관리");
		weightManagementStartBtn.setBounds(115, 150, 200, 30);
		contentPane.add(weightManagementStartBtn);
		weightManagementStartBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ManageWeightFrame(userid, 0, 0);
			}
		});
		
		// 체중 관리 내역 액션
		weightManagementListBtn = new JButton("체중 관리 내역");
		weightManagementListBtn.setBounds(115, 190, 200, 30);
		contentPane.add(weightManagementListBtn);
		weightManagementListBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new WeightRecordListFrame(userid);
				dispose();
			}
		});
		
		// 사용자 정보 내역 액션
		userInfoBtn = new JButton("사용자 정보");
		userInfoBtn.setBounds(115, 230, 200, 30);
		contentPane.add(userInfoBtn);
		userInfoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new UserInfoFrame(userid);
				dispose();
			}
		});
		
		// 로그아웃 액션
		logoutBtn = new JButton("로그아웃");
		logoutBtn.setBounds(115, 270, 200, 30);
		contentPane.add(logoutBtn);
		logoutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.CLOSED_OPTION) {
					
				} else if (result == JOptionPane.YES_OPTION) {
					dispose();
					new LoginFrame();
				} else {
					
				}
			}
		});
		
		// 로그인하지 않고 비정상적인 방법으로 실행했을 시 오류 메시지 출력 후 창 닫음
		if (userid == null) {
			JOptionPane.showMessageDialog(null, "인증되지 않은 사용자입니다.");
			dispose();
		} else {
			setVisible(true);
		}
	}
}
