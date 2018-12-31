package com.riteshakya.domain.models.usecase_dtos;

/**
 * @author Ritesh Shakya
 */

public class MonthlyBreakdownModel extends BreakdownModel {

    @Override protected int getValueCount() {
        return 6;
    }
}
