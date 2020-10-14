package slamach.weblab2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        long startTime = System.nanoTime();

        String xString = request.getParameter("xval");
        String yString = request.getParameter("yval").replace(',', '.');
        String rString = request.getParameter("rval");
        boolean isValuesValid = validateValues(xString, yString, rString);

        double xValue = isValuesValid ? Double.parseDouble(xString) : 0;
        double yValue = isValuesValid ? Double.parseDouble(yString) : 0;
        double rValue = isValuesValid ? Double.parseDouble(rString) : 0;
        boolean isHit = isValuesValid && checkHit(xValue, yValue, rValue);

        OffsetDateTime currentTimeObject = OffsetDateTime.now(ZoneOffset.UTC);
        try {
            currentTimeObject = currentTimeObject.minusMinutes(Long.parseLong(request.getParameter("timezone")));
        } catch (NumberFormatException exception) {}
        String currentTime = currentTimeObject.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String executionTime = String.valueOf(System.nanoTime() - startTime);

        String jsonData = '{' +
        "\"validate\":" + isValuesValid + "," +
        "\"xval\":\"" + xString + "\"," +
        "\"yval\":\"" + yString + "\"," +
        "\"rval\":\"" + rString + "\"," +
        "\"curtime\":\"" + currentTime + "\"," +
        "\"exectime\":\"" + executionTime + "\"," +
        "\"hitres\":" + isHit +
        "}";

        try (PrintWriter writer = response.getWriter()) {
            writer.println(jsonData);
        }
    }

    private boolean validateX(String xString) {
        try {
            Double xRange[] = {-2.0, -1.5, -1.0, -0.5, 0d, 0.5, 1.0, 1.5, 2.0};
            double xValue = Double.parseDouble(xString);
            return Arrays.asList(xRange).contains(xValue);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateY(String yString) {
        try {
            double yValue = Double.parseDouble(yString);
            return yValue >= -3 && yValue <= 5;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateR(String rString) {
        try {
            Double rRange[] = {1.0, 2.0, 3.0, 4.0, 5.0};
            double rValue = Double.parseDouble(rString);
            return Arrays.asList(rRange).contains(rValue);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateValues(String xString, String yString, String rString) {
        return validateX(xString) && validateY(yString) && validateR(rString);
    }

    private boolean checkTriangle(double xValue, double yValue, double rValue) {
        return xValue <= 0 && yValue <= 0 && yValue <= (-xValue - rValue);
    }

    private boolean checkRectangle(double xValue, double yValue, double rValue) {
        return xValue >= 0 && yValue >= 0 && xValue <= rValue/2 && yValue <= rValue;
    }

    private boolean checkCircle(double xValue, double yValue, double rValue) {
        return xValue >= 0 && yValue <= 0 && Math.sqrt(xValue*xValue + yValue*yValue) <= rValue;
    }

    private boolean checkHit(double xValue, double yValue, double rValue) {
        return checkTriangle(xValue, yValue, rValue) || checkRectangle(xValue, yValue, rValue) ||
                checkCircle(xValue, yValue, rValue);
    }
}
