package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.beans.UserDetails;
import com.ksatria.spring_restful_api.entity.Contact;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.dataloader.DataLoader;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ReportService {

    private final ContactRepository contactRepository;
    private final DataLoader<String, User> userLoader;


    @Transactional
    public ResponseEntity<byte[]> generateReport() {
        String jasperFilePath = "C:\\Users\\GTR6\\OneDrive - Ksatria Medical Systems Pty Ltd\\Documents\\GitHub\\spring_restful_api\\src\\main\\resources\\reports\\jasperDesigns\\ContactListReport.jasper";

        try {
            // Load Jasper Report
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFilePath);

            // Create data sources
            List<Contact> contacts = contactRepository.getAllContacts();
            contacts.forEach(contact -> userLoader.load(contact.getUser().getName()));

            List<UserDetails> userDetailsList = new ArrayList<>();
            userDetailsList.add(new UserDetails(contacts.getFirst().getUser().getUsername()));


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userDetailsList);
            JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(contacts);

            //Set parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("TABLE_DATA_SOURCE", tableDataSource);

            Locale locale = LocaleContextHolder.getLocale();
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle(locale));

            // Fill report and generate JasperPrint
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\GTR6\\OneDrive - Ksatria Medical Systems Pty Ltd\\Documents\\GitHub\\spring_restful_api\\src\\main\\resources\\reports\\generatedReports\\ContactListReport.pdf");

            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);

        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating report", e);
        }
    }

    private ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle("messages", locale);
    }
}
