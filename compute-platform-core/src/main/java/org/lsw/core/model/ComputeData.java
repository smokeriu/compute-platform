package org.lsw.core.model;

/**
 * 数据集。可能是Spark的Dataset，也可能是Flink的Table
 * @author liushengwei
 * @param <T> 数据集的具体类型
 */
public abstract class ComputeData<T> {

    private final String name;

    public ComputeData(String name) {
        this.name = name;
    }
}
