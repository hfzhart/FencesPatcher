module com.mycompany.mavenproject18 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
     requires java.desktop;

    opens com.mycompany.mavenproject18 to javafx.fxml;
    exports com.mycompany.mavenproject18;
}
