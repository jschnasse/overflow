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
package stack42910063;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.event.base.NotifyingRepositoryConnectionWrapper;
import org.eclipse.rdf4j.repository.event.base.RepositoryConnectionListenerAdapter;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.repository.util.RDFInserter;
import org.eclipse.rdf4j.repository.util.RDFLoader;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;

import org.junit.Test;

public class HowToLoadZippedRdfToRdf4jRepository {

	@Test
	public void variant1() {
		Repository repo = new SailRepository(new MemoryStore());
		repo.initialize();
		RDFFormat format = RDFFormat.NTRIPLES;
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("42910063.nt.gz");
		System.out.println("Load zip file of format " + format);
		try (NotifyingRepositoryConnectionWrapper con = new NotifyingRepositoryConnectionWrapper(repo,
						repo.getConnection());) {
			RepositoryConnectionListenerAdapter myListener = new RepositoryConnectionListenerAdapter() {
				private long count = 0;

				@Override
				public void add(RepositoryConnection arg0, Resource arg1, IRI arg2, Value arg3, Resource... arg4) {
					count++;
					if (count % 100000 == 0)
						System.out.println("Add statement number " + count + "\n" + arg1 + " " + arg2 + " " + arg3);
				}
			};
			con.addRepositoryConnectionListener(myListener);
			con.add(in, "", format);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void variant2() {
		Repository repo = new SailRepository(new MemoryStore());
		repo.initialize();
		RDFFormat format = RDFFormat.NTRIPLES;
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("42910063.nt.gz");
		System.out.println("Load zip file of format " + format);
		try (RepositoryConnection con = repo.getConnection()) {
			MyRdfInserter inserter = new MyRdfInserter(con);
			RDFLoader loader = new RDFLoader(con.getParserConfig(), con.getValueFactory());
			loader.load(in, "", RDFFormat.NTRIPLES, inserter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void variant3() throws MalformedURLException, IOException {
		Repository repo = new SailRepository(new MemoryStore());
		repo.initialize();
		IRI context = repo.getValueFactory().createIRI("info/mycontext:context1");
		RDFFormat format = RDFFormat.NTRIPLES;
		System.out.println("Load zip file of format " + format);
		try (InputStream in = new URL(
						"https://tools.wmflabs.org/wikidata-exports/rdf/exports/20160801/wikidata-terms.nt.gz")
										.openConnection().getInputStream();
						NotifyingRepositoryConnectionWrapper con = new NotifyingRepositoryConnectionWrapper(repo,
										repo.getConnection());) {
			RepositoryConnectionListenerAdapter myListener = new RepositoryConnectionListenerAdapter() {
				private long count = 0;

				@Override
				public void add(RepositoryConnection arg0, Resource arg1, IRI arg2, Value arg3, Resource... arg4) {
					count++;
					if (count % 100000 == 0)
						System.out.println("Add statement number " + count + "\n" + arg1 + " " + arg2 + " " + arg3);
				}
			};
			con.addRepositoryConnectionListener(myListener);
			con.add(in, "", format, context);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	class MyRdfInserter extends AbstractRDFHandler {
		RDFInserter rdfInserter;
		int count = 0;

		public MyRdfInserter(RepositoryConnection con) {
			rdfInserter = new RDFInserter(con);
		}

		@Override
		public void handleStatement(Statement st) {
			count++;
			if (count % 100000 == 0)
				System.out.println("Add statement number " + count + "\n" + st.getSubject().stringValue() + " "
								+ st.getPredicate().stringValue() + " " + st.getObject().stringValue());
			rdfInserter.handleStatement(st);
		}
	}

}
