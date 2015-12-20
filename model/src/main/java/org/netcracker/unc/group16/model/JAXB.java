package org.netcracker.unc.group16.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXB {


    public TaskManagerModel read() {
        TaskManagerModel taskManagerModel = null;

        try {

            File file = new File("./model/file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TaskManagerModel.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            taskManagerModel = (TaskManagerModel) jaxbUnmarshaller.unmarshal(file);


        } catch (JAXBException e) {
            System.err.println(e + "Error");
        }

        return taskManagerModel;
    }

    public void write(TaskManagerModel taskManagerModel) {
        try {
            File file = new File("./model/file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TaskManagerModel.class, Task.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(taskManagerModel, file);
//            jaxbMarshaller.marshal(taskManagerModel, System.out);

        } catch (JAXBException e) {
            System.err.println(e + "Error");
        }

    }
}
