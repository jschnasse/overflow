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
package stack47220687;

import java.io.FileReader;
import java.util.Map;

import org.junit.Test;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

public class HowToReadACSVFile {

	private static final CsvPreference COLON_DELIMITED = new CsvPreference.Builder('"', ':', "\n").build();

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // gamename
						new NotNull(), // gamescore
						new NotNull(), // minutestoplay
		};
		return processors;
	}

	@Test
	public void read() throws Exception {
		ICsvMapReader mapReader = null;
		try {
			mapReader = new CsvMapReader(new FileReader(
							Thread.currentThread().getContextClassLoader().getResource("stack47220687.txt").getPath()),
							COLON_DELIMITED);

			// the header columns are used as the keys to the Map
			final String[] header = mapReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			Map<String, Object> oneRecordInAMap;
			while ((oneRecordInAMap = mapReader.read(header, processors)) != null) {
				System.out.println(String.format("lineNo=%s, rowNo=%s, this line stored in a map=%s",
								mapReader.getLineNumber(), mapReader.getRowNumber(), oneRecordInAMap));
				/**
				 * oneRecordInAMap.get("Gamescore");
				 */

			}

		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}
	}

}
