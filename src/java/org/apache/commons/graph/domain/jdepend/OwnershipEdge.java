package org.apache.commons.graph.domain.jdepend;

import org.apache.commons.graph.*;
import jdepend.framework.*;

public class OwnershipEdge
  implements Edge, Named
{
  private JavaPackage pkg = null;
  private JavaClass clz = null;

  public OwnershipEdge( JavaPackage pkg,
			JavaClass clz) {
    this.pkg = pkg;
    this.clz = clz;
  }

  public JavaPackage getJavaPackage() {
    return pkg;
  }

  public JavaClass getJavaClass() {
    return clz;
  }

  public String getName() {
    return pkg.getName() + " owns " + clz.getName();
  }

  public String toString() {
    return getName();
  }

}





