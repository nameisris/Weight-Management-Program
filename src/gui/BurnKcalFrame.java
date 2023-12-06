package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.MemberDao;
import models.Active;
import models.Member;
import util.BodyCalculator;

public class BurnKcalFrame extends JFrame {
	
	private String userid;
	private double totalKcal;
	
	private JPanel contentPane;
	
	private JLabel lblTitle;
	private JLabel lblMin;
	
	private String[] actives = {"누워서 아무것도 안하기", "잠자기", "대화", "서 있기", "가벼운 물건 나르기", "당구", "걷기", "자전거",
			"가벼운 활동", "웨이트 트레이닝", "볼링", "배구", "수중운동", "수중체조", "탁구", "태극권", "배드민턴", "골프", "발레", "트위스트",
			"탭댄스", "소프트볼", "야구", "피구", "체조", "미용체조", "재즈댄스", "농구", "수영", "오르막길 걷기", "에어로빅", "조깅", "축구",
			"테니스", "스케이트", "스키", "등산", "유도", "유술", "킥복싱", "태권도", "럭비", "마라톤", "뛰어서 계단오르기"};
	private JComboBox activeCombo;
	
	private JTextField tfActiveMin;
	
	private JButton addBtn;
	private JButton undoBtn;
	private JButton insertCompleteBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BurnKcalFrame frame = new BurnKcalFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BurnKcalFrame() {
		this(null, 0, 0);
	}
	
	public BurnKcalFrame(String userid, double totalEat, double totalActive) {
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
		lblTitle = new JLabel("소모 칼로리");
		Font f1 = new Font("돋움", Font.BOLD, 20);
		lblTitle.setFont(f1); 
		lblTitle.setBounds(145, 40, 130, 20);
		contentPane.add(lblTitle);
		
		// 콤보 박스
		activeCombo = new JComboBox(actives);
		activeCombo.setBounds(50, 80, 160, 20);
		contentPane.add(activeCombo);
		
		// 활동 시간
		tfActiveMin = new JTextField();
		tfActiveMin.setBounds(220, 80, 40, 20);
		contentPane.add(tfActiveMin);
		
		lblMin = new JLabel("분");
		Font f2 = new Font("돋움", Font.BOLD, 12);
		lblMin.setFont(f2); 
		lblMin.setBounds(265, 80, 40, 20);
		contentPane.add(lblMin);
		
		// JTable 데이터 매핑하기 (데이터, 칼럼이름, 테이블모델)
		// 칼럼이름
		Vector<String> activeColumn = getActiveColumn();
		// 테이블모델
		DefaultTableModel tableModel = new DefaultTableModel(activeColumn, 0);
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 120, 300, 260);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		// 추가 액션
		addBtn = new JButton("추가");
		addBtn.setFont(f2);
		addBtn.setBounds(285, 80, 58, 20);
		contentPane.add(addBtn);
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Active active = new Active();
				Member member = new Member();
				
				MemberDao mdao = MemberDao.getInstance();
				
				member = mdao.getUserInfo(member, userid);
				active = mdao.getActiveInfo(active, activeCombo.getSelectedItem().toString());
				
				BodyCalculator bc = new BodyCalculator();
				
				totalKcal = totalKcal + bc.calculateMET(active.getMet(), member.getWeight(), Integer.parseInt(tfActiveMin.getText()));
				
				if(tfActiveMin.getText().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "시간을 입력해주세요.");
				} else {
					Vector<Object> row = new Vector<>();
					row.addElement(activeCombo.getSelectedItem().toString());
					row.addElement(tfActiveMin.getText() + "분");
					row.addElement(bc.calculateMET(active.getMet(), member.getWeight(), Integer.parseInt(tfActiveMin.getText())) + "kcal");
					tableModel.addRow(row); // table모델에 행 넣기
					JOptionPane.showMessageDialog(null, "추가되었습니다.");
				}
	
			}
			
		});
		
		// 돌아가기 액션
		undoBtn = new JButton("돌아가기");
		undoBtn.setBounds(50, 380, 150, 30);
		contentPane.add(undoBtn);
		undoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ManageWeightFrame(userid, totalEat, totalKcal);
			}
			
		});
		
		// 입력 액션
		insertCompleteBtn = new JButton("입력");
		insertCompleteBtn.setBounds(200, 380, 150, 30);
		contentPane.add(insertCompleteBtn);
		insertCompleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ManageWeightFrame(userid, totalEat, totalKcal);
			}
			
		});
		
		if (userid == null) {
			JOptionPane.showMessageDialog(null, "인증되지 않은 사용자입니다.");
			dispose();
		} else {
			setVisible(true);
		}
	}

	public static Vector<String> getActiveColumn(){
		// 칼럼 데이터
		Vector<String> activeColumn = new Vector<>();
		activeColumn.add("활동명");
		activeColumn.add("시간(분)");
		activeColumn.add("칼로리");
		
		return activeColumn;
	}
}
