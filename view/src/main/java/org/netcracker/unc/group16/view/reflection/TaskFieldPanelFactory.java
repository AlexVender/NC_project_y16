package org.netcracker.unc.group16.view.reflection;


import java.lang.reflect.Field;


public class TaskFieldPanelFactory {
    public TaskFieldPanel createPanel(Class aClass, Field field, String displayName, Integer order, Boolean editable) {
        switch (aClass.getSimpleName()) {
            case "String":
                return new TaskStringFieldPanel(field, displayName, order, editable);

            case "Integer":
                return new TaskIntegerFieldPanel(field, displayName, order, editable);

            case "Calendar":
                return new TaskCalendarFieldPanel(field, displayName, order, editable);

            default:
                return null;
        }
    }
}
