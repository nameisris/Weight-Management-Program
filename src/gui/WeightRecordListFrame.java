package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.MemberDao;
import models.WeightRecord;

public class WeightRecordListFrame extends JFrame {
	
	private String userid;
	private JPanel contentPane;
	private JPanel southPanel;
	
	private JButton undoBtn;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeightRecordListFrame frame = new WeightRecordListFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WeightRecordListFrame() {
		this(null);
	}
	
	public WeightRecordListFrame(String userid) {
		this.userid = userid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setBounds(100, 100, 1032, 584);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// 타이틀
		JLabel label = new JLabel("회원정보");
		label.setFont(new Font("굴림", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.NORTH);
		
		// 하단 패널
		southPanel = new JPanel(new GridLayout(1, 1));
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		// 돌아가기 액션
		undoBtn = new JButton("돌아가기");
		southPanel.add(undoBtn);
		undoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MenuFrame(userid);
			}
		});
		
		// 검색 테이블
		Vector<String> activeColumn = getColumn();
		// 데이터
		MemberDao dao = MemberDao.getInstance();
		Vector<WeightRecord> weightRecords = dao.getWeightRecordInfoList(userid);
		// 테이블모델
		DefaultTableModel tableModel = new DefaultTableModel(activeColumn, 0);
		// for문 돌면서 한 행씩 데이터 집어 넣기
		for (int i = 0; i < weightRecords.size(); i++) {
			Vector<Object> row = new Vector<>();
			row.addElement(weightRecords.get(i).getWdate());
			row.addElement(weightRecords.get(i).getIntakekcal());
			row.addElement(weightRecords.get(i).getBurnkcal());
			row.addElement(weightRecords.get(i).getWcomment());
			row.addElement(weightRecords.get(i).getWfeedback());
			tableModel.addRow(row); // table모델에 행 넣기
		}		
		
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);

		
		setVisible(true);
	}
	
	public static Vector<String> getColumn(){
		//칼럼 데이터
		Vector<String> activeColumn = new Vector<>();
		activeColumn.add("날짜");
		activeColumn.add("섭취 칼로리");
		activeColumn.add("소모 칼로리");
		activeColumn.add("결과");
		activeColumn.add("자가 피드백");
		
		return activeColumn;
	}
}
