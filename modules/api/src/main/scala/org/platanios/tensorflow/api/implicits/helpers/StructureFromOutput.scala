/* Copyright 2017-18, Emmanouil Antonios Platanios. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.platanios.tensorflow.api.implicits.helpers

import org.platanios.tensorflow.api.core.Shape
import org.platanios.tensorflow.api.ops.{Output, OutputIndexedSlices, SparseOutput}
import org.platanios.tensorflow.api.tensors.{SparseTensor, Tensor, TensorIndexedSlices}
import org.platanios.tensorflow.api.types.{DataType, INT64}

import shapeless._
import shapeless.ops.hlist.Tupler

import scala.collection.{MapLike, SeqLike}

/** Type trait used to map structures of symbolic tensors to structures of tensors, data types, and shapes.
  *
  * @author Emmanouil Antonios Platanios
  */
trait StructureFromOutput[O]

object StructureFromOutput extends StructureFromOutputLowPriorityImplicits {
  private type DataTypes3[D] = (INT64, D, INT64)
  private type Shapes3 = (Shape, Shape, Shape)

  type Aux[-T, O, -D, S] = StructureFromOutput[O]

  implicit def fromOutput[T]: Aux[Tensor[T], Output, DataType[T], Shape] = {
    new StructureFromOutput[Output] {}
  }

  implicit def fromOutputIndexedSlices[T]: Aux[TensorIndexedSlices[T], OutputIndexedSlices, DataTypes3[DataType[T]], Shapes3] = {
    new StructureFromOutput[OutputIndexedSlices] {}
  }

  implicit def fromSparseOutput[T]: Aux[SparseTensor[T], SparseOutput, DataTypes3[DataType[T]], Shapes3] = {
    new StructureFromOutput[SparseOutput] {}
  }

  implicit def fromArray[T, O, D, S](implicit
      ev: Aux[T, O, D, S]
  ): Aux[Array[T], Array[O], Array[D], Array[S]] = {
    new StructureFromOutput[Array[O]] {}
  }

  implicit def fromSeq[T, O, D, S, CC[A] <: SeqLike[A, CC[A]]](implicit
      ev: Aux[T, O, D, S]
  ): Aux[CC[T], CC[O], CC[D], CC[S]] = {
    new StructureFromOutput[CC[O]] {}
  }

  implicit def fromMap[T, O, D, S, K, CC[CK, CV] <: MapLike[CK, CV, CC[CK, CV]] with Map[CK, CV]](implicit
      ev: Aux[T, O, D, S]
  ): Aux[CC[K, T], CC[K, O], CC[K, D], CC[K, S]] = {
    new StructureFromOutput[CC[K, O]] {}
  }

  implicit val fromHNil: Aux[HNil, HNil, HNil, HNil] = {
    new StructureFromOutput[HNil] {}
  }

  implicit def fromRecursiveStructure[HT, HO, HD, HS, TT <: HList, TO <: HList, TD <: HList, TS <: HList](implicit
      evHead: Lazy[Aux[HT, HO, HD, HS]],
      evTail: Aux[TT, TO, TD, TS]
  ): Aux[HT :: TT, HO :: TO, HD :: TD, HS :: TS] = {
    new StructureFromOutput[HO :: TO] {}
  }

  implicit def fromProduct[PT, PO, PD, PS, HT <: HList, HO <: HList, HD <: HList, HS <: HList](implicit
      genO: Generic.Aux[PO, HO],
      evH: Aux[HT, HO, HD, HS],
      tuplerT: Tupler.Aux[HT, PT],
      tuplerD: Tupler.Aux[HD, PD],
      tuplerS: Tupler.Aux[HS, PS]
  ): Aux[PT, PO, PD, PS] = {
    new StructureFromOutput[PO] {}
  }
}

trait StructureFromOutputLowPriorityImplicits {
  implicit def structureFromTensorToStructureFromOutput[T, O, D, S](implicit
      ev: StructureFromTensor.Aux[T, O, D, S]
  ): StructureFromOutput.Aux[T, O, D, S] = {
    new StructureFromOutput[O] {}
  }
}
