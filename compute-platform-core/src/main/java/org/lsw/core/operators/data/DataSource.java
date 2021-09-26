package org.lsw.core.operators.data;

import org.lsw.core.model.ComputeData;

/**
 * 数据输入
 * @param <OUT>
 * @param <O>
 */
public abstract class DataSource<OUT, O extends DataSource<OUT, O>> extends ComputeData<OUT> {
    public DataSource(String name) {
        super(name);
    }

    /**
     * 读取数据
     * @return 读取到的数据
     */
    public abstract ComputeData<OUT> load();
}
