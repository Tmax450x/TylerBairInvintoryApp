/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Papa Bair
 */
public class FXMLProductScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Stage stage;
    Parent scene;
    Product product;
    private int modId;  //This helps keep the ids attached to the object.
        
    @FXML    
    private TableView<Part> tableViewPart;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partStock;

    @FXML
    private TableColumn<Part, Integer> partCost;
    
    @FXML
    private TableView<Part> tableViewAssociatedPart;

    @FXML
    private TableColumn<Part, Integer> associatedPartId;

    @FXML
    private TableColumn<Part, String> associatedPartName;

    @FXML
    private TableColumn<Part, Integer> associatedPartStock;

    @FXML
    private TableColumn<Part, Double> associatedPartPrice;
 
    @FXML
    private Label productLbl;
    
    @FXML
    private TextField partsSearchTb;

    @FXML
    private TextField productIdTb;

    @FXML
    private TextField productNameTb;

    @FXML
    private TextField productPriceTb;

    @FXML
    private TextField productInvTb;

    @FXML
    private TextField productMinTb;

    @FXML
    private TextField productMaxTb;
    
    //List handles all the associated parts for product page.
    @FXML  
    private ObservableList<Part> assParts = FXCollections.observableArrayList();
    
    @FXML
    void onActionProductAdd(ActionEvent event) {
        
        assParts = tableViewAssociatedPart.getItems();
        assParts.add(tableViewPart.getSelectionModel().getSelectedItem());
        tableViewAssociatedPart.setItems(assParts);
        
    }

    @FXML
    void onActionPartDelete(ActionEvent event) {
        
        //Allows user to confirm delete before deleting.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the current seleted Part row.  Do you wish to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent () && result.get() ==  ButtonType.OK)
        {
            if(product != null){
               product.deleteAssociatedPart(tableViewAssociatedPart.getSelectionModel().getSelectedItem());
            }else{
                assParts.remove(tableViewAssociatedPart.getSelectionModel().getSelectedItem());
        }
        }
    }

    @FXML
    void onActionProductSave(ActionEvent event) throws IOException {
        
        /**
         * This wraps the save handler in a try block that throws up a dialog box
         * if no information is input into the text fields.  The try block also
         * double checks to see if the user has any associated parts assigned
         * to the product.
         */
        try{
            
            if(assParts.size() == 0)
            {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Missing Part Warning");
                alert.setContentText("Product must include at least one part.  Please select a part from the list.");
                alert.showAndWait();

            }else{

                int id = 1;

                for(Product p: Inventory.GetAllProducts()){
                    if(p.getId() == modId){
                        id = modId;
                        break;
                    }else{
                        id++;
                    }
                }

                String name = productNameTb.getText();
                double price = Double.parseDouble(productPriceTb.getText());
                int stock = Integer.parseInt(productInvTb.getText());
                int min = Integer.parseInt(productMinTb.getText());
                int max = Integer.parseInt(productMaxTb.getText());

                Inventory.addProduct(new Product(id, name, price, stock, min, max, assParts));
                Inventory.updateProduct(id, product);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/FXMLMainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e)
        {               
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Exception Value: " + e.getMessage() + " values.  Please insert correct values in the textfields." + "\n" + "\n" + "Exception: " + e);
            alert.showAndWait();
        }
        
        
    }

    @FXML
    void onActionPartSearch(ActionEvent event) {
        
        String x = partsSearchTb.getText();

        tableViewPart.getSelectionModel().select(Inventory.lookupPart(x));

    }

    @FXML
    void onactionProductCancel(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Prodcut values have not been saved.  Do you wish to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent () && result.get() ==  ButtonType.OK)
        {        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/FXMLMainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }          

    }
        
    public void setProduct(Product setProduct) {
        
        /**
         * Brings in a product object and populates the text field 
         * values and associated parts from it.
         */
        this.product = setProduct;
        
        modId = setProduct.getId();
        productLbl.setText("Mod Product");
        productIdTb.setText(Integer.toString(setProduct.getId()));
        productNameTb.setText(setProduct.getName());
        productPriceTb.setText(Double.toString(setProduct.getPrice()));
        productInvTb.setText(Integer.toString(setProduct.getStock()));
        productMinTb.setText(Integer.toString(setProduct.getMin()));
        productMaxTb.setText(Integer.toString(setProduct.getMax()));
        tableViewAssociatedPart.setItems(setProduct.getAllAssociatedParts());
        assParts = setProduct.getAllAssociatedParts();
        
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               
        tableViewPart.setItems(Inventory.getAllParts());
        
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price")); 
        
    }    
    
}
