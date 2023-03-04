module cz.cvut.fel.pjv.arimaa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens cz.cvut.fel.pjv.arimaa to javafx.fxml;
    exports cz.cvut.fel.pjv.arimaa;
}