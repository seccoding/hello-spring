package com.hello.forum.bbs.vo;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;

public class BoardVO {

	private int id;
	
//	@NotEmpty(message = "제목은 필수 입력 값입니다.") // 필수 입력값 체크
	private String subject;
	
//	@NotEmpty(message = "내용은 필수 입력 값입니다.")
	private String content;
	
//	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
//	@Email(message = "올바른 형식으로 입력하세요.") // 입력값이 이메일 형태인지 검사.
	private String email;
	private int viewCnt;
	private String crtDt;
	private String mdfyDt;
	/**
	 * 서버에 저장된 파일의 이름 (난독화 처리된)
	 */
	private String fileName;
	/**
	 * 사용자가 업로드한 파일의 실제 이름
	 */
	private String originFileName;
	private String delYn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getCrtDt() {
		return crtDt;
	}

	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}

	public String getMdfyDt() {
		return mdfyDt;
	}

	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

}
