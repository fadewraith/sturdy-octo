// Create a class InvoiceItem as given class diagram
// https://www.javatpoint.com/understanding-toString()-method

import java.util.*;

class InvoiceItem{
    String id;
    String desc;
    int qty;
    double unitPrice;

    InvoiceItem(String id,String desc,int qty,double unitPrice){
        this.id = id;
        this.desc = desc;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    void getId(){
        System.out.println("The Id is: "+id);
    }

    void desc(){
        System.out.println("The description is: "+desc);
    }

    void getQty(){
        System.out.println("The quantity is: "+qty);
    }

    void setQty(int qty){
        this.qty = qty;
    }

    void getUnitPrice(){
        System.out.println("The unit price is: "+unitPrice);
    }

    void setUnitPrice(double unitPrice){
        this.unitPrice = unitPrice;
    }

    void getTotal(){
        double tot = unitPrice*qty;
        System.out.println("The total price is: "+tot);
    }

    public String toString(){
        return "Invoice Item ["+"id = "+id+", desc = "+desc+", qty = "+qty+", unitPrice = "+unitPrice+"]";
    }
}

class inVoiceClass{

    public static void main(String args[]){

        InvoiceItem invItem = new InvoiceItem("1001", "hello", 10, 1000);

        // System.out.println(invItem);

        invItem.getId();
        invItem.desc();
        invItem.getQty();
        invItem.getUnitPrice();

        invItem.setQty(15);
        invItem.setUnitPrice(200);
        invItem.getTotal();

        System.out.println(invItem);

    }
}