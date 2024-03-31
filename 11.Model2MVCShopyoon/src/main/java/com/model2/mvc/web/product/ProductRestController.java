package com.model2.mvc.web.product;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//RestController는 RestController로 어노테이션!!!!!!!!!!!
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

		
	public ProductRestController(){ 
		System.out.println(this.getClass());
	}
	

	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value="json/getProduct", method=RequestMethod.GET)
	public Product getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu ) throws Exception 
	{
		System.out.println("/product/json/getProduct");
		
		Product prod = productService.getProduct(prodNo);
		

		return prod;
	}
	
}