
package org.hibernate.bugs.model;

public class PojoRes {

	private Decimal res;

	public PojoRes(Decimal res) {
		this.res = res;
	}

	public Decimal getRes() {
		return this.res;
	}

}
