package com.goldenleaf.shop.exception;

public class IncorrectQuantityException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public IncorrectQuantityException(String message)
	{
		super(message);
	}

}
