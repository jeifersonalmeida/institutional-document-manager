package app.models.utils;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public abstract class DateTransformer {
  private static final Map<String, Integer> months = new HashMap<String, Integer>(){
    {
      put("JAN", 12);
      put("FEV", 11);
      put("MAR", 10);
      put("ABR", 9);
      put("MAI", 8);
      put("JUN", 7);
      put("JUL", 6);
      put("AGO", 5);
      put("SET", 4);
      put("OUT", 3);
      put("NOV", 2);
      put("DEZ", 1);
    }
  };

  private static final Map<String, String> monthsEn = new HashMap<String, String>(){
    {
      put("JAN", "JANEIRO");
      put("FEB", "FEVEREIRO");
      put("MAR", "MARÇO");
      put("APR", "ABRIL");
      put("MAY", "MAIO");
      put("JUN", "JUNHO");
      put("JUL", "JULHO");
      put("AGO", "AGOSTO");
      put("SEP", "SETEMBRO");
      put("OCT", "OUTUBRO");
      put("NOV", "NOVEMBRO");
      put("DEC", "DEZEMBRO");
    }
  };

  public static String StringFormatter(String string) {
    //01 DE FEVEREIRO DE 2019

    List<String> properString = Arrays.asList(string.split(" "));
    try {
      int index = properString.size() >= 5 ? 2 : 1;

      properString.set(index, months.get(properString.get(index).substring(0, 3)).toString());

      List<String> list = new ArrayList<>();
      list.add(properString.get(properString.size()-1).replace(".", ""));
      list.add(properString.get(index));
      list.add(properString.get(0));

      return String.join("-", list);
    }
    catch (Exception e){
      System.out.println(string);
      System.out.println(properString);
      System.out.println(properString.get(2));
      System.out.println(properString.get(2).substring(0, 3));
      return null;
    }
  }

  public static String dateToString(Date date){
    List<String> split = Arrays.asList(date.toString().split(" "));
    String month = monthsEn.get(split.get(1).toUpperCase());
    String day = split.get(2);
    String year = split.get(5);

    return day + " DE " + month + " DE " + year;
  }
}
