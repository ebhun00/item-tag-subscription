import os
import csv
import xml.dom.minidom as xml
import cx_Oracle
import xml.etree.cElementTree as ET

def create_category_xml(itemsList):
    categoryRoot = ET.Element("tXML")
    cHeader = ET.SubElement(categoryRoot, "Header")
    ET.SubElement(cHeader, "Source").text = "Host"
    ET.SubElement(cHeader, "Action_Type").text = "UPDATE"
    ET.SubElement(cHeader, "Message_Type").text = "Category Data"
    ET.SubElement(cHeader, "Company_ID").text = "70"
    
    cMessage = ET.SubElement(categoryRoot, "Message")
    for item in itemsList:
        cCategory = ET.SubElement(cMessage, "Category");
        ET.SubElement(cCategory, "BusinessUnit").text = "70"
        ET.SubElement(cCategory, "ExtCategoryCode").text = item
        ET.SubElement(cCategory, "CategoryName").text = item
        ET.SubElement(cCategory, "CategoryType").text = "L1"
        ET.SubElement(cCategory, "MarkForDeletion").text = "False"
        
    tree = ET.ElementTree(categoryRoot)
    tree.write(storeNumber+"_Category.xml")
        

def make_slayout(filepath):
    dataset = open(filepath, 'r')
    myreader = csv.reader(dataset)

    root = ET.Element("tXML")
    seqCount = 1;
    header = ET.SubElement(root, "Header")
    ET.SubElement(header, "Source").text = "Host"
    ET.SubElement(header, "Action_Type").text = "Update"
    ET.SubElement(header, "Message_Type").text = "Store Layout"
    ET.SubElement(header, "Company_ID").text = "70"
    
    message = ET.SubElement(root, "Message")
    storeLayout = ET.SubElement(message, "StoreLayout")
    ET.SubElement(storeLayout, "BusinessUnit").text = '70'
    ET.SubElement(storeLayout, "FacilityAliasId").text = '1502'
    ET.SubElement(storeLayout, "CategoryType").text = "L1"
    items = [];
    next(myreader, None) # Skip header
    for row in myreader:
        items.append(row[3])
        storeSection = ET.SubElement(storeLayout, "StoreSection")
        ET.SubElement(storeSection, "SectionName1").text = row[1]
        ET.SubElement(storeSection, "SectionSequence").text = str(seqCount)
        category = ET.SubElement(storeSection, "Category")
        ET.SubElement(category, "ExtCategoryCode").text = row[3]
        seqCount += 1;
    
    print('items for store : ' + str(storeNumber));
    print((list(set(items) - set(categoryList))));
    print('\n')
    missingCategoryItems = list(set(items) - set(categoryList));
    if len(missingCategoryItems) > 0:
        create_category_xml(missingCategoryItems);
        
    tree = ET.ElementTree(root)
    tree.write(storeNumber+".xml")
    
con = cx_Oracle.connect('osflca/osfl2ca@ecom-rac-qa:20001/OSFLQA')
print(con.version)

cursor = con.cursor();
cursor.execute('select EXT_CATEGORY_CODE from CATEGORY');
categoryList =[];
for row in cursor:
    categoryList.append(row[0]);

storeNumber = 0;
directory ="datain/";
for filename in os.listdir(directory):
    storeNumber = filename.split(".")[0];
    if filename.endswith(".csv") or filename.endswith(".py"): 
        filepath = os.path.join(directory, filename);
        make_slayout(filepath);
        continue
    else:
        continue
    

   # xmlString = xml.dom.minidom.parse("filename1.xml") 
    #pretty_xml_as_string = xml.toprettyxml()
    
    
