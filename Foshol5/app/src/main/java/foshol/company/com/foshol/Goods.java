package foshol.company.com.foshol;

/**
 * Created by User on 25-Aug-17.
 */

public class Goods {
    String id,name, price;


    public Goods() {
    }

    public Goods(String id, String name, String roll) {
        this.id=id;
        this.name = name;
        this.price = roll;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

}
