package core;

import java.net.URI;

public class ResourceURL {

	public static URI getResourceURL(String s) throws Exception{
		String str = ResourceURL.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		return new URI(str.substring(1, str.length()) + s);
	}
}
