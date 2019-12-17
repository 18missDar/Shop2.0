package com.example.project.service;

import com.example.project.domain.Goods;
import com.example.project.domain.Report;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MyHandler extends DefaultHandler {
    static final String REPORT_TAG = "report";
    static final String GOODS_TAG = "goods";
    static final String GOOD_TAG = "good";
    static final String TITLE_TAG = "title";
    static final String DESCRIPTION_TAG = "description";
    static final String COST_TAG = "cost";

    static final String GOODS_ATTRIBUTE = "category";

    private Report report;
    private Goods currentGood;
    private String numberCategory;
    private String currentElement;

    public Report getReport(){
        return report;
    }
    public MyHandler() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;

        switch (currentElement){
            case REPORT_TAG:{
                report = new Report();
            }break;
            case GOODS_TAG:{
                report.goodsList = new ArrayList<>();

                if (numberCategory == null){
                    numberCategory = attributes.getValue(GOODS_ATTRIBUTE);
                }
            }break;
            case GOOD_TAG:{
                currentGood = new Goods();
                currentGood.setCategory(numberCategory);
            }break;
            default:{
                //nothing
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        if (text.contains("<") || currentElement == null){
            return;
        }
        switch(currentElement){
            case TITLE_TAG:{
                currentGood.setTitle(text);
            }break;
            case DESCRIPTION_TAG:{
                currentGood.setDescription(text);
            }break;
            case COST_TAG:{
                currentGood.setCost(text);
            }break;
            default:{
                //nothing
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName){
            case GOOD_TAG:{
                currentGood.setActive(true);
                report.goodsList.add(currentGood);
                currentGood = null;
            }break;
            default:{
                //nothing
            }
        }

        currentElement = null;
    }
}
