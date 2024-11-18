package com.example.calculadoraelectrica;



import android.os.Environment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerarXML {
    private Document documet;

    public GenerarXML() throws ParserConfigurationException{
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construct = fabrica.newDocumentBuilder();
        documet = construct.newDocument();
    }

    public void generarDocumento(){
        Element elemento = documet.createElement("calculos");
        documet.appendChild(elemento);

        Element calculo = documet.createElement("Calc");
        Element calculo1 = documet.createElement("Calc");
        Element calculo2 = documet.createElement("Calc");
        Element calculo3 = documet.createElement("Calc");
        Element calculo4 = documet.createElement("Calc");


    }

    public void crearDocumento(String pathXml) throws TransformerException, IOException {
        TransformerFactory factoria = TransformerFactory.newInstance();
        Transformer transformer =  factoria.newTransformer();

        Source source = new DOMSource(documet);
        File file = new File(pathXml,"CalculadoraElectricaBackupXML.txt");
        FileWriter fwr = new FileWriter(file);
        PrintWriter pr = new PrintWriter(fwr);
        Result result = new StreamResult(pr);
        transformer.transform(source, result);
    }
}
