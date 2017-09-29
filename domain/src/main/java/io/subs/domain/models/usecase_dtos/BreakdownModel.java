package io.subs.domain.models.usecase_dtos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ritesh Shakya
 */

public abstract class BreakdownModel {
    private Map<Integer, Float> dataMap;

    public BreakdownModel() {
        this.dataMap = new HashMap<>();
        for (int i = 0; i < getValueCount(); i++) {
            dataMap.put(i, 0f);
        }
    }

    protected abstract int getValueCount();

    public float[] getData() {
        Collection<Float> floatList = dataMap.values();
        float[] floatArray = new float[floatList.size()];
        int i = 0;
        for (Float f : floatList) {
            floatArray[i++] = (f != null ? f : Float.NaN);
        }
        return floatArray;
    }
}
