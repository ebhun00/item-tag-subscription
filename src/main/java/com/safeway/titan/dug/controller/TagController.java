package com.safeway.titan.dug.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.safeway.titan.XmlConverter;
import com.safeway.titan.dug.domain.Category;
import com.safeway.titan.dug.domain.Header;
import com.safeway.titan.dug.domain.Message;
import com.safeway.titan.dug.domain.StoreLayout;
import com.safeway.titan.dug.domain.StoreSection;
import com.safeway.titan.dug.domain.Txml;
import com.safeway.titan.dug.service.ItemTagsService;

@RestController
@RequestMapping("/tags")
public class TagController {

	@Autowired
	private ItemTagsService itemTagsService;

	@GetMapping(path = "/generate-item-loc-mapping")
	public String getEmployee() throws Exception {
		
		return itemTagsService.readTags();
		
	}

	@PostMapping(path = "/dug")
	public String getTags(HttpServletRequest request, HttpServletResponse response) throws Exception {

		MultipartHttpServletRequest mRequest;

		mRequest = (MultipartHttpServletRequest) request;

		return itemTagsService.readTags();
	}

	// https://stackoverflow.com/questions/24339990/how-to-convert-a-multipart-file-to-file
	
	
	@Resource
	private XmlConverter xmlConverter;
	
	private void run() throws Exception {
		
		final String xmlFile = "Store-layout.xml";
		
		Category c = new Category("extCategoryCode");
		
		List<Category> cs = new ArrayList<Category>(Arrays.asList(c));
		
		StoreSection ss = new StoreSection("section", " sequence",cs);
		StoreSection ss1 = new StoreSection("section1", " sequence1",cs);
		List<StoreSection> sss =  new ArrayList<StoreSection>(Arrays.asList(ss,ss1));
		
		Txml txml = new Txml(new Header("source","actionType","Message","company"),
				new Message(new StoreLayout("businessUnit","facility","Category",sss)));
		
		
		//Convert Customer Object to Xml!
		System.out.println("Convert Customer Object to Xml!");
		xmlConverter.convertFromObjectToXML(txml, xmlFile);
		System.out.println("Done \n");
 
		
 
	}
}
