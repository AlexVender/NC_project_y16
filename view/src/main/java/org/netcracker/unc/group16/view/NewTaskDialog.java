package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.annotations.Displayed;
import org.netcracker.unc.group16.annotations.FieldSettings;
import org.netcracker.unc.group16.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;


public class NewTaskDialog extends JDialog {
    public static int CANCEL = 0;
    public static int OK = 1;

    private static int status = CANCEL;

    private JButton btnCancel;
    private JButton btnOK;

    public NewTaskDialog() {
        setModal(true);
        setTitle("Создать новую задачу");
        setLocationRelativeTo(null);

        initGUI();
        addListeners();

        pack();
    }

    class TaskField {
        Class aClass;
        String name;
        Integer order;
        String displayName;
        Boolean editable;

        public TaskField(Class aClass, String name, Integer order, String displayName, Boolean editable) {
            this.aClass = aClass;
            this.name = name;
            this.order = order;
            this.displayName = displayName;
            this.editable = editable;
        }
    }

    private void initGUI() {
        setFont(new Font("Verdana", Font.BOLD, 13));

        setModal(true);

        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);
//        setPreferredSize(new Dimension(400, 300));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));


        // ########## //

        ArrayList<TaskField> elements = new ArrayList<>();

        Task task = new Task();
        Field[] taskFields = task.getClass().getDeclaredFields();

        for (Field taskField : taskFields) {
            Displayed taskFieldDisplayed = taskField.getDeclaredAnnotation(Displayed.class);
            if (taskFieldDisplayed == null)
                continue;

            FieldSettings taskFieldSettings = taskField.getDeclaredAnnotation(FieldSettings.class);

            TaskField ts = new TaskField(
                    taskField.getType(),
                    taskField.getName(),
                    taskFieldSettings.orderNumb(),
                    taskFieldSettings.displayName(),
                    taskFieldSettings.editable());
            elements.add(ts);
        }

        elements.sort((o1, o2) -> o1.order.compareTo(o2.order));


        for (TaskField elm : elements) {
            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.LINE_AXIS));
            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            mainPanel.add(fieldPanel);

            fieldPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            JLabel label = new JLabel(elm.displayName);
            label.setPreferredSize(new Dimension(100, label.getHeight()));
            fieldPanel.add(label);

            if (elm.aClass == Calendar.class) {
                JTextField textField1 = new JTextField();
                fieldPanel.add(textField1);
                JTextField textField2 = new JTextField();
                fieldPanel.add(textField2);
                JTextField textField3 = new JTextField();
                fieldPanel.add(textField3);
                fieldPanel.add(Box.createRigidArea(new Dimension(10, 0)));
                JTextField textField4 = new JTextField();
                fieldPanel.add(textField4);
                JTextField textField5 = new JTextField();
                fieldPanel.add(textField5);
                fieldPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                continue;
            }

            if (elm.name.equals("description")) {
                JTextArea textArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(0,51));

                fieldPanel.add(scrollPane);
                fieldPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                continue;
            }

            JTextField textField = new JTextField();
            fieldPanel.add(textField);
            fieldPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        mainPanel.add(Box.createRigidArea(new Dimension(400, 25)));

        // ########## //

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        mainPanel.add(buttonsPanel);
        btnCancel = new JButton("Отмена");
        btnOK = new JButton("OK");

        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(btnOK);
        buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonsPanel.add(btnCancel);
        buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
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


            status = OK;
            setVisible(false);
        });
    }

    public int showDialog() {
        setVisible(true);
        return status;
    }
}
