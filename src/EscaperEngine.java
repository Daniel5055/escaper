import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;

public class EscaperEngine
{
    // Logic and data attributes
    State state;
    JFrame frame;
    CityEngine cityEngine;

    public EscaperEngine()
    {
        cityEngine = new CityEngine();
        cityEngine.addCountryConstraint("United Kingdom");

        // Frame initialization
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(750, 650));
        frame.getContentPane().setBackground(EscaperTheme.oceanGray);
        frame.setResizable(true);
        frame.setVisible(true);

        String[] cities = getDistantCities();
        EscaperState s = new EscaperState(this, 50, cities[0], cities[1]);

        // Must call to initiate state start
        s.popUpStartDialog();
        state = s;
    }

    public String[] getDistantCities()
    {
        // First get random city
        String[] cities = new String[2];

        cities[0] = cityEngine.getRandomCity();

        Point2D.Double startPoint = cityEngine.getCityPoint(cities[0]);
        Point2D.Double endPoint = null;

        // Iterate through random cities until far enough
        do
        {
            cities[1] = cityEngine.getRandomCity();
            endPoint = cityEngine.getCityPoint(cities[1]);
        }
        while (Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + Math.pow(endPoint.y - startPoint.y, 2)) < 5);

        return cities;
    }

    public void nextState(State state)
    {
        this.state = state;
        state.start();
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public CityEngine getCityEngine()
    {
        return cityEngine;
    }
}
