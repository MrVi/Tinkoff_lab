public class ApiResponse {
    private String base;
    private String date;
    private RateObject rates;
    public ApiResponse (){

    }

    public String getBase () {
        return base;
    }

    public String getDate() {
        return date;
    }

    public double getCur() {
        return rates.getRate();
    }

    public String getName () {
        return rates.getName();
    }

    @Override
    public String toString() {
        return String.format("(name=%s, rate=%s, rates=%s)", base, date, rates);
    }
}
