import java.text.SimpleDateFormat;
import java.util.Date;

public class test1 {
    public static void main(String args[]) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date).toString());

    }

}
