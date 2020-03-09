package tom.common.bbs;

import java.util.List;

import lombok.Data;

@Data
public class BasicListResponse {

	
	public static final int STATE_SUCCESS = 0;
	public static final int STATE_ERROR   = 1;
	
	public static final String STATE_SUCCESS_MESSAGE = "success";
	
	
	private AbstractSearchParam  sParam;
	private List<?> list;
	
	private Object data;
	
	
	private int state;
	private String stateMessage;
	private String errorMessage;
	
	
}
