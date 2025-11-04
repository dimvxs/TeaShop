package com.goldenleaf.shop.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;



@Entity
@Table(name = "admins")
public class Admin extends User {
	
	@Column(name = "secret_word", length = 100)
	private String secretWord;
	@Column(name = "is_super_admin", nullable = false) 
	private boolean isSuperAdmin;

	   public Admin() {
	        this.isSuperAdmin = false;
	        this.secretWord = "default";
	    }

    public Admin(String login, String passwordHash, String name, LocalDate lastActivity, String secretWord, boolean isSuperAdmin) {
        super(login, passwordHash, name, lastActivity);
        this.secretWord = secretWord;
        this.isSuperAdmin = isSuperAdmin;
    }
    
   public void setSecretWord(String secretWord)
   {
	   if(secretWord != null)
	   {
		   this.secretWord = secretWord;
	   }
	
   }
   
   public void setSuperAdmin(boolean isSuperAdmin)
   {
	 
		   this.isSuperAdmin = isSuperAdmin;
	 
	
   }
   
   public boolean getSuperAdmin()
   {
	   return this.isSuperAdmin;
   }
   
   public String getSecretWord()
   {
	   return this.secretWord;
   }
}
