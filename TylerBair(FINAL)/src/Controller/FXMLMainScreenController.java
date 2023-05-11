/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.InhousePart;
import Model.OutsourcedPart;
import Model.Product;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class FXMLMainScreenController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private TableView<Part> tableViewPart;
    
    @FXML
    private TableColumn<Part,Integer> partId;
    
    @FXML
    private TableColumn<Part,String> partName;
    
    @FXML
    private TableColumn<Part,Integer> partInventoryLvl;
    
    @FXML
    private TableColumn<Part,Double> partCostPerUnit;

    @FXML
    private TextField partsSearchTextbox;
    
    @FXML
    private TableView<Product> tableViewProduct;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productsInventoryLvl;

    @FXML
    private TableColumn<Product, Double> productCostPerUnit;
    
    @FXML
    private TextField productsSearchTextbox;
    
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/FXMLAddModInHousePartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/FXMLAddModProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the current seleted Part row.  Do you wish to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent () && result.get() ==  ButtonType.OK)
        {
        Inventory.deletePart(tableViewPart.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
                
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the current seleted Product row.  Do you wish to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent () && result.get() ==  ButtonType.OK)
        {
        Inventory.deleteProduct(tableViewProduct.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to exit the program?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent () && result.get() ==  ButtonType.OK)
        {        
            System.exit(0);
        }        
    }

    @FXML
    void onActionModPart(ActionEvent event) throws IOException {
        
        /**
         * This help the program find the correct instance of the part.
         * It determines if its an in house or out source object and
         * it will populate the correct page with the correct information.
         * It will also not allow the button to fire if nothing is selected.
         */
        Stage stage; 
        Parent root;  
        Object obj = tableViewPart.getSelectionModel().getSelectedItem();
        
        if(obj instanceof InhousePart){
            
            InhousePart objInHouse = (InhousePart) obj;
            stage=(Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "/View/FXMLAddModInHousePartScreen.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();            
            
            FXMLInHousePartScreenController inhouseController = loader.getController();
            inhouseController.setPart(objInHouse); 
            
        }else if(obj instanceof OutsourcedPart){
            
            OutsourcedPart objOutsource = (OutsourcedPart) obj;
            stage=(Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "/View/FXMLAddModOutSourcePartSceen.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        
            FXMLOutSourcePartSceenController outSourceController = loader.getController();
            outSourceController.setPart(objOutsource);
         } 
        
    }

    @FXML
    void onActionModProduct(ActionEvent event) throws IOException {
        
        /**
         * This help the program find the correct instance of the product.
         * also it doesn't allow the button to fire if nothing is selected.
         */        
        Stage stage; 
        Parent root;
        Object obj = tableViewProduct.getSelectionModel().getSelectedItem();
        
        if(obj instanceof Product){
            stage=(Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "/View/FXMLAddModProductScreen.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();            

            FXMLProductScreenController productController = loader.getController();
            productController.setProduct(tableViewProduct.getSelectionModel().getSelectedItem());
        }
        
    }

    @FXML
    void onActionSearchPart(ActionEvent event) {
        
        String x = partsSearchTextbox.getText();

        tableViewPart.getSelectionModel().select(Inventory.lookupPart(x));
    }

    @FXML
    void onActionSearchProduct(ActionEvent event) {
        
        String x = productsSearchTextbox.getText();

        tableViewProduct.getSelectionModel().select(Inventory.lookupProduct(x));
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Helps populate data on screen start.
        tableViewPart.setItems(Inventory.getAllParts());
        
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        tableViewProduct.setItems(Inventory.GetAllProducts());
        
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
