package mvc.controller;

import mvc.view.WindowView;
import mvc.model.WindowModel;

public final class WindowController {
	//Model
	WindowModel model;

    public WindowController(WindowView view){
        setView(view);
    }

    public void setView(WindowView view){
        //get model
    	model = new WindowModel(0);

        //link Model with View
        view.getCounter().textProperty().bind(model.counterProperty().asString());

        //link Controller to View - methods for buttons
        view.getCounterIncrement().setOnAction(event -> {
        	model.clickCounter();
            event.consume();
        });

    }

}