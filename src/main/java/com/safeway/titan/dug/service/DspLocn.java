package com.safeway.titan.dug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

@Component
@PropertySource("classpath:tag-sub/aisle.properties")
public class DspLocn {

	@Autowired
	private Environment env;

	public String getAisleName(String input) {

		String aisle = env.getProperty("tbd");
		Integer inputSection = Ints.tryParse(input);
		if (inputSection != null) {
			if (Range.open(1, 99).contains(inputSection))
				return env.getProperty("tag_sub.aisle.a001_099");

			if (Range.open(200, 249).contains(inputSection))
				return env.getProperty("tag_sub.aisle.a200_249");

			if (inputSection == 300)
				return env.getProperty("tag_sub.aisle.a300");
			if (inputSection == 350)
				return env.getProperty("tag_sub.aisle.a350");
			if (inputSection == 351)
				return env.getProperty("tag_sub.aisle.a351");
			if (inputSection == 400)
				return env.getProperty("tag_sub.aisle.a400");
			if (inputSection == 450)
				return env.getProperty("tag_sub.aisle.a450");
			if (inputSection == 500)
				return env.getProperty("tag_sub.aisle.a500");
			if (inputSection == 800)
				return env.getProperty("tag_sub.aisle.a800");
			if (inputSection == 900)
				return env.getProperty("tag_sub.aisle.a900");
		}
		return aisle;
	}

	public String getSectionName(String input) {

		String section = env.getProperty("tbd");

		Integer inputSection = Ints.tryParse(input);
		if (inputSection != null) {

			if (Range.open(100, 199).contains(inputSection))
				return env.getProperty("tag_sub.section.s100_199");

			if (Range.open(300, 399).contains(inputSection))
				return env.getProperty("tag_sub.section.s300_399");

			if (Range.open(400, 499).contains(inputSection))
				return env.getProperty("tag_sub.section.s400_499");

			if (Range.open(200, 299).contains(inputSection))
				return env.getProperty("tag_sub.section.s200_299");

		}
		return section;
	}

}
