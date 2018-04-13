/*******************************************************************************
 * Copyright 2018 Jan Schnasse
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

package stack724043;

import java.net.IDN;
import java.net.URI;
import java.net.URL;
/**
 * 
 * @author Jan Schnasse
 *
 */
public class URLUtil {
	public static String encode(String url) {
		try {
			URL u = new URL(url);
			URI uri = new URI(u.getProtocol(), u.getUserInfo(), IDN.toASCII(u.getHost()), u.getPort(), u.getPath(),
							u.getQuery(), u.getRef());
			String correctEncodedURL = uri.toASCIIString();
			return correctEncodedURL;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decode(String url) {
		return new String(decode(url.getBytes()));
	}

	/** Copy
	 * https://android.googlesource.com/platform/frameworks/base/+/a5408e6/core/java/android/webkit/URLUtil.java
	 * Copyright (C) 2006 The Android Open Source Project
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License"); you may
	 * not use this file except in compliance with the License. You may obtain a
	 * copy of the License at
	 *
	 * http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
	 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
	 * License for the specific language governing permissions and limitations
	 * under the License.
	 */
	public static byte[] decode(byte[] url) throws IllegalArgumentException {
		if (url.length == 0) {
			return new byte[0];
		}
		// Create a new byte array with the same length to ensure capacity
		byte[] tempData = new byte[url.length];
		int tempCount = 0;
		for (int i = 0; i < url.length; i++) {
			byte b = url[i];
			if (b == '%') {
				if (url.length - i > 2) {
					b = (byte) (parseHex(url[i + 1]) * 16 + parseHex(url[i + 2]));
					i += 2;
				} else {
					throw new IllegalArgumentException("Invalid format");
				}
			}
			tempData[tempCount++] = b;
		}
		byte[] retData = new byte[tempCount];
		System.arraycopy(tempData, 0, retData, 0, tempCount);
		return retData;
	}

	/** Copy
	 * https://android.googlesource.com/platform/frameworks/base/+/a5408e6/core/java/android/webkit/URLUtil.java
	 * * Copyright (C) 2006 The Android Open Source Project
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License"); you may
	 * not use this file except in compliance with the License. You may obtain a
	 * copy of the License at
	 *
	 * http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
	 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
	 * License for the specific language governing permissions and limitations
	 * under the License.
	 */
	private static int parseHex(byte b) {
		if (b >= '0' && b <= '9')
			return (b - '0');
		if (b >= 'A' && b <= 'F')
			return (b - 'A' + 10);
		if (b >= 'a' && b <= 'f')
			return (b - 'a' + 10);
		throw new IllegalArgumentException("Invalid hex char '" + b + "'");
	}
}
