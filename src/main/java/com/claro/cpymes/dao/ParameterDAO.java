package com.claro.cpymes.dao;

public interface ParameterDAO {

   public String findByName(String name) throws Exception;

   public void updateParameter(String fechaUltimoCargueNits, String dateString) throws Exception;
}
