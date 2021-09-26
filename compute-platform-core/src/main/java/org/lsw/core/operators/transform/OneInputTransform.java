package org.lsw.core.operators.transform;

import org.lsw.core.model.ComputeData;
import org.lsw.core.operators.Transform;

public abstract class OneInputTransform<IN, OUT, O extends OneInputTransform<IN, OUT, O>> extends Transform<OUT, O> {

    private final ComputeData<IN> input;

    public OneInputTransform(String name, ComputeData<IN> input) {
        super(name);
        this.input = input;
    }

    /**
     * 将输入转换为输出
     * @param input 待转换的数据
     */
    protected abstract ComputeData<OUT> translateToOutput(ComputeData<IN> input);
}
