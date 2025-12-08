package com.mycompany.library;

import org.w3c.dom.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.*;
import javax.xml.xpath.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private static final String XML_FILE = "library.xml";
    private static final String XSD_FILE = "librarySchema.xsd";
    private Document document;
    private XPath xpath;

    public boolean loadAndValidate() {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(XSD_FILE));
            
            Validator validator = schema.newValidator();
            validator.validate(new javax.xml.transform.stream.StreamSource(new File(XML_FILE)));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(new File(XML_FILE));
            document.getDocumentElement().normalize();
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            xpath = xpathFactory.newXPath();
            
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке XML: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveXML() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(XML_FILE));
            transformer.transform(source, result);
            return true;
        } catch (TransformerException e) {
            System.err.println("Ошибка при сохранении XML: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        
        if (document == null || xpath == null) {
            System.err.println("XML документ не загружен");
            return books;
        }
        
        try {
            NodeList nodeList = (NodeList) xpath.evaluate("/library/book", document, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                    
                int id = Integer.parseInt(elem.getAttribute("id"));
                String title = getTextContentByXPath(elem, "title");
                String author = getTextContentByXPath(elem, "author");
                int year = Integer.parseInt(getTextContentByXPath(elem, "year"));
                double price = Double.parseDouble(getTextContentByXPath(elem, "price"));
                String category = getTextContentByXPath(elem, "category");
                int amount = Integer.parseInt(getTextContentByXPath(elem, "amount"));
                int available = Integer.parseInt(getTextContentByXPath(elem, "available"));
                books.add(new Book(id, title, author, year, price, category, amount, available));
            }
        } catch (XPathExpressionException e) {
            System.err.println("Ошибка XPath при получении списка книг: " + e.getMessage());
            e.printStackTrace();
        }
        
        return books;
    }

    public boolean addBook(String title, String author, String year, double price, 
                          String category, int amount, int available) {
        if (document == null) {
            System.err.println("XML документ не загружен");
            return false;
        }
        
        try {
            Element library = document.getDocumentElement();
            
            // Поиск максимального ID
            NodeList books;
            try {
                books = (NodeList) xpath.evaluate("/library/book", document, XPathConstants.NODESET);
            } catch (XPathExpressionException e) {
                System.err.println("Ошибка при поиске книг: " + e.getMessage());
                books = null;
            }
            
            int maxId = 0;
            if (books != null) {
                for (int i = 0; i < books.getLength(); i++) {
                    try {
                        Element book = (Element) books.item(i);
                        int id = Integer.parseInt(book.getAttribute("id"));
                        if (id > maxId) maxId = id;
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка формата ID у книги #" + i);
                    }
                }
            }
            
            Element newBook = document.createElement("book");
            newBook.setAttribute("id", String.valueOf(maxId + 1));

            newBook.appendChild(createElementWithText("title", title));
            newBook.appendChild(createElementWithText("author", author));
            newBook.appendChild(createElementWithText("year", year));
            newBook.appendChild(createElementWithText("price", String.valueOf(price)));
            newBook.appendChild(createElementWithText("category", category));
            newBook.appendChild(createElementWithText("amount", String.valueOf(amount)));
            newBook.appendChild(createElementWithText("available", String.valueOf(available)));

            library.appendChild(newBook);
            return saveXML();
            
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении книги: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> searchBooks(String request, int criteriaType) {
        List<Book> result = new ArrayList<>();
        
        try {
            List<Book> allBooks = getAllBooks();
            String r = request.toLowerCase();

            for (Book b : allBooks) {
                try {
                    boolean matches = false;
                    switch (criteriaType) {
                        case 0: 
                            matches = b.getAuthor().toLowerCase().contains(r); 
                            break;
                        case 1: 
                            try {
                                matches = b.getYear() == Integer.parseInt(r); 
                            } catch (NumberFormatException e) {
                                System.err.println("Неверный формат года для поиска: " + r);
                            }
                            break;
                        case 2: 
                            matches = b.getCategory().toLowerCase().contains(r); 
                            break;
                        default:
                            System.err.println("Неизвестный тип критерия поиска: " + criteriaType);
                    }
                    if (matches) result.add(b);
                } catch (Exception e) {
                    System.err.println("Ошибка при проверке книги " + b.getTitle() + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при поиске книг: " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }

    public boolean updateBookPrice(int bookId, double newPrice) {
        if (document == null) {
            System.err.println("XML документ не загружен");
            return false;
        }
        
        try {
            Element bookElem = findBookElementById(bookId);
            if (bookElem == null) {
                System.err.println("Книга с ID " + bookId + " не найдена");
                return false;
            }
            
            Node priceNode;
            try {
                priceNode = (Node) xpath.evaluate("price", bookElem, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                System.err.println("Ошибка XPath при поиске цены: " + e.getMessage());
                return false;
            }
            
            if (priceNode == null) {
                System.err.println("Элемент 'price' не найден у книги ID " + bookId);
                return false;
            }
            
            priceNode.setTextContent(String.valueOf(newPrice));
            return saveXML();
            
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении цены книги ID " + bookId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean issueBook(int bookId) {
        if (document == null) {
            System.err.println("XML документ не загружен");
            return false;
        }
        
        try {
            Element bookElem = findBookElementById(bookId);
            if (bookElem == null) {
                System.err.println("Книга с ID " + bookId + " не найдена");
                return false;
            }
            
            Node availNode;
            try {
                availNode = (Node) xpath.evaluate("available", bookElem, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                System.err.println("Ошибка XPath при поиске доступных книг: " + e.getMessage());
                return false;
            }
            
            if (availNode == null) {
                System.err.println("Элемент 'available' не найден у книги ID " + bookId);
                return false;
            }
            
            int available;
            try {
                available = Integer.parseInt(availNode.getTextContent());
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат количества доступных книг: " + availNode.getTextContent());
                return false;
            }
            
            if (available <= 0) {
                System.err.println("Нет доступных экземпляров книги ID " + bookId);
                return false;
            }
            
            availNode.setTextContent(String.valueOf(available - 1));
            return saveXML();
            
        } catch (Exception e) {
            System.err.println("Ошибка при выдаче книги ID " + bookId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Element findBookElementById(int id) {
        if (document == null || xpath == null) {
            return null;
        }
        
        try {
            String expression = String.format("/library/book[@id='%d']", id);
            Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
            
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) node;
            }
        } catch (XPathExpressionException e) {
            System.err.println("Ошибка XPath при поиске книги ID " + id + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при поиске книги ID " + id + ": " + e.getMessage());
        }
        
        return null;
    }

    private String getTextContentByXPath(Element element, String tagName) {
        if (element == null || xpath == null) {
            return "";
        }
        
        try {
            Node node = (Node) xpath.evaluate(tagName, element, XPathConstants.NODE);
            return node != null ? node.getTextContent().trim() : "";
        } catch (XPathExpressionException e) {
            System.err.println("Ошибка XPath при получении тега '" + tagName + "': " + e.getMessage());
            return "";
        }
    }

    private Element createElementWithText(String tag, String text) {
        if (document == null) {
            return null;
        }
        
        Element elem = document.createElement(tag);
        elem.setTextContent(text);
        return elem;
    }
}