/* 
 * Copyright (c) 2010 Weigle Wilczek GmbH
 * All rights reserved.
 * 
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * 
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.slf4j.scala

import org.slf4j.{ Logger => SLF4JLogger }
import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito

class LoggerSpec extends SpecificationWithJUnit with Mockito {

  "Creating a Logger using Logger(clazz: Class[_])" should {

    "return a Logger namend like the given class" in {
      val clazz = classOf[String]
      Logger(clazz).name mustEqual clazz.getName
    }

    "throw an IAE when creating a Logger with a null class" in {
      Logger(null: Class[_]) must throwA[IllegalArgumentException]
    }
  }

  "Creating a Logger using Logger(name: String)" should {

    "return a Logger namend like the given name" in {
      val name = "MyLogger"
      Logger(name).name mustEqual name
    }

    "throw an IAE when creating a Logger with a null String" in {
      Logger(null: String) must throwA [IllegalArgumentException]
    }
  }

  "Calling Logger.error(msg)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.error when error not enabled" in {
      slf4jLogger.isErrorEnabled returns false
      logger error msg
      there was no(slf4jLogger).error(Msg)
      evaluated mustBe false
    }

    "call SLF4JLogger.error when error enabled" in {
      slf4jLogger.isErrorEnabled returns true
      logger error msg
      there was one(slf4jLogger).error(Msg)
      evaluated mustBe true
    }
  }

  "Calling Logger.error(msg, t)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.error when error not enabled" in {
      slf4jLogger.isErrorEnabled returns false
      logger.error(msg, t)
      there was no(slf4jLogger).error(Msg, t)
      evaluated mustBe false
    }

    "call SLF4JLogger.error when error enabled" in {
      slf4jLogger.isErrorEnabled returns true
      logger.error(msg, t)
      there was one(slf4jLogger).error(Msg ,t)
      evaluated mustBe true
    }
  }

  "Calling Logger.warn(msg)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.warn when warn not enabled" in {
      slf4jLogger.isWarnEnabled returns false
      logger warn msg
      there was no(slf4jLogger).warn(Msg)
      evaluated mustBe false
    }

    "call SLF4JLogger.warn when warn enabled" in {
      slf4jLogger.isWarnEnabled returns true
      logger warn msg
      there was one(slf4jLogger).warn(Msg)
      evaluated mustBe true
    }
  }

  "Calling Logger.warn(msg, t)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.warn when warn not enabled" in {
      slf4jLogger.isErrorEnabled returns false
      logger.warn(msg, t)
      there was no(slf4jLogger).warn(Msg, t)
      evaluated mustBe false
    }

    "call SLF4JLogger.warn when warn enabled" in {
      slf4jLogger.isWarnEnabled returns true
      logger.warn(msg, t)
      there was one(slf4jLogger).warn(Msg ,t)
      evaluated mustBe true
    }
  }

  "Calling Logger.info(msg)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.info when info not enabled" in {
      slf4jLogger.isInfoEnabled returns false
      logger info msg
      there was no(slf4jLogger).info(Msg)
      evaluated mustBe false
    }

    "call SLF4JLogger.info when info enabled" in {
      slf4jLogger.isInfoEnabled returns true
      logger info msg
      there was one(slf4jLogger).info(Msg)
      evaluated mustBe true
    }
  }

  "Calling Logger.info(msg, t)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.info when info not enabled" in {
      slf4jLogger.isInfoEnabled returns false
      logger.info(msg, t)
      there was no(slf4jLogger).info(Msg, t)
      evaluated mustBe false
    }

    "call SLF4JLogger.info when info enabled" in {
      slf4jLogger.isInfoEnabled returns true
      logger.info(msg, t)
      there was one(slf4jLogger).info(Msg, t)
      evaluated mustBe true
    }
  }

  "Calling Logger.debug(msg)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.debug when debug not enabled" in {
      slf4jLogger.isDebugEnabled returns false
      logger debug msg
      there was no(slf4jLogger).debug(Msg)
      evaluated mustBe false
    }

    "call SLF4JLogger.debug when debug enabled" in {
      slf4jLogger.isDebugEnabled returns true
      logger debug msg
      there was one(slf4jLogger).debug(Msg)
      evaluated mustBe true
    }
  }

  "Calling Logger.debug(msg ,t)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.debug when debug not enabled" in {
      slf4jLogger.isDebugEnabled returns false
      logger.debug(msg, t)
      there was no(slf4jLogger).debug(Msg, t)
      evaluated mustBe false
    }

    "call SLF4JLogger.debug when debug enabled" in {
      slf4jLogger.isDebugEnabled returns true
      logger.debug(msg, t)
      there was one(slf4jLogger).debug(Msg, t)
      evaluated mustBe true
    }
  }

  "Calling Logger.trace(msg)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.trace when trace not enabled" in {
      slf4jLogger.isTraceEnabled returns false
      logger trace msg
      there was no(slf4jLogger).trace(Msg)
      evaluated mustBe false
    }

    "call SLF4JLogger.trace when trace enabled" in {
      slf4jLogger.isTraceEnabled returns true
      logger trace msg
      there was one(slf4jLogger).trace(Msg)
      evaluated mustBe true
    }
  }

  "Calling Logger.trace(msg, t)" should {
    val (logger, slf4jLogger) = loggers
    var evaluated = false
    def msg = {
      evaluated = true
      Msg
    }

    "not call SLF4JLogger.trace when trace not enabled" in {
      slf4jLogger.isTraceEnabled returns false
      logger.trace(msg, t)
      there was no(slf4jLogger).trace(Msg, t)
      evaluated mustBe false
    }

    "call SLF4JLogger.trace when trace enabled" in {
      slf4jLogger.isTraceEnabled returns true
      logger.trace(msg, t)
      there was one(slf4jLogger).trace(Msg, t)
      evaluated mustBe true
    }
  }

  private lazy val Msg = "MESSAGE"

  private lazy val t = new Throwable

  private def loggers = {
    val mockSLF4JLogger = mock[SLF4JLogger]
    val logger = new Logger {
      override protected val slf4jLogger = mockSLF4JLogger
    }
    (logger, mockSLF4JLogger)
  }
}
