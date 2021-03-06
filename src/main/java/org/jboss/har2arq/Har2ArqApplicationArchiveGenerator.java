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

package org.jboss.har2arq;

import java.util.ServiceLoader;

import org.jboss.arquillian.spi.ApplicationArchiveGenerator;
import org.jboss.arquillian.spi.TestClass;
import org.jboss.har2arq.adapters.AdapterContext;
import org.jboss.har2arq.adapters.DefaultAdapterContext;
import org.jboss.har2arq.adapters.DeploymentAdapter;
import org.jboss.har2arq.adapters.ZipDeploymentAdapter;
import org.jboss.shrinkwrap.api.Archive;

/**
 * Generate Arquillian archive from Harness test class.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class Har2ArqApplicationArchiveGenerator implements ApplicationArchiveGenerator
{
   private DeploymentAdapter deploymentAdapter;

   @SuppressWarnings({"LoopStatementThatDoesntLoop"})
   public Har2ArqApplicationArchiveGenerator()
   {
      ServiceLoader<DeploymentAdapter> loader = ServiceLoader.load(DeploymentAdapter.class);
      for (DeploymentAdapter aLoader : loader)
      {
         deploymentAdapter = aLoader;
         break;
      }
      if (deploymentAdapter == null)
         deploymentAdapter = new ZipDeploymentAdapter();
   }

   /**
    * Adapt Harness test class to Arquillian test class.
    *
    * @param testClass the Harness test class
    * @return Arquillian test class
    */
   public Archive<?> generateApplicationArchive(TestClass testClass)
   {
      return deploymentAdapter.adapt(createAdapterContext(testClass));
   }

   /**
    * Create adapter context.
    *
    * @param testClass the test class
    * @return new adapter context
    */
   protected AdapterContext createAdapterContext(TestClass testClass)
   {
      return new DefaultAdapterContext(testClass);
   }
}
