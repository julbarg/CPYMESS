package com.claro.cpymes.services.remote;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


@Service
public interface SFTPService {

   public void download() throws JSchException, SftpException, FileNotFoundException;

}
