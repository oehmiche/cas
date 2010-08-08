/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.services;

import java.util.List;

import org.jasig.cas.server.session.RegisteredService;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * Implementation of the ServiceRegistryDao based on JPA.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 */
public final class JpaServiceRegistryDaoImpl extends JpaDaoSupport implements
    ServiceRegistryDao {

    public boolean delete(final RegisteredService registeredService) {
        getJpaTemplate().remove(
            getJpaTemplate().contains(registeredService) ? registeredService
                : getJpaTemplate().merge(registeredService));
        return true;
    }

    public List<RegisteredService> load() {
        return getJpaTemplate().find("select r from RegisteredServiceImpl r");
    }

    public RegisteredService save(final RegisteredService registeredService) {
        final boolean isNew = registeredService.getId() == -1;

        final RegisteredService r = getJpaTemplate().merge(registeredService);
        
        if (!isNew) {
            getJpaTemplate().persist(r);
        }
        
        return r;
    }

    public RegisteredService findServiceById(final long id) {
        return getJpaTemplate().find(RegisteredServiceImpl.class, id);
    }
}