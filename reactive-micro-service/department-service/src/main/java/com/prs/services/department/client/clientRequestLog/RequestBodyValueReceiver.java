package com.prs.services.department.client.clientRequestLog;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;

public class RequestBodyValueReceiver {
	private static final Object DUMMY = new Object();
	private final AtomicReference<Object> reference = new AtomicReference<>(DUMMY);
	private static RequestBodyValueReceiver instance = new RequestBodyValueReceiver();
	public static RequestBodyValueReceiver getInstance() {
		return instance;
	}
	private RequestBodyValueReceiver() {
	}
	
	public Object receiveValue(BodyInserter<?, ? extends ReactiveHttpOutputMessage> bodyInserter) {
		BodyInserter<?, ReactiveHttpOutputMessage> inserter = (BodyInserter<?, ReactiveHttpOutputMessage>) bodyInserter;
		inserter.insert(MinimalHttpOutputMessage.INSTANCE,
				new SingleWriterContext(new WriteToConsumer<>(reference::set)));
		Object value = reference.get();
		reference.set(DUMMY);
		if (value == DUMMY) {
			throw new RuntimeException("Value was not received, Check your inserter worked properly");
		} else {
			return value;
		}
	}
}
