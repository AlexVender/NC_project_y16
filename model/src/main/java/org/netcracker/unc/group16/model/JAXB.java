package org.netcracker.unc.group16.model;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

public class JAXB {

    final static Logger logger = Logger.getLogger(JAXB.class);

    public void read() {

        try {

            File file = new File("./model/file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TaskManagerModel.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TaskManagerModel taskManagerModel = (TaskManagerModel) jaxbUnmarshaller.unmarshal(file);
            for (Map.Entry<Integer, Task> entry: taskManagerModel.getHashMapTasks().entrySet()){
                Integer key = entry.getKey();
                Task value = entry.getValue();


                logger.info(key + "\n"
                                + value.getId() + "\n"
                                + value.getTitle() + "\n"
                                + value.getTime() + "\n"
                                + value.getDescription() + "\n"
                               // + value.getComment() + "\n***"
                );
            }

        } catch (JAXBException e) {
            logger.error("Error =(:", e);
        }

    }

    public void write(TaskManagerModel taskManagerModel) {
        try {
            File file = new File("./model/file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TaskManagerModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();


            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


            jaxbMarshaller.marshal(taskManagerModel, file);
            jaxbMarshaller.marshal(taskManagerModel, System.out);

        } catch (JAXBException e) {
            logger.error("Error =(:", e);
        }

    }
}
