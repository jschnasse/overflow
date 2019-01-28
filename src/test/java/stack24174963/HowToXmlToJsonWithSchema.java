/*******************************************************************************
 * Copyright 2019 Jan Schnasse
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
package stack24174963;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;


import stack24174963.datacite.Resource;

public class HowToXmlToJsonWithSchema {
	/**
	 * /opt/java/bin/xjc -d ~/development/overflow/src/test/java -p stack24174963.datacite https://schema.datacite.org/meta/kernel-4.1/metadata.xsd
	 */
	@Test
	public void readXmlAndConvertToSchemaWithXjc() throws Exception {
		String example = "schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1.xml";
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(example)) {
			Resource resource = JAXB.unmarshal(in, Resource.class);
			System.out.println(asJson(resource));
		}
	}
	 /** @formatter:off    
    
    {
  	  "identifier" : {
  	    "value" : "10.5072/testpub",
  	    "identifierType" : "DOI"
  	  },
  	  "creators" : {
  	    "creator" : [ {
  	      "creatorName" : {
  	        "value" : "Smith, John",
  	        "nameType" : "PERSONAL"
  	      },
  	      "givenName" : "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n<givenName xmlns=\"http://datacite.org/schema/kernel-4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">John</givenName>",
  	      "familyName" : "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n<familyName xmlns=\"http://datacite.org/schema/kernel-4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">Smith</familyName>",
  	      "nameIdentifier" : [ ],
  	      "affiliation" : [ ]
  	    }, {
  	      "creatorName" : {
  	        "value" : "つまらないものですが",
  	        "nameType" : null
  	      },
  	      "givenName" : null,
  	      "familyName" : null,
  	      "nameIdentifier" : [ {
  	        "value" : "0000000134596520",
  	        "nameIdentifierScheme" : "ISNI",
  	        "schemeURI" : "http://isni.org/isni/"
  	      } ],
  	      "affiliation" : [ ]
  	    } ]
  	  },
  	  "titles" : {
  	    "title" : [ {
  	      "value" : "Właściwości rzutowań podprzestrzeniowych",
  	      "titleType" : null,
  	      "lang" : "pl"
  	    }, {
  	      "value" : "Translation of Polish titles",
  	      "titleType" : "TRANSLATED_TITLE",
  	      "lang" : "en"
  	    } ]
  	  },
  	  "publisher" : "Springer",
  	  "publicationYear" : "2010",
  	  "resourceType" : {
  	    "value" : "Monograph",
  	    "resourceTypeGeneral" : "TEXT"
  	  },
  	  "subjects" : {
  	    "subject" : [ {
  	      "value" : "830 German & related literatures",
  	      "subjectScheme" : "DDC",
  	      "schemeURI" : null,
  	      "valueURI" : null,
  	      "lang" : "en"
  	    }, {
  	      "value" : "Polish Literature",
  	      "subjectScheme" : null,
  	      "schemeURI" : null,
  	      "valueURI" : null,
  	      "lang" : "en"
  	    } ]
  	  },
  	  "contributors" : {
  	    "contributor" : [ {
  	      "contributorName" : {
  	        "value" : "Doe, John",
  	        "nameType" : "PERSONAL"
  	      },
  	      "givenName" : "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n<givenName xmlns=\"http://datacite.org/schema/kernel-4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">John</givenName>",
  	      "familyName" : "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n<familyName xmlns=\"http://datacite.org/schema/kernel-4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">Doe</familyName>",
  	      "nameIdentifier" : [ {
  	        "value" : "0000-0001-5393-1421",
  	        "nameIdentifierScheme" : "ORCID",
  	        "schemeURI" : "http://orcid.org/"
  	      } ],
  	      "affiliation" : [ ],
  	      "contributorType" : "DATA_COLLECTOR"
  	    } ]
  	  },
  	  "dates" : null,
  	  "language" : "de",
  	  "alternateIdentifiers" : {
  	    "alternateIdentifier" : [ {
  	      "value" : "937-0-4523-12357-6",
  	      "alternateIdentifierType" : "ISBN"
  	    } ]
  	  },
  	  "relatedIdentifiers" : {
  	    "relatedIdentifier" : [ {
  	      "value" : "10.5272/oldertestpub",
  	      "resourceTypeGeneral" : null,
  	      "relatedIdentifierType" : "DOI",
  	      "relationType" : "IS_PART_OF",
  	      "relatedMetadataScheme" : null,
  	      "schemeURI" : null,
  	      "schemeType" : null
  	    } ]
  	  },
  	  "sizes" : {
  	    "size" : [ "256 pages" ]
  	  },
  	  "formats" : {
  	    "format" : [ "pdf" ]
  	  },
  	  "version" : "2",
  	  "rightsList" : {
  	    "rights" : [ {
  	      "value" : "Creative Commons Attribution-NoDerivs 2.0 Generic",
  	      "rightsURI" : "http://creativecommons.org/licenses/by-nd/2.0/",
  	      "lang" : null
  	    } ]
  	  },
  	  "descriptions" : {
  	    "description" : [ {
  	      "content" : [ "\n      Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea\n      takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores\n      et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.\n    " ],
  	      "descriptionType" : "ABSTRACT",
  	      "lang" : "la"
  	    } ]
  	  },
  	  "geoLocations" : null,
  	  "fundingReferences" : null
  	}
**/
	private String asJson(Object obj) throws Exception {
		StringWriter w = new StringWriter();
		new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
		String result = w.toString();
		return result;
	}

	@Test
	public void xmlToJsonWithJsonOrg() throws IOException {
		String example = "schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1.xml";
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(example)) {
			String xml = CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
			JSONObject xmlJSONObj = XML.toJSONObject(xml);
			String jsonPrettyPrintString = xmlJSONObj.toString(4);
			System.out.println(jsonPrettyPrintString);
		}
	}
	/** @formatter:off
            	 * {"resource": {
          "identifier": {
              "identifierType": "DOI",
              "content": "10.5072/testpub"
          },
          "formats": {"format": "pdf"},
          "rightsList": {"rights": {
              "rightsURI": "http://creativecommons.org/licenses/by-nd/2.0/",
              "content": "Creative Commons Attribution-NoDerivs 2.0 Generic"
          }},
          "creators": {"creator": [
              {
                  "givenName": "John",
                  "familyName": "Smith",
                  "creatorName": {
                      "nameType": "Personal",
                      "content": "Smith, John"
                  }
              },
              {
                  "creatorName": "つまらないものですが",
                  "nameIdentifier": {
                      "nameIdentifierScheme": "ISNI",
                      "schemeURI": "http://isni.org/isni/",
                      "content": "0000000134596520"
                  }
              }
          ]},
          "subjects": {"subject": [
              {
                  "xml:lang": "en",
                  "content": "830 German & related literatures",
                  "subjectScheme": "DDC"
              },
              {
                  "xml:lang": "en",
                  "content": "Polish Literature"
              }
          ]},
          "xsi:schemaLocation": "http://datacite.org/schema/kernel-4 http://schema.datacite.org/meta/kernel-4.1/metadata.xsd",
          "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
          "language": "de",
          "titles": {"title": [
              {
                  "xml:lang": "pl",
                  "content": "Właściwości rzutowań podprzestrzeniowych"
              },
              {
                  "titleType": "TranslatedTitle",
                  "xml:lang": "en",
                  "content": "Translation of Polish titles"
              }
          ]},
          "relatedIdentifiers": {"relatedIdentifier": {
              "relationType": "IsPartOf",
              "relatedIdentifierType": "DOI",
              "content": "10.5272/oldertestpub"
          }},
          "version": 2,
          "descriptions": {"description": {
              "xml:lang": "la",
              "descriptionType": "Abstract",
              "content": "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea\n      takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores\n      et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
          }},
          "xmlns": "http://datacite.org/schema/kernel-4",
          "alternateIdentifiers": {"alternateIdentifier": {
              "content": "937-0-4523-12357-6",
              "alternateIdentifierType": "ISBN"
          }},
          "sizes": {"size": "256 pages"},
          "publisher": "Springer",
          "publicationYear": 2010,
          "contributors": {"contributor": {
              "givenName": "John",
              "familyName": "Doe",
              "contributorType": "DataCollector",
              "contributorName": {
                  "nameType": "Personal",
                  "content": "Doe, John"
              },
              "nameIdentifier": {
                  "nameIdentifierScheme": "ORCID",
                  "schemeURI": "http://orcid.org/",
                  "content": "0000-0001-5393-1421"
              }
          }},
          "resourceType": {
              "resourceTypeGeneral": "Text",
              "content": "Monograph"
          }
      }}

            	 */
            }
       	 
       
       