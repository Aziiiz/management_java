package tom.management.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tom.management.dao.dto.ImageVO;
import tom.management.dao.dto.ProductVO;

public interface ImageDao {
	
	public List<ImageVO> selectAllImages(@Param("userId") int userId,
										@Param("limit") int limit);
	
	
	public List<ImageVO> selectImagesById(@Param("prodId") int prodId);
	
	public ProductVO selectProductbyId(@Param("prodId")int prodId);
	
	
	public int updateImagesForUser(@Param("userId") int userId,
									@Param("id") List<Integer> imgId);
	
	public int updatebyImgId(@Param("userId") int userId,
							 @Param("imgId") int imgId);
	
	
	public int updateImgType(@Param("type") int type,
							 @Param("imgId") int imgId);
	
	
	public ImageVO selectImg(@Param("limit") int limit);
	
	public int deleteImgData(@Param("id")List<Integer> ids,
							 @Param("prodId") int prodId);
	
	
	
	public int deleteImgByParentId(@Param("proId") int prodId);
}