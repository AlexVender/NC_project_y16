package org.netcracker.unc.group16.view.reflection;


import javax.swing.*;
import javax.swing.text.html.ObjectView;
import java.awt.*;
import java.lang.reflect.Field;


public abstract class TaskFieldPanel extends JPanel {
    protected Field field;
    protected Integer order;
    protected Boolean editable;

    protected JLabel lblDisplayName;

    public TaskFieldPanel(Field field, String displayName, Integer order, Boolean editable) {
        this.field = field;
        lblDisplayName = new JLabel(displayName);
        this.order = order;
        this.editable = editable;

        lblDisplayName.setPreferredSize(new Dimension(65, (int) lblDisplayName.getPreferredSize().getHeight()));
        lblDisplayName.setHorizontalAlignment(SwingConstants.RIGHT);

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridBagConstraints c = new GridBagConstraints();
        add(lblDisplayName, c);
        add(Box.createRigidArea(new Dimension(10, 0)));

    }

    public Field getField() {
        return field;
    }

    public Integer getOrder() {
        return order;
    }

    public Boolean isEditable() {
        return editable;
    }

    public abstract boolean isValidData();

    public abstract Object getData();
}
