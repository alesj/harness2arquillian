/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.har2arq.adapters;

import java.util.HashMap;
import java.util.Map;

import org.jboss.har2arq.types.JSR;
import org.jboss.har2arq.types.Packaging;

/**
 * Packaging adapters.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public final class PackagingAdapters
{
   private static Map<Packaging, Map<JSR, PackagingAdapter>> adapters;

   static
   {
      adapters = new HashMap<Packaging, Map<JSR, PackagingAdapter>>();

      Map<JSR, PackagingAdapter> jars = new HashMap<JSR, PackagingAdapter>();
      adapters.put(Packaging.JAR, jars);
      jars.put(JSR.CDI, new JarCdiPackagingAdapter());
      jars.put(JSR.BV, new JarBvPackagingAdapter());
      jars.put(JSR.NONE, new BasicJarPackagingAdapter());

      Map<JSR, PackagingAdapter> wars = new HashMap<JSR, PackagingAdapter>();
      adapters.put(Packaging.WAR, wars);
      wars.put(JSR.CDI, new WarCdiPackagingAdapter());
      wars.put(JSR.BV, new WarBvPackagingAdapter());

      Map<JSR, PackagingAdapter> ears = new HashMap<JSR, PackagingAdapter>();
      adapters.put(Packaging.EAR, ears);
      ears.put(JSR.CDI, new EarCdiPackagingAdapter());
      ears.put(JSR.BV, new EarBvPackagingAdapter());
   }

   /**
    * Get packaging adapter based on packaging and jsr.
    *
    * @param packaging the packaging type
    * @param jsr the jsr type
    * @return the packaging adapter
    */
   public static PackagingAdapter get(Packaging packaging, JSR jsr)
   {
      if (packaging == null)
         throw new IllegalArgumentException("Null packaging");
      if (jsr == null)
         throw new IllegalArgumentException("Null jsr");

      Map<JSR, PackagingAdapter> map = adapters.get(packaging);
      PackagingAdapter adapter = map.get(jsr);
      if (adapter == null)
         throw new IllegalArgumentException("No such adapter: " + packaging + "/" + jsr);
      return adapter;
   }
}