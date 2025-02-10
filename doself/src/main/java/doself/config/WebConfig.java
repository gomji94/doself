package doself.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import doself.common.interceptor.CommonInterceptor;
import doself.user.login.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	
	  @Value("${file.path}") private String fileRealPath;
	  
	  private final CommonInterceptor commonInterceptor; private final
	  LoginInterceptor loginInterceptor;
	  
	  @Override public void addInterceptors(InterceptorRegistry registry) {
	  
	  registry.addInterceptor(commonInterceptor) .addPathPatterns("/**")
	  .excludePathPatterns("/assets/**") .excludePathPatterns("/error/**")
	  .excludePathPatterns("/attachment/**") .excludePathPatterns("/images/**")
	  .excludePathPatterns("/manage/**");
	  
	  
	  registry.addInterceptor(loginInterceptor) .addPathPatterns("/**")
	  .excludePathPatterns("/manage/**") .excludePathPatterns("/")
	  .excludePathPatterns("/assets/**") .excludePathPatterns("/attachment/**")
	  .excludePathPatterns("/mainpage") .excludePathPatterns("/login")
	  .excludePathPatterns("/login/loginPro") .excludePathPatterns("/logout")
	  .excludePathPatterns("/register") .excludePathPatterns("/register/idCheck");
	  
	  WebMvcConfigurer.super.addInterceptors(registry); }
	  
	  @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  
	  String rootPath = getOSFilePath();
	  
	  registry.addResourceHandler("/attachment/**") .addResourceLocations(rootPath
	  + fileRealPath + "/attachment/") .setCachePeriod(3600) .resourceChain(true)
	  .addResolver(new PathResourceResolver());
	  
	  WebMvcConfigurer.super.addResourceHandlers(registry); }
	  
	  public String getOSFilePath() { String rootPath = "file:///"; String os =
	  System.getProperty("os.name").toLowerCase();
	  
	  if(os.contains("win")) { rootPath = "file:///c:"; }else
	  if(os.contains("linux")) { rootPath = "file://"; }else if(os.contains("mac"))
	  { rootPath = "file://"; }
	  
	  
	  return rootPath; }
	 
	
	/*
	 * @Value("${file.path}") // application.properties에서 파일 저장 경로 설정 private String
	 * fileRealPath;
	 * 
	 * private final CommonInterceptor commonInterceptor; private final
	 * LoginInterceptor loginInterceptor;
	 * 
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(commonInterceptor) .addPathPatterns("/**")
	 * .excludePathPatterns("/assets/**", "/error/**", "/attachment/**",
	 * "/images/**", "/manage/**");
	 * 
	 * registry.addInterceptor(loginInterceptor) .addPathPatterns("/**")
	 * .excludePathPatterns("/manage/**", "/", "/assets/**", "/attachment/**",
	 * "/mainpage", "/login", "/login/loginPro", "/logout", "/register",
	 * "/register/idCheck");
	 * 
	 * WebMvcConfigurer.super.addInterceptors(registry); }
	 * 
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * // OS에 따른 파일 경로 설정 String os = System.getProperty("os.name").toLowerCase();
	 * String fileStoragePath;
	 * 
	 * if (os.contains("win")) { // Windows 환경에서는 file: 접두사와 함께 경로 설정 (예:
	 * C:/uploads/attachment/) fileStoragePath = "file:///" +
	 * fileRealPath.replace("\\", "/") + "/attachment/"; } else { // Linux, Mac
	 * 환경에서는 file: 접두사 추가 (예: /var/jenkins_home/uploaded_images/attachment/)
	 * fileStoragePath = "file:" + fileRealPath + "/attachment/"; }
	 * 
	 * // 정적 리소스 핸들러 추가 registry.addResourceHandler("/attachment/**")
	 * .addResourceLocations(fileStoragePath) // file: 접두사 추가된 경로 사용
	 * .setCachePeriod(3600) .resourceChain(true) .addResolver(new
	 * PathResourceResolver());
	 * 
	 * WebMvcConfigurer.super.addResourceHandlers(registry); }
	 */
}
