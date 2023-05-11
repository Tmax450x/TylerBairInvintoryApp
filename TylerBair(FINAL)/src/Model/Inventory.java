/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Papa Bair
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part part){
        
        allParts.add(part);
    }
    
    public static void addProduct(Product product){
        
        allProducts.add(product);
    }
    
    public static void updatePart(int index, Part part){
        
        allParts.remove(part);
    }
    
    public static void updateProduct(int index, Product product){
        
        allProducts.remove(product);
    }  
    
    public static Part lookupPart(String searchItem){

        //Doesnt allow a null search and makes sure it finds the correct object.
        if(!searchItem.equals("")){
        for(Part p: Inventory.allParts){
            if(p.getName().contains(searchItem) || new Integer(p.getId()).toString().equals(searchItem)) return p;
        }
        }
        return null;
    }
    /* Not using methiod.  Part search is being implemented elsewhere. 
    public static ObservableList<Part> lookupPart(String partName){
 
    }
    */
    public static Product lookupProduct(String searchItem){
        
        //Doesnt allow a null search and makes sure it finds the correct object.
        if(!searchItem.equals("")){
        for(Product p: Inventory.allProducts){
            if(p.getName().contains(searchItem) || new Integer(p.getId()).toString().equals(searchItem)) return p;
        }
        }
        return null;        
    }
   /*  Not using methiod.  Product search is being implemented elsewhere.   
    public static ObservableList<Product> lookupProduct(String productName){
        
    }
    */
    public static void deletePart(Part part){
        
        //checks for null value before firing the remove method.
        for(Part p: allParts){
            if(!(part == null) && p.getId() == part.getId()){
                allParts.remove(p);
                break;
            }
        }
    }  
    
    public static void deleteProduct(Product product){
        
        //checks for null value before firing the remove method.
        for(Product p: allProducts){
            if(!(product == null) && p.getId() == product.getId()){
                allProducts.remove(p);
                break;
            }
        }
    }
    
    public static ObservableList<Part> getAllParts(){
        
        return allParts;
    }
    
    public static ObservableList<Product> GetAllProducts(){
        
        return allProducts;
    }
}
