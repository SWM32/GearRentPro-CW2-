module lk.cmjd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;
    requires jbcrypt;

    opens lk.cmjd to javafx.fxml;
    opens lk.cmjd.controller to javafx.fxml;

    exports lk.cmjd;
}
