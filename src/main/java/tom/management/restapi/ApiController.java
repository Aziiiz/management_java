package tom.management.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.internal.org.jline.utils.Log;
import tom.management.LoggerName;
import tom.management.dao.dto.AccountVO;

//import tom.common.basic.BasicController;

//import tom.common.basic.ObjectMapperInstance;
import tom.management.dao.dto.ProductVO;
import tom.management.restapi.param.InsertProductParam;
import tom.management.restapi.param.ParamProduct;
import tom.management.restapi.param.ParamUserAuth;
import tom.management.service.AccountSvc;
import tom.management.service.ProductService;
import tom.common.basic.BasicResponse;
import tom.common.basic.ObjectMapperInstance;
import tom.common.bbs.BasicListResponse;
import tom.common.basic.BasicController;

@RestController
public class ApiController  extends BasicController {
	private Logger log = LogManager.getLogger(LoggerName.SVC);
	
	
	private static final String URL_PREFIX = "api";
	
	
	@Autowired
	private ProductService productService;
	
	
	
	@Autowired
	private AccountSvc accountSvc;
	
	private List<AccountVO> user = null;
	
	@RequestMapping(
			value = {  URL_PREFIX + "/products" }, 
			method = {RequestMethod.GET },
			produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getAllProducts(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		int retSize = 0;
	
		//BasicResponse res = new BasicResponse();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		try {
			log.debug("[" + retSize + "].......... Start (");
				
			if(user != null) {
				BasicListResponse res =  productService.getAllProducts(user);
				resString = mapper.writeValueAsString(res);
			} else {
				resString = mapper.writeValueAsString("fail");
			}
			

		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());

			log.error("[status[" + resStatus + "] [" + e + "]", e);
		} finally {
			log.debug("......... End retSize(" + retSize + ") execTime(" + (System.currentTimeMillis() - startTime) + ")ms..........");
		}

		return new ResponseEntity<String>(resString, resStatus);
	}
	
	
	

	@RequestMapping(
			value = {URL_PREFIX + "/auth"},
			method = {RequestMethod.POST},
			produces = "application/json;charset=utf-8")
	public ResponseEntity<String> auth(Model model,
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session) {
		long startTime = System.currentTimeMillis();
		int retSize =0;
		
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		//String id="", pw="";
		JSONObject tempObj;
		try {
			log.debug("[]......Start("+genReqInfo(request)+ ")...");
			byte[] byteArray = getInputStreamToByte(request.getInputStream());
			
			ParamUserAuth param = mapper.readValue(byteArray, ParamUserAuth.class);
			
			String id =  param.getId();
			String passwd = param.getPasswd();
			
			//String method = requestData.getString("method");
//			id = requestData.getString("id");
//			pw = requestData.getString("passwd");
//			
			ArrayList<AccountVO> loginResult = accountSvc.checkPassword(id, passwd);
			
			if(loginResult != null && loginResult.size() > 0) {
				log.debug("login debug");
				session.setAttribute("id", loginResult.get(0).getAccId());
				session.setAttribute("userId", loginResult.get(0).getUserId());
				session.setAttribute("lastLogin", loginResult.get(0).getLastAccess());
				session.setAttribute("isAdmin", loginResult.get(0).getIsAdmin());
				
				
				tempObj = new JSONObject();
				int type =  loginResult.get(0).getIsAdmin();
				tempObj.put("result", type);
				user = loginResult;
				resString = mapper.writeValueAsString(loginResult);
			}else {
				tempObj = new JSONObject();
				tempObj.put("result", "fail");
				
				resString = mapper.writeValueAsString("fail");
			}
			
			
					
		} catch(Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());
			//resString = mapper.writeValueAsString(e.getMessage());
			
			log.error("status"+resStatus+ "error "+e);
		}finally {
			log.debug("........End retSize("+retSize+") exectime( "+(System.currentTimeMillis() - startTime) + ")ms..........");
		}
		
		return new ResponseEntity<String>(resString, resStatus);
	}
	
	
	
	
	
	@RequestMapping(
			value= {URL_PREFIX + "/save_images"},
			method = {RequestMethod.POST},
			produces = "application/json;charset=utf-8")
	
	public ResponseEntity<String> saveImages(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		int retSize=0;
		
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		try {
			log.debug("[" + retSize + "].......... Start (");
			log.debug("[]......Start("+genReqInfo(request)+ ")...");
			byte[] buf = getInputStreamToByte(request.getInputStream());
			log.debug("[]......Start("+new String(buf)+")...");
			
			InsertProductParam param = mapper.readValue(buf, InsertProductParam.class);
		//	log.debug("[" + param.getMainImage() + "].......... Start (");
			BasicListResponse res = productService.saveImages(param);
			//log.debug(sparam);
			log.debug(param);
			
			resString = mapper.writeValueAsString(res);
			
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());
			log.error("[status[" + resStatus + "] [" + e + "]", e);
		} finally {
			log.debug("......... End retSize(" + retSize + ") execTime(" + (System.currentTimeMillis() - startTime) + ")ms..........");
		}
		
		return new ResponseEntity<String>(resString, resStatus);
		
	}
	
	
	
	
	
	
	@RequestMapping(
			value= {URL_PREFIX + "/save_all_data"},
			method = {RequestMethod.POST},
			produces = "application/json;charset=utf-8")
	
	public ResponseEntity<String> saveAllData(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		int retSize=0;
		
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		try {
			log.debug("[" + retSize + "].......... Start (");
			log.debug("[]......Start("+genReqInfo(request)+ ")...");
			byte[] buf = getInputStreamToByte(request.getInputStream());
			log.debug("[]......Start("+new String(buf)+")...");
			
			InsertProductParam param = mapper.readValue(buf, InsertProductParam.class);
		//	log.debug("[" + param.getMainImage() + "].......... Start (");
			BasicListResponse res = productService.saveAllData(param);
			//log.debug(sparam);
			log.debug(param);
			
			resString = mapper.writeValueAsString(res);
			
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());
			log.error("[status[" + resStatus + "] [" + e + "]", e);
		} finally {
			log.debug("......... End retSize(" + retSize + ") execTime(" + (System.currentTimeMillis() - startTime) + ")ms..........");
		}
		
		return new ResponseEntity<String>(resString, resStatus);
		
	}
	
	
	
	
	
	
	
	
	
}
