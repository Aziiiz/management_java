package tom.management.restapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ApiControllerPage")
public class ApiControllerPage {
	
	
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
	
}