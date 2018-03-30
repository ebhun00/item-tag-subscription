package com.safeway.titan.dug.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.safeway.titan.dug.controller.TagController;
import com.safeway.titan.dug.service.ItemTagsService;

@RunWith(SpringRunner.class)
@WebMvcTest(TagController.class)
public class TagControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ItemTagsService itemTagsService;
	
	
	@Test
	public void verify_Item_tag_conversion_successful()
	  throws Exception {
	     
	    mockMvc.perform(get("/tags/generate-item-loc-mapping")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	}
}