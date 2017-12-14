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

package com.lucky.task.core.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.util.Set;

/**
 * Kryo的序列化/反序列化实现.
 * <p>
 * 要注意的是关掉了对存在循环引用的类型的支持, 如果一定要序列化/反序列化循环引用的类型,
 * 可以通过 {@link #setJavaSerializer(Class)} 设置该类型使用Java的序列化/反序列化机制,
 * 对性能有一点影响, 但只是影响一个'点', 不影响'面'.
 * <p>
 * jupiter
 * org.jupiter.serialization.kryo
 *
 * @author jiachun.fjc
 */
public class KryoSerializer {
    /**
     * The max buffer size for a {@link Serializer} to cached.
     */
    public static final int MAX_CACHED_BUF_SIZE = 256 * 1024;

    /**
     * The default buffer size for a {@link Serializer}.
     */
    public static final int DEFAULT_BUF_SIZE = 512;
    private static Set<Class<?>> useJavaSerializerTypes = new ConcurrentHashSet<>();

    static {
        useJavaSerializerTypes.add(Throwable.class);
    }

    private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {

        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            for (Class<?> type : useJavaSerializerTypes) {
                kryo.addDefaultSerializer(type, JavaSerializer.class);
            }
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            kryo.setRegistrationRequired(false);
            kryo.setReferences(false);
            return kryo;
        }
    };

    // 目的是复用 Output 中的 byte[]
    private static final ThreadLocal<Output> outputThreadLocal = new ThreadLocal<Output>() {

        @Override
        protected Output initialValue() {
            return new Output(DEFAULT_BUF_SIZE, -1);
        }
    };

    /**
     * Serializes {@code type}'s objects using Java's built in serialization mechanism,
     * note that this is very inefficient and should be avoided if possible.
     */
    public static void setJavaSerializer(Class<?> type) {
        useJavaSerializerTypes.add(type);
    }


    public <T> byte[] writeObject(T obj) {
        Output output = outputThreadLocal.get();
        try {
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, obj);
            return output.toBytes();
        } finally {
            output.clear();

            // 防止hold过大的内存块一直不释放
            if (output.getBuffer().length > MAX_CACHED_BUF_SIZE) {
                output.setBuffer(new byte[DEFAULT_BUF_SIZE], -1);
            }
        }
    }

    public <T> T readObject(byte[] bytes, int offset, int length, Class<T> clazz) {
        Input input = new Input(bytes, offset, length);
        Kryo kryo = kryoThreadLocal.get();
        return kryo.readObject(input, clazz);
    }

    public <T> T readObject(byte[] bytes, Class<T> clazz) {
        return readObject(bytes, 0, bytes.length, clazz);
    }

}
