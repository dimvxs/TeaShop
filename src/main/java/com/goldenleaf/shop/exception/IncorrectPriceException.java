package com.goldenleaf.shop.exception;

public class IncorrectPriceException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public IncorrectPriceException(String message)
	{
		super(message);
	}

}
