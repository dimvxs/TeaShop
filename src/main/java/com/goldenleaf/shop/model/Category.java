package com.goldenleaf.shop.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String name;

public Category() {}


public Category(String name)

{
	if(name == null || name.isBlank())
	{
		throw new IllegalArgumentException("Name cannot be null or empty");
	}
	
	this.name = name;
}

public int getId() {
    return id;
}

public void setName(String name)

{
	if(name == null || name.isBlank())
		
	{
		throw new IllegalArgumentException("Name cannot be null or empty");
	}
	
	this.name = name;
}

public String getName() {return this.name;}

}
