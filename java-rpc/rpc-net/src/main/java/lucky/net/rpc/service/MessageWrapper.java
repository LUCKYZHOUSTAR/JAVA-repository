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

package lucky.net.rpc.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Request data wrapper.
 * <p>
 * 请求消息包装.
 * <p>
 */

@Getter
@Setter
@ToString
public class MessageWrapper implements Serializable {

    private static final long serialVersionUID = 1009813828866652852L;

    private String serviceName;                 // 应用名称
    private String methodName;              // 目标方法名称
    private Object[] args;                  // 目标方法参数
}
