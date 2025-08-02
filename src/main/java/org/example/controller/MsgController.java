package org.example.controller;

import jakarta.xml.bind.*;
import org.example.model.InstantPayment;
import org.example.model.fin.com.fundtech.scl.commontypes.FndtMsgType;
import org.example.model.avq.pacs.iso.std.iso._20022.tech.xsd.pacs_008_001.Document;
import org.springframework.web.bind.annotation.*;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import java.io.StringReader;

@RestController
@RequestMapping
public class MsgController {

    @GetMapping
    public String sayHello() {
        return "Welcome to MsgApp";
    }

    @PostMapping
    public void getMessage(@RequestBody String str){
        System.out.println(str);
    }

    @PostMapping("/msg")
    public void getMessage2(@RequestBody String str) throws JAXBException {
        System.out.println("Incoming message: \n" + str);

        JAXBContext jaxbContext = JAXBContext.newInstance("org.example.model.avq.pacs.iso.std.iso._20022.tech.xsd.pacs_008_001:org.example.model.fin.com.fundtech.scl.commontypes", InstantPayment.class.getClassLoader());

        JAXBContext jaxbContext2 = JAXBContext.newInstance(InstantPayment.class);
        Marshaller marshaller2 = jaxbContext2.createMarshaller();

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader stringReader = new StringReader(str);

        //<FndtMsg>
        JAXBElement<FndtMsgType> jaxbElement = (JAXBElement<FndtMsgType>) unmarshaller.unmarshal(stringReader);
        FndtMsgType fndtMsgType = jaxbElement.getValue();

        //<Document>
        JAXBElement<Document> anyJAXBElement= (JAXBElement<Document>) fndtMsgType.getMsg().getPmnt().getAny();
        Document inPacs008 = anyJAXBElement.getValue();

        Document outPacs008 = new Document();
        outPacs008.setFIToFICstmrCdtTrf(inPacs008.getFIToFICstmrCdtTrf());

        InstantPayment outInstantPayment = new InstantPayment();
        outInstantPayment.setDocument(inPacs008);


        Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.marshal(new JAXBElement<PmntType>(
//                new QName("uri","local"),
//                        PmntType.class,
//                        ((FndtMsgType) jaxbElement.getValue()).getMsg().getPmnt()
//                ),System.out);

        System.out.println("Outgoing message:");
//        marshaller.marshal(new JAXBElement<Document>(
//                new QName("uri","local"),
//                Document.class,
//                outPacs008
//        ),System.out);

                marshaller2.marshal(new JAXBElement<InstantPayment>(
                new QName("uri","local"),
                InstantPayment.class,
                outInstantPayment
        ),System.out);

    }
}
