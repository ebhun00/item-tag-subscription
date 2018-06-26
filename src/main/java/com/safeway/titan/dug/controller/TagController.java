package com.safeway.titan.dug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safeway.titan.dug.service.ItemTagsService;

@RestController
@RequestMapping("/tags")
public class TagController {

	@Autowired
	private ItemTagsService itemTagsService;
	

	@GetMapping(path = "/generate-item-loc-mapping")
	public String prepareAndSendItemTags() throws Exception {
		
		return itemTagsService.readTags();
		
	}
}
