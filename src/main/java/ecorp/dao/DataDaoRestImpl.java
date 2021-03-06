package ecorp.dao;

import ecorp.config.DataAPIConfiguration;
import ecorp.config.LineAPIConfiguration;
import ecorp.domain.LineMsgControllerRequest;
import ecorp.service.MessageService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Repository
public class DataDaoRestImpl {

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private DataAPIConfiguration dataConfig;

	private static Logger logger = Logger.getLogger(MessageService.class);

	//Add to pretend request call from website
	private  final String USER_AGENT = "Mozilla/5.0";

	public ResponseEntity<String> sendExchange() throws RuntimeException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("User-Agent", USER_AGENT);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = dataConfig.getDataAPIPath();

		ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, entity, String.class);
		return response;
	}

}
