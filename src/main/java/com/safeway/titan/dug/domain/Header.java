package com.safeway.titan.dug.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Header {

	private String source;
	private String action_Type;
	private String message_Type;
	private String company_ID;
}
