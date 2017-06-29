package com.pucmm.practica4.services;

import javax.swing.table.TableRowSorter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by anyderre on 28/06/17.
 */
public class DeletePasteThread implements Runnable {
    PasteServices pasteServices;
    @Override
    public void run() {
        while (true){
            Date fecha = new Date();
            pasteServices= PasteServices.getInstancia();
            pasteServices.deleteByDate(TimeUnit.MILLISECONDS.toSeconds(fecha.getTime()));
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
