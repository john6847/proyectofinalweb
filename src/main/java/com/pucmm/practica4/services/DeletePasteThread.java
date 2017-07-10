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
        pasteServices= PasteServices.getInstancia();
        while (true){
            Date fecha = new Date();

            pasteServices.deleteByDate();
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
