package com.safeway.titan.dug.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import com.safeway.titan.dug.domain.ItemTags;

import lombok.Getter;

@Component
@PropertySource("classpath:tag-sub/aisle.properties")
public class DspLocn {

	@Autowired
	private Environment env;

	@Getter
	private String defaultComments;

	public String getAisleName(String input) {
		defaultComments = env.getProperty("tag_sub.aisle.default");
		Integer inputAisle = Ints.tryParse(input);
		if (inputAisle != null) {
			if (Range.open(NumberUtils.INTEGER_ZERO, 99).contains(inputAisle))
				return String.format("%02d", inputAisle);

			if (Range.open(199, 250).contains(inputAisle))
				return env.getProperty("tag_sub.aisle.a200_249");

			if (inputAisle == 300)
				return env.getProperty("tag_sub.aisle.a300");
			if (inputAisle == 350)
				return env.getProperty("tag_sub.aisle.a350");
			if (inputAisle == 351)
				return env.getProperty("tag_sub.aisle.a351");
			if (inputAisle == 400)
				return env.getProperty("tag_sub.aisle.a400");
			if (inputAisle == 450)
				return env.getProperty("tag_sub.aisle.a450");
			if (Range.open(499, 550).contains(inputAisle))
				return env.getProperty("tag_sub.aisle.a500_549");
			if (Range.open(600, 700).contains(inputAisle))
				return env.getProperty("tag_sub.aisle.a601_699");
			if (inputAisle == 800)
				return env.getProperty("tag_sub.aisle.a800");
			/*
			 * if (inputAisle == 900) return env.getProperty("tag_sub.aisle.a900");
			 */
		}
		return defaultComments;
	}

	public String getSectionName(String input, String aisle) {
		defaultComments = env.getProperty("tag_sub.section.default");
		Integer inputSection = Ints.tryParse(input);
		String requiredSectionValue = StringUtils.substring(input, input.length() - 2);
		if (inputSection != null && (aisle.equals(env.getProperty("tag_sub.aisle.a450"))
				|| aisle.equals(env.getProperty("tag_sub.aisle.a300")))) {

			if (Range.open(0, 100).contains(inputSection))
				return env.getProperty("tag_sub.section.s0_99");

			if (Range.open(99, 200).contains(inputSection))
				return env.getProperty("tag_sub.section.s100_199") + requiredSectionValue;

			if (Range.open(199, 300).contains(inputSection))
				return env.getProperty("tag_sub.section.s200_299") + requiredSectionValue;

			if (Range.open(299, 400).contains(inputSection))
				return env.getProperty("tag_sub.section.s300_399") + requiredSectionValue;

			if (Range.open(399, 500).contains(inputSection))
				return env.getProperty("tag_sub.section.s400_499") + requiredSectionValue;
		}

		if (inputSection != null && aisle.equals(env.getProperty("tag_sub.aisle.a601_699"))) {
			return env.getProperty("tag_sub.section.CH") + requiredSectionValue;
		}
		if (inputSection != null) {
			if (Range.open(99, 200).contains(inputSection))
				return env.getProperty("tag_sub.section.s100_199") + requiredSectionValue;

			if (Range.open(299, 400).contains(inputSection))
				return env.getProperty("tag_sub.section.s300_399") + requiredSectionValue;

		}
		return defaultComments;
	}

	public String getCommentForInvalidItem(ItemTags itemTags) {

		StringBuilder comments = new StringBuilder(itemTags.getComments());

		if (getAisleName(itemTags.getAisle()).contains("Invalid")) {
			comments.append(", ").append(getAisleName(itemTags.getAisle()));
		}
		if (getSectionName(itemTags.getSection(), getAisleName(itemTags.getAisle())).contains("Invalid")) {
			comments.append(", ").append(getSectionName(itemTags.getSection(), getAisleName(itemTags.getAisle())));
		}

		return comments.toString();
	}
}
