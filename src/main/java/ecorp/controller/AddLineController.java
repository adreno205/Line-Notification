package ecorp.controller;

import ecorp.domain.LineMsgControllerRequest;
import ecorp.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddLineController {

    @Autowired
    private MessageService messageService;

    private static Logger logger = Logger.getLogger(AddLineController.class);
    //use exchange for send message Request
    @CrossOrigin
    @RequestMapping(value = "/message",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public String addLineMsg(@RequestParam(name = "message", required = true) String message) {
        logger.info("Sending Line Notification");
        LineMsgControllerRequest lineRequest = new LineMsgControllerRequest();
        lineRequest.setMessage(message);
        return messageService.addLineNoti(lineRequest);
    }
}
