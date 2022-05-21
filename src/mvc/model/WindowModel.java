package mvc.model;

import javafx.beans.property.*;

public final class WindowModel {
    private final IntegerProperty counterProperty;

    public WindowModel(Integer defaultCounter) {
        this.counterProperty = new SimpleIntegerProperty(defaultCounter);
    }

    public IntegerProperty counterProperty() {
        return counterProperty;
    }

    public int getCounter() {
        return counterProperty.get();
    }
    
    public void clickCounter() {
    	this.counterProperty.set(this.getCounter()+1);
    }
}