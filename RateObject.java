public class RateObject {
    private String name;
    private double rate;
    public RateObject() {

    }

    public RateObject(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return String.format("(name=%s, rate=%s)", name, rate);
    }

    public String getName (){
        return name;
    }

    public double getRate() {
        return rate;
    }
}
