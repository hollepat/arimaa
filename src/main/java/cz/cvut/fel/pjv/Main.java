package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.App;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                App.run();
            }
        });
    }
}
