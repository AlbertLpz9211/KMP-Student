package patrones

import patrones.chainofresponsibility.ejecutarDemo as demoChain
import patrones.command.ejecutarDemo as demoCommand
import patrones.mediator.ejecutarDemo as demoMediator
import patrones.memento.ejecutarDemo as demoMemento
import patrones.observer.ejecutarDemo as demoObserver
import patrones.state.ejecutarDemo as demoState
import patrones.strategy.ejecutarDemo as demoStrategy
import patrones.visitor.ejecutarDemo as demoVisitor

fun main() {
    demoChain()
    demoCommand()
    demoMediator()
    demoMemento()
    demoObserver()
    demoState()
    demoStrategy()
    demoVisitor()
}
