package ecorp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LineAPIConfiguration {

	@Value("${line.api.url}")
	private String lineAPIPath;

	@Value("#{'${line.api.token}'.split(',')}")
	private List<String> lineAPITokenList;

	public String getLineAPIPath() {
		return lineAPIPath;
	}

	public List<String> getLineAPITokenList() {return lineAPITokenList;}
}
