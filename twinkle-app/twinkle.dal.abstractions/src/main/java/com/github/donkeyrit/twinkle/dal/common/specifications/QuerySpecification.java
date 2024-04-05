package com.github.donkeyrit.twinkle.dal.common.specifications;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public interface QuerySpecification<T extends Identifiable, TPredicate>
{
	public abstract TPredicate toCondition();
}
