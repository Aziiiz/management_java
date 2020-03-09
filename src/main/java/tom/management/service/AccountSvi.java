package tom.management.service;

import java.util.ArrayList;

import tom.management.dao.dto.AccountVO;

public interface AccountSvi {
	
	ArrayList<AccountVO> selectAccount(String id, String passwd);
	
	int updateLoginTime(AccountVO account) throws Exception;
}