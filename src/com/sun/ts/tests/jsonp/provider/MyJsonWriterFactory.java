/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package com.sun.ts.tests.jsonp.provider;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import com.sun.ts.lib.util.TestUtil;

import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;

/*
 * MyJsonWriterFactory is a Json Test WriterFactory used by the pluggability tests
 * to test the Json SPI layer. This parser tracks that the proper callback
 * methods are invoked within the parser when Json API methods are called.
 */

public class MyJsonWriterFactory implements JsonWriterFactory {
  private OutputStream out = null;

  private Writer writer = null;

  private Charset charset = null;

  private Map<String, ?> config = null;

  private void dumpInstanceVars() {
    TestUtil.logTrace("writer=" + writer);
    TestUtil.logTrace("out=" + out);
    TestUtil.logTrace("charset=" + charset);
    TestUtil.logTrace("config=" + config);
  }

  // call methods
  private static StringBuilder calls = new StringBuilder();

  public static String getCalls() {
    return calls.toString();
  }

  public static void clearCalls() {
    calls.delete(0, calls.length());
  }

  private static void addCalls(String s) {
    calls.append(s);
  }

  public MyJsonWriterFactory(Map<String, ?> config) {
    this.config = config;
  }

  public Map<String, ?> getConfigInUse() {
    TestUtil.logTrace("public Map<String, ?> getConfigInUse()");
    addCalls("public Map<String, ?> getConfigInUse()");
    return config;
  }

  public JsonWriter createWriter(OutputStream out) {
    TestUtil.logTrace("public JsonWriter createWriter(OutputStream)");
    addCalls("public JsonWriter createWriter(OutputStream)");
    this.out = out;
    return null;
  }

  public JsonWriter createWriter(OutputStream out, Charset charset) {
    TestUtil.logTrace("public JsonWriter createWriter(OutputStream, Charset)");
    addCalls("public JsonWriter createWriter(OutputStream, Charset)");
    this.out = out;
    this.charset = charset;
    return null;
  }

  public JsonWriter createWriter(Writer writer) {
    TestUtil.logTrace("public JsonWriter createWriter(Writer)");
    addCalls("public JsonWriter createWriter(Writer)");
    this.writer = writer;
    return null;
  }
}
