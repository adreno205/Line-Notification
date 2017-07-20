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

        LineMsgControllerRequest lineRequest = new LineMsgControllerRequest();
        lineRequest.setMessage(response);
        messageService.addLineNoti(lineRequest);
    }
}
