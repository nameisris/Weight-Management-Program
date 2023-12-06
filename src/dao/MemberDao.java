package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import models.Active;
import models.Member;
import models.WeightRecord;

public class MemberDao {
	private MemberDao() {}
	private static MemberDao instance = new MemberDao();
	public static MemberDao getInstance() {
		return instance;
	}
	
	private Connection conn; // DB 연결 객체
	private PreparedStatement pstmt; // Query 작성 객체
	private ResultSet rs; // Query 결과 커서
	
	// DB 자원연결 반납
	public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null)
                rs.close(); // 유효성검사 후 자원 반납
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("DB close");
            e.printStackTrace();
        }
 
    }
	
	// 사용자 아이디 중복 여부 확인
	// 매개변수로 넣은 userid가 MEMBER 테이블에 이미 존재한다면 false, 아니라면 true, 없으면 DB반납
	public boolean checkID(String userid) {
		// DB 연결
		conn = DBConnection.getConnection();
		
		try {
			// Query 작성
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE USERID = ?");
			
			// Query 완성 (index 1번 부터 시작)
			pstmt.setString(1, userid);
			

			// Query 실행
			rs = pstmt.executeQuery(); // return값은 처리된 레코드의 개수
			
			if(rs.next()) { // next()함수는 커서를 한칸 내리면서 해당 행에 데이터가 있으면 true, 없으면 false 반환
				return false; // 중복 여부 확인 실패 (해당 테이블에 이미 userid에 대해 같은 값이 있으므로)
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return true; // 중복 여부 확인 성공
	}
	
	// 사용자 정보로 회원가입
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean saveUserInfo(Member member) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO MEMBER(USERID, PASSWORD, NAME, EMAIL, PHONE, CREATEDATE) VALUES(?,?,?,?,?, SYSDATE)");
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.executeUpdate();
			
			return true; // 회원가입 성공
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false; // 회원가입 실패
	}
	
	// 사용자 로그인
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean findByUserIDAndPassword(String userid, String password) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE USERID = ? AND PASSWORD = ?");
			
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				return true;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 첫 로그인 여부 확인
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean checkFirst(String userid, String password) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE (USERID, PASSWORD) IN (SELECT ?, ? FROM MEMBER) AND GENDER IS NULL");
			
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 체중 정보 저장
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean saveBodyInfo(Member member, String userid) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("UPDATE MEMBER SET GENDER = ?, AGE = ?, WEIGHT = ?, HEIGHT = ?, BMI = ? WHERE USERID = ?");
			pstmt.setString(1, member.getGender());
			pstmt.setInt(2, member.getAge());
			pstmt.setDouble(3, member.getWeight());
			pstmt.setDouble(4, member.getHeight());
			pstmt.setDouble(5, member.getBmi());
			pstmt.setString(6, userid);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 체중 목표 저장
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean saveTargetInfo(Member member, String userid) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("UPDATE MEMBER SET TWEIGHT = ?, TTERM = ?, ACTIVE = ? WHERE USERID = ?");
			pstmt.setDouble(1, member.getTWeight());
			pstmt.setInt(2, member.getTTerm());
			pstmt.setString(3, member.getActive());
			pstmt.setString(4, userid);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 정보 가져오기
	// 성공 true, 실패 false, 없으면 DB반납
	public Member getUserInfo(Member member, String userid) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE USERID = ?");
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member.setUserid(rs.getString("userid"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				member.setGender(rs.getString("gender"));
				member.setAge(rs.getInt("age"));
				member.setWeight(rs.getDouble("weight"));
				member.setHeight(rs.getDouble("height"));
				member.setBmi(rs.getDouble("bmi"));
				member.setTWeight(rs.getDouble("tweight"));
				member.setTTerm(rs.getInt("tterm"));
				member.setActive(rs.getString("active"));
			}
 		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return member;
	}
	
	
	// 회원 탈퇴시, 사용자 비밀번호 확인
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean checkPassword(String password) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE PASSWORD = ?");

			pstmt.setString(1, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 회원 탈퇴
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean deleteUser(Object userid) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("DELETE MEMBER WHERE USERID = ?");
			pstmt.setObject(1, userid);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("DELETE WEIGHTRECORD WHERE USERID = ?");
			pstmt.setObject(1, userid);
			pstmt.executeUpdate();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 정보 리스트로 가져오기
	// 성공 Vector<Member>, 실패 null
	public Vector<Member> findByAll(){
		conn = DBConnection.getConnection();
		Vector<Member> members = new Vector<>();
		try {
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setUserid(rs.getString("userid"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				member.setCreateDate(rs.getTimestamp("createDate"));
				members.add(member);
			}
			return members;
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}

		return null;
	}
	
	// 사용자 체중 정보 저장
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean saveWeightRecord(WeightRecord weightRecord) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO WEIGHTRECORD(USERID, WDATE, INTAKEKCAL, BURNKCAL) VALUES(?,?,?,?)");
			pstmt.setString(1, weightRecord.getUserid());
			pstmt.setString(2, weightRecord.getWdate());
			pstmt.setDouble(3, weightRecord.getIntakekcal());
			pstmt.setDouble(4, weightRecord.getBurnkcal());
			pstmt.executeUpdate();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 체중 관리 내역 저장
	// 성공 true, 실패 false, 없으면 DB반납
	public boolean saveWeightRecordComment(WeightRecord weightRecord, String userid, String wdate) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("UPDATE WEIGHTRECORD SET WCOMMENT = ?, WFEEDBACK = ? WHERE USERID = ? AND WDATE = ?");
			pstmt.setString(1, weightRecord.getWcomment());
			pstmt.setString(2, weightRecord.getWfeedback());
			pstmt.setString(3, userid);
			pstmt.setString(4, wdate);
			pstmt.executeUpdate();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
		
		return false;
	}
	
	// 사용자 체중 관리 내역 가져오기
	// 성공 true, 실패 false, 없으면 DB반납
	public WeightRecord getWeightRecordInfo(WeightRecord weightRecord, String userid) {
		conn = DBConnection.getConnection();
			
		try {
			pstmt = conn.prepareStatement("SELECT * FROM WEIGHTRECORD WHERE USERID = ?");
			pstmt.setString(1, userid);
				
			rs = pstmt.executeQuery();
				
			while(rs.next()) {
				weightRecord.setWdate(rs.getString("wdate"));
				weightRecord.setIntakekcal(rs.getDouble("intakekcal"));
				weightRecord.setBurnkcal(rs.getDouble("burnkcal"));
				weightRecord.setWcomment(rs.getString("wcomment"));
				weightRecord.setWfeedback(rs.getString("wfeedback"));
			}
	 	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}
			
		return weightRecord;
	}
	
	// 사용자 체중 관리 내역 리스트로 가져오기
	// 성공 Vector<Member>, 실패 null, 없으면 DB 반납
	public Vector<WeightRecord> getWeightRecordInfoList(String userid){
		conn = DBConnection.getConnection();
		Vector<WeightRecord> weightRecords = new Vector<>();
		try {
			pstmt = conn.prepareStatement("SELECT * FROM WEIGHTRECORD WHERE USERID = ?");
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WeightRecord weightRecord = new WeightRecord();
				weightRecord.setWdate(rs.getString("wdate"));
				weightRecord.setIntakekcal(rs.getDouble("intakekcal"));
				weightRecord.setBurnkcal(rs.getDouble("burnkcal"));
				weightRecord.setWcomment(rs.getString("wcomment"));
				weightRecord.setWfeedback(rs.getString("wfeedback"));
				weightRecords.add(weightRecord);
			}
			return weightRecords;
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstmt, rs);
		}

		return null;
	}
	
	// 운동 정보 가져오기
		// 성공 true, 실패 false, 없으면 DB반납
		public Active getActiveInfo(Active active, String activeName) {
			conn = DBConnection.getConnection();
			
			try {
				pstmt = conn.prepareStatement("SELECT * FROM ACTIVE WHERE ACTIVENAME = ?");
				pstmt.setString(1, activeName);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					active.setActiveName(rs.getString("activename"));
					active.setMet(rs.getInt("met"));
				}
	 		} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.closeAll(conn, pstmt, rs);
			}
			
			return active;
		}
}
