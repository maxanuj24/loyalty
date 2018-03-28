package com.example.stpl.loyality_ui.bean;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by stpl on 26/2/18.
 */

public class PacketData {

    private long accumulationStartDate;
    private float totalEarning;
    private int id;
    private String state;
    private long expiryDate;
    private long accumulationEndDate;
    private int serialno;
    public static String dateFormat = "dd-MM-yyyy hh:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
    private String astart_date,aend_date,expiryString;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public int getSerialno() {
        return serialno;
    }

    public void setSerialno(int serialno) {
        this.serialno = serialno;
    }


    public float getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(float totalEarning) {
        this.totalEarning = totalEarning;
    }

    public void setState(String state) {
        this.state = state;
    }



    public long getAccumulationEndDate() {
        return accumulationEndDate;
    }


    public String getAend_date() {
        return aend_date;
    }

    public void setAend_date(long accumulationEndDate) {
        this.aend_date = ConvertMilliSecondsToFormattedDate(accumulationEndDate);
    }

    public void setAccumulationEndDate(long accumulationEndDate) {
        this.accumulationEndDate = accumulationEndDate;
        setAend_date(accumulationEndDate);
    }
    
    public static String ConvertMilliSecondsToFormattedDate(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }





    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
        setExpiryString(expiryDate);
    }

    public String getExpiryString() {
        return expiryString;
    }

    public void setExpiryString(long expiryString) {
        this.expiryString = ConvertMilliSecondsToFormattedDate(expiryString);
    }




    public long getAccumulationStartDate() {
        return accumulationStartDate;
    }

    public void setAccumulationStartDate(long accumulationStartDate) {
        this.accumulationStartDate = accumulationStartDate;
        setAstart_date(accumulationStartDate);
    }

    public String getAstart_date() {

        return astart_date;
    }

    public void setAstart_date(long accumulationStartDate) {
        this.astart_date = ConvertMilliSecondsToFormattedDate(accumulationStartDate);;
    }






}