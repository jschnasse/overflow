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
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import sun.nio.cs.ThreadLocalCoders;

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
		try {
			URL u = new URL(url);
			String protocol=u.getProtocol();
			String userInfo=u.getUserInfo();
			String host = u.getHost()!=null?IDN.toUnicode(u.getHost()):null;
			int port = u.getPort();
			String path = u.getPath() !=null?URLDecoder.decode(u.getPath(),StandardCharsets.UTF_8.name()):null;
			String ref = u.getRef();
			String query= u.getQuery() !=null?URLDecoder.decode(u.getQuery(),StandardCharsets.UTF_8.name()):null;
			
			protocol = protocol!=null?protocol+"://":"";
			userInfo=userInfo!=null?userInfo:"";
			host=host!=null?host:"";
			String portStr = port!=-1?":"+port:"";
			path=path!=null?path:"";
			query = query!=null?"?"+query:"";
			ref=ref!=null?"#"+ref:"";
	
			
			return String.format("%s%s%s%s%s%s%s", protocol,userInfo,host,portStr,path,ref,query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	

	  private static int decode(char c) {
	        if ((c >= '0') && (c <= '9'))
	            return c - '0';
	        if ((c >= 'a') && (c <= 'f'))
	            return c - 'a' + 10;
	        if ((c >= 'A') && (c <= 'F'))
	            return c - 'A' + 10;
	        assert false;
	        return -1;
	    }

	    private static byte decode(char c1, char c2) {
	        return (byte)(  ((decode(c1) & 0xf) << 4)
	                      | ((decode(c2) & 0xf) << 0));
	    }

	    // Evaluates all escapes in s, applying UTF-8 decoding if needed.  Assumes
	    // that escapes are well-formed syntactically, i.e., of the form %XX.  If a
	    // sequence of escaped octets is not valid UTF-8 then the erroneous octets
	    // are replaced with '\uFFFD'.
	    // Exception: any "%" found between "[]" is left alone. It is an IPv6 literal
	    //            with a scope_id
	    //
	    private static String decodeQuery(String s) {
	        if (s == null)
	            return s;
	        int n = s.length();
	        if (n == 0)
	            return s;
	        if (s.indexOf('%') < 0)
	            return s;

	        StringBuffer sb = new StringBuffer(n);
	        ByteBuffer bb = ByteBuffer.allocate(n);
	        CharBuffer cb = CharBuffer.allocate(n);
	        CharsetDecoder dec = ThreadLocalCoders.decoderFor("UTF-8")
	            .onMalformedInput(CodingErrorAction.REPLACE)
	            .onUnmappableCharacter(CodingErrorAction.REPLACE);

	        // This is not horribly efficient, but it will do for now
	        char c = s.charAt(0);
	        boolean betweenBrackets = false;

	        for (int i = 0; i < n;) {
	            assert c == s.charAt(i);    // Loop invariant
	            if (c == '[') {
	                betweenBrackets = true;
	            } else if (betweenBrackets && c == ']') {
	                betweenBrackets = false;
	            }
	            if (c != '%' || betweenBrackets) {
	                sb.append(c);
	                if (++i >= n)
	                    break;
	                c = s.charAt(i);
	                continue;
	            }
	            bb.clear();
	            int ui = i;
	            for (;;) {
	                assert (n - i >= 2);
	                bb.put(decode(s.charAt(++i), s.charAt(++i)));
	                if (++i >= n)
	                    break;
	                c = s.charAt(i);
	                if (c != '%')
	                    break;
	            }
	            bb.flip();
	            cb.clear();
	            dec.reset();
	            CoderResult cr = dec.decode(bb, cb, true);
	            assert cr.isUnderflow();
	            cr = dec.flush(cb);
	            assert cr.isUnderflow();
	            sb.append(cb.flip().toString());
	        }

	        return sb.toString();
	    }
}
