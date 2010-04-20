/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.aggregator;

import java.util.Map;

import org.springframework.integration.channel.MessageChannelTemplate;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.MessageBuilder;
import org.springframework.util.Assert;

/**
 * Base class for MessageGroupProcessor implementations that aggregate the
 * group of Messages into a single Message.
 * 
 * @author Iwein Fuld
 * @author Alexander Peters
 * @author Mark Fisher
 * @since 2.0
 */
public abstract class AbstractAggregatingMessageGroupProcessor implements MessageGroupProcessor {

	public final void processAndSend(MessageGroup group, MessageChannelTemplate channelTemplate, MessageChannel outputChannel) {
		Assert.notNull(group, "MessageGroup must not be null");
		Assert.notNull(outputChannel, "'outputChannel' must not be null");
		Object payload = this.aggregatePayloads(group);
		Map<String, Object> headers = this.aggregateHeaders(group);
		Message<?> message = MessageBuilder.withPayload(payload).copyHeadersIfAbsent(headers).build();
		channelTemplate.send(message, outputChannel);
	}

	protected abstract Map<String, Object> aggregateHeaders(MessageGroup group);

	protected abstract Object aggregatePayloads(MessageGroup group);

}