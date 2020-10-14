package slamach.weblab2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String xString = request.getParameter("xval");
        String yString = request.getParameter("yval");
        String rString = request.getParameter("rval");
        boolean isValuesValid = validateValues(xString, yString, rString);

        double xValue = isValuesValid ? Double.parseDouble(xString) : 0;
        double yValue = isValuesValid ? Double.parseDouble(yString) : 0;
        double rValue = isValuesValid ? Double.parseDouble(rString) : 0;
        boolean isHit = isValuesValid && checkHit(xValue, yValue, rValue);

        String currentTime = "0:0:0";
        String executionTime = "0:0:0";

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
        return true;
    }

    private boolean validateY(String yString) {
        return true;
    }

    private boolean validateR(String rString) {
        return true;
    }

    private boolean validateValues(String xString, String yString, String rString) {
        return validateX(xString) && validateY(yString) && validateR(rString);
    }

    private boolean checkTriangle(double xValue, double yValue, double rValue) {
        return true;
    }

    private boolean checkRectangle(double xValue, double yValue, double rValue) {
        return true;
    }

    private boolean checkCircle(double xValue, double yValue, double rValue) {
        return true;
    }

    private boolean checkHit(double xValue, double yValue, double rValue) {
        return checkTriangle(xValue, yValue, rValue) || checkRectangle(xValue, yValue, rValue) ||
                checkCircle(xValue, yValue, rValue);
    }
}
