package doself.common.interceptor;

import java.util.List;
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
	
	private final static List<String> excludeUri = List.of("/market/purchaseitem", "/ticket/payment", "/challenge/list/view"
															, "/challenge/feed/memberlist", "/challenge/feed/feedcomment"
															, "/ticket/payment/result", "/challenge/checkDuplicateName", "/feed/list"
															, "/ticket/purchasedetail/refund", "/register/idCheck", "/feed/createFeed"
															, "/challenge/list/view/participation", "/ticket/purchasedetail/refund"
															, "/ticket/purchasedetail/isCheck", "/challenge/feed/view/**");

	
	
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
		
		Set<String> paramKeys = request.getParameterMap().keySet();
		String uri = request.getRequestURI();
		StringJoiner param = new StringJoiner("&");
		
		log.info(request.getRequestURI());

		for(String paramkey : paramKeys) {
			log.info("=============> paramkey : {}", paramkey);
			if(!paramkey.equals("currentPage")) {			
				param.add(paramkey + "="+ request.getParameter(paramkey));
			}
		}

		log.info("queryParam : {}", param);
		
		if(!excludeUri.contains(uri)) modelAndView.addObject("queryParam", param);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
