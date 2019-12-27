package com.miaosha.demo;

import com.miaosha.demo.dao.ItemStockDOMapper;
import com.miaosha.demo.dao.PromoDOMapper;
import com.miaosha.demo.dataobject.ItemStockDO;
import com.miaosha.demo.dataobject.PromoDO;
import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.service.ItemService;
import com.miaosha.demo.service.UserService;
import com.miaosha.demo.service.model.ItemModel;
import com.miaosha.demo.service.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	UserService userService ;

	@Autowired
	ItemService itemService;

	@Autowired
	ItemStockDOMapper itemStockDOMapper;

	@Autowired
	PromoDOMapper promoDOMapper;

	/**
	 * UserService insert 测试
	 */


	@Test
	public void testUserServiceInsert() throws BusinessException {
		UserModel userModel = new UserModel();
		userModel.setName("1");
		userModel.setAge(1);
		byte a = 2 ;
		userModel.setGender(a);
		userModel.setTelphone("1234567");
		userModel.setRegisterMode("byphone");
		userModel.setEncrptPassword("123456");
		userService.register(userModel);
	}


	/**
	 * ItemService create 测试
	 */
	@Test
	public void testItemCreate() throws BusinessException {
		ItemModel itemModel = new ItemModel();
		itemModel.setTitle("华为");
		itemModel.setStock(1);
		itemModel.setDescription("国产");
		itemModel.setImgUrl("123");
		itemModel.setPrice(new BigDecimal(123));

		itemService.createItem(itemModel);
	}

	/**
	 *ItemService selectById 测试
	 */
	@Test
	public void testItemSelectById() throws BusinessException {
		ItemModel itemModel = itemService.getItemById(18);
		System.out.println(itemModel.getTitle());
	}

	/**
	 * itemStock mapper 测试
	 */
	@Test
	public void testItemStockList(){
		List<ItemStockDO> itemStockDOList = itemStockDOMapper.listItemStock(19,22);
		System.out.println(itemStockDOList.get(2).getItemId());
	}


	/**
	 * itemService 测试
	 */

	@Test
	public void  testItemServiceItemModelList() throws BusinessException {
		itemService.listItem();
	}


	/**
	 * promMapperSelectByItemId 测试
	 */
	@Test
	public void testPromoGetByItemId(){
		PromoDO promoDO = promoDOMapper.selectByItemId(2);
		System.out.println(promoDO.getStartTime());
	}

}
