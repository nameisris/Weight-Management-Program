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
import models.Member;

public class MemberListFrame extends JFrame {

	private JPanel contentPane;
	private JPanel southPanel;
	private JLabel lblTitle;
	private JButton btnLogout;
	private JButton btnDelete;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberListFrame frame = new MemberListFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MemberListFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Weight Management Program");
		setBounds(100, 100, 1032, 584);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// 타이틀
		lblTitle = new JLabel("\uD68C\uC6D0\uC815\uBCF4");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitle, BorderLayout.NORTH);

		// 하단 패널
		southPanel = new JPanel(new GridLayout(1, 2));
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		// 로그아웃 액션
		btnLogout = new JButton("로그아웃");
		southPanel.add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
				dispose();
				new LoginFrame();
			}
		});

		// JTable 데이터 매핑하기 (데이터, 칼럼이름, 테이블모델)
		// 칼럼이름
		Vector<String> member = getMemberInfo();
		// 데이터
		MemberDao dao = MemberDao.getInstance();
		Vector<Member> members = dao.findByAll();
		// 테이블모델
		DefaultTableModel tableModel = new DefaultTableModel(member, 0);
		// for문 돌면서 한 행씩 데이터 집어 넣기
		for (int i = 0; i < members.size(); i++) {
			Vector<Object> row = new Vector<>();
			row.addElement(members.get(i).getUserid());
			row.addElement(members.get(i).getPassword());
			row.addElement(members.get(i).getName());
			row.addElement(members.get(i).getEmail());
			row.addElement(members.get(i).getPhone());
			row.addElement(members.get(i).getCreateDate());
			tableModel.addRow(row); // table모델에 행 넣기
		}

		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		// 삭제 액션
		btnDelete = new JButton("삭제");
		southPanel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				
				//행이 선택되지 않으면 row는 -1이 됨.
				if(row < 0) {
					row = table.getRowCount()-1;
				}
				Object id = table.getValueAt(row, 0);
				//UI제거
				tableModel.removeRow(row);
				//DB제거
				MemberDao dao = MemberDao.getInstance();
				
				if(dao.deleteUser(id)) { //
					JOptionPane.showMessageDialog(null, "삭제를 완료하였습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "삭제를 실패하였습니다.");
				}
			}
		});

		setVisible(true);
	}

	public static Vector<String> getMemberInfo(){
		//칼럼 데이터
		Vector<String> memberName = new Vector<>();
		memberName.add("USERID");
		memberName.add("PASSWORD");
		memberName.add("NAME");
		memberName.add("EMAIL");
		memberName.add("PHONE");
		memberName.add("CREATEDATE");
		
		return memberName;
	}
}
