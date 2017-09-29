package io.subs.domain.models.usecase_dtos;

/**
 * @author Ritesh Shakya
 */

public class YearlyBreakdownModel extends BreakdownModel {

    @Override protected int getValueCount() {
        return 12;
    }
}
