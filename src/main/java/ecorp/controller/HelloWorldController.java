package ecorp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

@RestController
public class HelloWorldController {

    private static Logger logger = Logger.getLogger(HelloWorldController.class);

    @RequestMapping(value = "/",
                    method = RequestMethod.GET)
    public String index(){
        logger.info("Use GET Method");
        return "This is Default Page";
    }

}
