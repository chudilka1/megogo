import io.restassured.path.xml.XmlPath;
import json.JsonParser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.UtilMethods;
import xml.XmlParser;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TEST {

    private String jsonResponse,
            formattedStartJson;

    private XmlPath xmlPath;

    @BeforeTest
    public void setUp() throws ParseException {

        //Get Json and XML
        jsonResponse = JsonParser.getJsonString("http://epg.megogo.net/channel/now?external_id=295");
        String xmlResponse = XmlParser.getXmlString("http://www.vsetv.com/export/megogo/epg/3.xml");

        //Convert date and time from Json to find corresponding object in XML response
        String startJson = JsonParser.getJsonValue(jsonResponse, "data.programs.start");
        formattedStartJson = UtilMethods.dateConverter(startJson);

        //Set XML root to assert only values of one XML object during tests
        String xmlRootPath = "tv.programme.find {it.@start == '" + formattedStartJson + "'}";
        xmlPath = XmlParser.setXmlPath(xmlResponse, xmlRootPath);
    }

    @Test
    public void startStopTimeTest() throws Exception {

        String endJson = JsonParser.getJsonValue(jsonResponse, "data.programs.end");
        String formattedEndJson = UtilMethods.dateConverter(endJson);

        String startXML = xmlPath.getString("@start");
        String stopTimeXML = xmlPath.getString("@stop");

        assertThat(formattedStartJson, equalTo(startXML));
        assertThat(formattedEndJson, equalTo(stopTimeXML));

    }

    @Test (dependsOnMethods = "startStopTimeTest")
    public void yearTitleTest() {

        String titleJson = JsonParser.getJsonValue(jsonResponse, "data.programs.title");
        String yearJson = JsonParser.getJsonValue(jsonResponse, "data.programs.year");

        String titleXML = xmlPath.getString("title");
        String productionYearXML = xmlPath.getString("production_year");

        assertThat(titleJson, equalTo(titleXML));
        assertThat(yearJson, equalTo(productionYearXML));
    }

    @Test (dependsOnMethods = "yearTitleTest")
    public void genreTest() {

        String genreJson = JsonParser.getJsonValue(jsonResponse, "data.programs.genre.title");
        String genreXML = xmlPath.getString("genre");

        assertThat(genreJson, equalTo(genreXML));

    }
}
