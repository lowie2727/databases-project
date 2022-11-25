module be.uhasselt.databasesproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.jdbi.v3.core;
    requires org.jdbi.v3.sqlite;
    requires java.sql;
    requires org.slf4j;
    requires com.github.benmanes.caffeine;

    opens be.uhasselt.databasesproject to javafx.fxml;
    opens be.uhasselt.databasesproject.controller to javafx.fxml;
    exports be.uhasselt.databasesproject;
    exports be.uhasselt.databasesproject.controller;
    exports be.uhasselt.databasesproject.jdbi;
    exports be.uhasselt.databasesproject.model;
}
