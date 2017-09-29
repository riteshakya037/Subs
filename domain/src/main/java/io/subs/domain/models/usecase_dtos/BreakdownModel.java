package io.subs.domain.models.usecase_dtos;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ritesh Shakya
 */

public abstract class BreakdownModel {
    private Map<Integer, Float> dataMap;

    protected BreakdownModel() {
        this.dataMap = new LinkedHashMap<>();
        for (int i = 0; i < getValueCount(); i++) {
            dataMap.put(i, 0f);
        }
    }

    protected abstract int getValueCount();

    public float[] getData() {
        float[] floatArray = new float[dataMap.size()];
        int i = 0;
        for (Float f : dataMap.values()) {
            floatArray[i++] = (f != null ? f : Float.NaN);
        }
        return floatArray;
    }

    public void addData(int index, float subscriptionAmount) {
        float totalValue = dataMap.get(index - 1) + subscriptionAmount;
        dataMap.put(index - 1, totalValue);
    }
}
