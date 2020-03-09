package tom.management.restapi.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tom.common.bbs.AbstractSearchParam;


@Data
@EqualsAndHashCode(callSuper=false)
public class ParamProduct extends AbstractSearchParam{


	private String userId;
	private int limit;
	private String svcStatus;
	private String metaType;
	
}
