package tom.management.dao.dto;

import java.sql.Timestamp;

import lombok.Data;


@Data
public class AccountVO {
	public AccountVO() {
		
	}
	public AccountVO(int userId, String accId, String accPwd, Timestamp lastAccess, int isAdmin) {
		this.userId = userId;
		this.accId = accId;
		this.accPwd = accPwd;
		this.lastAccess = lastAccess;
		this.isAdmin = isAdmin;
	}
	private int userId;
	private String accId;
	private String accPwd;
	private int isAdmin;
	private Timestamp lastAccess;
}