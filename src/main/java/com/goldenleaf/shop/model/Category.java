package com.goldenleaf.shop.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false, length = 100)
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

public Long getId() {
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
