/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tylerbair;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Papa Bair
 */
public class TylerBair extends Application {
    
        @Override
    public void start(Stage primaryStage) throws Exception {
       primaryStage.setTitle("Invantory System");
       Pane myPane = (Pane)FXMLLoader.load(getClass().getResource
    ("/View/FXMLMainScreen.fxml"));
       Scene myScene = new Scene(myPane);
       primaryStage.setScene(myScene);
       primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Creates new objects to populate when program starts.
        InhousePart IHP = new InhousePart(1, "Bolt", 100, 25, 100, 50, 70);
        OutsourcedPart OSP = new OutsourcedPart(2, "Iron Rod", 55, 600, 5, 15, "Bae Systems");
        InhousePart IHP2 = new InhousePart(3, "Canvas", 55, 600, 5, 20, 80);
        
        Inventory.addPart(IHP);
        Inventory.addPart(OSP);
        Inventory.addPart(IHP2);
        
        ObservableList<Part> Parts1 = FXCollections.observableArrayList();
        ObservableList<Part> Parts = FXCollections.observableArrayList();
        Product pro = new Product(1, "Pump", 2200, 23, 55, 1, Parts);
        Product pro1 = new Product(2, "Sail", 2200, 23, 55, 1, Parts1);
        
        Inventory.addProduct(pro);
        Inventory.addProduct(pro1);
        
        launch(args);
        
    }
    
}
