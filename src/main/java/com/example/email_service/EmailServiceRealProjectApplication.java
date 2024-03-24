package com.example.email_service;

import com.example.email_service.service.PdfGenerateService;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@SpringBootApplication
@EnableAsync
public class EmailServiceRealProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceRealProjectApplication.class, args);
    }

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @Override
    public void run(String... args) throws Exception {
//        Map<String, Object> data = new HashMap<>();
//        User customer = new User();
//        customer.setName("Simple Solution");
//        customer.setPassword("123, Simple Street");
//        customer.setEmail("john@simplesolution.dev");
//        customer.setEnabled(true);
//        data.put("customer", customer);

//        Map<String, Object> data = new HashMap<>();
//        Customer customer = new Customer();
//        customer.setCompanyName("Simple Solution");
//        customer.setContactName("John Doe");
//        customer.setAddress("123, Simple Street");
//        customer.setEmail("john@simplesolution.dev");
//        customer.setPhone("123 456 789");
//        data.put("customer", customer);
//
//        List<QuoteItem> quoteItems = new ArrayList<>();
//        QuoteItem quoteItem1 = new QuoteItem();
//        quoteItem1.setDescription("Test Quote Item 1");
//        quoteItem1.setQuantity(1);
//        quoteItem1.setUnitPrice(100.0);
//        quoteItem1.setTotal(100.0);
//        quoteItems.add(quoteItem1);
//
//        QuoteItem quoteItem2 = new QuoteItem();
//        quoteItem2.setDescription("Test Quote Item 2");
//        quoteItem2.setQuantity(4);
//        quoteItem2.setUnitPrice(500.0);
//        quoteItem2.setTotal(2000.0);
//        quoteItems.add(quoteItem2);
//
//        QuoteItem quoteItem3 = new QuoteItem();
//        quoteItem3.setDescription("Test Quote Item 3");
//        quoteItem3.setQuantity(2);
//        quoteItem3.setUnitPrice(200.0);
//        quoteItem3.setTotal(400.0);
//        quoteItems.add(quoteItem3);
//
//        data.put("quoteItems", quoteItems);
//        pdfGenerateService.generatePdfFile("emailtemplate", data, "quotation.pdf");

//        LocalDate ld = LocalDate.now();
//        String pdfName = ld + ".pdf";
//        CodingErrorPdfInvoiceCreator cepdf = new CodingErrorPdfInvoiceCreator(pdfName);
//        cepdf.createDocument();
//
//        //Create Header start
//        HeaderDetails header = new HeaderDetails();
//        header.setInvoiceNo("RK35623").setInvoiceDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).build();
//        cepdf.createHeader(header);
//        //Header End
//
//        //Create Address start
//        AddressDetails addressDetails = new AddressDetails();
//        addressDetails
//                .setBillingCompany("Coding Error")
//                .setBillingName("Bhaskar")
//                .setBillingAddress("Bangluru,karnataka,india\n djdj\ndsjdsk")
//                .setBillingEmail("codingerror303@gmail.com")
//                .setShippingName("Customer Name \n")
//                .setShippingAddress("Banglore Name sdss\n swjs\n")
//                .build();
//
//        cepdf.createAddress(addressDetails);
//        //Address end
//
//        //Product Start
//        ProductTableHeader productTableHeader = new ProductTableHeader();
//        cepdf.createTableHeader(productTableHeader);
//        List<Product> productList = cepdf.getDummyProductList();
//        productList = cepdf.modifyProductList(productList);
//        cepdf.createProduct(productList);
//        //Product End
//
//        //Term and Condition Start
//        String pdfD = System.getProperty("user.home") + "/Downloads/images/";
//        List<String> TncList = new ArrayList<>();
//        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer.");
//        TncList.add("2. The Seller warrants the product for one (1) year from the date of shipment");
//        String imagePath = pdfD + "forest.jpg";
//        cepdf.createTnc(TncList, false, imagePath);
//        // Term and condition end
//        System.out.println("pdf genrated");

        String ORIG = "src/main/resources/templates/new.html";
        String OUTPUT_FOLDER = "src/main/resources/";

        File htmlSource = new File(ORIG);
        File pdfDest = new File(OUTPUT_FOLDER + "output.pdf");
        HtmlConverter.convertToPdf(new FileInputStream(htmlSource), new FileOutputStream(pdfDest));

    }

}
