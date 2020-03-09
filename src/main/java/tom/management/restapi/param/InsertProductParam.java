package tom.management.restapi.param;


import java.util.List;

import lombok.Data;


@Data

public class InsertProductParam {
	
	
	private int mainimage;
	
	private int prodid;
	private int userid;
	private int addimg;
	private List<Integer> listmain;
	private List<Integer> listextra;
	private List<Integer> listids;
	private List<Integer> checkbox;
	private List<Integer> idsremove;
	
}