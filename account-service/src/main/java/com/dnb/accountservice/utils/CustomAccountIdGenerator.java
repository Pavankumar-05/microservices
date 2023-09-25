package com.dnb.accountservice.utils;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.spi.TypeConfiguration;

public class CustomAccountIdGenerator extends SequenceStyleGenerator {
	public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
	public static final String VALUE_PREFIX_DEFAULT = "";
	private String valuePrefix;

	public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
	public static final String NUMBER_FORMAT_DEFAULT = "%d";
	private String numbeerFormat;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// Object -> Account/Customer
		// session -> physical connection to the database
		//to assign the value
		return valuePrefix + String.format(numbeerFormat, super.generate(session, object));
	}
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		// when generator is called that tym this method will be called
		//hibernate data jpa can't handle the string based ids.
		//to overcome this problem we need to tell the jpa that we have to generate sequence of type long and convert into string later.
		super.configure(new TypeConfiguration().getBasicTypeRegistry().getRegisteredType(Long.class), params, serviceRegistry);
		valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, params,VALUE_PREFIX_DEFAULT);
		numbeerFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params,NUMBER_FORMAT_DEFAULT);
		
		
	}
}
