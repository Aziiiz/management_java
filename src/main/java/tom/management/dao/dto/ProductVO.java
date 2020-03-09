package tom.management.dao.dto;

import java.security.Timestamp;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import lombok.Data;



@Data
public class ProductVO {
	
	public ProductVO() {
		
	}
	
	public ProductVO(int prodId, String name, String detailUrl, List<ImageVO> imageList, int userId) {
		this.prodId = prodId;
		this.name = name;
		this.detailUrl = detailUrl;
		this.userId = userId;
		
		this.imageList = imageList;
		
	}
		
	private int prodId;
	private String name;
	private String detailUrl;
	private int userId;
	
	private List<ImageVO> imageList;
	
	
	
	
	
}
