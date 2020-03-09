package tom.management.dao.dto;

import lombok.Data;


@Data
public class ImageVO {
	public ImageVO() {
		
	}
	
	public ImageVO(int imageId, int type, String url, String data, int state, int userId, int parent) {
		this.imageId = imageId;
		this.type = type;
		this.url = url;
		this.data = data;
		this.state = state;
		this.userId = userId;
		this.parent= parent;
		
	}
	
	
	
	private int imageId;
	private int type;
	private String url;
	private String data;
	private int state;
	private int userId;
	private int parent;
	
}