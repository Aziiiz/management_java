package tom.management.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tom.management.dao.dto.ProductVO;
import tom.management.restapi.param.ParamProduct;



public interface ProductDao {
	
	public List<ProductVO> selectAllProducts(@Param("userId") int userId,
											 @Param("limit") int limit);
	
	
	public int removeProduct(@Param("id") int id);
	
	public int editProduct(@Param("id") int id,
						   @Param("title") String title,
						   @Param("imgUrl") String imgUrl);
	
	
	public Long selectListCount();
	
	
	public ProductVO selectProductbyId(@Param("prodId") int prodId);
	
	
	public int updateByProdId(@Param("img") int img,
							  @Param("prodId")int prodId);
	
	
}


   