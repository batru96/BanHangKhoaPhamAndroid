package models;
public class Cart extends Product{
    private int counting;

    public Cart() {

    }

    public Cart(int id, String name, int price, String[] imageUrl, int counting) {
        super(id, name, price, imageUrl);
        this.counting = counting;
    }

    public int getCounting() {
        return counting;
    }

    public void setCounting(int counting) {
        this.counting = counting;
    }
}
