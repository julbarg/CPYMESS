package com.claro.cpymes.services.remote;

import org.springframework.stereotype.Service;


@Service
public interface DaemonMySQLService {

   public void inicializarDaemon(int timer);

}
