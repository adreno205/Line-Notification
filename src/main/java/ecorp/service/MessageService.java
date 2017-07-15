package ecorp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.JsonPath;
import ecorp.config.DataAPIConfiguration;
import ecorp.dao.DataDaoRestImpl;
import ecorp.dao.LineDaoRestImpl;
import ecorp.domain.LineMsgControllerRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private LineDaoRestImpl lineDaoRest;

    @Autowired
    private DataDaoRestImpl dataDaoRest;

    @Autowired
    private DataAPIConfiguration dataConfig;

    private static Logger logger = Logger.getLogger(MessageService.class);

    public String addLineNoti(LineMsgControllerRequest lineRequest) {

        String response = lineDaoRest.sendExchange(lineRequest);
        logger.info("Send line notification Successful");

        return response;
    }

    public String calculateData(String name) {

        //you must use name to specific what's coin to calculated
        ResponseEntity<String> responseJson = dataDaoRest.sendExchange();

        System.out.printf("Response for Get api is ==>  ");
        System.out.println(responseJson.getBody().toString());

        String buyJsonExp = dataConfig.getHighBuyJsonPath();
        Integer highestBuyPrice = JsonPath.read(responseJson.getBody(),buyJsonExp);

        String sellJsonExp = dataConfig.getHighSellJsonPath();
        Integer highestSellPrice = JsonPath.read(responseJson.getBody(),sellJsonExp);

        logger.info("Calculate Data Successful");

        String responseMessage = "ราคารับซื้อล่าสุด : "+highestBuyPrice+"\nราคาตั้งขายล่าสุด : "+highestSellPrice;
        return responseMessage;
    }


}
