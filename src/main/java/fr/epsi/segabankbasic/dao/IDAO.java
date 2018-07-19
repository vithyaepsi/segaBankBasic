/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.dao;

import java.util.List;

/**
 *
 * @author bouboule
 */
public interface IDAO<T> {
	
	public T find( String id );
	
	public List<T> findAll();
	
	public void create( T o );
	public void update( T o );
	public void delete( T o );
	

}
