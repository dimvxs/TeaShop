package com.goldenleaf.shop.exception;

public class IncorrectEmailException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public IncorrectEmailException(String message)
	{
		super(message);
	}

}
