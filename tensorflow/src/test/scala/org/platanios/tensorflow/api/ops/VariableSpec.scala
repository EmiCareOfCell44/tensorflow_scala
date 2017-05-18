package org.platanios.tensorflow.api.ops

import org.platanios.tensorflow.api._
import org.platanios.tensorflow.api.tf._
import org.platanios.tensorflow.api.ops.Basic.constant

import org.scalatest._

/**
  * @author Emmanouil Antonios Platanios
  */
class VariableSpec extends FlatSpec with Matchers {
  "Variable creation" must "work" in {
    val graph = Graph()
    val variable = createWith(graph = graph) {
      val initializer = tf.constantInitializer(tf.Tensor(tf.Tensor(2, 3)))
      tf.Variable(initializer, shape = Shape(1, 2), dataType = tf.INT64)
    }
    assert(variable.dataType === tf.INT64)
    assert(graph.getCollection(Graph.Keys.GLOBAL_VARIABLES).contains(variable))
    assert(graph.getCollection(Graph.Keys.TRAINABLE_VARIABLES).contains(variable))
    val session = Session(graph = graph)
    session.run(targets = variable.initializer)
    val outputs = session.run(fetches = variable.value)
    val expectedResult = tf.Tensor(tf.INT64, tf.Tensor(2, 3))
    assert(outputs(0, 0).scalar === expectedResult(0, 0).scalar)
    assert(outputs(0, 1).scalar === expectedResult(0, 1).scalar)
    session.close()
    graph.close()
  }

  "Variable assignment" must "work" in {
    val graph = Graph()
    val (variable, variableAssignment) = createWith(graph = graph) {
      val a = constant(tf.Tensor(tf.Tensor(5, 7)), tf.INT64, name = "A")
      val initializer = tf.constantInitializer(tf.Tensor(tf.Tensor(2, 3)))
      val variable = tf.Variable(initializer, shape = Shape(1, 2), dataType = tf.INT64)
      val variableAssignment = variable.assign(a)
      (variable, variableAssignment)
    }
    assert(variable.dataType === tf.INT64)
    assert(graph.getCollection(Graph.Keys.GLOBAL_VARIABLES).contains(variable))
    assert(graph.getCollection(Graph.Keys.TRAINABLE_VARIABLES).contains(variable))
    val session = Session(graph = graph)
    session.run(targets = variable.initializer)
    val outputs = session.run(fetches = Seq(variableAssignment, variable.value))
    assert(outputs.length == 2)
    val expectedResult = tf.Tensor(tf.INT64, tf.Tensor(5, 7))
    assert(outputs(1)(0, 0).scalar === expectedResult(0, 0).scalar)
    assert(outputs(1)(0, 1).scalar === expectedResult(0, 1).scalar)
    session.close()
    graph.close()
  }
}
