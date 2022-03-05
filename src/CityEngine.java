import java.awt.geom.Point2D;
import java.sql.*;

public class CityEngine
{
      // Static Constants
      private final static String DATA_PATH = "cities.db";

      // Database connection
      private Connection connection;

      // Scope
      private String country;

      public CityEngine(String country)
      {
            this.country = country;
            try
            {
                  String dbUrl = "jdbc:sqlite:" + DATA_PATH;
                  connection = DriverManager.getConnection(dbUrl);
            }
            catch (SQLException e)
            {
                  System.out.println("bad");
            }
      }

      public Point2D.Double getCity(String name) throws SQLException
      {
            PreparedStatement statement = connection.prepareStatement(
                     "SELECT lat, lng FROM cities WHERE country = ? AND " +
                             "( city = ? OR city_ascii = ? OR admin_name = ?) " +
                             "ORDER BY population");

            statement.setString(1, country);
            statement.setString(2, name);
            statement.setString(3, name);
            statement.setString(4, name);

            ResultSet coords = statement.executeQuery();

            // Get coords, take first (most po
            if (coords.next())
            {
                  return new Point2D.Double(coords.getDouble(2), coords.getDouble(1));
            }
            else
            {
                  // Return null if city not found in database
                  return null;
            }
      }
}