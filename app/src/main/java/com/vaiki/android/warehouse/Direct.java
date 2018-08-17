package com.vaiki.android.warehouse;

/**
 * Created by E_not on 26.07.2018.
 */
import java.util.UUID;
public class Direct {
    private UUID mId;
    private String name_directory;
    private String name_product;
    private String description;
    private int qty;
    public Direct(){
        mId = UUID.randomUUID();

    }
    public Direct(String name,String catalog, String description ){
        mId = UUID.randomUUID();
        name_product = name;
        this.name_directory = catalog;
        this.description = description;
    }
    public UUID getId() {
        return mId;
    }


    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getName_directory() {
        return name_directory;
    }

    public void setName_directory(String name_directory) {
        this.name_directory = name_directory;
    }

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
