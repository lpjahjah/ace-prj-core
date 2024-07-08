package com.ace.exames.core.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

@Data
public class AuthDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@NonNull
	private String login;
	
	@NonNull
	private Integer minutesToExpire;
}