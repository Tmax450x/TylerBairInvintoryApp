/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Papa Bair
 */
public class OutsourcedPart extends Part {
    
    private String companyName;

    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName; //added company name to constructor to save to object.
    }
    
    public void setCompanyName(String companyName){
        
         this.companyName = companyName;
    }
    
    public String getCompanyName(){
        
        return companyName;
    }
}
