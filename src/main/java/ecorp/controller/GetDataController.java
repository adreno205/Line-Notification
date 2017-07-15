package ecorp.controller;

import ecorp.domain.LineMsgControllerRequest;
import ecorp.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetDataController {

    @Autowired
    private MessageService messageService;

    private static Logger logger = Logger.getLogger(GetDataController.class);
    //use exchange for send message Request
    @CrossOrigin
    @RequestMapping(value = "/data/{name}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)

    public String getData(@PathVariable String name) {
        logger.info("Getting Data from coins : "+name);
        String response = messageService.calculateData(name);

        LineMsgControllerRequest lineRequest = new LineMsgControllerRequest();
        lineRequest.setMessage(response);
        messageService.addLineNoti(lineRequest);

        return response;
    }
}
