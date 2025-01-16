package doself.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import doself.common.interceptor.CommonInterceptor;
import doself.user.login.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final CommonInterceptor commonInterceptor;
	private final LoginInterceptor loginInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(commonInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/assets/**")
				.excludePathPatterns("/error/**")
				.excludePathPatterns("/manage/**");
				
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/manage/**")
				.excludePathPatterns("/")
				.excludePathPatterns("/assets/**")
				.excludePathPatterns("/mainpage")
				.excludePathPatterns("/login")
				.excludePathPatterns("/login/loginPro")
				.excludePathPatterns("/logout")
				.excludePathPatterns("/register")
				.excludePathPatterns("/register/idCheck");
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
