package org.netcracker.unc.group16;

import org.netcracker.unc.group16.annotations.Displayed;
import org.netcracker.unc.group16.annotations.FieldSettings;

import javax.swing.*;
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
        Integer order;
        String displayName;
        Boolean editable;

        public TaskField(Class aClass, Integer order, String displayName, Boolean editable) {
            this.aClass = aClass;
            this.order = order;
            this.displayName = displayName;
            this.editable = editable;
        }
    }

    private void initGUI() {
        setModal(true);

        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);

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
                    taskField.getDeclaringClass(),
                    taskFieldSettings.orderNumb(),
                    taskFieldSettings.displayName(),
                    taskFieldSettings.editable());

            elements.add(ts);
        }

        elements.sort((o1, o2) -> o1.order.compareTo(o2.order));


        for (TaskField elm : elements) {
            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.LINE_AXIS));
            mainPanel.add(fieldPanel);

            JLabel label = new JLabel(elm.displayName);
            fieldPanel.add(label);

            JTextField textField = new JTextField();
            fieldPanel.add(textField);
        }

        // ########## //

        btnCancel = new JButton("Отмена");
        btnOK = new JButton("OK");
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
