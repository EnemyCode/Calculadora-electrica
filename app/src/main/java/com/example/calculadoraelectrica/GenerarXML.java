package com.example.calculadoraelectrica;


import android.database.Cursor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
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

    //FABRICA DEL DOCUMENTO
    public GenerarXML() throws ParserConfigurationException{
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construct = fabrica.newDocumentBuilder();
        documet = construct.newDocument();
    }

    //LLAMAR A ESTEMETODO SIEMPRE ABRIENDO ANTES LA CONEXION Y DERRANDO DESPUES DE LA BASE DE DATOS
    public void generarDocumento(Cursor dbCursor){
        Element cabecera = documet.createElement("calculos");
        documet.appendChild(cabecera);

        while(dbCursor.moveToNext()){
            Element calculo = documet.createElement("Calc");
            calculo.setAttribute(dbCursor.getColumnName(0), String.valueOf(dbCursor.getInt(0)));
            calculo.setAttribute(dbCursor.getColumnName(1),dbCursor.getString(1));
            for (int iter1 = 2;iter1 <= 7; iter1++) {
                calculo.setAttribute(dbCursor.getColumnName(iter1), String.valueOf(dbCursor.getDouble(iter1)));
            }
            cabecera.appendChild(calculo);
        }
    }

    //CREADOR DEL DOCUMENTO FISICO EN EL DISPOSITIVO
    public void crearDocumento(String pathXml) throws TransformerException, IOException {
        TransformerFactory factoria = TransformerFactory.newInstance();
        Transformer transformer =  factoria.newTransformer();

        Source source = new DOMSource(documet);
        File file = new File(pathXml,"CalculadoraElectricaBackupXML.xml");
        FileWriter fwr = new FileWriter(file);
        PrintWriter pr = new PrintWriter(fwr);
        Result result = new StreamResult(pr);
        transformer.transform(source, result);
    }
}
