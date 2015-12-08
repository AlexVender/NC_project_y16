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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class NotificationView extends JFrame {
    private JLabel countLabel;
    private JButton OK;
    private JButton Postpone;

    public NotificationView() {
        //Подготавливаем компоненты объекта
        countLabel = new JLabel("You've got new appointment!");
        OK = new JButton("OK");
        Postpone = new JButton("Postpone");
        setLocationRelativeTo(null);

        //Подготавливаем временные компоненты
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        //Расставляем компоненты по местам
        add(countLabel, BorderLayout.NORTH); //О размещении компонент поговорим позже

        buttonsPanel.add(OK);
        buttonsPanel.add(Postpone);

        add(buttonsPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}