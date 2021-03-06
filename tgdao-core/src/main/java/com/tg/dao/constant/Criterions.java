package com.tg.dao.constant;

/**
 * Created by twogoods on 2017/7/30.
 */
public enum Criterions {
    EQUAL("="),
    NOT_EQUAL("!="),
    GREATER(">"),
    GREATER_OR_EQUAL(">="),
    LESS("<="),
    LESS_OR_EQUAL("<="),
    LIKE("like"),
    IN("in"),
    NOT_IN("not in");


    private String criterion;

    Criterions(String criterion) {
        this.criterion = criterion;
    }

    public String getCriterion() {
        return criterion;
    }

    public boolean inCriterion() {
        if ("in".equals(criterion) || "not in".equals(criterion)) {
            return true;
        }
        return false;
    }
}
