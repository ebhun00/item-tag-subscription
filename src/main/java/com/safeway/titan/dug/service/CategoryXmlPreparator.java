package com.safeway.titan.dug.service;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


@Component
@ConfigurationProperties("tag_sub")
public class CategoryXmlPreparator {

	@Autowired
	private Environment env;

	public String generateCategoryXml(Set<String> items, String storeNum) {
		String categoryXml = null;
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("tXML");
			doc.appendChild(rootElement);

			createHeader(doc, rootElement);
			createMessage(doc, rootElement, items);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileWriter(env.getProperty("tag-sub.categoryFiles") + storeNum + "-category.xml"));
			StringWriter writer = new StringWriter();
			StreamResult resultXml = new StreamResult(writer);
			transformer.transform(source, result);
			transformer.transform(source, resultXml);
			categoryXml = writer.toString();

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return categoryXml;
	}

	private void createHeader(Document doc, Element rootElement) {
		String[] headerElements = { "Source", "Action_Type", "Message_Type", "Company_ID" };
		Element staff = doc.createElement("Header");
		rootElement.appendChild(staff);

		for (String tag : headerElements) {
			Element element = doc.createElement(tag);
			element.appendChild(doc.createTextNode(tag.equals("Message_Type") ? "Category Data" : env.getProperty(tag)));
			staff.appendChild(element);
		}

	}

	private void createMessage(Document doc, Element rootElement, Set<String> items) {

		String[] msgElements = { "BusinessUnit", "ExtCategoryCode", "CategoryName", "CategoryType", "MarkForDeletion" };
		Element msg = doc.createElement("Message");
		rootElement.appendChild(msg);

		Element category = doc.createElement("Category");
		msg.appendChild(category);

		for (String item : items) {
			Element businessUnit = doc.createElement(msgElements[0]);
			businessUnit.appendChild(doc.createTextNode(env.getProperty(msgElements[0])));
			category.appendChild(businessUnit);

			Element extCategoryCode = doc.createElement(msgElements[1]);
			extCategoryCode.appendChild(doc.createTextNode(item));
			category.appendChild(extCategoryCode);

			Element categoryName = doc.createElement(msgElements[2]);
			categoryName.appendChild(doc.createTextNode(item));
			category.appendChild(categoryName);

			Element categoryType = doc.createElement(msgElements[3]);
			categoryType.appendChild(doc.createTextNode("L1"));
			category.appendChild(categoryType);

			Element markForDeletion = doc.createElement(msgElements[4]);
			markForDeletion.appendChild(doc.createTextNode("False"));
			category.appendChild(markForDeletion);

		}
	}

}
