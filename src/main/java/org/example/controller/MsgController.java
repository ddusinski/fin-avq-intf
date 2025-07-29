package org.example.controller;

import jakarta.xml.bind.*;
import org.example.model.com.fundtech.scl.commontypes.FndtMsgHeaderType;
import org.example.model.com.fundtech.scl.commontypes.FndtMsgType;
import org.example.model.com.fundtech.scl.commontypes.PmntType;
import org.example.model.iso.std.iso._20022.tech.xsd.pacs_008_001.FIToFICustomerCreditTransferV08;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("received message: \n" + str);

//        JAXBContext jaxbContext = JAXBContext.newInstance(FndtMsgType.class);
        JAXBContext jaxbContext = JAXBContext.newInstance("org.example.model.iso.std.iso._20022.tech.xsd.pacs_008_001:org.example.model.com.fundtech.scl.commontypes");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader stringReader = new StringReader(str);

        JAXBElement jaxbElement = (JAXBElement) unmarshaller.unmarshal(stringReader);


        FndtMsgType fndtMsgType = (FndtMsgType)jaxbElement.getValue();

        System.out.println(fndtMsgType.getMsg().getPmnt().getAny());

        Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.marshal(jaxbElement, System.out);
//        marshaller.marshal(new JAXBElement<FndtMsgHeaderType>(
//                new QName("uri","local"),
//                FndtMsgHeaderType.class,
//                ((FndtMsgType) jaxbElement.getValue()).getHeader()
//        ), System.out);

//        marshaller.marshal(new JAXBElement<PmntType>(
//                new QName("uri","local"),
//                        PmntType.class,
//                        ((FndtMsgType) jaxbElement.getValue()).getMsg().getPmnt()
//                ),System.out);


        marshaller.marshal(new JAXBElement<FIToFICustomerCreditTransferV08>(
                new QName("uri","local"),
                FIToFICustomerCreditTransferV08.class,
                ((FIToFICustomerCreditTransferV08) ((FndtMsgType) jaxbElement.getValue()).getMsg().getPmnt().getAny())
        ),System.out);

    }
}
