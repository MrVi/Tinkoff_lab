
import java.util.*;

public class Main {

    enum currency {
        AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK,
        GBP, HKD, HRK, HUF, IDR, ILS, INR, JPY,
        KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON,
        RUB, SEK, SGD, THB, TRY, USD, ZAR, EUR;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String file_name = "library.txt";
        String from_cur = null, to_cur = null;
        double answer = 0.0;
        Finding new_thr = new Finding();

        printAllAvailableCurrency();
        System.out.println("Enter from currency:");
        from_cur = scanner.nextLine();
        while (!contains(from_cur)) {
            System.out.println("Wrong currency");
            printAllAvailableCurrency();
            System.out.println("Enter from currency:");
            from_cur = scanner.nextLine();
        }
        System.out.println("Enter to currency:");
        to_cur = scanner.nextLine();
        while (!contains(to_cur)) {
            System.out.println("Wrong currency");
            printAllAvailableCurrency();
            System.out.println("Enter to currency:");
            to_cur = scanner.nextLine();
        }
        System.out.println("From currency: " + from_cur + " to currency: " + to_cur);

        new_thr.setFileName(file_name);
        new_thr.setFromCur(from_cur);
        new_thr.setToCur(to_cur);
        new_thr.start();
        System.out.print("Loading .");
        do {
            System.out.print(" .");
            try{
                new_thr.join(500);
            }
            catch(InterruptedException e){}
        } while(new_thr.isAlive());
        System.out.println(" .");
        answer = new_thr.getAnswer();
        System.out.println("RESULT: " + from_cur + " => " + to_cur + " " + answer);
    }

    public static void printAllAvailableCurrency(){
        System.out.println("Available currency: ");
        for (currency k : currency.values()) {
            System.out.print( k + " " );
        }
        System.out.println(" ");
    }

    public static boolean contains(String test) {
        for (currency c : currency.values()) {
            if (c.name().equals(test)) {    return true;    }
        }
        return false;
    }
}