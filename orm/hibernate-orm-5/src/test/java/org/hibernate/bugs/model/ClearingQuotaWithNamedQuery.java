
package org.hibernate.bugs.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Access(AccessType.FIELD)
@Entity
@Table(name = "CLEARING_QUOTA")
@NamedQueries({
	@NamedQuery(name = ClearingQuotaWithNamedQuery.SELECT_SUM, query = "SELECT new org.hibernate.bugs.model.PojoRes(sum(c.quotaCt)) FROM ClearingQuota c")
})
public class ClearingQuotaWithNamedQuery {

	public static final String SELECT_SUM = "select_sum";

	// CONSTRUCTORS
	public ClearingQuotaWithNamedQuery() {
	}

	public ClearingQuotaWithNamedQuery(Long idClearingQuota) {
		this.idClearingQuota = idClearingQuota;
	}

	// PROPERTIES SECTION
	private Long idClearingQuota;
	@Column(name = "QUOTA_CT", nullable = false)
	@Type(type = "org.hibernate.bugs.model.DecimalType")
	private Decimal quotaCt;
	@Version
	@Column(name = "VERSION")
	private Integer version;

	// GETTERS AND SETTERS SECTION.
	@Id
	@Access(AccessType.PROPERTY)
	@Column(name = "ID_CLEARING_QUOTA")
	@GeneratedValue(generator = "S_CLEARING_QUOTA")
	public Long getIdClearingQuota() {
		return this.idClearingQuota;
	}

	public void setIdClearingQuota(final Long idClearingQuota) {
		this.idClearingQuota = idClearingQuota;
	}

	public Decimal getQuotaCt() {
		return this.quotaCt;
	}

	public void setQuotaCt(final Decimal quotaCt) {
		this.quotaCt = quotaCt;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	protected Long giveIdValue() {
		return getIdClearingQuota();
	}

}
