package org.lm.quick.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.data.repository.core.RepositoryMetadata;

public class MyJpaReposotoryFactory extends JpaRepositoryFactory {

	private Class<?> domainType;

	public MyJpaReposotoryFactory(EntityManager entityManager) {
		super(entityManager);
	}

	public MyJpaReposotoryFactory(EntityManager entityManager, Class<?> domainType) {
		super(entityManager);
		this.domainType = domainType;
	}

	public Class<?> getDomainType() {
		return domainType;
	}

	@Override
	protected RepositoryMetadata getRepositoryMetadata(Class<?> repositoryInterface) {
		final RepositoryMetadata meta = super.getRepositoryMetadata(repositoryInterface);
		if (meta.getDomainType().equals(Object.class)) {
			return new RepositoryMetadata() {

				@Override
				public boolean isPagingRepository() {
					return meta.isPagingRepository();
				}

				@Override
				public Class<?> getReturnedDomainClass(Method method) {
					return meta.getReturnedDomainClass(method);
				}

				@Override
				public Class<?> getRepositoryInterface() {
					return meta.getRepositoryInterface();
				}

				@Override
				public Class<? extends Serializable> getIdType() {
					return meta.getIdType();
				}

				@Override
				public Class<?> getDomainType() {
					return MyJpaReposotoryFactory.this.getDomainType();
				}

				@Override
				public CrudMethods getCrudMethods() {
					return meta.getCrudMethods();
				}

				@Override
				public Set<Class<?>> getAlternativeDomainTypes() {
					return meta.getAlternativeDomainTypes();
				}
			};

		}
		return meta;
	}

}
