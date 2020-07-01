package com.github.donkeyrit.javaapp.database.DatabaseModelProviders;

import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Car;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.model.enums.CarStatus;

import java.sql.Date;
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

        try (ResultSet carSet = provider.select(query)) {
            while (carSet.next()) {

                int id = carSet.getInt("idCar");

                carBuilder.setModelYear(carSet.getDate("modelYear"))
                        .setImagesNum(id)
                        .setCost(carSet.getDouble("cost"))
                        .setModelName(carSet.getString("modelName"))
                        .setMarkName(carSet.getString("markName"))
                        .setNameCountry(carSet.getString("nameCountry"))
                        .setInfo(carSet.getString("info"))
                        .setBodyTypeName(carSet.getString("bodyTypeName"))
                        .setStatus(getCarStatus(id))
                        .setCurrentHolder(getCurrentHolderForSpecificCar(id));

                result.add(carBuilder.create());
                carBuilder.flush();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result.stream();
    }

    public Stream<String> getAllCarsMark() {

        final String query = "SELECT DISTINCT(markName) FROM mark";
        List<String> markList = new ArrayList<>();

        try (ResultSet markSet = provider.select(query);) {
            while (markSet.next()) {
                markList.add(markSet.getString("markName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return markList.stream();
    }

    public Stream<String> getAllCarsBodyType() {

        final String query = "SELECT DISTINCT(bodyTypeName) FROM bodytype";
        List<String> bodyTypeList = new ArrayList<>();

        try (ResultSet bodyTypeSet = provider.select(query)) {
            while (bodyTypeSet.next()) {
                bodyTypeList.add(bodyTypeSet.getString("bodyTypeName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bodyTypeList.stream();
    }

    public Stream<Double> getAllCarsPrice() {

        final String query = "SELECT MAX(cost) as price FROM car ORDER BY cost";
        List<Double> carsPrice = new ArrayList<>();

        try (ResultSet priceSet = provider.select(query)) {
            while (priceSet.next()) {
                carsPrice.add(priceSet.getDouble("price"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return carsPrice.stream();
    }

    public Stream<String> getAllModelsForSpecificMark(String mark) {

        final String query = String.format("SELECT modelName FROM mark INNER JOIN model ON mark.idMark = model.idMark WHERE markName='%s';", mark);
        List<String> carsModelList = new ArrayList<>();

        try (ResultSet bodyTypeSet = provider.select(query)) {
            while (bodyTypeSet.next()) {
                carsModelList.add(bodyTypeSet.getString("modelName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return carsModelList.stream();
    }

    private CarStatus getCarStatus(int id) {
        final String query = String.format("SELECT * FROM renta WHERE idCar = %d ORDER BY dataEnd, dataPlan DESC LIMIT 1", id);
        CarStatus result = CarStatus.OPEN;

        try (ResultSet statusSet = provider.select(query)) {
            while (statusSet.next()) {
                Date rentDate = statusSet.getDate("dataEnd");
                if (rentDate == null) {
                    result = CarStatus.BUSY;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private User getCurrentHolderForSpecificCar(int idCar){
        final String queryToDatabase = String.format(
                "SELECT * FROM user WHERE idUser = (" +
                        "SELECT idUser FROM client WHERE idClient = (" +
                        "SELECT idClient FROM renta WHERE idCar = %d " +
                        "ORDER BY dataEnd,dataPlan DESC LIMIT 1))",
                idCar
        );
        User result = null;

        try (ResultSet resultSet = provider.select(queryToDatabase)) {
            while (resultSet.next()) {

                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                boolean role = resultSet.getBoolean("role");

                result = new User(login, password, role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
