package com.goldenleaf.shop.exception;

public class EmptyExpiryException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public EmptyExpiryException(String message)
	{
		super(message);
	}

}
