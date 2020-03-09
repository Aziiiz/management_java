package tom.management.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tom.common.bbs.BasicListResponse;
import tom.management.dao.AccountDao;
import tom.management.dao.dto.AccountVO;

@Service("accountSvc")
public class AccountSvc implements AccountSvi {
	
	public static int SUPER_MANAGER = 1;
	public static int USER = 2;
	
	
	private Logger log = LogManager.getLogger("management");
	
	//@Resource(name="accountDao")
	@Autowired
	private AccountDao accountDao;
	
	
	
	@Override
	public ArrayList<AccountVO> selectAccount(String id, String passwd) {
		
		return accountDao.selectAccount(id, passwd);
	}
	
	
	public ArrayList<AccountVO> checkPassword(String id, String passwd) {
		
		log.debug("password check");
		ArrayList<AccountVO> account = accountDao.selectAccount(id, null);
		String password = account.get(0).getAccPwd();
		
		
		boolean result = compare(password, passwd);
		
		if(result) {
			return account;
		}else {
			return null;
		}
	}
	
	public boolean compare(String first, String second) {
		if(first.contentEquals(second)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	@Override
	public int updateLoginTime(AccountVO account) throws Exception {
		return accountDao.updateLoginTime(account);
	}
	
} 