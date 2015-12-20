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


public class NewTaskDialog extends JDialog {
    public static final int CANCEL = 0;
    public static final int OK = 1;

    private static int status = CANCEL;

    private JButton btnCancel;
    private JButton btnOK;
    
    private ArrayList<FieldPanel> panels;
    private Task oldTask;
    private Task newTask;

    private Boolean editMode;

    public NewTaskDialog(Component parentComponent, Task task) {
        setModal(true);
        setResizable(false);
        setTitle("Редактировать");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.oldTask = task;
        try {
            this.newTask = task.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ignored) {}
        editMode = true;

        initGUI();
        addListeners();

        pack();
        setLocationRelativeTo(parentComponent);
    }

    public NewTaskDialog(Component parentComponent, Class aClass) throws IllegalAccessException, InstantiationException {
        setModal(true);
        setResizable(false);
        setTitle("Создать");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.newTask = (Task) aClass.newInstance();
        this.oldTask = this.newTask;
        try {
            this.newTask = (Task) aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ignored) {}
        editMode = false;


        initGUI();
        addListeners();

        pack();
        setLocationRelativeTo(parentComponent);
    }

    private void analyze(Object o) {
        TaskFieldPanelFactory taskFieldPanelFactory = new TaskFieldPanelFactory();

        Field[] taskFields = o.getClass().getDeclaredFields();
        for (Field taskField : taskFields) {
            NotDisplayed taskFieldNotDisplayed = taskField.getDeclaredAnnotation(NotDisplayed.class);
            FieldSettings taskFieldSettings = taskField.getDeclaredAnnotation(FieldSettings.class);
            if (taskFieldNotDisplayed != null || taskFieldSettings == null) {
                continue;
            }
            Object defaultVal = null;
            try {
                taskField.setAccessible(true);
                defaultVal = taskField.get(oldTask);
            } catch (IllegalAccessException ignored) {}

            FieldPanel newFieldPanel = taskFieldPanelFactory.createPanel(taskField.getType(), taskField, defaultVal,
                    taskFieldSettings.displayName(), taskFieldSettings.orderNumb(), taskFieldSettings.editable());

            if (newFieldPanel != null) {
                panels.add(newFieldPanel);
            }
        }

        try {
            Class superClass = o.getClass().getSuperclass();
            if (superClass != Object.class && superClass != null) {
                Object instance = superClass.newInstance();
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
        analyze(newTask);

        panels.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));

        c.gridy = 0;
        for (FieldPanel panel : panels) {
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
            @Override
            public void windowClosing(WindowEvent e) {
                status = CANCEL;
            }
        });


        btnCancel.addActionListener(e -> {
            status = CANCEL;
            setVisible(false);
        });


        btnOK.addActionListener(e -> {
            status = OK;
            for (FieldPanel panel : panels) {
                if (!panel.isValidData()) {
                    JOptionPane.showMessageDialog(this,
                            "Введены некорректные данные",
                            "Ошибка ввода",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Field field = panel.getField();
                String fieldName = field.getName();
                String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                Object instance = newTask;
                do {
                    try {
                        Method[] methods = instance.getClass().getDeclaredMethods();
                        for (Method method : methods) {
                            if (method.getName().equals(setterName)) {
                                instance = null;
                                method.invoke(newTask, panel.getData());
                                break;
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        Throwable exCause = ex.getCause();
                        if (exCause.getClass() == IllegalTimeSetException.class) {
                            String exMessage = exCause.getLocalizedMessage();
                            JOptionPane.showMessageDialog(this,
                                    exMessage,
                                    "Ошибка ввода",
                                    JOptionPane.WARNING_MESSAGE);
                            status = CANCEL;
                        }
                    }
                    // Go to the super class
                    if (instance != null) {
                        Class superClass = instance.getClass().getSuperclass();
                        if (superClass != Object.class && superClass != null) {
                            try {
                                instance = superClass.newInstance();
                            } catch (InstantiationException | IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } while (instance != null);
            }
            if (status == OK) {
                setVisible(false);
            }
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
