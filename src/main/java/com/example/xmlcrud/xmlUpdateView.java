package com.example.xmlcrud;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Servlet to handle XML data retrieval for updating
@WebServlet(name = "xmlupdateview", value = "/xml-updateview")
public class xmlUpdateView extends HttpServlet {

    // Initialization method
    public void init() {
        // Initialization logic can be placed here if needed
    }

    // Handling GET requests
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieve searchName parameter from the request
        String searchName = request.getParameter("searchName");

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

            // Get the root element of the XML document and the list of person elements
            Element rootElement = doc.getDocumentElement();
            NodeList personList = rootElement.getElementsByTagName("person");

            // Search for a specific person by name and set the results in the request attribute
            NodeList searchPersonlist;
            if (searchName != null && !searchName.isEmpty()) {
                searchPersonlist = searchByName(doc, searchName);
                request.setAttribute("personlist", searchPersonlist);
            } else {
                request.setAttribute("personlist", personList);
            }

            // Forward the request and response to the update.jsp page for rendering
            RequestDispatcher dispatcher = request.getRequestDispatcher("/update.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to search for a person by name in the XML document
    private NodeList searchByName(Document doc, String searchName) {
        NodeList personList = doc.getElementsByTagName("person");
        List<Element> searchResults = new ArrayList<>();

        for (int i = 0; i < personList.getLength(); i++) {
            Element personElement = (Element) personList.item(i);
            String name = getTextContent(personElement, "name");

            // If the name matches the searchName, add the person element to the search results
            if (name != null && name.equalsIgnoreCase(searchName)) {
                searchResults.add(personElement);
            }
        }

        // Convert the List<Element> to NodeList
        return NodeListConverter.toNodeList(searchResults);
    }

    // Utility method to get text content of a specific tag within an element
    private static String getTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    // Destruction method
    public void destroy() {
        // Cleanup or resource release logic can be placed here if needed
    }
}
