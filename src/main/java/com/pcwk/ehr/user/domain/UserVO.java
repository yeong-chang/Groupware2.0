package com.pcwk.ehr.user.domain;

import com.pcwk.ehr.cmn.DTO;

public class UserVO extends DTO {

	private int userId; //사용자 ID
	private int deptNo; //부서번호
	private int supUserId; //상급자 ID
	private String name; // 이름
	private String password; // 비밀번호
	private String position; // 직급
	private String birthday; // 생년월일
	private String hiredate; // 고용일
	private String phoneNo; // 연락처
	private int status; // 상태 (재직 중 / 휴가)
	
	public UserVO() {
		
	}

	public UserVO(int userId, int deptNo, int supUserId, String name, String password, String position, String birthday,
			String hiredate, String phoneNo, int status) {
		super();
		this.userId = userId;
		this.deptNo = deptNo;
		this.supUserId = supUserId;
		this.name = name;
		this.password = password;
		this.position = position;
		this.birthday = birthday;
		this.hiredate = hiredate;
		this.phoneNo = phoneNo;
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public int getSupUserId() {
		return supUserId;
	}

	public void setSupUserId(int supUserId) {
		this.supUserId = supUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", deptNo=" + deptNo + ", supUserId=" + supUserId + ", name=" + name
				+ ", password=" + password + ", position=" + position + ", birthday=" + birthday + ", hiredate="
				+ hiredate + ", phoneNo=" + phoneNo + ", status=" + status + "]";
	}
}
