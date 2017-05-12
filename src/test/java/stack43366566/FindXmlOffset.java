    package stack43366566;
    import java.io.InputStream;
    import java.util.ArrayList;
    import java.util.List;

    import org.antlr.v4.runtime.ANTLRInputStream;
    import org.antlr.v4.runtime.CommonTokenStream;
    import org.antlr.v4.runtime.tree.ParseTreeWalker;

    import stack43366566.gen.XMLLexer;
    import stack43366566.gen.XMLParser;
    import stack43366566.gen.XMLParser.DocumentContext;
    import stack43366566.gen.XMLParserBaseListener;

    public class FindXmlOffset {
    	
    	public static List<Integer> offsets = new ArrayList<>();
    	public static String searchForElement="book";
    	
    	public static class MyXMLListener extends XMLParserBaseListener {
    		public void enterElement(XMLParser.ElementContext ctx) {
    			String name = ctx.Name().get(0).getText();
    			if (searchForElement.equals(name)) {
    				FindXmlOffset.offsets.add(ctx.start.getStartIndex());
    			}
    		}
    	}

    	public static void main(String[] arg) {
    		System.out.println("Search for Book offsets.");
    		try( InputStream in=
    			Thread.currentThread().getContextClassLoader().getResourceAsStream("books.xml")){
    		XMLLexer lexer = new XMLLexer(new ANTLRInputStream(in));
    	    // Get a list of matched tokens
    	    CommonTokenStream tokens = new CommonTokenStream(lexer);
    	    // Pass the tokens to the parser
    	    XMLParser parser = new XMLParser(tokens);
    	    // Specify our entry point
    	    DocumentContext ctx=parser.document();
    	    // Walk it and attach our listener
    	    ParseTreeWalker walker = new ParseTreeWalker();
    	    MyXMLListener listener = new MyXMLListener();
    	    walker.walk(listener, ctx);
    		System.out.println("myBookOffsets: "+offsets);
    		} catch (Exception e) {
    			throw new RuntimeException(e);
    		}
    	}
    }
