package tom.management.restapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tom.common.basic.BasicController;

@Controller("accountPageCtr")
public class AccountPageCtr extends BasicController {
	
	private Logger log = LogManager.getLogger("monitoring");
	
	
	@RequestMapping(value = "/login")
	public String login() {
		log.info("View Login");
		return "login";
	}
	
	@RequestMapping(value = "/account_regist")
	public String accountRegist() {
		log.info("View admin_regist");
		return "account_regist";
	}
	
//	@RequestMapping(value = "/account_modify")
//	public String accountModify() {
//		log.info("View admin_modify");
//		return "account_modify";
//	}

	
	@RequestMapping(value = "/account_list")
	public String accountList() {
		log.info("account_list");
		return "account_list";
	}
	
	
}
