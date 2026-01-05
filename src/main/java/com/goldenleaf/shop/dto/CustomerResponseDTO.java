package com.goldenleaf.shop.dto;

public class CustomerResponseDTO {
    private Long id;
    private String login;
    private String email;
    private String mobile;
    
    public CustomerResponseDTO(Long id, String login, String email, String mobile) {
		this.id = id;
		this.login = login;
		this.email = email;
		this.mobile = mobile;
	}
    
    public Long getId() {
		return id;
	}
    
    public String getLogin() {
    			return login;
    }
    
    public String getEmail() {
    			return email;
    }
    
    public String getMobile() {
				return mobile;
	}
    
    
    public void setId(Long id) {
    			this.id = id;
    }
    
    public void setLogin(String login) {
				this.login = login;
	}
    
    
    public void setEmail(String email) {
				this.email = email;
	}
    	
    public void setMobile(String mobile) {
				this.mobile = mobile;
	}
}
