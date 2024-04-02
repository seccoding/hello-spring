package com.hello.forum.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // AOP 모듈
@Component // Spring Bean 생성해주는 Annotation
public class TimingAspect {

	private Logger logger = LoggerFactory.getLogger("timing");

	/**
	 * <pre>
	 * AOP를 적용할 대상을 지정.
	 * execution() : 실행단계에서 적용할 대상을 지정.
	 *   public : public 접근 제어자를 사용하는 것을 대상으로 한다.
	 *   * : 모든 반환타입을 대상으로 한다.
	 *   com.hello.forum..service.*ServiceImpl: com.hello.forum 아래에 있는 모든 패키 중에서 
	 *                                          service 패키지 내부에 있는
	 *                                          ServiceImpl로 끝나는 모든 클래스를 대상으로 한다.
	 *   .*(..) : ServiceImpl로 끝나는 모든 클래스 내부의 모든 메소드를 대상.
	 *   
	 *   *ServiceImpl 클래스 밑에 있는 public 모든반환타입 메소드명() 을 대상으로 Aspect 를 실행한다.
	 * </pre>
	 */
	@Pointcut("execution(public * com.hello.forum..service.*ServiceImpl.*(..))")
	public void aroundTarget() {
	}

	/**
	 * <pre>
	 * 원래 실행될 메소드의 전, 후에 공통코드를 실행한다.
	 * ServiceImpl에 있는 메소드가 실행될 때(Pointcut 대상이 실행될 때)
	 * 아래 메소드가 실행된다. - Weaving된 코드가 실행된다.
	 * </pre>
	 * 
	 * @param pjp 원래 실행될 클래스와 메소드의 정보
	 * @return 원래 실행될 메소드의 반환 값.
	 * @throws Throwable
	 */
//	@Before
//	@After
//	@AfterReturning
//	@AfterThrowing
	@Around("aroundTarget()")
	public Object timimgAdvice(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object result = null;

		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			// ServiceImpl에서 발생한 예외를 그대로 던진다.
			// Controller로 예외가 전달되고, 마지막으로 ControllerAdvice로 예외가 전달되어
			// 공통 예외를 처리할 수 있기 때문.
			throw e;
		} finally {
			stopWatch.stop();

			// 원래 실행되어야 하는 클래스.
			String classPath = pjp.getTarget().getClass().getName();
			// 원래 실행되어야 하는 메소드.
			String methodName = pjp.getSignature().getName();
			logger.debug("{}.{} 걸린시간: {}ms", classPath, methodName,
					stopWatch.lastTaskInfo().getTimeMillis());
		}

		return result;
	}

}
