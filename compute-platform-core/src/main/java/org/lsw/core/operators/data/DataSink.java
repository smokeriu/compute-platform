package org.lsw.core.operators.data;

import org.lsw.core.model.ComputeData;

/**
 * 数据输出
 * @author liushengwei
 * @date 2021/09/23
 * @param <IN> 输入的数据类型
 */
public abstract class DataSink<IN> {
    /**
     * 需要输出的数据
     */
    public final ComputeData<IN> input;

    public DataSink(ComputeData<IN> input) {
        this.input = input;
    }

    /**
     * 将数据存储至外部
     * @param input 待存储的数据
     */
    public abstract void save(ComputeData<IN> input);
}
