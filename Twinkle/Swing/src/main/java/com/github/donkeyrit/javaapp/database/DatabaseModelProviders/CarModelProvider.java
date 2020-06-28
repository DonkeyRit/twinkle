package com.github.donkeyrit.javaapp.database.DatabaseModelProviders;

import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CarModelProvider {

    private DatabaseProvider provider;

    public CarModelProvider(DatabaseProvider provider) {
        this.provider = provider;
    }

    public Stream<Car> getAllCars() {

        final String query = "SELECT idCar,modelYear,info,cost,modelName,markName,nameCountry,bodyTypeName FROM\n" +
                "(SELECT idCar,modelYear,info,cost,modelName,idBodyType,markName,nameCountry FROM\n" +
                "(SELECT idCar,modelYear,image,info,cost,modelName,idBodyType,markName,idCountry FROM \n" +
                "(SELECT idCar,modelYear,image,info,cost,modelName,idMark,idBodyType FROM car \n" +
                "INNER JOIN model ON car.idModel = model.idModel) as join1\n" +
                "INNER JOIN mark ON join1.idMark = mark.idMark) as join2\n" +
                "INNER JOIN country ON join2.idCountry = country.idCountry) as join3\n" +
                "INNER JOIN bodytype ON join3.idBodyType = bodytype.idBodyType\n" +
                "ORDER BY modelYear DESC";

        List<Car> result = new ArrayList<>();
        Car.CarBuilder carBuilder = new Car.CarBuilder();

        ResultSet carSet = provider.select(query);
        try {
            while (carSet.next()) {
                carBuilder.setModelYear(carSet.getDate("modelYear"))
                        .setImagesNum(carSet.getInt("idCar"))
                        .setCost(carSet.getDouble("cost"))
                        .setModelName(carSet.getString("modelName"))
                        .setMarkName(carSet.getString("markName"))
                        .setNameCountry(carSet.getString("nameCountry"))
                        .setInfo(carSet.getString("info"))
                        .setBodyTypeName(carSet.getString("bodyTypeName"));
                result.add(carBuilder.create());
                carBuilder.flush();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result.stream();
    }
}
