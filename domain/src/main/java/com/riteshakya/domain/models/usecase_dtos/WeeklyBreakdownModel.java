package com.riteshakya.domain.models.usecase_dtos;

/**
 * @author Ritesh Shakya
 */

public class WeeklyBreakdownModel extends BreakdownModel {

    @Override protected int getValueCount() {
        return 7;
    }
}
