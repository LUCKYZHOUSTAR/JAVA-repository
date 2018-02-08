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

package org.lucky.transport.channel;


import org.lucky.transport.Directory;
import org.lucky.transport.UnresolvedAddress;

import java.util.List;

/**
 * Based on the same address of the channel group.
 * <p>
 * 要注意的是它管理的是相同地址的
 * <p>
 * jupiter
 * org.jupiter.transport.channel
 */
public interface LChannelGroup {


    UnresolvedAddress remoteAddress();


    LChannel next();


    List<? extends LChannel> channels();


    boolean isEmpty();


    boolean add(LChannel channel);


    boolean remove(LChannel channel);

    int size();


    void setCapacity(int capacity);


    int getCapacity();


    boolean isAvailable();


    boolean waitForAvailable(long timeoutMillis);

    /**
     * Weight of service.
     */
    int getWeight(Directory directory);

    /**
     * Sets the weight of service.
     */
    void setWeight(Directory directory, int weight);

    /**
     * Removes the weight of service.
     */
    void removeWeight(Directory directory);

    /**
     * Warm-up time.
     */
    int getWarmUp();

    /**
     * Sets warm-up time.
     */
    void setWarmUp(int warmUp);

    /**
     * Returns {@code true} if warm up to complete.
     */
    boolean isWarmUpComplete();

    /**
     * Time of birth.
     */
    long timestamp();

    /**
     * Deadline millis.
     */
    long deadlineMillis();
}
