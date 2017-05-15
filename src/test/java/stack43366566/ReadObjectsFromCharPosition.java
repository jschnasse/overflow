package stack43366566;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ReadObjectsFromCharPosition<T> {
	private Class<T> typeOfT;

	public ReadObjectsFromCharPosition(Class<T> typeParameterClass) {
		this.typeOfT = typeParameterClass;
	}

	private List<T> extractObjects(String filename, List<Integer>offsets) {
		List<T> objects = new ArrayList<>();
		for (Integer offset : offsets) {
			T object = readPage(offset, filename);
			objects.add(object);
		}
		return objects;
	}

	private T readPage(Integer offset, String filename) {
		try (Reader in = new FileReader(filename)) {
			in.skip(offset);
			ObjectMapper mapper = new XmlMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			T object = mapper.readValue(in, typeOfT);
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String objectToString(Object object) {
		try {
			return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(object);
		} catch (Exception e) {
			return "To String failed " + e.getMessage();
		}
	}

	public static void main(String[] args) {
		String filename = "/home/jan/dewiki-20170501-pages-articles-multistream.xml";
		String elementName = "page";

		List<Integer> offsets = new FindXmlOffset().createOffsets(filename, elementName);
		System.out.println("Found "+offsets.size()+" pages");
		System.out.println(objectToString(offsets));
		
		ReadObjectsFromCharPosition<Page> c = new ReadObjectsFromCharPosition<>(Page.class);
		List<Page> objects = c.extractObjects(filename, offsets);
		System.out.println("Found "+objects.size()+" pages");
		System.out.println(objectToString(objects));
	}

}
