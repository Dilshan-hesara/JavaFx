module edu.fxdemo.supermarketfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires javafx.base;
    requires java.desktop;
    requires java.sql;
    requires net.sf.jasperreports.core;



    opens edu.fxdemo.supermarketfx.controller to javafx.fxml;
    opens edu.fxdemo.supermarketfx.dto.TM to javafx.base;
    exports edu.fxdemo.supermarketfx;

}
