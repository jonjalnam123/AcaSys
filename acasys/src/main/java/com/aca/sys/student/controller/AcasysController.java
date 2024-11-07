package com.aca.sys.student.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aca.sys.Paging;
import com.aca.sys.login.vo.AmsLoginVO;
import com.aca.sys.student.service.AcasysService;
import com.aca.sys.student.vo.AcasysCommCdVO;
import com.aca.sys.student.vo.AcasysStudentInfoVO;
import com.aca.sys.student.vo.AcasysStudentScoreVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AcasysController {

	@Autowired
	AcasysService acasysService;

	/*
	 * @Method Name : acasysMainList
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학원생 리스트
	 * @return
	**/
	@GetMapping("/student/acasysStudetnList.do")  
	public String acasysStudetnList(@RequestParam(defaultValue = "0") int curPage, @RequestParam(defaultValue = "") String studenNmSearch, Model model, HttpServletRequest request, AcasysStudentInfoVO acasysStudentInfoVO) throws Exception{
		
	    // 로그인 여부 체크
		AmsLoginVO loginUser = (AmsLoginVO) request.getSession().getAttribute("LOGIN_USER");
	    if (loginUser == null) {
	        return "redirect:/login/amsMain.do"; // 로그인 페이지로 리디렉션
	    }
		
	    String adminId = loginUser.getAdminId();
	    
	    //페이징 처리
        Paging paging = acasysService.getPaging(curPage, acasysStudentInfoVO);
        model.addAttribute("paging", paging);
        
        //페이징 검색조건
        paging.setStudenNmSearch(acasysStudentInfoVO.getStudenNmSearch());
        acasysStudentInfoVO.setPaging(paging);  

		// 학생 리스트
		List<AcasysStudentInfoVO> studentList = acasysService.selectAcasysStudentList(acasysStudentInfoVO);
		
		String termVal = "term";
		List<AcasysCommCdVO> termCd = acasysService.termCd(termVal);  
		
		/** json 변환 **/
		ObjectMapper ObjectMapper = new ObjectMapper();
		String termCdVal = ObjectMapper.writeValueAsString(termCd);
		
		// 학생 전체 건 수
		int count = paging.getTotalCount();  

		model.addAttribute("termCd", termCdVal);
		model.addAttribute("adminId", adminId);
		model.addAttribute("studentList", studentList);
		model.addAttribute("count", count);
		
		return "student/acasysStudentList";
	}

	/**
	 * @Method Name : acasysStudentScoreSearch
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 조회
	 * @return
	 */
	@PostMapping("/student/acasysStudentScoreSearch.do")
	@ResponseBody
	public HashMap<String, Object> acasysStudentScoreSearch(@ModelAttribute AcasysStudentScoreVO acasysStudentScoreVO, HttpServletRequest request) {

		List<AcasysStudentScoreVO> studentScore = acasysService.acasysStudentScoreSearch(acasysStudentScoreVO);
		
		String termVal = "term";
		List<AcasysCommCdVO> termCd = acasysService.termCd(termVal);
		
	    // 결과 객체 생성
		HashMap<String, Object> response = new HashMap<>();
		
	    if (studentScore.isEmpty()) {
		   response.put("studentScore", 'E');
		   response.put("termCd", termCd);
	    } else {    
	    	response.put("studentScore", studentScore);
	    	response.put("termCd", termCd);
	    }
	    
	    return response;  
	}
	
	/**
	 * @Method Name : acasysStudentScoreRegistProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 등록 진행
	 * @return
	 */
	@PostMapping("/student/acasysStudentScoreRegistProc.do")
	@ResponseBody
	public HashMap<String, String>  acasysStudentScoreRegistProc (@RequestBody HashMap<String, List<AcasysStudentScoreVO>> acasysStudentScoreVO, HttpServletRequest request) {

	    List<AcasysStudentScoreVO> scoresList = acasysStudentScoreVO.get("scoresList");

	    String adminId = ((AmsLoginVO) request.getSession().getAttribute("LOGIN_USER")).getAdminId();

	    HashMap<String, String> response = new HashMap<>();
	    String overallResult = "SUCCESS";

	    for (AcasysStudentScoreVO score : scoresList) {
	        score.setUpdUserId(adminId); // 업데이트 시에도 adminId 설정

	        if ("insert".equals(score.getGubunVal())) { 
	            score.setRegUserId(adminId);
	            String result = acasysService.acasysStudentScoreRegistProc(score);
	            if (!"SUCCESS".equals(result)) {
	                overallResult = "ERROR";
	            }
	        } else if ("update".equals(score.getGubunVal())) {
	            String result = acasysService.acasysStudentScoreUpdateProc(score);
	            if (!"SUCCESS".equals(result)) {
	                overallResult = "ERROR";  
	            }
	        }
	    }

	    response.put("status", "SUCCESS".equals(overallResult) ? "success" : "error");
	    return response;
	}
	
	/**
	 * @Method Name : acasysStudentScoreDelProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 삭제 진행
	 * @return
	 */
	@PostMapping("/student/acasysStudentScoreDelProc.do")
	@ResponseBody
	public HashMap<String, String>  acasysStudentScoreDelProc (@RequestBody HashMap<String, List<AcasysStudentScoreVO>> acasysStudentScoreVO, HttpServletRequest request) {

	    List<AcasysStudentScoreVO> studentsToDelete = acasysStudentScoreVO.get("studentsToDelete");

	    HashMap<String, String> response = new HashMap<>();
	    String overallResult = "SUCCESS";

	    for (AcasysStudentScoreVO delScore : studentsToDelete) {

        	String result = acasysService.acasysStudentScoreDelProc(delScore);
            if (!"SUCCESS".equals(result)) {
                overallResult = "ERROR";
            } 

	    }

	    response.put("status", "SUCCESS".equals(overallResult) ? "success" : "error");
	    return response;
	}
	
	/**
	 * @Method Name : acasysMain
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 등록 화면
	 * @return
	 */
	@GetMapping("/student/acasysStudentRegist.do")
	public String acasysStudentRegist(Model model) {
		
		String schoolGubunVal = "schoolgubun";
		List<AcasysCommCdVO> schoolGubunCd = acasysService.schoolGubunCd(schoolGubunVal);
		
		String schoolMajorVal = "schoolmajor";
		List<AcasysCommCdVO> schoolMajorCd = acasysService.schoolMajorCd(schoolMajorVal);
		
		String tierVal = "tier";
		List<AcasysCommCdVO> tierCd = acasysService.tierCd(tierVal);
		
		String schoolVocatiMajorVal = "schoolvocatimajor";
		List<AcasysCommCdVO> schoolVocatiMajorCd = acasysService.schoolVocatiMajorCd(schoolVocatiMajorVal);

		model.addAttribute("schoolGubunCd", schoolGubunCd);
		model.addAttribute("schoolMajorCd", schoolMajorCd);
		model.addAttribute("schoolVocatiMajorCd", schoolVocatiMajorCd);
		model.addAttribute("tierCd", tierCd);
		
		return "student/acasysStudentRegist";  
	}
	
	/**
	 * @Method Name : acasysStudentRegistProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 등록 진행
	 * @return
	 */
	@PostMapping("/student/acasysStudentRegistProc.do")
	@ResponseBody
	public HashMap<String, String>  acasysStudentRegistProc (@ModelAttribute AcasysStudentInfoVO acasysStudentInfoVO, HttpServletRequest request) {

		 //로그인 아이디
		String adminId = ((AmsLoginVO) request.getSession().getAttribute("LOGIN_USER")).getAdminId();
		
		acasysStudentInfoVO.setRegUserId(adminId);
		acasysStudentInfoVO.setUpdUserId(adminId);  
		
		String result = acasysService.acasysStudentRegistProc(acasysStudentInfoVO);
		
	    // 결과 객체 생성
		HashMap<String, String> response = new HashMap<>();
		
	    if ("SUCCESS".equals(result)) {
	        response.put("status", "success");
	    } else {
	        response.put("status", "error");  
	    }
	    
	    return response;
	}
	
	/**
	 * @Method Name : acasysStudentDelProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 등록 삭제
	 * @return
	 */
	@PostMapping("/student/acasysStudentDelProc.do")
	@ResponseBody
	public HashMap<String, String>  acasysStudentDelProc (@ModelAttribute AcasysStudentInfoVO acasysStudentInfoVO, HttpServletRequest request) {

		 //로그인 아이디
		String adminId = ((AmsLoginVO) request.getSession().getAttribute("LOGIN_USER")).getAdminId();
		
		acasysStudentInfoVO.setUpdUserId(adminId);  
		
		String result = acasysService.acasysStudentDelProc(acasysStudentInfoVO);
		
	    // 결과 객체 생성
		HashMap<String, String> response = new HashMap<>();
		
	    if ("SUCCESS".equals(result)) {
	        response.put("status", "success");
	    } else {
	        response.put("status", "error");  
	    }
	    
	    return response; 
	}
	
	/**
	 * @Method Name : acasysStudentDetail
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 상세
	 * @return
	 */
	@PostMapping("/student/acasysStudentDetail.do")
	public String acasysStudentDetail(@RequestParam String studentNo, Model model) {
		
		AcasysStudentInfoVO studentDetailList = acasysService.acasysStudentDetail(studentNo);
		
		String schoolGubunVal = "schoolgubun";
		List<AcasysCommCdVO> schoolGubunCd = acasysService.schoolGubunCd(schoolGubunVal);
		
		String schoolMajorVal = "schoolmajor";
		List<AcasysCommCdVO> schoolMajorCd = acasysService.schoolMajorCd(schoolMajorVal);
		
		String tierVal = "tier";
		List<AcasysCommCdVO> tierCd = acasysService.tierCd(tierVal);
		
		
		String schoolVocatiMajorVal = "schoolvocatimajor";
		List<AcasysCommCdVO> schoolVocatiMajorCd = acasysService.schoolVocatiMajorCd(schoolVocatiMajorVal);
		
		model.addAttribute("schoolGubunCd", schoolGubunCd);
		model.addAttribute("schoolMajorCd", schoolMajorCd);
		model.addAttribute("tierCd", tierCd);
		model.addAttribute("studentDetailList", studentDetailList);  
		model.addAttribute("schoolVocatiMajorCd", schoolVocatiMajorCd);
		
		return "student/acasysStudentDetail";  
	}
	
	/**
	 * @Method Name : acasysStudentDetailUpdateProc
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 상세 수정
	 * @return
	 */
	@PostMapping("/student/acasysStudentDetailUpdateProc.do")
	@ResponseBody
	public HashMap<String, String> acasysStudentDetailUpdateProc(@ModelAttribute AcasysStudentInfoVO acasysStudentInfoVO, HttpServletRequest request) {

		 //로그인 아이디
		String adminId = ((AmsLoginVO) request.getSession().getAttribute("LOGIN_USER")).getAdminId();

		acasysStudentInfoVO.setUpdUserId(adminId);  
		
		String result = acasysService.acasysStudentDetailUpdateProc(acasysStudentInfoVO);
		
	    // 결과 객체 생성
		HashMap<String, String> response = new HashMap<>();
		
	    if ("SUCCESS".equals(result)) {
	        response.put("status", "success");
	    } else {
	        response.put("status", "error");  
	    }
	    
	    return response;  
	}
	
	/**
	 * @Method Name : acasysStudentScoreExcel
	 * @작성일 : 2024. 10. 21
	 * @작성자 : 최정석
	 * @변경이력 :
	 * @Method 설명 : 학생 성적 엑셀
	 * @return
	 */
	@PostMapping("/student/acasysStudentScoreExcel.do")
	public void acasysStudentScoreExcel (@ModelAttribute AcasysStudentScoreVO acasysStudentScoreVO, HttpServletResponse response) throws IOException {
//      Workbook wb = new HSSFWorkbook(); xls 확장자를 가진 엑셀 파일 -> HSSFWorkbook
      Workbook wb = new XSSFWorkbook(); // xlsx 확장자를 가진 엑셀 파일 -> XSSFWorkbook
      Sheet sheet = wb.createSheet("첫번째 시트");
      Row row = null;
      Cell cell = null;
      int rowNum = 0;
      
      String studentNo = acasysStudentScoreVO.getStudentNo();
      String studentName = acasysService.acasysStudentNameForExcel(studentNo);
      List<AcasysStudentScoreVO> excel = acasysService.acasysStudentScoreExcel(studentNo);

      // Header
      row = sheet.createRow(rowNum++);
      cell = row.createCell(0);
      cell.setCellValue("시작날짜");
      cell = row.createCell(1);
      cell.setCellValue("종료날짜");
      cell = row.createCell(2);
      cell.setCellValue("학기");
      cell = row.createCell(3);
      cell.setCellValue("국어");
      cell = row.createCell(4);
      cell.setCellValue("수학");
      cell = row.createCell(5);
      cell.setCellValue("영어");
      cell = row.createCell(6);
      cell.setCellValue("사회");
      cell = row.createCell(7);
      cell.setCellValue("역사");
      cell = row.createCell(8);
      cell.setCellValue("과학");
      cell = row.createCell(9);
      cell.setCellValue("분기 평균");
      
      // Header 열 너비 설정 (단위: 1/256)
      sheet.setColumnWidth(0, 256 * 15); // 시작날짜  
      sheet.setColumnWidth(1, 256 * 15); // 종료날짜
      sheet.setColumnWidth(2, 256 * 10); // 학기
      sheet.setColumnWidth(3, 256 * 10); // 국어
      sheet.setColumnWidth(4, 256 * 10); // 수학
      sheet.setColumnWidth(5, 256 * 10); // 영어
      sheet.setColumnWidth(6, 256 * 10); // 사회
      sheet.setColumnWidth(7, 256 * 10); // 역사
      sheet.setColumnWidth(8, 256 * 10); // 과학
      sheet.setColumnWidth(9, 256 * 10); // 분기 평균
      
      for (AcasysStudentScoreVO score : excel) {
    	  
          row = sheet.createRow(rowNum++);
          cell = row.createCell(0);
          cell.setCellValue(score.getStartDate());
          cell = row.createCell(1);
          cell.setCellValue(score.getEndDate());
          cell = row.createCell(2);
          cell.setCellValue(score.getTermNm());  
          cell = row.createCell(3);
          cell.setCellValue(score.getKorean());  
          cell = row.createCell(4);
          cell.setCellValue(score.getMath());  
          cell = row.createCell(5);
          cell.setCellValue(score.getEnglish());  
          cell = row.createCell(6);
          cell.setCellValue(score.getSociety());  
          cell = row.createCell(7);
          cell.setCellValue(score.getHistory());  
          cell = row.createCell(8);
          cell.setCellValue(score.getScience());
          cell = row.createCell(9);
          cell.setCellValue(score.getAverageScore()); 
      }
      
      //마지막 값 확인
      int lastRowNum = rowNum;     
      row = sheet.createRow(lastRowNum);
      cell = row.createCell(0);
      cell.setCellValue("");
      cell = row.createCell(1);
      cell.setCellValue("");
      cell = row.createCell(2);
      cell.setCellValue("과목평균"); 
      cell = row.createCell(3);
      cell.setCellValue(acasysStudentScoreVO.getAvgKorean());
      cell = row.createCell(4);
      cell.setCellValue(acasysStudentScoreVO.getAvgMath());
      cell = row.createCell(5);
      cell.setCellValue(acasysStudentScoreVO.getAvgEnglish());
      cell = row.createCell(6);
      cell.setCellValue(acasysStudentScoreVO.getAvgSociety());
      cell = row.createCell(7);
      cell.setCellValue(acasysStudentScoreVO.getAvgHistory());
      cell = row.createCell(8);
      cell.setCellValue(acasysStudentScoreVO.getAvgScience());
      cell = row.createCell(9); 
      cell.setCellValue("");
      cell = row.createCell(10);
      cell.setCellValue("학생 번호");
      cell = row.createCell(11);
      cell.setCellValue(" : " + studentNo);
      cell = row.createCell(12);
      cell.setCellValue("학생 이름");
      cell = row.createCell(13);    
      cell.setCellValue(" : " + studentName);  

      Date today = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
      String formattedDate = formatter.format(today);  
      
      // 컨텐츠 타입과 파일명 지정
      response.setContentType("ms-vnd/excel"); 
      response.setHeader("Content-Disposition", "attachment;filename=" + formattedDate + "_studentScore.xlsx");

      // Excel File Output
      wb.write(response.getOutputStream());    
      wb.close();
	}

}