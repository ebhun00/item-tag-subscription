package com.safeway.titan.dug.service;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.safeway.titan.dug.domain.ItemDestMap;


@Component
public class StoreLayoutXmlPreparator {

	@Autowired
	private Environment env;
	

	public String generateStoreLayout(List<ItemDestMap> itemsWithMapping, String storeNum, List<String> csvItems) {
		String storeLayoutXml = null;
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("tXML");
			doc.appendChild(rootElement);

			createHeader(doc, rootElement);
			createMessage(doc, rootElement, storeNum, itemsWithMapping,csvItems);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileWriter(env.getProperty("tag_sub.storeLayoutFiles") + storeNum + "-storeLayout.xml"));
			
			StringWriter writer = new StringWriter();
		    StreamResult resultXml = new StreamResult(writer);
		    
			transformer.transform(source, result);
			transformer.transform(source, resultXml);
			storeLayoutXml = writer.toString();

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return storeLayoutXml;
	}

	private void createHeader(Document doc, Element rootElement) {
		String[] headerElements = { "Source", "Action_Type", "Message_Type", "Company_ID" };
		Element staff = doc.createElement("Header");
		rootElement.appendChild(staff);

		for (String tag : headerElements) {
			Element element = doc.createElement(tag);
			element.appendChild(doc.createTextNode(env.getProperty(tag)));
			staff.appendChild(element);
		}

	}

	private void createMessage(Document doc, Element rootElement, String storeNum, List<ItemDestMap> itemsWithMapping, List<String> csvItems) {

		String[] msgElements = { "BusinessUnit", "FacilityAliasId", "CategoryType" };
		Element msg = doc.createElement("Message");
		rootElement.appendChild(msg);

		Element storeLayout = doc.createElement("StoreLayout");
		msg.appendChild(storeLayout);

		for (String tag : msgElements) {
			Element element = doc.createElement(tag);
			element.appendChild(doc.createTextNode(tag.equals("FacilityAliasId") ? storeNum : env.getProperty(tag)));
			storeLayout.appendChild(element);
		}
		updateXmlWithTagLocations(doc, storeLayout, itemsWithMapping, storeNum,csvItems);

	}

	private void updateXmlWithTagLocations(Document doc, Element storeLayoutElement, List<ItemDestMap> itemsWithMapping,
			String storeNum, List<String> csvItems) {

		String prevLocation = null;
		Element prevSSection = null;
		int counter = 0;
		for (ItemDestMap itemDestMap : itemsWithMapping) {
			csvItems.add(itemDestMap.getSkuBrcd());
			if (itemDestMap.getDspLocn().equals(prevLocation)) {

				Element category = doc.createElement("Category");
				prevSSection.appendChild(category);

				Element extCategoryCode = doc.createElement("ExtCategoryCode");
				extCategoryCode.appendChild(doc.createTextNode(itemDestMap.getSkuBrcd()));
				category.appendChild(extCategoryCode);
			} else {
				prevLocation = itemDestMap.getDspLocn();

				Element storeSection = doc.createElement("StoreSection");
				storeLayoutElement.appendChild(storeSection);

				prevSSection = storeSection;
				Element sectionName1 = doc.createElement("SectionName1");
				sectionName1.appendChild(doc.createTextNode(itemDestMap.getDspLocn()));
				storeSection.appendChild(sectionName1);

				Element sectionSequence = doc.createElement("SectionSequence");
				sectionSequence.appendChild(doc.createTextNode(String.valueOf(++counter)));
				storeSection.appendChild(sectionSequence);

				Element category = doc.createElement("Category");
				storeSection.appendChild(category);

				Element extCategoryCode = doc.createElement("ExtCategoryCode");
				extCategoryCode.appendChild(doc.createTextNode(itemDestMap.getSkuBrcd()));
				category.appendChild(extCategoryCode);
			}

		}
	}
}
