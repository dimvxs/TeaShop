package com.goldenleaf.shop.exception;

public class EmptyLoginException extends Exception{

	private static final long serialVersionUID = 1L;

	public EmptyLoginException(String message)
	{
		super(message);
	}
}
