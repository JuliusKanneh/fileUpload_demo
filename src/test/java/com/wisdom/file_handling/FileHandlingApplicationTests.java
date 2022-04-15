package com.wisdom.file_handling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FileHandlingApplicationTests {
	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	@Rollback(value = false)
	void testInsertDocument() throws IOException {
		File file = new File("C:\\Users\\jkann\\OneDrive\\Documents\\Books\\java-learning-map.pdf");
		Document document = new Document();
		document.setName(file.getName());

		byte[] bytes = Files.readAllBytes(file.toPath());
		document.setContent(bytes);
		long fileSize = bytes.length;
		document.setSize(fileSize);
		document.setUploadTime(new Date());

		Document savedDocument = documentRepository.save(document);
		Document exitDocument = entityManager.find(Document.class, savedDocument.getId());

		assertThat(exitDocument.getSize()).isEqualTo(fileSize);
	}

}
