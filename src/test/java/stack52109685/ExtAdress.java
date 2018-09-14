package stack52109685;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtAdress extends Adress {
	public String type;

	public ExtAdress() {
	}
}
