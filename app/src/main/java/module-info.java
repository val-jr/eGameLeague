module tech.w3ll.egameleague.app {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jpro.webapi;

    exports tech.w3ll.egameleague;
    opens tech.w3ll.egameleague to javafx.fxml;
    exports tech.w3ll.egameleague.controllers;
    opens tech.w3ll.egameleague.controllers to javafx.fxml;

    opens tech.w3ll.egameleague.modelos to javafx.base;
}
