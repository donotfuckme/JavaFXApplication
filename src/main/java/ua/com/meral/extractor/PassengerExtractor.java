package ua.com.meral.extractor;

import org.apache.log4j.Logger;
import ua.com.meral.exception.BadDataException;
import ua.com.meral.model.Passenger;

import java.util.function.Function;

public class PassengerExtractor implements Function<String[], Passenger> {

    private static final Logger LOG = Logger.getLogger(PassengerExtractor.class);

    @Override
    public Passenger apply(String[] strings) {
        if (strings == null) {
            throw new BadDataException("Cannot extract Passenger from nothing");
        }
        int i = 0;
        Passenger passenger = new Passenger();
        passenger.setId(Integer.parseInt(strings[i++]));
        passenger.setSurvived(parseBool(strings[i++]));
        passenger.setPClass(Integer.parseInt(strings[i++]));
        passenger.setName(strings[i++] + strings[i++]);
        passenger.setSex(strings[i++]);
        passenger.setAge(parseFloat(strings[i++]));
        passenger.setSibSp(Integer.parseInt(strings[i++]));
        passenger.setParch(Integer.parseInt(strings[i++]));
        passenger.setTicket(strings[i++]);
        passenger.setFare(Float.parseFloat(strings[i++]));
        passenger.setCabin(strings[i++]);
        if (strings.length == 13) {
            passenger.setEmbarked(strings[i]);
        } else {
            passenger.setEmbarked("");
        }
        return passenger;
    }

    private float parseFloat(String string) {
        return string.isEmpty() ? 0 : Float.parseFloat(string);
    }

    private boolean parseBool(String string) {
        return Integer.parseInt(string) == 1;
    }
}
