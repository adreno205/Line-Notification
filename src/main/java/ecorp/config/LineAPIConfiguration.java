package ecorp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineAPIConfiguration {

	@Value("${line.api.url}")
	private String lineAPIPath;

	@Value("${line.api.token}")
	private String lineAPIToken;

	public String getLineAPIPath() {
		return lineAPIPath;
	}

	public String getLineAPIToken() {
		return lineAPIToken;
	}
}
