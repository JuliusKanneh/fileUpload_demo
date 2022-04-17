package com.wisdom.file_handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {
    @Autowired
    private DocumentService documentService;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model){
        List<Document> documents = documentService.getAll();
        model.addAttribute("documents", documents);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout-success")
    public String logout(){
        return "login";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) throws IOException {
        Document document = new Document();
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        document.setName(fileName);
        document.setSize(multipartFile.getSize());
        document.setContent(multipartFile.getBytes());
        document.setUploadTime(new Date());

        documentService.save(document);

        redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");

        return "redirect:/";
    }

    @GetMapping("/download")
    public void download(@Param("id") long id, HttpServletResponse response) throws Exception {
        Optional<Document> result = documentService.findById(id);
        if (!result.isPresent()){
            throw new Exception("Could not find document with ID "+ id);
        }

        Document document = result.get();

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+document.getName();

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(document.getContent());
        outputStream.close();
    }

    @GetMapping("/test")
    public String testPage(){
        return "index";
    }
}
