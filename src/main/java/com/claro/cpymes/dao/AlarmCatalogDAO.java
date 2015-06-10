package com.claro.cpymes.dao;

import java.util.ArrayList;

import com.claro.cpymes.entity.pymes.AlarmCatalogEntity;


public interface AlarmCatalogDAO {

   public ArrayList<AlarmCatalogEntity> findByFilter(String filter) throws Exception;

}
