package com.example.smartfoodcourt.Model;


import java.util.List;

public class Order {
    private String phone;
     private String total;
    private String status = "0";
    private String comment;
    private String supplierID;
    private List<CartItem> foods;

    public Order() {
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public Order(String phone, CartStallItem t) {
        this.phone = phone;
        this.total = t.getTotal().toString();
         this.supplierID = t.getSupplierID();
        this.foods = t.getCartItemList();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public List<CartItem> getFoods() {
        return foods;
    }

    public void setFoods(List<CartItem> foods) {
        this.foods = foods;
    }
}