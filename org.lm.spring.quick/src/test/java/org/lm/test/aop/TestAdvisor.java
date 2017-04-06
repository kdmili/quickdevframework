package org.lm.test.aop;

import java.lang.reflect.Method;

import junit.framework.Test;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.cglib.core.ReflectUtils;

import aj.org.objectweb.asm.Type;

public class TestAdvisor extends AbstractBeanFactoryPointcutAdvisor{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2918165549692124496L;

	@Override
	public Pointcut getPointcut() {
		NameMatchMethodPointcut np = new NameMatchMethodPointcut();
		np.setMappedNames("*test");
		np.setClassFilter(new ClassFilter() {
			@Override
			public boolean matches(Class<?> paramClass) {
			return	TestAop.class.isAssignableFrom(paramClass);
			}
		});
		return np;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Method m = TestAdvisor.class.getMethod("test", null);
		String desc = Type.getMethodDescriptor(m);
		System.out.println(desc);
		System.out.println(m.getName());
		
//		Method m = ReflectUtils.findMethod("org.lm.test.aop.TestAdvisor.test");
//		System.out.println(m);
 	boolean match = new TestAdvisor().getPointcut().getMethodMatcher().matches(m, null);
 	System.out.println(match);

 
		
	}
}
