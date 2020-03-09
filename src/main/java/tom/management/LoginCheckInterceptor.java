package tom.management;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tom.management.dao.dto.AccountVO;


public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	private Logger log = LogManager.getLogger("monitoring");
	 
	//  인증 체크가 필요 없는 URL 리스트   
	List<String> urls;
	 
	public void setUrls(List urls) {
		this.urls = urls;
	}
	 
	
	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response, 
			Object handler) throws Exception {
		
		try {
		
			log.debug("[PreHandle Interceptor]"+urls.size());          
			log.debug("[INTERCEPTOR] request - "+request.getRequestURI());
			
			if(request.getMethod().contentEquals("POST")) {
				log.debug("[This request's method is POST]");
				log.debug("[This request's url is \""+request.getRequestURI()+"\"");
				if(request.getRequestURI().contentEquals("/monitoring/api/v1/block.do")) {
					log.debug("[Block POST]");
					return true;
				}
			}
			
	        // 예외 URL 확인 
			for(int i=0; i < urls.size(); i++) {
				
				log.debug("[INTERCEPTOR] excepts - "+urls.get(i));
			
			    if (request.getRequestURI().matches(urls.get(i))) {
			    	log.debug("인증 체크가 필요없는 URL");
			    	/*
				    log.debug("== 인증 체크가 필요 없는 URL ============================");
				    log.debug("== URL : "+ urls.get(i) +" ============================");
				    log.debug("== return true ============================");
				    log.debug("== 인터셉터 종료 ============================");
				    */
				    return true;
			    }
			 
			}
			
			
			// session 객체를 가져옴
	        HttpSession session = request.getSession();
	        // login처리를 담당하는 사용자 정보를 담고 있는 객체를 가져옴
	        Object obj = session.getAttribute("grade");
	          
	        
	        if ( obj == null ){
	            // 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
	        	log.debug("[세션 없음]");
	        	PrintWriter writer = response.getWriter();
	        	writer.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=\"utf-8\" /></head><body>");
	        	writer.println("<script \"text/javascript\" charset=\"utf-8\" >alert('Session expirated!'); location.href='/login';</script>");
	        	writer.println("</body></html>");
	        	writer.flush();
	        	writer.close();
	            response.sendRedirect("/login");
	            return false; // 더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
	        }
	        
	        log.debug("[세션 확인됨] - "+session.getAttribute("grade"));
	        log.debug("[세션 확인됨] - "+session.getAttribute("activation"));
	        
	        int actCode = ((Integer)(session.getAttribute("activation"))).intValue();
//	        
//	        if(actCode == AccountVO.INACT_CODE) {
//	        	log.info("비활성 계정으로 로그인 시도");
//	        	PrintWriter writer = response.getWriter();
//	        	writer.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=\"utf-8\" /></head><body>");
//	        	writer.println("<script \"text/javascript\" charset=\"utf-8\" >alert('Inactive Account.'); location.href='/login';</script>");
//	        	writer.println("</body></html>");
//	        	writer.flush();
//	        	writer.close();
//	        	return false;
//	        }
	        
		} catch (Exception e) {
			log.error("[INTERCEPTOR ERROR] : " + e.getMessage());
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("[PostHandle Interceptor]");		
		super.postHandle(request, response, handler, modelAndView);
	}
	 
 
}
