package org.netcracker.unc.group16.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlAdapter;
/**
 * Created by Ivan on 25.11.2015.
 */
@XmlSeeAlso(Appointment.class)
public class HashMapAdapter extends XmlAdapter<MyMapType,Map<Integer, Task>> {

    @Override
    public MyMapType marshal(Map<Integer, Task> arg0) throws Exception {
        MyMapType myMapType = new MyMapType();
        for(Entry<Integer, Task> entry : arg0.entrySet()) {
            MyMapEntryType myMapEntryType =
                    new MyMapEntryType();
            myMapEntryType.setKey(entry.getKey());
            myMapEntryType.setValue(entry.getValue());
            myMapType.entry.add(myMapEntryType);
        }
        return myMapType;
    }

    @Override
    public Map<Integer, Task> unmarshal(MyMapType arg0) throws Exception {
        HashMap<Integer, Task> hashMap = new HashMap<Integer, Task>();
        for(MyMapEntryType myEntryType : arg0.entry) {
            hashMap.put(myEntryType.getKey(), myEntryType.getValue());
        }
        return hashMap;
    }

}
