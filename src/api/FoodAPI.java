package api;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FoodAPI {

	public String[][] searchFood (String foodName) {
		String[][] foodInfo = null;
        String foodname = "", kcal = "";
        
        try {
            // XML 데이터를 읽어옴
            URL url = new URL("http://openapi.foodsafetykorea.go.kr/api/f1ed8bc533ae4efeb89b/I2790/xml/1/14/DESC_KOR="+URLEncoder.encode(foodName,"UTF-8"));
            // 탐색
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept-language", "ko");
            InputStream in = url.openStream();
            // XML 데이터 가져오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(in);

            Element el = doc.getDocumentElement();
            // <item> ~ </item>을 하나의 노드로 노드 리스트를 만듬
            NodeList itemList = el.getElementsByTagName("row");
            
        	foodInfo = new String[itemList.getLength()][2];
        	
            for(int i = 0 ; i < itemList.getLength() ; i++) {
                // <item> ~ </item> 노드를 하나씩 읽어옴
                Node itemNode = itemList.item(i);
                // <item> ~ </item> 사이의 태그들로 노드 리스트를 만듬
                NodeList subList = itemNode.getChildNodes();
                
                // <item> ~ </item> 사이의 태그를 하나씩 읽어와 해당 태그와 일치할 경우 변수에 저장
                for(int j = 0 ; j < subList.getLength() ; j++) {
                    Node subNode = subList.item(j);
                    if(subNode.getNodeName().equals("DESC_KOR"))
                    	foodname = subNode.getTextContent();
                    if(subNode.getNodeName().equals("NUTR_CONT1"))
                    	kcal = subNode.getTextContent();
                }
                // 변수에 넣어줌
                foodInfo[i][0] = foodname;
                foodInfo[i][1] = kcal;
            }
  
        } catch(Exception e) {
            e.printStackTrace();
        }
        
		return foodInfo;
    }
	
}