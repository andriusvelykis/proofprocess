package org.ai4fm.proofprocess.isabelle.parse;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import scala.Tuple2;
import scala.collection.JavaConversions.JListWrapper;

import isabelle.Markup;
import isabelle.XML;
import isabelle.XML$Elem$;
import isabelle.XML$Text$;
import isabelle.XML.Elem;
import isabelle.XML.Text;
import isabelle.XML.Tree;

/**
 * @author Andrius Velykis
 */
public class YXmlTermParser {

	public static String convertToYXml(Tree term) {
		// cannot use YXML, because its control characters are not representable in XML 1.0
		// and Java XML 1.1 parsers have bugs when long attributes are encountered 
//		return YXML.string_of_tree(term);
		
		return XML.string_of_tree(term);
	}
	
	public static Tree parseYXml(String yxml) {
//		return YXML.parse_failsafe(yxml);
		
		try {
			return parseXml(yxml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static Tree parseXml(String xml) throws ParserConfigurationException, SAXException,
			IOException {
		
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
		
		InputSource source = new InputSource(new StringReader(xml));
		
		IsaXmlHandler handler = new IsaXmlHandler();
		parser.parse(source, handler);
		
		return handler.root;
	}
	
	private static class IsaXmlHandler extends DefaultHandler2 {

		private final Stack<IsaElemData> elemStack = new Stack<IsaElemData>();
		private Elem root;
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			
			List<Tuple2<String, String>> isaAttrs = new ArrayList<Tuple2<String,String>>();
			
			for (int i = 0; i < attributes.getLength(); i++) {
				String name = attributes.getQName(i);
				String value = attributes.getValue(i);
				isaAttrs.add(new Tuple2<String, String>(name, value));
			}
			
			Markup isaMarkup = new Markup(qName, toScalaList(isaAttrs));
			elemStack.add(new IsaElemData(isaMarkup));
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			
			IsaElemData elemData = elemStack.pop();
			Elem elem = XML$Elem$.MODULE$.apply(elemData.markup, toScalaList(elemData.children));
			
			// add to the parent
			if (elemStack.isEmpty()) {
				root = elem;
			} else {
				elemStack.peek().children.add(elem);
			}
		}
		
		private static <E> scala.collection.immutable.List<E> toScalaList(List<E> list) {
			return new JListWrapper<E>(list).toList();
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			
			StringBuilder text = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				text.append(ch[start + i]);
			}
			
			Text textElem = XML$Text$.MODULE$.apply(text.toString());
			elemStack.peek().children.add(textElem);
		}
	}
	
	private static class IsaElemData {
		private Markup markup = null;
		private List<Tree> children = new ArrayList<Tree>();
		
		public IsaElemData(Markup markup) {
			this.markup = markup;
		}
	}
	
}
