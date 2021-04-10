package com.comparison.service;

import com.comparison.service.controller.ProductController;
import com.comparison.service.model.Product;
import com.comparison.service.model.ProductDetail;
import com.comparison.service.model.Provider;
import com.comparison.service.repository.ProductRepository;
import com.comparison.service.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComparisonServiceApplicationTests {

	@Autowired
	ProductService productService;

	@MockBean
	ProductRepository productRepository;

	private MockMvc mockMvc;

	@Autowired
	private ProductController productController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCountAllProduct() {
		Mockito.when(productRepository.findAll())
				.thenReturn(Arrays.asList(new Product(1, "Samsung Note 9","https://fptshop.com.vn/dien-thoai/note-9-tiki.jpg",null),
											new Product(2,"Samsung Note 10","https://fptshop.com.vn/dien-thoai/note-10-tiki.jpg",null)));

		Assert.assertEquals(2, productService.getAllProduct().size());
	}

	@Test
	public void testGetAllProductAPI() throws Exception {
		List<Product> products = Arrays.asList(new Product(1, "Samsung Note 9","https://fptshop.com.vn/dien-thoai/note-9-tiki.jpg",
				Arrays.asList(new ProductDetail(1, 12.2f, 8.5f, Provider.TIKI, "https://fptshop.com.vn/dien-thoai/note-9-tiki.jpg", null))));

		Mockito.when(productService.getAllProduct())
				.thenReturn(products);

		mockMvc.perform(get("/api/product")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}
}
