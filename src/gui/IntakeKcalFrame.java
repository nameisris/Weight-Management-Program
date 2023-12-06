package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import api.FoodAPI;

public class IntakeKcalFrame extends JFrame {
	
	private String userid;
	private double totalKcal;
	
	private JPanel contentPane;
	
	private JLabel lblTitle;
	
	private JTextField tfSearchFood;
	
	private JButton searchBtn;
	private JButton addBtn;
	private JButton undoBtn;
	private JButton insertCompleteBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntakeKcalFrame frame = new IntakeKcalFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public IntakeKcalFrame() {
		this(null, 0, 0);
	}
	
	public IntakeKcalFrame(String userid, double totalEat, double totalActive) {
		this.userid = userid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setSize(430, 490);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// 타이블
		lblTitle = new JLabel("섭취 칼로리");
		Font f1 = new Font("돋움", Font.BOLD, 20);
		lblTitle.setFont(f1); 
		lblTitle.setBounds(145, 40, 130, 20);
		contentPane.add(lblTitle);
		
		// 음식 검색
		tfSearchFood = new JTextField();
		tfSearchFood.setBounds(50, 80, 130, 20);
		contentPane.add(tfSearchFood);
		
		// 검색 테이블
		// JTable 데이터 매핑하기 (칼럼이름, 테이블모델)
		// 1. 칼럼이름
		Vector<String> activeColumn1 = getActiveColumn();
		// 2. 테이블모델
		DefaultTableModel tableModel1 = new DefaultTableModel(activeColumn1, 0);
		JTable table1 = new JTable(tableModel1);
		JScrollPane scrollPane1 = new JScrollPane(table1);
		scrollPane1.setBounds(50, 100, 300, 130);
		contentPane.add(scrollPane1, BorderLayout.CENTER);
		
		// 추가 테이블
		Vector<String> activeColumn2 = getActiveColumn();
		DefaultTableModel tableModel2 = new DefaultTableModel(activeColumn2, 0);
		JTable table2 = new JTable(tableModel2);
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(50, 230, 300, 130);
		contentPane.add(scrollPane2, BorderLayout.CENTER);
		
		// 검색 액션
		searchBtn = new JButton("검색");
		Font f2 = new Font("돋움", Font.BOLD, 12);
		searchBtn.setFont(f2);
		searchBtn.setBounds(200, 80, 65, 20);
		contentPane.add(searchBtn);
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JTable 초기화
				tableModel1.setNumRows(0);
				
				FoodAPI fapi = new FoodAPI();
				String[][] foodInfo;
				
				foodInfo = new String[fapi.searchFood(tfSearchFood.getText()).length][2];
				foodInfo = fapi.searchFood(tfSearchFood.getText());
				
				if(tfSearchFood.getText().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "검색어를 입력해주세요.");
				} else {
			        for(int i = 0;i < foodInfo.length;i++) {
						Vector<Object> row = new Vector<>();
						row.addElement(foodInfo[i][0]);
						row.addElement(foodInfo[i][1]);
						tableModel1.addRow(row); // table모델에 행 넣기
					}
					JOptionPane.showMessageDialog(null, "검색되었습니다.");
				}
			}
			
		});
		
		// 추가 액션
		addBtn = new JButton("추가");
		addBtn.setFont(f2);
		addBtn.setBounds(280, 80, 65, 20);
		contentPane.add(addBtn);
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table1.getSelectedRow();
				
				totalKcal = totalKcal + Double.parseDouble((String) table1.getValueAt(selectedRow, 1));
				
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "음식을 선택해주세요.");
				} else {
					Vector<Object> row = new Vector<>();
					row.addElement(table1.getValueAt(selectedRow, 0));
					row.addElement(table1.getValueAt(selectedRow, 1));
					tableModel2.addRow(row); // table모델에 행 넣기
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
				new ManageWeightFrame(userid, totalKcal, totalActive);
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
				new ManageWeightFrame(userid, totalKcal, totalActive);
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
		//칼럼 데이터
		Vector<String> activeColumn = new Vector<>();
		activeColumn.add("음식");
		activeColumn.add("칼로리");
		
		return activeColumn;
	}
}
