package stack52109685;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ExtAdress")
public class ExtAdress extends Adress {
	public String type;

	public ExtAdress() {
	}
}
