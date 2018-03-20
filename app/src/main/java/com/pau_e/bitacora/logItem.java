package com.pau_e.bitacora;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by pau_e on 15/03/2018.
 */

public class logItem {

        private Date fechaHora;
        private String log;
        private static Calendar calendar = new GregorianCalendar();

    public long getTimeMili(){
        calendar.setTime(this.fechaHora);
        return calendar.getTimeInMillis();
    }

    public String getDateString() {
        calendar.setTime(this.fechaHora);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return String.format("%02d/%02d/%04d",day,month+1,year);
    }
    public String getHourString(){
        calendar.setTime(this.fechaHora);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        return String.format("%02d:%02d",hour,min);

    }

    public logItem(Date fechaHora, String log) {
        this.fechaHora = fechaHora;
        this.log = log;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
