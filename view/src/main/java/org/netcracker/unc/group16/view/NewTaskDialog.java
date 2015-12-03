package org.netcracker.unc.group16.view;

import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;
import org.netcracker.unc.group16.annotations.FieldSettings;
import org.netcracker.unc.group16.annotations.NotDisplayed;
import org.netcracker.unc.group16.model.Appointment;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.view.reflection.TaskFieldPanel;
import org.netcracker.unc.group16.view.reflection.TaskFieldPanelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class NewTaskDialog extends JDialog {
    public static int CANCEL = 0;
    public static int OK = 1;

    private static int status = CANCEL;

    private JButton btnCancel;
    private JButton btnOK;
    
    private ArrayList<TaskFieldPanel> panels;
    private Task task;

    private Boolean editMode;

    public NewTaskDialog(/*Task task*/) {
        setModal(true);
        setResizable(false);
        setTitle("Создать новую встречу");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        if (editMode == null) {
//            editMode = true;
//        }

        initGUI();
        addListeners();

        pack();
        setLocationRelativeTo(null);
    }

//    public NewTaskDialog(Class aClass) {
//        try {
//            new NewTaskDialog((Class) aClass.newInstance());
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    private void analyze(Object o) {
        TaskFieldPanelFactory taskFieldPanelFactory = new TaskFieldPanelFactory();

        Field[] taskFields = o.getClass().getDeclaredFields();
        for (Field taskField : taskFields) {
            NotDisplayed taskFieldNotDisplayed = taskField.getDeclaredAnnotation(NotDisplayed.class);
            if (taskFieldNotDisplayed != null) {
                continue;
            }

            FieldSettings taskFieldSettings = taskField.getDeclaredAnnotation(FieldSettings.class);
            if (taskFieldSettings == null) {
                continue;
            }

            TaskFieldPanel newFieldPanel = taskFieldPanelFactory.createPanel(
                    taskField.getType(), taskField,
                    taskFieldSettings.displayName(), taskFieldSettings.orderNumb(), taskFieldSettings.editable());

            if (newFieldPanel != null) {
                panels.add(newFieldPanel);
            }
        }

        try {
            if (o.getClass().getSuperclass() != null && o.getClass().getSuperclass() != Object.class) {
                Object instance = o.getClass().getSuperclass().newInstance();
                analyze(instance);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initGUI() {
        setFont(new Font("Verdana", Font.BOLD, 13));

        setModal(true);

        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;


        panels = new ArrayList<>();
        task = new Appointment();
        analyze(task);

        panels.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));

        c.gridy = 0;
        for (TaskFieldPanel panel : panels) {
            mainPanel.add(panel, c);
            c.gridy++;
        }

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        c.anchor = GridBagConstraints.NORTHEAST;
        c.insets = new Insets(20, 0, 5, 5);
        mainPanel.add(buttonsPanel, c);
        btnOK = new JButton("OK");
        btnOK.setPreferredSize(new Dimension(70, 23));
        btnCancel = new JButton("Отмена");
        btnCancel.setPreferredSize(new Dimension(70, 23));

        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(btnOK);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonsPanel.add(btnCancel);

    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                status = CANCEL;
            }
        });


        btnCancel.addActionListener(e -> {
            status = CANCEL;
            setVisible(false);
        });


        btnOK.addActionListener(e -> {
            for (TaskFieldPanel panel : panels) {
                if (!panel.isValidData()) {
                    JOptionPane.showMessageDialog(this,
                            "Введены некорректные данные.",
                            "Ошибка ввода",
                            JOptionPane.WARNING_MESSAGE);

                    return;
                }

                Field field = panel.getField();
                field.setAccessible(true);
                try {
                    field.set(task, panel.getData());
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

                /* old - using setter
                String fieldName = field.getName();
                String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                try {
                    Method setter = task.getClass().getDeclaredMethod(setterName, field.getType());
                    setter.invoke(task, panel.getData());

                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }*/
            }
            status = OK;
            setVisible(false);
        });
    }

    public int showDialog() {
        setVisible(true);
        return status;
    }

    public Task getResult() {
        if (status == OK) {
            return task;
        }

        return null;
    }
}
