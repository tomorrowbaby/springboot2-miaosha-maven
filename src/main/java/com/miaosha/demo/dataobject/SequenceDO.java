package com.miaosha.demo.dataobject;

public class SequenceDO {
    private Integer step;

    private String name;

    private Integer currentValue;

    public SequenceDO(Integer step, String name, Integer currentValue) {
        this.step = step;
        this.name = name;
        this.currentValue = currentValue;
    }

    public SequenceDO() {
        super();
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }
}