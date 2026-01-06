package com.goldenleaf.shop.service;

public interface CrudService<T, ID> {
	T findById(ID id);
	T create(T dto);
	T update(ID id, T dto);
	void delete(ID id);

}
