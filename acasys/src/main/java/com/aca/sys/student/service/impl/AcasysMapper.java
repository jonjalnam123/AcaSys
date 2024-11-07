package com.aca.sys.student.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aca.sys.student.vo.AcasysCommCdVO;
import com.aca.sys.student.vo.AcasysStudentInfoVO;
import com.aca.sys.student.vo.AcasysStudentScoreVO;

@Mapper
public interface AcasysMapper {

	/**
	 * @Method Name : studentCount
	 * @작성일 : 2024. 10. 22
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생전체 목록 건 수
	 * @return
	 */
	String studentCount();
	
	/**
	 * @param paging 
	 * @Method Name : selectAcasysStudentList
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 메인 학생 조회
	 * @return
	 */
	List<AcasysStudentInfoVO> selectAcasysStudentList(AcasysStudentInfoVO acasysStudentInfoVO);
	
	/**
	 * @Method Name : studentCountForPaging
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 페이징 관련 조회
	 * @return
	 */
	int studentCountForPaging(AcasysStudentInfoVO acasysStudentInfoVO);

	/**
	 * @Method Name : schoolGubunCd
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 계열 코드
	 * @return
	 */
	List<AcasysCommCdVO> schoolGubunCd(String schoolGubunVal);

	/**
	 * @Method Name : schoolMajorCd
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 전공 코드
	 * @return
	 */
	List<AcasysCommCdVO> schoolMajorCd(String schoolMajorVal);
	
	/**
	 * @Method Name : schoolVocatiMajorCd
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 실업계 코드 조회
	 * @return
	 */
	List<AcasysCommCdVO> schoolVocatiMajorCd(String schoolVocatiMajorVal);

	/**
	 * @Method Name : tierCd
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 성적 코드
	 * @return
	 */
	List<AcasysCommCdVO> tierCd(String tierVal);

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
	 * @Method Name : acasysStudentRegistProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 등록 진행
	 * @return
	 */
	int acasysStudentRegistProc(AcasysStudentInfoVO acasysStudentInfoVO);
	
	/**
	 * @Method Name : acasysStudentDelProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 등록 삭제
	 * @return
	 */
	int acasysStudentDelProc(AcasysStudentInfoVO acasysStudentInfoVO);

	/**
	 * @Method Name : acasysStudentDetail
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 상세
	 * @return
	 */
	AcasysStudentInfoVO acasysStudentDetail(String studentNo);

	/**
	 * @Method Name : acasysStudentDetailUpdateProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 상세 수정
	 * @return
	 */
	int acasysStudentDetailUpdateProc(AcasysStudentInfoVO acasysStudentInfoVO);

}
