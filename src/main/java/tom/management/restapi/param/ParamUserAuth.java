package tom.management.restapi.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tom.common.bbs.AbstractSearchParam;


@Data

public class ParamUserAuth{


	private String id;
	private String passwd;
	private String prodid;
	private String mainimage;
}

