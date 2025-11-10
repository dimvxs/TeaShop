package com.goldenleaf.shop.dto;

public class AdminDTO extends UserDTO{
	
	    private String secretWord;
	    private boolean isSuperAdmin;

	    public String getSecretWord() { return secretWord; }
	    public void setSecretWord(String secretWord) { this.secretWord = secretWord; }

	    public boolean isSuperAdmin() { return isSuperAdmin; }
	    public void setSuperAdmin(boolean superAdmin) { isSuperAdmin = superAdmin; }

}
