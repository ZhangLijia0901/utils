package com.utils.database.configuration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.utils.database.common.Constant;
import com.utils.database.entity.DBConnectInfo;

@Configuration
public class CustomizeWebMvcConfigurer implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
		resolvers.add(new SessionHandlerMethodArgumentResolver(DBConnectInfo.class, Constant.DB_CONNECT_INFO));
	}
}

/***
 * 
 * @Description: session参数解析
 * @author: 张礼佳
 * @date: 2018年10月11日 下午1:31:03
 */
class SessionHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	private Class<?> resolverClazz;
	private String sessionName;

	public SessionHandlerMethodArgumentResolver(Class<?> resolverClazz, String sessionName) {
		this.resolverClazz = resolverClazz;
		this.sessionName = sessionName;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		if (clazz == this.resolverClazz && parameter.getMethodAnnotation(RequestBody.class) == null)
			return true;

		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = httpServletRequest.getSession();
		return session.getAttribute(this.sessionName);
	}
}
