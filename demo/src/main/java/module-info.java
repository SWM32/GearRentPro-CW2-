module lk.cmjd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens lk.cmjd to javafx.fxml;

    exports lk.cmjd;
}
