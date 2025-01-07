package doself.common.interceptor;

import java.util.Set;
import java.util.StringJoiner;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonInterceptor implements HandlerInterceptor {
	
	//컨트롤러 진입전 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Set<String> paramKeys = request.getParameterMap().keySet();
		
		StringJoiner param = new StringJoiner(", ");
		
		log.info(request.getRequestURI());
		for(String paramkey : paramKeys) {
			param.add(paramkey + ": "+ request.getParameter(paramkey));
			log.info("paramkey : {}",paramkey);
		}
		
		log.info("ACCESS INFO ====================================");
		log.info("PORT 		:::::::::::: {}", request.getLocalPort());
		log.info("SERVERNAME 	:::::::::::: {}", request.getServerName());
		log.info("HTTP METHOD 	:::::::::::: {}", request.getMethod());
		log.info("URI 		:::::::::::: {}", request.getRequestURI());
		log.info("CLIENT IP 	:::::::::::: {}", request.getRemoteAddr());
		log.info("PARAMETER 	:::::::::::: {}", param);
		log.info("================================================");
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
