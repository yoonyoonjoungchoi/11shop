package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

		
	public ProductController(){ 
		System.out.println(this.getClass());
	}
	

	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	

	
	//@RequestMapping("/addProductView.do")
	//public String addProductView() throws Exception {
	@RequestMapping( value="addProduct", method=RequestMethod.GET )
	public String addProduct() throws Exception{
		
		System.out.println("/product/addProduct : GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping( value="addProduct", method=RequestMethod.POST )
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/product/addProduct : POST");
		//Business Logic
		product.setManufactureDay(product.getManufactureDay().replace("-", ""));
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	//@RequestMapping("/getProduct.do")  
	@RequestMapping( value="getProduct", method=RequestMethod.GET )
	public String getProduct( HttpServletRequest request, HttpServletResponse response,  @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
	//cookievalue�� ���� �ʿ�. 			
	//	������ ��Ű�� �ȵǴ� ���� : interceptor�� ���İ� cookie�� path �̰� ������ �ٸ��� ���ͼ��� �������� �ٲ�ٰ� �����ϸ� ���ϴ�
	//  ������ ��Ű ������ ���ۿ� �˻��ϸ� ������ ���� ���´�.
		
		System.out.println("/product/getProduct : GET");
	
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		
		 String history = null;
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null && cookies.length > 0) {
	            for (int i = 0; i < cookies.length; i++) {
	                Cookie cookie = cookies[i];
	                
	                if (cookie.getName().equals("history")) {
	                    history = cookie.getValue();
	                }
	            }
	        }
	        
	       
	        
	        if (history == null) {
	            history = Integer.toString(prodNo);
	        } else {
	            history += "/" + prodNo;
	        }
	        
	        Cookie historyCookie = new Cookie("history", history);
	        historyCookie.setPath("/");
	        response.addCookie(historyCookie);
	        
	       System.out.println("menu test:::"+request.getParameter("menu"));
	        if (request.getParameter("menu").equals("search")) {
	              return  "forward:/product/getProduct.jsp";
	        } 
	        else {
	         
	           return "forward:/product/updateProductView.jsp";
	        }
		} 
	       
	    
	

	
	
//	@RequestMapping("/updateProductView.do")
//	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{
	@RequestMapping( value="updateProduct", method=RequestMethod.GET )
	public String updateProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{
	
		System.out.println("/product/updateProduct. : GET");
		
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	//@RequestMapping("/updateProduct.do")   //menu��� name�� ������ �װ� ã���ְڴٴ� ��. �׸��� �װ� ã�Ƽ� string menu�� �־��ְڴ�.
	@RequestMapping( value="updateProduct", method=RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session, @RequestParam("menu") String menu) throws Exception{
		
		System.out.println("/product/updateProduct : POST");
		
		productService.updateProduct(product);
		
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo()+"&menu="+menu;
	}
	
	
	@RequestMapping( value="listProduct" )
	public String listProduct(@ModelAttribute("search") Search search,@RequestParam("menu") String menu, Model model) throws Exception{
		
		//System.out.println("/listProduct.do");
		System.out.println("/product/listProduct : GET / POST");    //�길   get/post�� ����: a �±�, form  �±� �� �� ��������. 
		
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		
		
		System.out.println("�˻���:"+search.getSearchCondition());
		
		search.setPageSize(pageSize);
		
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
//		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
}