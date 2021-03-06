package com.example.project.controller;

import com.example.project.domain.Goods;
import com.example.project.domain.Item;
import com.example.project.domain.Report;
import com.example.project.repository.GoodsRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.MyHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private GoodsRepository messageRepo;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    public boolean isActive(Goods good){
        return messageRepo.findById(good.getId()).get().isActive();
    }


    public List<Goods> findActiveGoods(){
        return messageRepo.findAll().stream()
                .filter(this::isActive)
                .collect(Collectors.toList());
    }

    public List<Goods> findFilterActiveGoods(String filter){
        return messageRepo.findByTitle(filter).stream()
                .filter(this::isActive)
                .collect(Collectors.toList());
    }

    public List<Goods> filterIsOk(String filter){
        return StringUtils.isNotBlank(filter) ? findFilterActiveGoods(filter) : findActiveGoods();
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {

        model.addAttribute("messages", filterIsOk(filter));
        model.addAttribute("filter", filter);

        return "main";
    }

    @RequestMapping("/login")
    public String loginUser(Model model){
        model.addAttribute("error", false);
        return "login";
    }



    @RequestMapping("/login-fail")
    public String loginUser2(Model model){
        model.addAttribute("error", true);
        return "login";
    }

    public void encodingAndSavePicture(Goods message, MultipartFile file) throws IOException{
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            byte[] fileContent = FileUtils.readFileToByteArray(convFile);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            message.setFilename("data:image/jpeg;base64," + encodedString);
        }
        messageRepo.save(message);
    }


    @PostMapping("/main")
    public String add(@RequestParam String title,
                      @RequestParam String description,
                      @RequestParam String cost,
                      @RequestParam String category,
                      Map<String, Object> model,
                      @RequestParam("file") MultipartFile file) throws IOException {
        Goods message = new Goods(title, description, cost, category);

        encodingAndSavePicture(message, file);

        model.put("messages", messageRepo.findAll());

        return "main";
    }

    @PostMapping("/xml")
    public String addFrom(Map<String, Object> model,
                          @RequestParam("file") String file) throws IOException, ParserConfigurationException, SAXException {
        File file1 = new File("xmlfile.xml");
        if (!file1.exists()){
            file1.createNewFile();
        }
        PrintWriter pw = new PrintWriter(file1);
        pw.print(file);
        pw.close();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        MyHandler handler = new MyHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse("xmlfile.xml");

        Report report = handler.getReport();

        for (int i = 0; i<report.goodsList.size(); i++){
            messageRepo.save(report.goodsList.get(i));
        }

        model.put("messages", messageRepo.findAll());
        file1.delete();
        return "main";

    }

    @GetMapping("/uploadXML")
    public String showPageUpload(Model model){
        String result = "";
        model.addAttribute("result", result);
        return "uploadXML";
    }

    public List<Goods> findActiveGoodsByCategory(String category){
        return messageRepo.findByCategory(category).stream()
                .filter(this::isActive)
                .collect(Collectors.toList());
    }

    public String creategoodXmlFromCategory(String category){
        StringBuilder goodsFromXml = new StringBuilder();
        List<Goods> goodsList = findActiveGoodsByCategory(category);
        for (int i = 0; i< goodsList.size(); i++){
            Goods good = goodsList.get(i);
            goodsFromXml.append("<good>\n")
                    .append("<title> " + good.getTitle() +"</title>\n")
                    .append("<description> " + good.getDescription() +"</description>\n")
                    .append("<cost> " + good.getCost() +"</cost>\n")
                    .append("</good>\n");
        }
        return goodsFromXml.toString();
    }

    public String formationXml(String category) {
        StringBuilder xmlfile = new StringBuilder();
        xmlfile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n")
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n")
                .append("<report>\n");
        String goodsXml = creategoodXmlFromCategory(category);
        xmlfile.append("<goods category = \"" + category +"\">\n")
                .append(goodsXml)
                .append("</goods>\n")
                .append("</report>");
        return xmlfile.toString();
    }

    public String formationAllXml() {
        StringBuilder xmlfile = new StringBuilder();
        xmlfile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n")
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n")
                .append("<report>\n");
        String goodsXml1 = creategoodXmlFromCategory("1");
        String goodsXml2 = creategoodXmlFromCategory("2");
        String goodsXml3 = creategoodXmlFromCategory("3");
        String goodsXml4 = creategoodXmlFromCategory("4");
        xmlfile.append("<goods category = \"" + 1 +"\">\n")
                .append(goodsXml1 + "</goods>\n")
                .append("<goods category = \"" + 2 +"\">\n")
                .append(goodsXml2 + "</goods>\n")
                .append("<goods category = \"" + 3 +"\">\n")
                .append(goodsXml3 + "</goods>\n")
                .append("<goods category = \"" + 4 +"\">\n")
                .append(goodsXml4 + "</goods>\n")
                .append("</report>");
        return xmlfile.toString();
    }

    @PostMapping("/uploadXML")
    public String showPageUpload2(@RequestParam String category, Model model){
        String result;
        if (category.equals("5")){
            result = formationAllXml();
        }
        else{
            result = formationXml(category);
        }
        model.addAttribute("result", result);
        return "uploadXML";
    }



}