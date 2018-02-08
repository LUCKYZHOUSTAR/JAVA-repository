/*
 * Copyright (c) 2015 The Jupiter Project
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lucky.transport.processor;


import org.lucky.transport.Status;
import org.lucky.transport.channel.LChannel;
import org.lucky.transport.payload.LRequestBytes;

/**
 * Provider's processor.
 *
 * jupiter
 * org.jupiter.transport.processor
 *
 * @author jiachun.fjc
 */
public interface ProviderProcessor {

    /**
     * 处理正常请求
     */
    void handleRequest(LChannel channel, LRequestBytes request) throws Exception;

    /**
     * 处理异常
     */
    void handleException(LChannel channel, LRequestBytes request, Status status, Throwable cause);
}
