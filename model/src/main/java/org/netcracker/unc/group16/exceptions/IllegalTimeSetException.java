package org.netcracker.unc.group16.exceptions;


public class IllegalTimeSetException extends IllegalArgumentException {
    @Override
    public String getMessage() {
        return "End time should be after the start time";
    }

    @Override
    public String getLocalizedMessage() {
        return "Дата окончания должна быть после даты начала";
    }
}
