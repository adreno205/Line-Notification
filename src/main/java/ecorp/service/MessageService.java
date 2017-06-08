package ecorp.service;

import ecorp.dao.LineDaoRestImpl;
import ecorp.domain.LineMsgControllerRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private LineDaoRestImpl lineDaoRest;

    private static Logger logger = Logger.getLogger(MessageService.class);

    public String addLineNoti(LineMsgControllerRequest lineRequest) {

        String response = lineDaoRest.sendExchange(lineRequest);
        logger.info("Send line notification Successful");

        return response;
    }


}
