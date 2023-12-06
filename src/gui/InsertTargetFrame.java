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

public class InsertTargetFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	
	private JLabel lblTitle;
	private JLabel lblTWeight;
	private JLabel lblTTerm;
	private JLabel lblActive;
	
	private JLabel lblactiveBtn1;
	private JLabel lblactiveBtn2;
	private JLabel lblactiveBtn3;
	private JLabel lblactiveBtn4;
	private JLabel lblactiveBtn5;
	
	private JTextField tfTWeight;
	private JTextField tfTTerm;
	
	private JRadioButton activeBtn1;
	private JRadioButton activeBtn2;
	private JRadioButton activeBtn3;
	private JRadioButton activeBtn4;
	private JRadioButton activeBtn5;
	private ButtonGroup activeGroupBtn;
	private JButton insertCompleteBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertTargetFrame frame = new InsertTargetFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InsertTargetFrame() {
		this(null);
	}
	
	public InsertTargetFrame(String userid) {
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
		lblTitle = new JLabel("목표 입력");
		Font f1 = new Font("돋움", Font.BOLD, 20);
		lblTitle.setFont(f1);
		lblTitle.setBounds(150, 30, 120, 30);
		contentPane.add(lblTitle);
		
		lblTWeight = new JLabel("목표 체중");
		Font f2 = new Font("돋움", Font.BOLD, 14); //궁서 바탕 돋움
		lblTWeight.setFont(f2);
		lblTWeight.setBounds(69, 113, 69, 20);
		contentPane.add(lblTWeight);
		
		lblTTerm = new JLabel("목표 기간");
		lblTTerm.setFont(f2);
		lblTTerm.setBounds(69, 163, 69, 20);
		contentPane.add(lblTTerm);
		
		lblActive = new JLabel("평소 활동량");
		lblActive.setFont(f2);
		lblActive.setBounds(69, 213, 99, 20);
		contentPane.add(lblActive);
		
		// 목표 체중
		tfTWeight = new JTextField();
		tfTWeight.setBounds(159, 106, 146, 35);
		contentPane.add(tfTWeight);
		
		// 목표 기간
		tfTTerm = new JTextField();
		tfTTerm.setBounds(159, 156, 146, 35);
		contentPane.add(tfTTerm);
		
		// 평소 활동량
		activeBtn1 = new JRadioButton("활동 안함");
		activeBtn2 = new JRadioButton("가벼운 활동");
		activeBtn3 = new JRadioButton("보통 활동");
		activeBtn4 = new JRadioButton("매우 활동");
		activeBtn5 = new JRadioButton("심한 활동");
		activeGroupBtn = new ButtonGroup();
		activeGroupBtn.add(activeBtn1);
		activeGroupBtn.add(activeBtn2);
		activeGroupBtn.add(activeBtn3);
		activeGroupBtn.add(activeBtn4);
		activeGroupBtn.add(activeBtn5);
		activeBtn1.setBounds(65, 240, 95, 30);
		activeBtn2.setBounds(65, 270, 95, 30);
		activeBtn3.setBounds(65, 300, 95, 30);
		activeBtn4.setBounds(65, 330, 95, 30);
		activeBtn5.setBounds(65, 360, 95, 30);
		contentPane.add(activeBtn1);
		contentPane.add(activeBtn2);
		contentPane.add(activeBtn3);
		contentPane.add(activeBtn4);
		contentPane.add(activeBtn5);
		
		lblactiveBtn1 = new JLabel("활동이 적거나 운동을 안함");
		lblactiveBtn2 = new JLabel("가벼운 활동이나 운동 주 1~3일");
		lblactiveBtn3 = new JLabel("보통의 활동이나 운동 주 3~5일");
		lblactiveBtn4 = new JLabel("적극적인 활동이나 운동 주 6~7일");
		lblactiveBtn5 = new JLabel("매우 심한 활동이나 물리적 작업");
		lblactiveBtn1.setBounds(175, 240, 180, 30);
		lblactiveBtn2.setBounds(175, 270, 180, 30);
		lblactiveBtn3.setBounds(175, 300, 180, 30);
		lblactiveBtn4.setBounds(175, 330, 180, 30);
		lblactiveBtn5.setBounds(175, 360, 180, 30);
		contentPane.add(lblactiveBtn1);
		contentPane.add(lblactiveBtn2);
		contentPane.add(lblactiveBtn3);
		contentPane.add(lblactiveBtn4);
		contentPane.add(lblactiveBtn5);
		
		// 일력 액션
		insertCompleteBtn = new JButton("입력");
        insertCompleteBtn.setBounds(146, 400, 129, 29);
		contentPane.add(insertCompleteBtn);
		insertCompleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Member member = new Member();
				
				double tWeight = Double.parseDouble(tfTWeight.getText());
				int tTerm = Integer.parseInt(tfTTerm.getText());
				String active = null;

				if(activeBtn1.isSelected()) {
					active = "활동 안함";
				} else if(activeBtn2.isSelected()) {
					active = "가벼운 활동";
				} else if(activeBtn3.isSelected()) {
					active = "보통 활동";
				} else if(activeBtn4.isSelected()) {
					active = "매우 활동";
				} else if(activeBtn5.isSelected()) {
					active = "심한 활동";
				}
		
				member.setTWeight(tWeight);
				member.setTTerm(tTerm);
				member.setActive(active);
				
				MemberDao dao = MemberDao.getInstance();
				
				// 각 입력 항목 공백 여부 확인한 뒤 목표 체중 입력
				if(tfTWeight.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"목표 체중을 입력해주세요.");
					tfTWeight.requestFocus(); // 포커스 이동
				} else if(tfTTerm.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"목표 기간을 입력해주세요.");
					tfTTerm.requestFocus(); // 포커스 이동
				} else if(activeBtn1.isSelected() == false && activeBtn2.isSelected() == false && activeBtn3.isSelected() == false && activeBtn4.isSelected() == false && activeBtn5.isSelected() == false) {
					JOptionPane.showMessageDialog(null,"활동량을 선택해주세요.");
				} else {
					if (dao.saveTargetInfo(member, userid)) {
						JOptionPane.showMessageDialog(null, "목표 체중 입력이 완료되었습니다.");
						new MenuFrame(userid);
						dispose(); // 현재 화면 종료
					} else {
						JOptionPane.showMessageDialog(null, "목표 체중 입력을 실패하였습니다.");
						
					}	
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
