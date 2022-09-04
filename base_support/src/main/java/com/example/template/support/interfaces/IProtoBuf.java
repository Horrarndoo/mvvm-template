package com.example.template.support.interfaces;

/**
 * Created by Horrarndoo on 2019/3/12.
 * <p>
 * ProtoBuf接口
 */
public interface IProtoBuf<T> {
    T toBean(byte[] bytes);

    byte[] toBytes();
}
