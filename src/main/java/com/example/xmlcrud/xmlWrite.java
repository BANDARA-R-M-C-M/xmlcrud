package com.example.xmlcrud;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// Servlet to handle writing data to an XML file
@WebServlet(name = "xmlwrite", value = "/xml-write")
public class xmlWrite extends HttpServlet {

    // Initialization method
    public void init() {
        // Initialization logic can be placed here if needed
    }

    // Handling POST requests
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieve data from the request parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String degree = request.getParameter("degree");
        String idNum = request.getParameter("idNum");

        try {
            // Get the servlet context
            ServletContext context = getServletContext();

            // Get the real path of the base directory and specify the relative path of the XML file
            String baseDirectory = context.getRealPath("/data");
            String relativePath = "data.xml";

            // Create a File object representing the XML file
            File xmlFile = new File(baseDirectory, relativePath);

            // Create a DocumentBuilder to parse the XML file
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);

            // Create a new person element
            Element personElement = doc.createElement("person");

            // Add fields to the person element
            personElement.appendChild(createElement(doc, "id", generateUniqueId()));
            personElement.appendChild(createElement(doc, "name", name));
            personElement.appendChild(createElement(doc, "email", email));
            personElement.appendChild(createElement(doc, "phone", phone));
            personElement.appendChild(createElement(doc, "degree", degree));
            personElement.appendChild(createElement(doc, "idNum", idNum));

            // Add the person element to the root element of the XML document
            doc.getDocumentElement().appendChild(personElement);

            // Save the changes back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            // Redirect to the XML read servlet to display the updated data
            response.sendRedirect(request.getContextPath() + "/xml-read");

        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    // Method to generate a unique ID based on the current date and time
    private String generateUniqueId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");
        return now.format(formatter);
    }

    // Utility method to create an XML element with a given tag name and text content
    private Element createElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }

    // Destruction method
    public void destroy() {
        // Cleanup or resource release logic can be placed here if needed
    }
}
