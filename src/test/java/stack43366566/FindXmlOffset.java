/*******************************************************************************
 * Copyright 2017 Jan Schnasse
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
    package stack43366566;
    import java.util.ArrayList;
    import java.util.List;

    import org.antlr.v4.runtime.ANTLRFileStream;
    import org.antlr.v4.runtime.CommonTokenStream;
    import org.antlr.v4.runtime.tree.ParseTreeWalker;

    import stack43366566.gen.XMLLexer;
    import stack43366566.gen.XMLParser;
    import stack43366566.gen.XMLParser.DocumentContext;
    import stack43366566.gen.XMLParserBaseListener;

    public class FindXmlOffset {
    	
    	List<Integer> offsets = new ArrayList<>();
    	String searchForElement="page";
    	
    	public class MyXMLListener extends XMLParserBaseListener {
    		public void enterElement(XMLParser.ElementContext ctx) {
    			String name = ctx.Name().get(0).getText();
    			if (searchForElement.equals(name)) {
    				offsets.add(ctx.start.getStartIndex());
    			}
    		}
    	}

		public List<Integer> createOffsets(String file,String elementName) {
			searchForElement=elementName;
			offsets = new ArrayList<>();
    		try{
    		XMLLexer lexer = new XMLLexer(new ANTLRFileStream(file));
    	    CommonTokenStream tokens = new CommonTokenStream(lexer);
    	    XMLParser parser = new XMLParser(tokens);
    	    DocumentContext ctx=parser.document();
    	    ParseTreeWalker walker = new ParseTreeWalker();
    	    MyXMLListener listener = new MyXMLListener();
    	    walker.walk(listener, ctx);
    		return offsets;
    		} catch (Exception e) {
    			throw new RuntimeException(e);
    		}
		}
		
		public static void main(String[] arg) {
			System.out.println("Search for offsets.");
    		List<Integer> offsets=new FindXmlOffset().createOffsets("/home/jan/dewiki-20170501-pages-articles-multistream.xml","page");
    		System.out.println("myBookOffsets: "+offsets);
    	}

    }
