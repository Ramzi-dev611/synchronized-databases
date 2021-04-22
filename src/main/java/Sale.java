package main.java;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public class Sale {
    protected String id;
    protected String date;
    protected String region;
    protected String product;
    protected int quantity;
    protected double cost;
    protected double amt;
    protected double tax;
    protected double total;

    public Sale(String date, String region, String product,
                int quantity, double cost, double tax) {
        this.id = date+product+region;
        this.date = date;
        this.region = region;
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
        this.amt = this.cost * this.quantity;
        this.tax = tax;
        this.total = this.amt + this.tax;
    }

    public Sale(String jsonFormat){
        BufferedReader bufReader =
                new BufferedReader(new StringReader(jsonFormat));
        try {
            String line = null; int i=0;
            while ((line = bufReader.readLine()) != null) {
                if (!(i == 0 || i == 10)){
                    String content = line.split(":", 2)[1];
                    content = content.substring(2, content.length()-2);
                    switch (i) {
                        case 1 : {
                            id = content;
                            break;
                        }
                        case 2 : {
                            date = content;
                            break;
                        }
                        case 3 : {
                            region = content;
                            break;
                        }
                        case 4 : {
                            product = content;
                            break;
                        }
                        case 5 : {
                            quantity = Integer.parseInt(content);
                            break;
                        }
                        case 6 : {
                            cost = Double.parseDouble(content);
                            break;
                        }
                        case 7 : {
                            amt = Double.parseDouble(content);
                            break ;
                        }
                        case 8 : {
                            tax = Double.parseDouble(content);
                            break;
                        }
                        case 9 : {
                            total = Double.parseDouble(content);
                            break;
                        }
                    }
                }i++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String toJSON() {
        return "{\n" +
                " \"id\": \""+ this.id +"\",\n" +
                " \"date\": \""+ this.date +"\",\n" +
                " \"region\": \""+ this.region +"\",\n" +
                " \"product\": \""+ this.product +"\",\n" +
                " \"quantity\": \""+ this.quantity +"\",\n" +
                " \"cost\": \""+ this.cost +"\",\n" +
                " \"amt\": \""+ this.amt +"\",\n" +
                " \"tax\": \""+ this.tax +"\",\n" +
                " \"total\": \""+ this.total +"\",\n" +
                "}";
    }

    public void assign (Sale s){
        this.id = s.id;
        this.date = s.date;
        this.region = s.region;
        this.product = s.product;
        this.quantity = s.quantity;
        this.cost = s.cost;
        this.amt= s.amt;
        this.tax = s.tax;
        this.total = s.total;
    }

    public double getAmt() {
        return amt;
    }

    public double getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getRegion() {
        return region;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public double getTax() {
        return tax;
    }

    public ArrayList <String> toArray(){
        ArrayList<String> result = new ArrayList<String>(9);
        result.add(id);
        result.add(date);
        result.add(region);
        result.add(product);
        result.add(""+quantity);
        result.add(""+cost);
        result.add(""+amt);
        result.add(""+tax);
        result.add(""+total);
        return result;
    }
}
