package com.example.xmlcrud;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

// Class to convert List<Element> to NodeList
public class NodeListConverter {

    // Static method to convert a List<Element> to NodeList
    public static NodeList toNodeList(List<Element> elements) {
        return new NodeListWrapper(elements); // Return an instance of NodeListWrapper
    }

    // Private static inner class implementing NodeList
    private static class NodeListWrapper implements NodeList {

        private final List<Element> elements;

        // Constructor taking a List<Element> as a parameter
        public NodeListWrapper(List<Element> elements) {
            this.elements = elements;
        }

        // Implementation of the item method from NodeList
        @Override
        public Node item(int index) {
            // Return the Element at the specified index, or null if the index is out of bounds
            if (index >= 0 && index < elements.size()) {
                return elements.get(index);
            }
            return null;
        }

        // Implementation of the getLength method from NodeList
        @Override
        public int getLength() {
            // Return the number of elements in the List
            return elements.size();
        }
    }
}
