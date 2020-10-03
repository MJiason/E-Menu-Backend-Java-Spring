package com.MJaison.Emenu.dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.net.URL;
import java.util.Objects;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @NotNull
    @Column(name = "price")
    private int price;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "weight")
    private int weight;
    @NotNull
    @Column(name = "photo")
    private URL photo;


    public Dish(long id, int price, String name, int weight) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.weight = weight;
    }

    public Dish() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public URL getPhoto() {
        return photo;
    }

    public void setPhoto(URL photo) {
       this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id &&
                price == dish.price &&
                weight == dish.weight &&
                Objects.equals(name, dish.name) &&
                Objects.equals(photo, dish.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, weight, photo);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", photo='" + photo + '\'' +
                '}';
    }
}
