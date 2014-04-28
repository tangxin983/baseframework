package com.tx.framework.web.common.persistence.test;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibInterceptor implements MethodInterceptor {
	
	private Object delegate;
	
	public CglibInterceptor(Object delegate){
		this.delegate = delegate;
	}

	/**
	 * 第1个参数是cglib动态生成的代理类（即delegate的子类）
	 * 第2个参数是delegate的方法这里即post()
	 * 第3个参数是delegate的方法参数数组，由于post()方法无参数所以是null
	 * 第4个参数是第2个参数的代理有一些增强的方法
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy mProxy) throws Throwable {
		System.out.println("before");
		Object o = method.invoke(delegate, args);
		System.out.println("after");
		return o;
	}
	
	public static void main(String[] args) {
		// 要增强的目标类
		DefaultOffer offer = new DefaultOffer();
		
		// 构建目标类的子类
		Enhancer hancer = new Enhancer();
		hancer.setSuperclass(offer.getClass());
		hancer.setCallback(new CglibInterceptor(offer));
		DefaultOffer newOffer = (DefaultOffer)hancer.create();
		
		// 调用方法
		newOffer.post();
		
	}

}
