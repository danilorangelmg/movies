package com.br.movies.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by danilorangel on 05/06/17.
 */

public class Buy {

    private boolean isAtive;
    private boolean isAdditional;
    private String dateOfBuy;
    private String price;

    public boolean isAtive() {
        return isAtive;
    }

    public void setAtive(boolean ative) {
        isAtive = ative;
    }

    public String getDateOfBuy() {
        return dateOfBuy;
    }

    public void setDateOfBuy(String dateOfBuy) {
        this.dateOfBuy = dateOfBuy;
    }

    public String getPrice() {
        BigDecimal value = new BigDecimal(price);
        String formmatedPrice = "R$ "+value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return formmatedPrice;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFormattedDate() {
        try {
            String dateStr = dateOfBuy.substring(0, 10);
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parser.parse(dateStr);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
            return format.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean isAdditional() {
        return isAdditional;
    }

    public void setAdditional(boolean additional) {
        isAdditional = additional;
    }
}
