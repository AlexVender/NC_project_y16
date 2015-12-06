package org.netcracker.unc.group16.view.reflection;


import java.lang.reflect.Field;


public class TaskFieldPanelFactory {
    public FieldPanel createPanel(Class aClass, Field field, Object defaultVal, String displayName, Integer order, Boolean editable) {
        switch (aClass.getSimpleName()) {
            case "String":
                return new StringFieldPanel(field, defaultVal, displayName, order, editable);

            case "Integer":
                return new IntegerFieldPanel(field, defaultVal, displayName, order, editable);

            case "Calendar":
                return new CalendarFieldPanel(field, defaultVal, displayName, order, editable);

            default:
                return null;
        }
    }
}
