package doself.user.login.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		
		HttpSession session = request.getSession();
		
		String sessionId = (String) session.getAttribute("SID");
		
		boolean isProcess = true;
		
		if(sessionId == null) {
			
			response.sendRedirect("/login");
			isProcess = false;
		}else {
			String memberGrade = (String) session.getAttribute("SGRD");
			
			String requestUri = request.getRequestURI();			
			if("mg_002".equals(memberGrade) || "mg_003".equals(memberGrade)) {
				if(requestUri.indexOf("/admin") > -1) {
					response.sendRedirect("/feed");
					isProcess = false;
				}
			}
		}
		return isProcess;
	}
}
