package com.wisdom.file_handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public void save(Document document){
        documentRepository.save(document);
    }

    public List<Document> getAll(){
        return documentRepository.findAll();
    }

    public Optional<Document> findById(long id){
        return documentRepository.findById(id);
    }
}
