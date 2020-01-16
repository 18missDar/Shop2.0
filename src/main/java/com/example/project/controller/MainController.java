package com.example.project.controller;

import com.example.project.domain.Goods;
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

    public List<Goods> filterIsOk(String filter){
        return StringUtils.isNotBlank(filter) ? messageRepo.findByTitle(filter) : messageRepo.findAll();
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

}