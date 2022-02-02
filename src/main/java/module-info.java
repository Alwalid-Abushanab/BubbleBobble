module upei.cs {
    requires javafx.controls;
    requires javafx.fxml;

    requires eu.hansolo.tilesfx;
    exports upei.cs.algs4;

    opens upei.cs to javafx.fxml;
    exports upei.cs;
}