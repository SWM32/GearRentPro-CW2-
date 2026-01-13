module lk.cmjd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    opens lk.cmjd to javafx.fxml;

    exports lk.cmjd;
}
