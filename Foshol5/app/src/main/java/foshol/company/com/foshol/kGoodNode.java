package foshol.company.com.foshol;

/**
 * Created by User on 9/4/2017.
 */

public class kGoodNode {
    String id,name,date;

    public kGoodNode() {
    }

    public kGoodNode(String id, String name, String date) {
        this.id=id;
        this.name = name;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
