package com.aca.sys.score.service;

import java.util.List;

import com.aca.sys.score.vo.AmsScoreVO;
import com.aca.sys.student.vo.AcasysCommCdVO;
import com.aca.sys.student.vo.AcasysStudentScoreVO;

public interface AmsScoreService {
	
	/**
	 * @Method Name : acasysStudentScoreSearch
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 조회
	 * @return
	 */
	List<AmsScoreVO> acasysStudentScoreSearch(AmsScoreVO amsScoreVO);

	/**
	 * @Method Name : tierCd
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학기 코드
	 * @return
	 */
	List<AcasysCommCdVO> termCd(String termVal);
	
	/**
	 * @Method Name : acasysStudentScoreRegistProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 등록 진행
	 * @return
	 */
	String acasysStudentScoreRegistProc(AmsScoreVO score);
	
	/**
	 * @Method Name : acasysStudentScoreUpdateProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 수정 진행
	 * @return
	 */
	String acasysStudentScoreUpdateProc(AmsScoreVO score);
	
	/**
	 * @Method Name : acasysStudentScoreDelProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 삭제 진행
	 * @return
	 */
	String acasysStudentScoreDelProc(AmsScoreVO delScore);

	/**
	 * @Method Name : acasysStudentScoreExcel
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 엑셀
	 * @return
	 */
	List<AmsScoreVO> acasysStudentScoreExcel(String studentNo);

	/**
	 * @Method Name : acasysStudentNameForExcel
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 엑셀 이름 조회
	 * @return
	 */
	String acasysStudentNameForExcel(String studentNo);
	

}
