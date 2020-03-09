package tom.management.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import tom.management.dao.dto.AccountVO;



public interface AccountDao {
	
	
	ArrayList<AccountVO> selectAccount(@Param("id")String id, 
										@Param("passwd")String passwd);
	
	int updateLoginTime(AccountVO account) throws Exception;
}