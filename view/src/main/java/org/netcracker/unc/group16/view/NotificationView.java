/**
 * Created by Ivan.Chikhanov on 09.11.2015.
 */

package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.annotations.FieldSettings;
import org.netcracker.unc.group16.annotations.NotDisplayed;
import org.netcracker.unc.group16.exceptions.IllegalTimeSetException;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.view.reflection.FieldPanel;
import org.netcracker.unc.group16.view.reflection.TaskFieldPanelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NotificationView extends JFrame {
    private JLabel countLabel;
    private JLabel timeLabel;
    private JLabel TitleLabel;
    private JLabel descriptionLabel;
    private JButton OK;
    private boolean status = true;


    public NotificationView(Map<Integer, Task> currentTasks) {
        setTitle("Notification");
        countLabel = new JLabel("You've got new appointment!");
        OK = new JButton("OK");
        setLocationRelativeTo(null);


        JPanel buttonsPanel = new JPanel(new FlowLayout());
        add(countLabel, BorderLayout.NORTH);
        //Добавление всех тасок (реализовано плохо, т.к. просто выводится одна таска)
        Map<Integer, Task> jjalbeMap = new HashMap<>(currentTasks);
        for (HashMap.Entry<Integer, Task> entry: jjalbeMap.entrySet()){
            int hour = entry.getValue().getTime().get(Calendar.HOUR_OF_DAY);
            int min = entry.getValue().getTime().get(Calendar.MINUTE);


            String formedText = (hour > 9 ? "" : "0") + hour + ":" +
                    (min > 9 ? "" : "0") + min + " " + entry.getValue().getTitle();
            timeLabel = new JLabel(formedText);
            add(timeLabel, BorderLayout.CENTER);
            System.out.println(formedText);
        }


        buttonsPanel.add(OK);

        add(buttonsPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        addListeners();
        paint();
    }

    private void addListeners(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                status = false;
            }
        });

        OK.addActionListener(e -> {
            status = false;
            NotificationView.this.setVisible(false);
        });

    }

    public void paint() {

    }
}