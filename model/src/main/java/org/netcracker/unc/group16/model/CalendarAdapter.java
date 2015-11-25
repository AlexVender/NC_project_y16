package org.netcracker.unc.group16.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 26.11.2015.
 */
public class CalendarAdapter extends XmlAdapter<Long, Calendar> {


    @Override
    public Long marshal(Calendar arg0) throws Exception {
        return arg0.getTimeInMillis();
    }

    @Override
    public Calendar unmarshal(Long arg0) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(arg0);
        return  c;
    }
}
