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

package org.platanios.tensorflow.api.ops

import org.platanios.tensorflow.api.tensors.Tensor

/**
  * @author Emmanouil Antonios Platanios
  */
 trait Text {
  /** $OpDocTextRegexReplace
    *
    * @group TextOps
    * @param  input         Tensor containing the text to be processed.
    * @param  pattern       Tensor containing the regular expression to match the input.
    * @param  rewrite       Tensor containing the rewrite to be applied to the matched expression.
    * @param  replaceGlobal If `true`, the replacement is global, otherwise the replacement is done only on the first
    *                       match.
    * @param  name          Name for the created op.
    * @return Created op output.
    */
  def regexReplace(
      input: Output[String],
      pattern: Output[String],
      rewrite: Output[String],
      replaceGlobal: Boolean = true,
      name: String = "RegexReplace"
  ): Output[String] = {
    Op.Builder[(Output[String], Output[String], Output[String]), Output[String]](
      opType = "RegexReplace",
      name = name,
      input = (input, pattern, rewrite)
    ).setAttribute("replace_global", replaceGlobal)
        .build().output
  }

  /** $OpDocTextStringJoin
    *
    * @group TextOps
    * @param  inputs    Sequence of string tensors that will be joined. The tensors must all have the same shape, or be
    *                   scalars. Scalars may be mixed in; these will be broadcast to the shape of the non-scalar inputs.
    * @param  separator Separator string.
    * @param  name      Name for the created op.
    * @return Created op output.
    */
  def stringJoin(
      inputs: Seq[Output[String]],
      separator: String = "",
      name: String = "StringJoin"
  ): Output[String] = {
    Op.Builder[Seq[Output[String]], Output[String]](
      opType = "StringJoin",
      name = name,
      input = inputs
    ).setAttribute("separator", separator)
        .build().output
  }

  /** $OpDocTextStringSplit
    *
    * @group TextOps
    * @param  input     Input tensor.
    * @param  delimiter Delimiter used for splitting. If `delimiter` is an empty string, each element of the `source` is
    *                   split into individual strings, each containing one byte. (This includes splitting multi-byte
    *                   sequences of UTF-8 characters). If `delimiter` contains multiple bytes, it is treated as a set
    *                   of delimiters with each considered a potential split point.
    * @param  skipEmpty Boolean value indicating whether or not to skip empty tokens.
    * @param  name      Name for the created op.
    * @return Created op output.
    */
  def stringSplit(
      input: Output[String],
      delimiter: Output[String] = " ",
      skipEmpty: Boolean = true,
      name: String = "StringSplit"
  ): SparseOutput[String] = {
    Op.Builder[(Output[String], Output[String]), SparseOutput[String]](
      opType = "StringSplit",
      name = name,
      input = (input, delimiter)
    ).setAttribute("skip_empty", skipEmpty)
        .build().output
  }

  /** $OpDocTextStringEncodeBase64
    *
    * @group TextOps
    * @param  input Input tensor.
    * @param  pad   Boolean value indicating whether or not padding is applied at the string ends.
    * @param  name  Name for the created op.
    * @return Created op output.
    */
  def encodeBase64(
      input: Output[String],
      pad: Boolean = false,
      name: String = "EncodeBase64"
  ): Output[String] = {
    Op.Builder[Output[String], Output[String]](
      opType = "EncodeBase64",
      name = name,
      input = input
    ).setAttribute("pad", pad)
        .build().output
  }

  /** $OpDocTextStringDecodeBase64
    *
    * @group TextOps
    * @param  input Input tensor.
    * @param  name  Name for the created op.
    * @return Created op output.
    */
  def decodeBase64(
      input: Output[String],
      name: String = "DecodeBase64"
  ): Output[String] = {
    Op.Builder[Output[String], Output[String]](
      opType = "DecodeBase64",
      name = name,
      input = input
    ).build().output
  }

  /** $OpDocTextStringToHashBucket
    *
    * @group TextOps
    * @param  input      Tensor containing the strings to assign to each bucket.
    * @param  numBuckets Number of buckets.
    * @param  name       Name for the created op.
    * @return Created op output, which has the same shape as `input`.
    */
  @deprecated("It is recommended to use `stringToHashBucketFast` or `stringToHashBucketStrong`.", "0.1.0")
  def stringToHashBucket(
      input: Output[String],
      numBuckets: Int,
      name: String = "StringToHashBucket"
  ): Output[Long] = {
    Op.Builder[Output[String], Output[Long]](
      opType = "StringToHashBucket",
      name = name,
      input = input
    ).setAttribute("num_buckets", numBuckets)
        .build().output
  }

  /** $OpDocTextStringToHashBucketFast
    *
    * @group TextOps
    * @param  input      Tensor containing the strings to assign to each bucket.
    * @param  numBuckets Number of buckets.
    * @param  name       Name for the created op.
    * @return Created op output, which has the same shape as `input`.
    */
  def stringToHashBucketFast(
      input: Output[String],
      numBuckets: Int,
      name: String = "StringToHashBucketFast"
  ): Output[Long] = {
    Op.Builder[Output[String], Output[Long]](
      opType = "StringToHashBucketFast",
      name = name,
      input = input
    ).setAttribute("num_buckets", numBuckets)
        .build().output
  }

  /** $OpDocTextStringToHashBucketStrong
    *
    * @group TextOps
    * @param  input      Tensor containing the strings to assign to each bucket.
    * @param  numBuckets Number of buckets.
    * @param  key1       First part of the key for the keyed hash function.
    * @param  key2       Second part of the key for the keyed hash function.
    * @param  name       Name for the created op.
    * @return Created op output, which has the same shape as `input`.
    */
  def stringToHashBucketStrong(
      input: Output[String],
      numBuckets: Int,
      key1: Long,
      key2: Long,
      name: String = "StringToHashBucketStrong"
  ): Output[Long] = {
    Op.Builder[Output[String], Output[Long]](
      opType = "StringToHashBucketStrong",
      name = name,
      input = input
    ).setAttribute("num_buckets", numBuckets)
        .setAttribute("key", Tensor(key1, key2))
        .build().output
  }
}

object Text extends Text {
  private[ops] trait Implicits {
    implicit def outputConvertibleToTextOps[OC](
        value: OC
    )(implicit f: OC => Output[String]): TextOps = {
      new TextOps(f(value))
    }

    implicit class TextOps(val output: Output[String]) {
      /** $OpDocTextRegexReplace
        *
        * @group TextOps
        * @param  pattern       Tensor containing the regular expression to match the input.
        * @param  rewrite       Tensor containing the rewrite to be applied to the matched expression.
        * @param  replaceGlobal If `true`, the replacement is global, otherwise the replacement is done only on the first
        *                       match.
        * @param  name          Name for the created op.
        * @return Created op output.
        */
      def regexReplace(
          pattern: Output[String],
          rewrite: Output[String],
          replaceGlobal: Boolean = true,
          name: String = "RegexReplace"
      ): Output[String] = {
        Text.regexReplace(output, pattern, rewrite, replaceGlobal, name)
      }

      /** $OpDocTextStringSplit
        *
        * @group TextOps
        * @param  delimiter Delimiter used for splitting. If `delimiter` is an empty string, each element of the `source`
        *                   is split into individual strings, each containing one byte. (This includes splitting
        *                   multi-byte sequences of UTF-8 characters). If `delimiter` contains multiple bytes, it is
        *                   treated as a set of delimiters with each considered a potential split point.
        * @param  skipEmpty Boolean value indicating whether or not to skip empty tokens.
        * @param  name      Name for the created op.
        * @return Created op output.
        */
      def stringSplit(
          delimiter: Output[String] = " ",
          skipEmpty: Boolean = true,
          name: String = "StringSplit"
      ): SparseOutput[String] = {
        Text.stringSplit(output, delimiter, skipEmpty, name)
      }

      /** $OpDocTextStringEncodeBase64
        *
        * @group TextOps
        * @param  pad  Boolean value indicating whether or not padding is applied at the string ends.
        * @param  name Name for the created op.
        * @return Created op output.
        */
      def encodeBase64(
          pad: Boolean = false,
          name: String = "EncodeBase64"
      ): Output[String] = {
        Text.encodeBase64(output, pad, name)
      }

      /** $OpDocTextStringDecodeBase64
        *
        * @group TextOps
        * @param  name Name for the created op.
        * @return Created op output.
        */
      def decodeBase64(
          name: String = "DecodeBase64"
      ): Output[String] = {
        Text.decodeBase64(output, name)
      }

      /** $OpDocTextStringToHashBucket
        *
        * @group TextOps
        * @param  numBuckets Number of buckets.
        * @param  name       Name for the created op.
        * @return Created op output, which has the same shape as `input`.
        */
      @deprecated("It is recommended to use `stringToHashBucketFast` or `stringToHashBucketStrong`.", "0.1.0")
      def stringToHashBucket(
          numBuckets: Int,
          name: String = "StringToHashBucket"
      ): Output[Long] = {
        Text.stringToHashBucket(output, numBuckets, name)
      }

      /** $OpDocTextStringToHashBucketFast
        *
        * @group TextOps
        * @param  numBuckets Number of buckets.
        * @param  name       Name for the created op.
        * @return Created op output, which has the same shape as `input`.
        */
      def stringToHashBucketFast(
          numBuckets: Int,
          name: String = "StringToHashBucketFast"
      ): Output[Long] = {
        Text.stringToHashBucketFast(output, numBuckets, name)
      }

      /** $OpDocTextStringToHashBucketStrong
        *
        * @group TextOps
        * @param  numBuckets Number of buckets.
        * @param  key1       First part of the key for the keyed hash function.
        * @param  key2       Second part of the key for the keyed hash function.
        * @param  name       Name for the created op.
        * @return Created op output, which has the same shape as `input`.
        */
      def stringToHashBucketStrong(
          numBuckets: Int,
          key1: Long,
          key2: Long,
          name: String = "StringToHashBucketStrong"
      ): Output[Long] = {
        Text.stringToHashBucketStrong(output, numBuckets, key1, key2, name)
      }
    }
  }

  /** @define OpDocTextRegexReplace
    *   The `regexReplace` op replaces the match of a regular expression pattern in a string with another provided
    *   string. The op uses the [re2 syntax](https://github.com/google/re2/wiki/Syntax) for regular expressions.
    *
    * @define OpDocTextStringJoin
    *   The `stringJoin` op joins the strings in the given list of string tensors into one tensor, using the provided
    *   separator (which defaults to an empty string).
    *
    * @define OpDocTextStringSplit
    *   The `stringSplit` op splits elements of `input` based on `delimiter` into a sparse tensor.
    *
    *   Let `N` be the size of the input (typically `N` will be the batch size). The op splits each element of `input`
    *   based on `delimiter` and returns a sparse tensor containing the split tokens. `skipEmpty` determines whether
    *   empty tokens are ignored or not.
    *
    *   If `delimiter` is an empty string, each element of the `source` is split into individual strings, each
    *   containing one byte. (This includes splitting multi-byte sequences of UTF-8 characters). If `delimiter` contains
    *   multiple bytes, it is treated as a set of delimiters with each considered a potential split point.
    *
    *   For example:
    *   {{{
    *     // N = 2
    *     // input = Tensor("hello world", "a b c")
    *     val st = stringSplit(input)
    *     st.indices ==> [[0, 0], [0, 1], [1, 0], [1, 1], [1, 2]]
    *     st.values ==> ["hello", "world", "a", "b", "c"]
    *     st.denseShape ==> [2, 3]
    *   }}}
    *
    * @define OpDocTextStringEncodeBase64
    *   The `encodeBase64` op encodes strings into a web-safe base64 format.
    *
    *   Refer to [this article](https://en.wikipedia.org/wiki/Base64) for more information on base64 format. Base64
    *   strings may have padding with `=` at the end so that the encoded string has length that is a multiple of 4.
    *   Refer to the padding section of the link above for more details on this.
    *
    *   Web-safe means that the encoder uses `-` and `_` instead of `+` and `/`.
    *
    * @define OpDocTextStringDecodeBase64
    *   The `decodeBase64` op decodes web-safe base64-encoded strings.
    *
    *   The input may or may not have padding at the end. See `encodeBase64` for more details on padding.
    *
    *   Web-safe means that the encoder uses `-` and `_` instead of `+` and `/`.
    *
    * @define OpDocTextStringToHashBucket
    *   The `stringToHashBucket` op converts each string in the input tensor to its hash mod the number of buckets.
    *
    *   The hash function is deterministic on the content of the string within the process. Note that the hash function
    *   may change from time to time.
    *
    * @define OpDocTextStringToHashBucketFast
    *   The `stringToHashBucketFast` op converts each string in the input tensor to its hash mod the number of buckets.
    *
    *   The hash function is deterministic on the content of the string within the process and will never change.
    *   However, it is not suitable for cryptography. This method may be used when CPU time is scarce and inputs are
    *   trusted or are unimportant. There is a risk of adversaries constructing inputs that all hash to the same bucket.
    *   To prevent this problem, use `stringToHashBucketStrong`.
    *
    * @define OpDocTextStringToHashBucketStrong
    *   The `stringToHashBucketStrong` op converts each string in the input tensor to its hash mod the number of
    *   buckets.
    *
    *   The hash function is deterministic on the content of the string within the process. The hash function is a keyed
    *   hash function, where `key1` and `key2` define the key of the hash function. A strong hash is important when
    *   inputs may be malicious (e.g., URLs with additional components). Adversaries could try to make their inputs hash
    *   to the same bucket for a denial-of-service attack or to skew the results. A strong hash prevents this by making
    *   it difficult, if not infeasible, to compute inputs that hash to the same bucket. This comes at a cost of roughly
    *   4x higher compute time than `stringToHashBucketFast`.
    */
  private[ops] trait Documentation
}
