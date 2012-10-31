package br.com.caelum.project.dao;

import javax.persistence.EntityManager;

public class DAO<T> {

	private EntityManager entityManager;
	
	private Class<T> clazz;

	public DAO(EntityManager entityManager, Class<T> clazz) {
		this.entityManager = entityManager;
		this.clazz = clazz;
	}

	public void add(T t) {
		this.entityManager.persist(t);
	}
	
	public T find(Long id) {
		return this.entityManager.find(clazz, id);
	}
}
