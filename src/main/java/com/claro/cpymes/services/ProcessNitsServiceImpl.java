package com.claro.cpymes.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cpymes.dao.NitOnixDAO;
import com.claro.cpymes.dao.ParameterDAO;
import com.claro.cpymes.entity.pymes.NitOnixEntity;
import com.claro.cpymes.exceptions.FileNitEmptyException;
import com.claro.cpymes.exceptions.PercentageDifferenceException;
import com.claro.cpymes.services.remote.ProcessNitsService;
import com.claro.cpymes.services.remote.SFTPService;
import com.claro.cpymes.util.Constant;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


@Service
public class ProcessNitsServiceImpl implements ProcessNitsService {

   private static Logger LOGGER = LogManager.getLogger(ProcessNitsServiceImpl.class.getName());

   @Autowired
   private SFTPService SFTPService;

   @Autowired
   private NitOnixDAO nitOnixDAO;

   @Autowired
   private ParameterDAO parameterDAO;

   private ArrayList<NitOnixEntity> listNitOnix;

   private static final int A = CellReference.convertColStringToIndex("A");

   private static final int B = CellReference.convertColStringToIndex("B");

   private static final int C = CellReference.convertColStringToIndex("C");

   private static final int D = CellReference.convertColStringToIndex("D");

   private static final int E = CellReference.convertColStringToIndex("E");

   @Override
   public void processNits() {
      try {
         listNitOnix = new ArrayList<NitOnixEntity>();
         getFileNitsCopyServe();
         processFile();
         validateNumberRegisters();
         deleteTableNits();
         createRegistersNits();
         updateLastDateLoad();
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al obtener el archivo de Nits", e);

      }

   }

   private void getFileNitsCopyServe() throws FileNotFoundException, JSchException, SftpException {
      SFTPService.download();
   }

   @SuppressWarnings("resource")
   private void processFile() throws IOException, FileNitEmptyException {
      File pathFile = new File(getPathFile());
      FileInputStream file = new FileInputStream(pathFile);
      XSSFWorkbook myWorkBook = new XSSFWorkbook(file);
      XSSFSheet mySheet = myWorkBook.getSheetAt(0);

      int i = 0;
      for (Row row : mySheet) {
         if (i++ > 0) {
            NitOnixEntity nitOnix = getInfoFromRow(row);
            if (nitOnix != null) {
               listNitOnix.add(nitOnix);
            }
         }
      }
      if (listNitOnix.isEmpty()) {
         throw new FileNitEmptyException("No se encontraron registros dentro del archivo");
      }

   }

   private void validateNumberRegisters() throws Exception {
      int numberRegistersPrevious = nitOnixDAO.findAllCount();
      int numberRegistersNew = listNitOnix.size();
      double difference = Math.abs(numberRegistersPrevious - numberRegistersNew);
      double percentageDifference = (difference / numberRegistersPrevious) * 100;
      if (percentageDifference > Constant.PERCENTAGE_DIFFERENCE_NITS) {
         throw new PercentageDifferenceException("La diferncia porcentual de Numero de Registros de Nits es anormal");
      }

   }

   private NitOnixEntity getInfoFromRow(Row row) {
      NitOnixEntity nitOnix = new NitOnixEntity();
      try {
         Cell cell = row.getCell(A);
         nitOnix.setId((long) cell.getNumericCellValue());

         cell = row.getCell(B);
         nitOnix.setIdEnlace(cell.getStringCellValue());

         cell = row.getCell(C);
         nitOnix.setNit((long) cell.getNumericCellValue());

         cell = row.getCell(D);
         nitOnix.setIdCliente((long) cell.getNumericCellValue());

         cell = row.getCell(E);
         nitOnix.setEstadoServicio(cell.getStringCellValue());
         return nitOnix;
      } catch (Exception e) {
         return null;
      }

   }

   private String getPathFile() {
      return Constant.SERVER_WORKING_DIR + Constant.NAME_FILE_NITS;
   }

   private void deleteTableNits() throws Exception {
      nitOnixDAO.removeAll();
   }

   private void createRegistersNits() throws Exception {
      nitOnixDAO.createList(listNitOnix);

   }

   private void updateLastDateLoad() {
      try {
         Date today = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
         String dateString = dateFormat.format(today);
         parameterDAO.updateParameter(Constant.FECHA_ULTIMO_CARGUE_NITS, dateString);
      } catch (Exception e) {
         LOGGER.error("Error al actualizar fecha de ultimo cargue", e);
      }
   }

}
