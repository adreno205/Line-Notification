package ecorp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.JsonPath;
import ecorp.config.Constant;
import ecorp.config.DataAPIConfiguration;
import ecorp.config.LineAPIConfiguration;
import ecorp.dao.DataDaoRestImpl;
import ecorp.dao.LineDaoRestImpl;
import ecorp.domain.LineMsgControllerRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

// may be we must put it in main class
// https://github.com/line/line-bot-sdk-java/blob/master/sample-spring-boot-echo/src/main/java/com/example/bot/spring/echo/EchoApplication.java

@Service
@LineMessageHandler
public class MessageService {

    @Autowired
    private LineDaoRestImpl lineDaoRest;

    @Autowired
    private DataDaoRestImpl dataDaoRest;

    @Autowired
    private DataAPIConfiguration dataConfig;

    @Autowired
    private LineAPIConfiguration lineConfig;

    private static Logger logger = Logger.getLogger(MessageService.class);

    public String addLineNoti(LineMsgControllerRequest lineRequest) {
        String response = "";
        for (String token: lineConfig.getLineAPITokenList()) {
            lineRequest.setToken(token);
             response = lineDaoRest.sendExchange(lineRequest);
        }
        logger.info("Send line notification Successful");

        return response;
    }

    public String calculateData(String name) {

        ResponseEntity<String> responseJson = dataDaoRest.sendExchange();

        System.out.printf("Response for Get api is ==>  ");
        System.out.println(responseJson.getBody().toString());

        String buyJsonExp = dataConfig.getHighBuyJsonPath();
        String sellJsonExp = dataConfig.getHighSellJsonPath();

        switch (name) {
            case "thb_btc":
                buyJsonExp = buyJsonExp.replace("X", Constant.THB_BTC_ID);
                sellJsonExp = sellJsonExp.replace("X", Constant.THB_BTC_ID);break;
            case "btc_eth":
                buyJsonExp = buyJsonExp.replace("X", Constant.BTC_ETH_ID);
                sellJsonExp = sellJsonExp.replace("X", Constant.BTC_ETH_ID);break;
            case "thb_eth":
                buyJsonExp = buyJsonExp.replace("X", Constant.THB_ETH_ID);
                sellJsonExp = sellJsonExp.replace("X", Constant.THB_ETH_ID);break;
            case "thb_xrp":
                buyJsonExp = buyJsonExp.replace("X", Constant.THB_XRP_ID);
                sellJsonExp = sellJsonExp.replace("X", Constant.THB_XRP_ID);break;
            case "thb_omg":
                buyJsonExp = buyJsonExp.replace("X", Constant.THB_OMG_ID);
                sellJsonExp = sellJsonExp.replace("X", Constant.THB_OMG_ID);break;
            default:
                buyJsonExp = buyJsonExp.replace("X", Constant.THB_BTC_ID);
                sellJsonExp = sellJsonExp.replace("X", Constant.THB_BTC_ID);break;
        }

        Object highestBuyPrice = JsonPath.read(responseJson.getBody(),buyJsonExp);
        Object highestSellPrice = JsonPath.read(responseJson.getBody(),sellJsonExp);

        logger.info("Calculate Data Successful");

        String responseMessage = "Coins: "+name.toUpperCase()+"\nราคารับซื้อล่าสุด : "+highestBuyPrice+"\nราคาตั้งขายล่าสุด : "+highestSellPrice;
        return responseMessage;
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("event: " + event);

        String gotMsg = event.getMessage().getText();
        String sentMsg;
        if(gotMsg.substring(0,1).equals("@")){
            sentMsg = gotMsg.substring(1);
            sentMsg = calculateData(sentMsg);
        }
        else {
            sentMsg = event.getMessage().getText();
        }
        return new TextMessage(sentMsg);
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }


}
