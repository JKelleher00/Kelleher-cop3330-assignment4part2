package ucf;

import javafx.beans.property.SimpleStringProperty;

public class ListObj extends Scene2{
    private String duedate;
    private String description;
    private String ch;
    public ListObj(String due, String desc, boolean checked){
        this.duedate = due;
        this.description = desc;
        if(checked)
            this.ch = "Y";
        else
            this.ch = "N";
    }
    public String getDueDate(){
        return duedate;
    }
    public String getDescription(){
        return description;
    }
    public String getCh(){
        return ch;
    }
    public void setDueDate(String newduedate){
        this.duedate = newduedate;
    }
    public void setDescription(String desc){
        this.description = desc;
    }
    public void setCh(String checked){
        this.ch = checked;
    }
}
