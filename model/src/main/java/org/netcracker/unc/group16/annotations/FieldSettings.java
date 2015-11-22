package org.netcracker.unc.group16.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldSettings {
    String displayName();
    boolean editable();
    int orderNumb();
}
