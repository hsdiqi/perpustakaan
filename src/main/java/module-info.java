module perpus.perpustakaan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens perpus.perpusApp to javafx.fxml;
    exports perpus.perpusApp;
    exports perpus.controller;
    opens perpus.controller to javafx.fxml;
}