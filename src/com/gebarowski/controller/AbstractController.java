package com.gebarowski.controller;


/**
 * Links two controllers using ModelAccess
 * Starting point
 *
 */
public abstract class AbstractController {

    private ModelAccess modelAccess;

    public AbstractController(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }

    public ModelAccess getModelAccess() {
        return modelAccess;
    }
}
