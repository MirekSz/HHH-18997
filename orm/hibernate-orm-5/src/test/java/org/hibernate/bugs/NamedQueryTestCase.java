
package org.hibernate.bugs;

import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.bugs.model.ClearingQuota;
import org.hibernate.bugs.model.ClearingQuotaWithNamedQuery;
import org.hibernate.bugs.model.Decimal;
import org.hibernate.bugs.model.PojoRes;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM. Although this is perfectly acceptable as a
 * reproducer, usage of ORMUnitTestCase is preferred!
 */
public class NamedQueryTestCase {

	private SessionFactory sf;

	@Before
	public void setup() {
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
				// Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
				.applySetting("hibernate.query.startup_check", "true").applySetting("hibernate.show_sql", "true")
				.applySetting("hibernate.format_sql", "true").applySetting("hibernate.hbm2ddl.auto", "update");

		Metadata metadata = new MetadataSources(srb.build())
				// Add your entities here.
				.addAnnotatedClass(ClearingQuota.class).addAnnotatedClass(ClearingQuotaWithNamedQuery.class).buildMetadata();

		this.sf = metadata.buildSessionFactory();
	}

	// Add your tests, using standard JUnit.

	@Test
	public void hhh18997Test() throws Exception {
		// given
		Session openSession = this.sf.openSession();
		openSession.beginTransaction();
		ClearingQuota cq1 = new ClearingQuota();
		cq1.setQuotaCt(new Decimal("1.15"));
		openSession.save(cq1);
		ClearingQuota cq2 = new ClearingQuota();
		cq2.setQuotaCt(new Decimal("3.15"));
		openSession.save(cq2);

		// when
		PojoRes pojo = (PojoRes) openSession.createNamedQuery(ClearingQuotaWithNamedQuery.SELECT_SUM).getSingleResult();

		// then
		Assertions.assertThat(pojo.getRes()).isInstanceOf(Decimal.class).isEqualTo(new Decimal("4.30"));

	}

}
