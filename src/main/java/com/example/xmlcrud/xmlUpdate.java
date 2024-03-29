package com.example.xmlcrud;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "xmlupdate", value = "/xml-update")
public class xmlUpdate extends HttpServlet {

    // Initialization method
    public void init() {
        // Initialization logic can be placed here if needed
    }

    // Handling POST requests
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve parameters from the request
        String personId = request.getParameter("personId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String degree = request.getParameter("degree");
        String idNum = request.getParameter("idNum");

        try {
            // Get the servlet context
            ServletContext context = getServletContext();

            // Use the servlet context to obtain the real path of the base directory
            String baseDirectory = context.getRealPath("/data");

            // Specify the relative path of the XML file
            String relativePath = "data.xml";
            File xmlFile = new File(baseDirectory, relativePath);

            // Create a DocumentBuilder to parse the XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Get the list of person elements in the XML document
            NodeList personList = doc.getElementsByTagName("person");

            // Iterate through the person elements and update the values if the IDs match
            for (int i = 0; i < personList.getLength(); i++){
                Element personElement = (Element) personList.item(i);
                String id = getTextContent(personElement, "id");

                if(id.equals(personId)){
                    // Update each attribute with the new values
                    updateTextContent(personElement, "name", name);
                    updateTextContent(personElement, "email", email);
                    updateTextContent(personElement, "phone", phone);
                    updateTextContent(personElement, "degree", degree);
                    updateTextContent(personElement, "idNum", idNum);
                }
            }

            // Save the changes back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            // Redirect to the servlet that reads and displays the updated data
            response.sendRedirect(request.getContextPath() + "/xml-updateview");

            System.out.println("Data updated to data.xml");

        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
            System.err.println("Error: Data update failed");
        }
    }

    // Utility method to get text content of a specific tag within an element
    private String getTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    // Utility method to update the text content of a specific tag within an element
    private void updateTextContent(Element element, String tagName, String newText) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(newText);
        }
    }

    // Destruction method
    public void destroy() {
        // Cleanup or resource release logic can be placed here if needed
    }
}
