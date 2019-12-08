package app.models.utils;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public abstract class DateTransformer {
  private static final Map<String, Integer> months = new HashMap<String, Integer>(){
    {
      put("JAN", 1);
      put("FEV", 2);
      put("MAR", 3);
      put("ABR", 4);
      put("MAI", 5);
      put("JUN", 6);
      put("JUL", 7);
      put("AGO", 8);
      put("SET", 9);
      put("OUT", 10);
      put("NOV", 11);
      put("DEZ", 12);
    }
  };

  public static String StringFormatter(String string) {
    //01 DE FEVEREIRO DE 2019

    List<String> properString = Arrays.asList(string.split(" "));
    try {
      int index = properString.size() >= 5 ? 2 : 1;

      properString.set(index, months.get(properString.get(index).substring(0, 3)).toString());

      List<String> list = new ArrayList<>();
      list.add(properString.get(properString.size()-1));
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
}
