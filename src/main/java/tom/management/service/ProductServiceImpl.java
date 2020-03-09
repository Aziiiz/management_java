package tom.management.service;


import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.ListUtils;

import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tom.common.basic.BasicResponse;
import tom.common.bbs.BasicListResponse;
import tom.common.util.HttpRequestor;
import tom.management.LoggerName;
import tom.management.dao.ImageDao;
import tom.management.dao.ProductDao;
import tom.management.dao.dto.AccountVO;
import tom.management.dao.dto.ImageVO;
import tom.management.dao.dto.ProductVO;
import tom.management.restapi.param.InsertProductParam;
import tom.management.restapi.param.ParamProduct;


@Service
public class ProductServiceImpl implements ProductService {
	private Logger log = LogManager.getLogger(LoggerName.SVC);
	
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ImageDao imageDao;
	
	
	@Override
	public BasicListResponse getAllProducts(List<AccountVO> account) {
		BasicListResponse res = new BasicListResponse();
		try {
			
			
			
			int userId = account.get(0).getUserId();
			log.debug("user id "+userId);
			List<ImageVO> products = imageDao.selectAllImages(userId, 100);
			log.debug("[getAllProducts] user product qty: "+products.size());
			if(products.size()> 0) {
				int limit =  100 - products.size();
				List<ProductVO> list = new ArrayList<>();
				for(int i=0; i<products.size(); i++) {
				  int prodId = products.get(i).getParent();
				  ProductVO product = new ProductVO();
				  log.debug("[getAllProducts]"+prodId);
				  product = productDao.selectProductbyId(prodId);		
				  List<ImageVO> img = imageDao.selectImagesById(prodId);
				  product.setImageList(img);
				  list.add(product);  
				}	
				if(limit > 0) {	
					List<ImageVO> prodList = imageDao.selectAllImages(-1, limit);
					List<Integer> listImages = new ArrayList<>();
					
					for(int i=0; i<prodList.size(); i++) {
					  int prodId = prodList.get(i).getParent();
					  ProductVO product = new ProductVO();
					  log.debug("[getAllProducts]"+prodId);
					  product = productDao.selectProductbyId(prodId);		
					  List<ImageVO> img = imageDao.selectImagesById(prodList.get(i).getParent());
					  product.setImageList(img);
					  list.add(product);
					  for(int j=0; j<img.size(); j++) {
						  listImages.add(img.get(j).getImageId());
					  }
					}
					for(int k=0; k<listImages.size(); k++) {
						int updateImages = imageDao.updatebyImgId(userId, listImages.get(k));
						log.debug("list of images "+updateImages);					
					}
					
				}
		
				res.setList(list);
				
			}else {
				
				List<ImageVO> prodList = imageDao.selectAllImages(-1, 100);
				List<ProductVO> list = new ArrayList<>();
				List<Integer> listImages = new ArrayList<>();
				
				for(int i=0; i<prodList.size(); i++) {
				  int prodId = prodList.get(i).getParent();
				  ProductVO product = new ProductVO();
				  log.debug("[getAllProducts]"+prodId);
				  product = productDao.selectProductbyId(prodId);		
				  List<ImageVO> img = imageDao.selectImagesById(prodList.get(i).getParent());
				  product.setImageList(img);
				  list.add(product);
				  for(int j=0; j<img.size(); j++) {
					  listImages.add(img.get(j).getImageId());
				  }
				}
				//int updateImages = imageDao.updateImagesForUser(userId, listImages);
				for(int k=0; k<listImages.size(); k++) {
					int updateImages = imageDao.updatebyImgId(userId, listImages.get(k));
					log.debug("list of images "+updateImages);
				}
				res.setList(list);
				
			}
			res.setData(account);
			
		}catch(Exception e) {
			log.error(" error getting productList "+e,e);
			res.setState(BasicListResponse.STATE_ERROR);
			res.setStateMessage(e.getMessage());
		}
		
		return res;
	}


	@Override
	public BasicListResponse saveImages(InsertProductParam param) {
		
		BasicListResponse res = new BasicListResponse();
		try {
		
		int prodId = param.getProdid();
		log.debug("[saveImages] "+ prodId);
		int mainImage = param.getMainimage();
		log.debug("[saveImages] "+mainImage);
		List<Integer> extra = param.getListextra();
		int check = param.getAddimg();
		log.debug("[extraImages] "+extra);
		if(param.getIdsremove() != null) {
			imageDao.deleteImgByParentId(prodId);
		}else {
		 
			if(check != 0) {
				productDao.updateByProdId(1,prodId);
			}else {
				productDao.updateByProdId(0, prodId);
			}
			
			
			if(extra != null) {
					 for(int i=0; i<extra.size(); i++) {
			          int extraimgs = imageDao.updateImgType(2, extra.get(i));
			          log.debug("update extraimagesArr "+extraimgs);
			       }
			       
			}
			if(mainImage != 0) {
				  imageDao.updateImgType(1, mainImage);
				  extra.add(mainImage);
				  imageDao.deleteImgData(extra, prodId);
		    }else {
				  imageDao.deleteImgByParentId(prodId);
			  }
			  
				
			}

		 res.setState(BasicListResponse.STATE_SUCCESS); 
		
		}catch(Exception e) {
			log.error(" error getting productList "+e,e);
			res.setState(BasicListResponse.STATE_ERROR);
			res.setStateMessage(e.getMessage());
		}
		return res;
		
	}


	@Override
	public BasicListResponse saveAllData(InsertProductParam param) {
		BasicListResponse res = new BasicListResponse();
		try {
			List<Integer> mainList = param.getListmain();
			log.debug(mainList);
			List<Integer> listIds  = param.getListids();
			log.debug(listIds);
			List<Integer> extraImages = param.getListextra();
			log.debug(extraImages);
			
			List<Integer> checkList = param.getCheckbox();
			log.debug(checkList);
			List<Integer> prodIds = param.getIdsremove();
			log.debug(prodIds);
			
			log.debug("get imgList");
//
			
            
			if(checkList != null) {
				for(int i=0; i<checkList.size(); i++) {
					productDao.updateByProdId(1,checkList.get(i));
				}
			}
			
			Boolean main =  updateType(mainList, 1);
			Boolean extra = updateType(extraImages, 2);
			List<Integer> imgList = new ArrayList<>();
			if(main) {
				if(extra) {
					 imgList = ListUtils.union(mainList, extraImages);
					 removeData(listIds, imgList);
				}else {
					removeData(listIds, mainList);
				}
				 
				
				
			}
			
			
			if(prodIds.size()> 0) {
				for(int i=0; i<prodIds.size(); i++) {
					imageDao.deleteImgByParentId(prodIds.get(i));
				}
			}
			
			
			
			
			

         res.setState(BasicListResponse.STATE_SUCCESS);
			
		}catch(Exception e) {
			log.error(" error getting productList "+e,e);
			res.setState(BasicListResponse.STATE_ERROR);
			res.setStateMessage(e.getMessage());
		}
		
		
		
		return res;
	}
	
	
	
	
	
	
	private Boolean updateType(List<Integer>list, int type ) {
		
		
		if(list != null) {
			for(int i=0; i<list.size(); i++) {
				imageDao.updateImgType(type, list.get(i));
			}
			
			return true;
		}else {
			return false;
		}
		
	}
	
	
	private Boolean removeData(List<Integer>listIds, List<Integer>imgList) {
			
		
		if(listIds.size()>0) {
			
		
			for(int i=0; i<listIds.size(); i++) {
				
				imageDao.deleteImgData(imgList, listIds.get(i));
			}
			return true;
		}else {
			return false;
		}
		
		
	
		
	}
	
	
	
	
	
	
	
	

	




}