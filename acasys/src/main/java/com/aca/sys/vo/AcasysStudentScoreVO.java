package com.aca.sys.vo;

public class AcasysStudentScoreVO {

	private String studentNo;
	private String studentName;
	private String scoreNo;
	private String startDate;
	private String endDate;
	private String termCd;
	private String korean;
	private String math;
	private String english;
	private String society;
	private String science;
	private String history;
	private String gubunVal;
	private String regUserId;
	private String regDttm;
	private String updUserId;
	private String updDttm;

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getScoreNo() {
		return scoreNo;
	}

	public void setScoreNo(String scoreNo) {
		this.scoreNo = scoreNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTermCd() {
		return termCd;
	}

	public void setTermCd(String termCd) {
		this.termCd = termCd;
	}

	public String getKorean() {
		return korean;
	}

	public void setKorean(String korean) {
		this.korean = korean;
	}

	public String getMath() {
		return math;
	}

	public void setMath(String math) {
		this.math = math;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getSociety() {
		return society;
	}

	public void setSociety(String society) {
		this.society = society;
	}

	public String getScience() {
		return science;
	}

	public void setScience(String science) {
		this.science = science;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getGubunVal() {
		return gubunVal;
	}

	public void setGubunVal(String gubunVal) {
		this.gubunVal = gubunVal;
	}

	public String getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public String getRegDttm() {
		return regDttm;
	}

	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public String getUpdDttm() {
		return updDttm;
	}

	public void setUpdDttm(String updDttm) {
		this.updDttm = updDttm;
	}

	@Override
	public String toString() {
		return "AcasysStudentScoreVO [studentNo=" + studentNo + ", studentName=" + studentName + ", scoreNo=" + scoreNo
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", termCd=" + termCd + ", korean=" + korean
				+ ", math=" + math + ", english=" + english + ", society=" + society + ", science=" + science
				+ ", history=" + history + ", gubunVal=" + gubunVal + ", regUserId=" + regUserId + ", regDttm="
				+ regDttm + ", updUserId=" + updUserId + ", updDttm=" + updDttm + "]";
	}

}