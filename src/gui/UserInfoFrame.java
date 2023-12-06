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

public class UserInfoFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	private JLabel lblTitle;
	private JLabel lblUserid;
	private JLabel lblName;
	private JLabel lblEmail;
	private JLabel lblPhone;
	private JLabel lblUseridIs;
	private JLabel lblNameIs;
	private JLabel lblEmailIs;
	private JLabel lblPhoneIs;
	private JButton bodyInfoBtn;
	private JButton undoBtn;
	private JButton withdrawalBtn;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInfoFrame frame = new UserInfoFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public UserInfoFrame() {
		this(null);
	}
	
	public UserInfoFrame(String userid) {
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
		lblTitle = new JLabel("사용자 정보");
		Font f1 = new Font("돋움", Font.BOLD, 20);
		lblTitle.setFont(f1);
		lblTitle.setBounds(150, 30, 120, 30);
		contentPane.add(lblTitle);

		lblUserid = new JLabel("아이디");
		Font f2 = new Font("돋움", Font.BOLD, 14);
		lblUserid.setFont(f2);
		lblUserid.setBounds(60, 120, 70, 20);
		contentPane.add(lblUserid);
		
		lblName = new JLabel("이름");
		lblName.setFont(f2);
		lblName.setBounds(60, 170, 70, 20);
		contentPane.add(lblName);
		
		lblEmail = new JLabel("이메일");
		lblEmail.setFont(f2);
		lblEmail.setBounds(60, 220, 70, 20);
		contentPane.add(lblEmail);
		
		lblPhone = new JLabel("전화번호");
		lblPhone.setFont(f2);
		lblPhone.setBounds(60, 270, 70, 20);
		contentPane.add(lblPhone);
		
		// userid로 해당 사용자 정보 DB에서 가져오기
		MemberDao dao = MemberDao.getInstance();
		Member member = new Member();
		member = dao.getUserInfo(member, userid);
		
		// 아이디
		lblUseridIs = new JLabel(member.getUserid());
		lblUseridIs.setFont(f2);
		lblUseridIs.setBounds(170, 120, 300, 20);
		contentPane.add(lblUseridIs);
		
		// 이름
		lblNameIs = new JLabel(member.getName() + "");
		lblNameIs.setFont(f2);
		lblNameIs.setBounds(170, 170, 300, 20);
		contentPane.add(lblNameIs);
		
		// 이메일
		lblEmailIs = new JLabel(member.getEmail() + "");
		lblEmailIs.setFont(f2);
		lblEmailIs.setBounds(170, 220, 300, 20);
		contentPane.add(lblEmailIs);
		
		// 전화번호
		lblPhoneIs = new JLabel(member.getPhone() + "");
		lblPhoneIs.setFont(f2);
		lblPhoneIs.setBounds(170, 270, 300, 20);
		contentPane.add(lblPhoneIs);
		
	    // 신체 정보 액션
		bodyInfoBtn = new JButton("신체 정보");
		bodyInfoBtn.setBounds(160, 350, 120, 29);
		contentPane.add(bodyInfoBtn);
		bodyInfoBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BodyInfoFrame(userid);
			}
		});
		
		// 돌아가기 액션
		undoBtn = new JButton("돌아가기");
		undoBtn.setBounds(80, 400, 120, 29);
		contentPane.add(undoBtn);
		undoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MenuFrame(userid);
			}
			
		});
		
		// 회원탈퇴 액션
		withdrawalBtn = new JButton("회원탈퇴");
		withdrawalBtn.setBounds(230, 400, 120, 29);
		contentPane.add(withdrawalBtn);
		withdrawalBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new WithdrawalFrame(userid);
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
