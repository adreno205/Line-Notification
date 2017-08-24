package ecorp.service;

import ecorp.domain.LineMsgControllerRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetPriceService {

    //set minute to schedule (60 minute)
    final int time = 60 * 60 * 1000;

    @Autowired
    private MessageService messageService;

    private static Logger logger = Logger.getLogger(GetPriceService.class);
    @CrossOrigin
    @Scheduled(fixedRate = time)
    public void getData() {
        logger.info("Getting Price from BTC coins : ");
        String name = "thb_btc";
        String response = messageService.calculateData(name);

        logger.info("Getting Price from ETH coins : ");
        String name2 = "thb_eth";
        String response2 = messageService.calculateData(name2);

        logger.info("Getting Price from OMG coins : ");
        String name3 = "thb_omg";
        String response3 = messageService.calculateData(name3);

        LineMsgControllerRequest lineRequest = new LineMsgControllerRequest();
        lineRequest.setMessage(response+"\n\n"+response2+"\n\n"+response3);
        messageService.addLineNoti(lineRequest);
    }
}
