# Billing service

A spring boot application which utilizes the iTextPdf7 (https://mvnrepository.com/artifact/com.itextpdf/sign/7.1.12) library and takes in data from a tsv (tab separated value) and converts that into Pdf invoices. 
The data is grouped into different pages based on the receiver information and date. Some calculation is performed to print the total at the end of the page.
