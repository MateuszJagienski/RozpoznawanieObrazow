module RozpoznawanieObrazow {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.javafx.rozpoznawanieOb to javafx.fxml;
    exports com.example.javafx.rozpoznawanieOb;

}