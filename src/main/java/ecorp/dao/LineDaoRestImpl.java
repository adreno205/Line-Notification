package ecorp.dao;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@Repository
public class LineDaoRestImpl {

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private LineAPIConfiguration lineConfig;

	private static Logger logger = Logger.getLogger(MessageService.class);

	public String sendExchange(LineMsgControllerRequest request) throws RuntimeException {
		try {
			HttpComponentsClientHttpRequestFactory httpComponetFactory = new HttpComponentsClientHttpRequestFactory();
			httpComponetFactory.setHttpClient(httpClient);

			httpComponetFactory.setConnectTimeout(10000);
			httpComponetFactory.setReadTimeout(10000);

			RestTemplate restTemplate = new RestTemplate(httpComponetFactory);
			restTemplate.setErrorHandler(new ResponseErrorHandler() {
				@Override
				public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
					return false;
				}

				@Override
				public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

				}
			});

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", "Bearer " + lineConfig.getLineAPIToken());

			MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
			body.add("message", request.getMessage());

			HttpEntity<MultiValueMap<?, ?>> entity = new HttpEntity<MultiValueMap<?, ?>>(body, headers);

			long startTime = System.currentTimeMillis();
			// We can call Rest with exchange & postForObject
			ResponseEntity<String> response = restTemplate.exchange(
					lineConfig.getLineAPIPath(), HttpMethod.POST, entity, String.class);

			long endTime = System.currentTimeMillis();
			logger.info("line send data elapsed time = " + (endTime - startTime) + " ms.");

			String responseString = response.getBody();
			logger.info("Server Response is : " + responseString);

			return responseString;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

}
