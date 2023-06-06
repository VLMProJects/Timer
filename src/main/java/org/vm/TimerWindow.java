package org.vm;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @author Vladimir Metodiev
 */

public class TimerWindow extends JFrame {
    private final Timer timer = new Timer(1000, taskPerformer());
    private static long time = 0L;
    private static final JLabel DISPLAY = new JLabel(String.valueOf(time));
    private static final JButton START = new JButton("Start");
    private static final JButton STOP = new JButton("Stop");
    private static final JButton CLEAN = new JButton("Clean");
    private static final JButton ENTER = new JButton("Enter");

    static {
        DISPLAY.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        DISPLAY.setBorder(BorderFactory.createLineBorder(new Color(51, 51, 204)));
        DISPLAY.setHorizontalAlignment(JLabel.CENTER);
        START.setPreferredSize(new Dimension(100, 32));
        START.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        STOP.setPreferredSize(new Dimension(100, 32));
        STOP.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        CLEAN.setPreferredSize(new Dimension(100, 32));
        CLEAN.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        ENTER.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
    }

    public TimerWindow() throws HeadlessException {
        super("Timer");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = getContentPane();

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipady = 10;
        constraints.ipadx = 10;
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.fill = GridBagConstraints.BOTH;

        constraints.weighty = 3;
        constraints.weightx = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        content.add(DISPLAY, constraints);

        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.weighty = 0.3;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        START.addActionListener(ae -> timer.start());
        START.setMnemonic(KeyEvent.VK_R);
        content.add(START, constraints);

        constraints.gridx = 1;
        STOP.addActionListener(ae -> timer.stop());
        STOP.setMnemonic(KeyEvent.VK_F);
        content.add(STOP, constraints);

        constraints.gridx = 2;
        CLEAN.addActionListener(ae -> {
            time = 0L;
            DISPLAY.setText(String.valueOf(0L));
        });
        CLEAN.setMnemonic(KeyEvent.VK_C);
        content.add(CLEAN, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        ENTER.addActionListener(ae -> {
            if (timer.isRunning()) {
                timer.stop();
            }
            new EnterDialog();
        });
        ENTER.setMnemonic(KeyEvent.VK_E);
        content.add(ENTER, constraints);

        setLocationRelativeTo(null);
        pack();
    }

    private ActionListener taskPerformer() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (time == 0L) {
                    timer.stop();
                    return;
                }
                time--;
                DISPLAY.setText(String.valueOf(time));
            }
        };
    }

    private static final class EnterDialog extends JDialog {

        EnterDialog() {
            initComponents();
        }

        private void initComponents() {
            setTitle("Input dialog");
            setResizable(false);
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

            setModalityType(Dialog.DEFAULT_MODALITY_TYPE);

            GridBagLayout layout = new GridBagLayout();
            setLayout(layout);

            GridBagConstraints cDialog = new GridBagConstraints();
            cDialog.ipady = 2;
            cDialog.ipadx = 2;
            cDialog.insets = new Insets(4, 4, 4, 4);

            cDialog.gridy = 0;
            cDialog.gridx = 0;
            JLabel lab = new JLabel("Set seconds:");
            lab.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            add(lab, cDialog);

            cDialog.gridy = 0;
            cDialog.gridx = 1;
            JTextField text = new JTextField(10);
            text.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            add(text, cDialog);

            cDialog.gridy = 1;
            cDialog.gridx = 0;
            cDialog.gridwidth = 2;
            cDialog.fill = GridBagConstraints.HORIZONTAL;
            JButton setButton = new JButton("Set");
            setButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
            setButton.addActionListener(ae -> {
                if (text.getText().isBlank()) {
                    dispose();
                } else if (text.getText().matches("\\d+")) {
                    time = Long.parseLong(text.getText());
                    DISPLAY.setText(String.valueOf(time));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(setButton, "Please, enter a positive number!");
                }
            });
            setButton.setMnemonic(KeyEvent.VK_S);
            add(setButton, cDialog);

            setLocationRelativeTo(this);
            pack();
            setVisible(true);
        }
    }
}


