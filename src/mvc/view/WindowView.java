package mvc.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class WindowView {

    //View Nodes
    Label counter = new Label("");
    Button counterIncrement = new Button("Clique sur moi !");

    //View
    Parent view;

    public WindowView() {
        view = createView();
    }
    
    public Label getCounter() { return this.counter; }
    
    public Button getCounterIncrement() { return this.counterIncrement; }

    private VBox createView(){
        VBox vBox = new VBox(15);
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(250);
        vBox.setPadding(new Insets(15));
        vBox.getChildren().add(this.createCounterLabel());
        vBox.getChildren().add(this.getCounter());
        vBox.getChildren().add(this.createCounterIncrement());
        
        return vBox;
    }
    
    private Node createCounterLabel() {
    	Label counterLabel = new Label("Compteur:");
    	counterLabel.setMaxWidth(300);
    	return counterLabel;
    }
    
    private Node createCounterIncrement() {
    	return this.getCounterIncrement();
    }

    public Parent getView() {
        return view;
    }
}