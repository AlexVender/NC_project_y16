package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.annotations.FieldSettings;
import org.netcracker.unc.group16.annotations.NotDisplayed;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.view.reflection.TaskFieldPanel;
import org.netcracker.unc.group16.view.reflection.TaskFieldPanelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class NewTaskDialog extends JDialog {
    public static int CANCEL = 0;
    public static int OK = 1;

    private static int status = CANCEL;

    private JButton btnCancel;
    private JButton btnOK;
    
    private ArrayList<TaskFieldPanel> panels;

    private Task newTask;

    public NewTaskDialog(Component rel) {
        setModal(true);
        setResizable(false);
        setTitle("Создать новую задачу");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initGUI();
        addListeners();

        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setFont(new Font("Verdana", Font.BOLD, 13));

        setModal(true);

        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 5, 2, 5);
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;


        // ########## //
        panels = new ArrayList<>();
        
        newTask = new Task();
        Field[] taskFields = newTask.getClass().getDeclaredFields();

        int line;
        for (line = 0; line < taskFields.length; line++) {
            Field taskField = taskFields[line];
            NotDisplayed taskFieldNotDisplayed = taskField.getDeclaredAnnotation(NotDisplayed.class);
            if (taskFieldNotDisplayed != null)
                continue;

            FieldSettings taskFieldSettings = taskField.getDeclaredAnnotation(FieldSettings.class);

            TaskFieldPanel newFieldPanel = TaskFieldPanelFactory.createPanel(
                    taskField.getType(), taskField,
                    taskFieldSettings.displayName(), taskFieldSettings.orderNumb(), taskFieldSettings.editable());

            c.gridy = line;
            if (newFieldPanel != null) {
                panels.add(newFieldPanel);
                mainPanel.add(newFieldPanel, c);
            }
        }

        // ########## //

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
//        buttonsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        c.gridy = ++line;
        c.anchor = GridBagConstraints.NORTHEAST;
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
            for (TaskFieldPanel panel : panels) { // FIXME
                if (!panel.isValidData()) {
                    JOptionPane.showMessageDialog(this,
                            "Введены некорректные данные.",
                            "Ошибка ввода",
                            JOptionPane.WARNING_MESSAGE);

                    return;
                }

                Field field = panel.getField();
                String fieldName = field.getName();
                String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                try {
                    Method setter = newTask.getClass().getDeclaredMethod(setterName, field.getType());
                    setter.invoke(newTask, panel.getData());

                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
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
            return newTask;
        }

        return null;
    }
}
