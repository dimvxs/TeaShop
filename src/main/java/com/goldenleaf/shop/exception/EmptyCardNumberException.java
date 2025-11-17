package com.goldenleaf.shop.exception;

public class EmptyCardNumberException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public EmptyCardNumberException(String message)
	{
		super(message);
	}
	
}


