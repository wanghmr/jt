package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;

@Controller
@RequestMapping("/card")
public class CartController {

	@Reference(check = false)
	private DubboCartService dubboCartService;
	/**
	 *  要求回显购物车列表信息.
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model) {
		long userId = 7L; //暂时写死
		List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
		System.out.print("--------------"+cartList.toString()+"---------------");
		model.addAttribute("cartList", cartList);
		return "cart";
	}

}
