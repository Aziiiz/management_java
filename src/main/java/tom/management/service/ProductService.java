package tom.management.service;

import java.util.List;

import tom.common.basic.BasicResponse;
import tom.common.bbs.BasicListResponse;
import tom.management.dao.dto.AccountVO;
import tom.management.dao.dto.ProductVO;
import tom.management.restapi.param.InsertProductParam;
import tom.management.restapi.param.ParamProduct;


public interface ProductService {
	
	public BasicListResponse getAllProducts(List<AccountVO> account);
	
	public BasicListResponse saveImages(InsertProductParam param);
	
	
	public BasicListResponse saveAllData(InsertProductParam param);
	
	
	
}