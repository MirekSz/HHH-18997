
package org.hibernate.bugs.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class DecimalType implements UserType, Serializable {

	@Override
	public Object assemble(final Serializable cached, final Object owner) {
		return cached;
	}

	@Override
	public Object deepCopy(final Object value) {
		return value;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return (Serializable) value;

	}

	@Override
	public boolean equals(final Object x, final Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (null == x || null == y) {
			return false;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(final Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) {
		return original;
	}

	@Override
	public Class returnedClass() {
		return Decimal.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {
			Types.NUMERIC
		};
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor session, final Object owner)
			throws HibernateException, SQLException {
		String columnLabel = names[0];
		int columnNumber = rs.findColumn(columnLabel);
		BigDecimal value = rs.getBigDecimal(columnNumber);
		int scale = rs.getMetaData().getScale(columnNumber);
		if (value == null) {
			return null;
		}
		if (value.scale() < scale) {
			value = value.setScale(scale);
		}
		return new Decimal(value);
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		Decimal decimal = (Decimal) value;
		if (decimal == null) {
			st.setNull(index, Types.NUMERIC);
		} else {
			st.setBigDecimal(index, decimal.getBigDecimal());
		}

	}

}
