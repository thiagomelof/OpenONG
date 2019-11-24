package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtils {

    public static Date primeiroDiaDoMes() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        int dia = 1;
        int mes = (cal.get(Calendar.MONDAY) + 1);
        int ano = cal.get(Calendar.YEAR);

        System.out.println(dia + "/" + mes + "/" + ano);
        Date data = new Date();
        try {
            data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Date ultimoDiaDoMes() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());

        int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = (cal.get(Calendar.MONDAY) + 1);
        int ano = cal.get(Calendar.YEAR);

        System.out.println(dia + "/" + mes + "/" + ano);
        Date data = new Date();
        try {
            data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Date primeiroDiaDoAno() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        int dia = 1;
        int mes = 1;
        int ano = cal.get(Calendar.YEAR);

        System.out.println(dia + "/" + mes + "/" + ano);
        Date data = new Date();
        try {
            data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Date ultimoDiaDoAno() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());

        int dia = 31;
        int mes = 12;
        int ano = cal.get(Calendar.YEAR);

        System.out.println(dia + "/" + mes + "/" + ano);
        Date data = new Date();
        try {
            data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Date primeiroDiaDoAno(int ano) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        int dia = 1;
        int mes = 1;

        System.out.println(dia + "/" + mes + "/" + ano);
        Date data = new Date();
        try {
            data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Date ultimoDiaDoAno(int ano) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());

        int dia = 31;
        int mes = 12;

        System.out.println(dia + "/" + mes + "/" + ano);
        Date data = new Date();
        try {
            data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

}
