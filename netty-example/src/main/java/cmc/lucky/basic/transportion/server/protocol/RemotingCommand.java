/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cmc.lucky.basic.transportion.server.protocol;

/**
 * @author LUCKY
 */
public class RemotingCommand {
    public static String RemotingVersionKey = "remoting.version";
    private RemotingCommandType type;
    /**
     * Body 部分
     */
    private transient byte[] body;

    public enum RemotingCommandType {
        REQUEST_COMMAND,
        RESPONSE_COMMAND;
    }


    public static RemotingCommand createRequestCommand() {
        RemotingCommand cmd = new RemotingCommand();
        cmd.setType(RemotingCommandType.REQUEST_COMMAND);
        return cmd;
    }

    /**
     * 只有通信层内部会调用，业务不会调用
     */
    public static RemotingCommand createResponseCommand(byte[] body) {
        RemotingCommand cmd = new RemotingCommand();
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        cmd.setBody(body);
        return cmd;
    }

    public static RemotingCommand createResponseCommand(int code, String remark) {
        RemotingCommand cmd = new RemotingCommand();
        String str = "error[" + code + "] " + remark;
        cmd.setBody(str.getBytes());
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        return cmd;
    }

    public byte[] getBody() {
        return body;
    }


    public void setBody(byte[] body) {
        this.body = body;
    }

    public RemotingCommandType getType() {
        return type;
    }

    public void setType(RemotingCommandType type) {
        this.type = type;
    }


}
