package mx.bidg.pojos;

/**
 * Created by josueolvera on 1/09/16.
 */
public class PojoSiscom {
    int id;
    String name;
    double amount;
    int requestNumber;

    public PojoSiscom() {
    }

    public PojoSiscom(int id) {
        this.id = id;
    }

    public PojoSiscom(int id, String name, double amount, int requestNumber) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.requestNumber = requestNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }


}
