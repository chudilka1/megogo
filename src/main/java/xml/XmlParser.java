package xml;

import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;

import static io.restassured.RestAssured.get;

public class XmlParser {

    /**Get XML as string passing url as parameter*/
    public static String getXmlString(String url) {
        return get(url).asString();
    }

    /**Set xmlPath passing XML String response as parameter
     * returned is XML path with disabled External Dtd that
     * allows getting values bypassing DOCTYPE line*/
    public static XmlPath setXmlPath(String xmlResponse) {
        return new XmlPath(xmlResponse).using(XmlPathConfig.xmlPathConfig().with().disableLoadingOfExternalDtd());
    }

    /**Set xmlPath passing XML String response and root path as parameters
     * to access only the required XML object among others with the same keys
     * returned is XML path with disabled External Dtd that
     * allows getting values bypassing DOCTYPE line*/
    public static XmlPath setXmlPath(String xmlResponse, String rootPath) {
        return new XmlPath(xmlResponse).using(XmlPathConfig.xmlPathConfig().with().disableLoadingOfExternalDtd()).setRoot(rootPath);
    }

}
