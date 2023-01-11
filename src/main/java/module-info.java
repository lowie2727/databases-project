module be.uhasselt.databasesproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.jdbi.v3.core;
    requires org.jdbi.v3.sqlite;
    requires java.sql;
    requires org.slf4j;
    requires com.github.benmanes.caffeine;
    requires org.apache.commons.lang3;

    opens be.uhasselt.databasesproject to javafx.fxml;
    opens be.uhasselt.databasesproject.controller to javafx.fxml;
    opens be.uhasselt.databasesproject.controller.user to javafx.fxml;
    opens be.uhasselt.databasesproject.controller.admin to javafx.fxml;
    opens be.uhasselt.databasesproject.controller.admin.table to javafx.fxml;
    opens be.uhasselt.databasesproject.controller.admin.edit to javafx.fxml;

    exports be.uhasselt.databasesproject;
    exports be.uhasselt.databasesproject.jdbi;
    exports be.uhasselt.databasesproject.model;
    exports be.uhasselt.databasesproject.controller;
    exports be.uhasselt.databasesproject.controller.admin;
    exports be.uhasselt.databasesproject.controller.user;
    exports be.uhasselt.databasesproject.controller.admin.edit;
    exports be.uhasselt.databasesproject.controller.admin.table;
}
