package com.hello.forum.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;

/**
 * Spring이 기본적으로 생성하는 Bean 이외의 개발자가 직접 만든 클래스의 Bean을 Bean Container에 적재시키는 역할.
 * 
 * @SpringBootConfiguration의 역할 Spring Boot Application이 실행될 때 (서버를 Run 시킬 때)
 *                           Spring Boot가 이 애노테이션이 적용된 클래스를 찾아 실행시키고 필요한 bean들을
 *                           Bean Container에 적재한다.
 */
@SpringBootConfiguration
public class CustomBeanInitializer {

	/**
	 * application.yml 파일에 작성된 사용자 환경설정 정보들을 읽어와서 멤버변수로 할당해 둔다.
	 */
	@Value("${app.multipart.base-dir:c:/uploadFiles}")
	private String baseDir;

	@Value("${app.multipart.obfuscation.enable:false}")
	private boolean enableObfuscation;

	@Value("${app.multipart.obfuscation.hide-ext.enable:false}")
	private boolean enableObfuscationHideExt;

	@Value("${app.multipart.available-file-list.enable:false}")
	private boolean enableAvailableFileList;

	@Value("${app.multipart.available-file-list.list}")
	private List<String> availableFileList;

	@Value("${app.multipart.available-file-list.handler:tika}")
	private String fileMimeTypeHandler;

	public CustomBeanInitializer() {
		System.out.println("CustomBeanInitializer 실행됨!!!");
		// 생성자가 실행되는 시점에서는 yml의 값이 할당되지 않는다!
		// 생성자가 실행되고 난 이후의 시점에서 yml의 값이 할당된다.
		System.out.println("baseDir: " + baseDir);
		System.out.println("enableObfuscation: " + enableObfuscation);
		System.out.println(
				"enableObfuscationHideExt: " + enableObfuscationHideExt);
	}

	/**
	 * 스프링이 클래스를 객체화 시키고 필요한 값들이나 객체를 모두 할당한 이후에
	 * 
	 * @PostContruct 가 적용된 메소드를 실행시킨다.
	 */
	@PostConstruct
	public void postContructor() {
		System.out.println("생성자가 실행된 이후의 시점.");
		System.out.println("> baseDir: " + baseDir);
		System.out.println("> enableObfuscation: " + enableObfuscation);
		System.out.println(
				"> enableObfuscationHideExt: " + enableObfuscationHideExt);
		System.out.println("> availableFileList: " + availableFileList);
		System.out.println("> availableFileList: " + availableFileList.size());
		System.out.println("> availableFileList: " + availableFileList.get(0));
		System.out.println("> availableFileList: " + availableFileList.get(1));
	}

	/**
	 * @Bean 애노테이션이 적용된 메소드가 실행되면 이 메소드가 반환하는 객체를 Bean Container에 적재를 한다.
	 * 
	 *       메소드의 이름이 Bean객체의 이름이 된다.
	 * @return
	 */
	@Bean
	FileHandler fileHanlder() {
		FileHandler fileHandler = new FileHandler();
		fileHandler.setBaseDir(this.baseDir);
		fileHandler.setEnableObfuscation(this.enableObfuscation);
		fileHandler.setEnableObfuscationHideExt(this.enableObfuscationHideExt);
		fileHandler.setAvailableFileList(this.availableFileList);
		fileHandler.setEnableAvailableFileList(this.enableAvailableFileList);
		fileHandler.setHandler(this.fileMimeTypeHandler);
		return fileHandler;
	}

	@Bean
	SHA sha() {
		return new SHA();
	}

}
