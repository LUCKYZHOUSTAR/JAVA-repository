package com.lucky.design.patterns.acyclic.visitor;

/**
 * ZoomVisitor interface
 */
public interface ZoomVisitor extends ModemVisitor {
    void visit(Zoom zoom);
}
