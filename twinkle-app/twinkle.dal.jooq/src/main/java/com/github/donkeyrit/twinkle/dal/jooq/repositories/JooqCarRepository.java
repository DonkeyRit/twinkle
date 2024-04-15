package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.CarRecord;
import com.github.donkeyrit.twinkle.dal.common.specifications.PagedSpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;

import com.google.inject.Inject;
import java.math.BigDecimal;

import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.impl.DSL;

public class JooqCarRepository 
	extends JooqFilterableRepository<Car, CarQuerySpecification, CarRecord>
	implements CarRepository {

	@Inject
	public JooqCarRepository(DSLContext dslContext) {
		super(dslContext, com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Car.CAR);
	}

	@Override
	public Page<Car> getPagedResult(PagedSpecification<Car> filter) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented JooqCarRepository method 'getPagedResult'");
	}

	@Override
	public Double getMaxPrice() {

		Field<Double> maxPriceField = DSL.field(DSL.name("max_price"), Double.class);
		CommonTableExpression<Record1<Double>> maxPriceCte = DSL.name("max_price_cte").as(
                DSL.select(DSL.max(com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Car.CAR.COST).as(maxPriceField))
                    .from(DSL.table(DSL.name("car")))
        );

        var result = this.context.with(maxPriceCte)
                .select(maxPriceField)
                .from(DSL.table(maxPriceCte.getName()))
                .fetchOne();

        return result != null ? result.value1() : 0;
	}

	@Override
	public Condition toCondition(CarQuerySpecification querySpecification) {
		return DSL.noCondition();
	}

	@Override
	protected Car mapRecordToEntity(CarRecord record) {
		return new Car(
			record.getId(), 
			record.getModelYear(), 
			record.getInfo(), 
			record.getImage(), 
			record.getCost(), 
			record.getIdModel());	
	}

	@Override
	protected CarRecord entityToRecord(Car entity) {
		return new CarRecord(
			entity.getId(), 
			entity.getModelYear(), 
			entity.getModelOfCar().getId(), 
			entity.getInfo(), 
			entity.getImageId(), 
			entity.getCost());
	}
}
