package org.vm;

import javax.swing.SwingUtilities;

/**
 * @author Vladimir Metodiev
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimerWindow().setVisible(true));
    }
}
