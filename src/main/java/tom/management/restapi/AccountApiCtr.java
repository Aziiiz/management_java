//package tom.management.restapi;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.JSONObject;
//import org.mybatis.logging.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import tom.common.basic.BasicController;
//import tom.common.basic.ObjectMapperInstance;
//import tom.management.LoggerName;
//import tom.management.dao.dto.AccountVO;
//import tom.management.restapi.param.ParamUserAuth;
//import tom.management.service.AccountSvc;
//
////@Controller("accountApiCtr")
////@RequestMapping("api")
//
//public class AccountApiCtr extends BasicController {
//	
//	
//	private static final String URL_PREFIX = "api";
//	
//	private Logger log = LogManager.getLogger("management");
//	
//	@Autowired
//	private AccountSvc accountSvc;
//	
//	@RequestMapping(
//			value = {URL_PREFIX + "/auth"},
//			method = {RequestMethod.POST},
//			produces = "application/json;charset=utf-8")
//	public ResponseEntity<String> auth(Model model,
//			HttpServletRequest request, 
//			HttpServletResponse response,
//			HttpSession session) {
//		long startTime = System.currentTimeMillis();
//		int retSize =0;
//		
//		String resString = "{}";
//		HttpStatus resStatus = HttpStatus.OK;
//		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
//		//String id="", pw="";
//		JSONObject tempObj;
//		try {
//			log.debug("[]......Start("+genReqInfo(request)+ ")...");
//			byte[] byteArray = getInputStreamToByte(request.getInputStream());
//			
//			ParamUserAuth param = mapper.readValue(byteArray, ParamUserAuth.class);
//			
//			String id =  param.getId();
//			String passwd = param.getPasswd();
//			
//			//String method = requestData.getString("method");
////			id = requestData.getString("id");
////			pw = requestData.getString("passwd");
////			
//			ArrayList<AccountVO> loginResult = accountSvc.checkPassword(id, passwd);
//			
//			if(loginResult != null && loginResult.size() > 0) {
////				log.debug("login debug");
////				session.setAttribute("id", loginResult.get(0).getAccId());
////				session.setAttribute("userId", loginResult.get(0).getUserId());
////				session.setAttribute("lastLogin", loginResult.get(0).getLastAccess());
////				
//				
//				tempObj = new JSONObject();
//				int type =  loginResult.get(0).getIsAdmin();
//				tempObj.put("result", type);
//				resString = mapper.writeValueAsString(tempObj);
//			}else {
//				tempObj = new JSONObject();
//				tempObj.put("result", "fail");
//				
//				resString = mapper.writeValueAsString(tempObj);
//			}
//			
//			
//					
//		} catch(Exception e) {
//			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//			response.setHeader("X-Status-Code", "500");
//			response.setHeader("X-Status-Reason", e.getMessage());
//			//resString = mapper.writeValueAsString(e.getMessage());
//			
//			log.error("status"+resStatus+ "error "+e);
//		}finally {
//			log.debug("........End retSize("+retSize+") exectime( "+(System.currentTimeMillis() - startTime) + ")ms..........");
//		}
//		
//		return new ResponseEntity<String>(resString, resStatus);
//	}
//	
//}