package org.example.model;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.XmlType;
import org.example.model.avq.hdr.Hdr;
import org.example.model.avq.pacs.iso.std.iso._20022.tech.xsd.pacs_008_001.Document;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "hdr",
        "document",
})


@XmlRootElement(name = "InstantPayment", namespace = "")
public class InstantPayment {
    @XmlElement(name = "Hdr", namespace = "", required = true)
    protected Hdr hdr;
    @XmlElement(name = "Document", required = true)
    protected Document document;

    public Hdr getHdr() {
        return hdr;
    }

    public void setHdr(Hdr hdr){
        this.hdr = hdr;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
