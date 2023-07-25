package QIWI;
import org.jsoup.Jsoup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static String updateDate(String text){
        String date = text.substring(text.indexOf("date=") + 5);
        String y = date.substring(0, date.indexOf("-"));
        String m = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
        String d = date.substring(date.lastIndexOf("-") + 1);
        return (d + "/" + m + "/" + y);
    }
    public static void main(String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = null;
        try {
            text = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String currency = text.substring(21, text.indexOf("date=") - 2);
        String date = updateDate(text);
        var document =  Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date);
        try {
            for(var element : document.get().select("Valute")) {
                if(element.text().contains(currency)){
                    System.out.println(currency + " (" + element.select("Name").text() + ")" + ": " +  element.select("Value").text());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
