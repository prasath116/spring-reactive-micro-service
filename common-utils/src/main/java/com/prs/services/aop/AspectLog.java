package com.prs.services.aop;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class AspectLog {
	@Singular
	private List<String> paths;
	@Singular
	private Map<String, Object> pathVariables;
	private HttpHeaders headers;
	private Object requestBody;
	private Object responseBody;
	private long timeTaken;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(timeTaken).append(" ms taken for request -");
		Optional.ofNullable(paths).ifPresent(o -> sb.append(" paths : ").append(o));
		Optional.ofNullable(headers).ifPresent(o -> sb.append(" headers : ").append(o));
		Optional.ofNullable(pathVariables).ifPresent(o -> sb.append(" pathVariables : ").append(o));
		Optional.ofNullable(requestBody).ifPresent(o -> sb.append(" requestBody : ").append(o));
		Optional.ofNullable(responseBody).ifPresent(o -> sb.append(" responseBody : ").append(o));
		return sb.toString();
	}
}
