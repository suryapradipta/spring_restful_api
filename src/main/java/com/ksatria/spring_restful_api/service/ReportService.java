package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.beans.UserDetails;
import com.ksatria.spring_restful_api.entity.Contact;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.repository.ContactRepository;
import com.ksatria.spring_restful_api.repository.UserRepository;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.dataloader.DataLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {

    private final ContactRepository contactRepository;
    private final DataLoader<String, User> userLoader;

    @Transactional
    public ResponseEntity<byte[]> generateReport() {
        String jasperFilePath = "C:\\Users\\GTR6\\OneDrive - Ksatria Medical Systems Pty Ltd\\Documents\\GitHub\\spring_restful_api\\src\\main\\resources\\reports\\jasperDesigns\\ContactListReport.jasper";

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFilePath);

            List<Contact> contacts = contactRepository.getAllContacts();
            contacts.forEach(contact -> userLoader.load(contact.getUser().getName()));

            List<UserDetails> userDetailsList = new ArrayList<>();
            for (Contact contact : contacts) {
                String username = contact.getUser().getUsername();
                userDetailsList.add(new UserDetails(username));
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userDetailsList);


            JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(contacts);


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("TABLE_DATA_SOURCE", tableDataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "ContactListReport.pdf");

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\GTR6\\OneDrive - Ksatria Medical Systems Pty Ltd\\Documents\\GitHub\\spring_restful_api\\src\\main\\resources\\reports\\generatedReports\\ContactListReport.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating report", e);
        }
    }
}
