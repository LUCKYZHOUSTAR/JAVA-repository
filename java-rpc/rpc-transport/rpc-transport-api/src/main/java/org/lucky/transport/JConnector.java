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

package org.lucky.transport;



import org.lucky.transport.channel.CopyOnWriteGroupList;
import org.lucky.transport.channel.DirectoryJChannelGroup;
import org.lucky.transport.channel.LChannelGroup;
import org.lucky.transport.processor.ConsumerProcessor;

import java.util.Collection;

/**
 * 注意 JConnector 单例即可, 不要创建多个实例.
 *
 * jupiter
 * org.jupiter.transport
 *
 * @author jiachun.fjc
 */
public interface JConnector<C> extends Transporter {

    /**
     * Connector options [parent, child].
     */
    JConfig config();

    /**
     * Binds the rpc processor.
     */
    void withProcessor(ConsumerProcessor processor);

    /**
     * Connects to the remote peer.
     */
    C connect(UnresolvedAddress address);

    /**
     * Connects to the remote peer.
     */
    C connect(UnresolvedAddress address, boolean async);


    LChannelGroup group(UnresolvedAddress address);


    Collection<LChannelGroup> groups();


    boolean addChannelGroup(Directory directory, LChannelGroup group);

    boolean removeChannelGroup(Directory directory, LChannelGroup group);


    CopyOnWriteGroupList directory(Directory directory);


    boolean isDirectoryAvailable(Directory directory);


    DirectoryJChannelGroup directoryGroup();


    JConnectionManager connectionManager();


    void shutdownGracefully();

    interface ConnectionWatcher {

        /**
         * Start to connect to server.
         */
        void start();

        /**
         * Wait until the connections is available or timeout,
         * if available return true, otherwise return false.
         */
        boolean waitForAvailable(long timeoutMillis);
    }
}
