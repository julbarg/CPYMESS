package com.claro.cpymes.dao;

import java.util.ArrayList;

import com.claro.cpymes.entity.pymes.NitOnixEntity;


public interface NitOnixDAO {

   public ArrayList<NitOnixEntity> findByEstado(String filter) throws Exception;

   public void createList(ArrayList<NitOnixEntity> listNitOnix) throws Exception;

   public void removeAll() throws Exception;

   public int findAllCount() throws Exception;

}
