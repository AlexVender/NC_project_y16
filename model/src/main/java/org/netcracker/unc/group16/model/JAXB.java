package org.netcracker.unc.group16.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

public class JAXB {

    public void read() {

        try {

            File file = new File("file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TaskManagerModel.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TaskManagerModel taskManagerModel = (TaskManagerModel) jaxbUnmarshaller.unmarshal(file);
            System.out.println(taskManagerModel);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public void write(TaskManagerModel taskManagerModel) {
        try {



            File file = new File("model\\src\\main\\java\\org\\netcracker\\unc\\group16\\file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TaskManagerModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();


            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(taskManagerModel, file);
            jaxbMarshaller.marshal(taskManagerModel, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
