/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * @(#)BEJB.java	1.7 03/05/16
 */

package com.sun.ts.tests.ejb.ee.pm.manyXmany.bi.btob;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import com.sun.ts.lib.util.RemoteLoggingInitException;
import com.sun.ts.lib.util.TestUtil;

import jakarta.ejb.CreateException;
import jakarta.ejb.EJBException;
import jakarta.ejb.EntityBean;
import jakarta.ejb.EntityContext;
import jakarta.ejb.RemoveException;

public abstract class BEJB implements EntityBean {

  private EntityContext context = null;

  // ===========================================================
  // getters and setters for cmp fields

  public abstract String getId();

  public abstract void setId(String v);

  public abstract String getName();

  public abstract void setName(String v);

  public abstract int getValue();

  public abstract void setValue(int v);

  // ===========================================================
  // getters and setters for relationship fields

  // manyxmany
  public abstract Collection getA();

  public abstract void setA(Collection v);

  // ===========================================================
  // B interface business methods

  public void init(Properties p) {
    TestUtil.logTrace("init");
    try {
      TestUtil.init(p);
    } catch (RemoteLoggingInitException e) {
      TestUtil.printStackTrace(e);
      throw new EJBException(e.getMessage());
    }
  }

  public boolean isA() {
    TestUtil.logTrace("isA");
    if (getA().isEmpty() != true)
      TestUtil.logMsg("Relationship set for A ...");
    else
      TestUtil.logMsg("Relationship not set for A ...");
    return getA().isEmpty() != true;
  }

  public boolean setCmrFieldToNull() {
    TestUtil.logTrace("setCmrFieldToNull");
    try {
      setA(null);
      TestUtil.logErr("no exception when setting collection cmr field to null");
      return false;
    } catch (IllegalArgumentException e) {
      TestUtil.logMsg("IllegalArgumentException caught as expected");
      return true;
    } catch (Exception e) {
      TestUtil.logErr("Expected IllegalArgumentException, received " + e);
      return false;
    }
  }

  public Collection getAInfo() {
    TestUtil.logTrace("getAInfo");
    try {
      Vector v = new Vector();
      if (isA()) {
        Collection acol = getA();
        Iterator iterator = acol.iterator();
        while (iterator.hasNext()) {
          ALocal a = (ALocal) iterator.next();
          ADVC aDVC = new ADVC(a.getId(), a.getName(), a.getValue());
          v.add(aDVC);
        }
      }
      return v;
    } catch (Exception e) {
      TestUtil.logErr("Exception: " + e, e);
      return null;
    }
  }

  // ===========================================================
  // EJB Specification Required Methods

  public String ejbCreate(String id, String name, int value)
      throws CreateException {
    TestUtil.logTrace("ejbCreate");
    try {
      setId(id);
      setName(name);
      setValue(value);
    } catch (Exception e) {
      TestUtil.printStackTrace(e);
      throw new CreateException("Exception occurred: " + e);
    }
    return null;
  }

  public void ejbPostCreate(String id, String name, int value)
      throws CreateException {
    TestUtil.logTrace("ejbPostCreate");
  }

  public void setEntityContext(EntityContext c) {
    TestUtil.logTrace("setEntityContext");
    context = c;
  }

  public void unsetEntityContext() {
    TestUtil.logTrace("unsetEntityContext");
  }

  public void ejbRemove() throws RemoveException {
    TestUtil.logTrace("ejbRemove");
  }

  public void ejbActivate() {
    TestUtil.logTrace("ejbActivate");
  }

  public void ejbPassivate() {
    TestUtil.logTrace("ejbPassivate");
  }

  public void ejbLoad() {
    TestUtil.logTrace("ejbLoad");
  }

  public void ejbStore() {
    TestUtil.logTrace("ejbStore");
  }
}
