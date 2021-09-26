package org.lsw.core.operators;

import org.lsw.core.model.ComputeData;

/**
 * 所有操作的父类，操作必然会产生OUT。故Sink不属于子类
 * @author liushengwei
 * @param <OUT> 输出的类型，
 * @param <O> 自身的类型。用于返回自身
 */
public abstract class Transform<OUT, O extends Transform<OUT, O>> extends ComputeData<OUT> {
    public Transform(String name) {
        super(name);
    }
}
