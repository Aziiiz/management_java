package tom.management.restapi;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import tom.common.basic.BasicController;
//import tom.monitoring.CustomConfig;

@Controller
public class ViewController {

	
//	private CustomConfig customConfig;
	
	@RequestMapping(value = {"/page/template" }, method = {RequestMethod.GET })
	public String html(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//log.debug(">>> " + customConfig);
		
		HttpSession mySession = request.getSession();
		String sessionId = mySession.getId();
		String myId = (String)mySession.getAttribute("MY_ID");
		
		if(myId == null) {
			mySession.setAttribute("MY_ID", "ID:" + sdf.format(new Date()));
		}
		
	//	log.debug("SID["+sessionId+"] >>> ID["+myId+"]");
		
		return "template";
	}
}
