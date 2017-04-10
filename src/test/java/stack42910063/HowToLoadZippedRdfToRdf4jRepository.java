package stack42910063;

import java.io.InputStream;
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
