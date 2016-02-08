package com.tr.csvgenerator.FeatureService;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by roman on 08/02/16.
 */
@Service
@Scope("singleton")
public class TimeStampFeature implements  FeatureService {

    private Calendar calendar = new GregorianCalendar();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss.SSS");
    private Date date = null;
    private Random random = new Random();
    public  TimeStampFeature(){
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            date = calendar.getTime();
    }

    @Override
    public String getFeatureNameHeader() {
        return "TimeStamp";
    }

    @Override
    public String getValueForIndex() {
        int randomMilliseconds = random.nextInt(999);
        calendar.setTimeInMillis(date.getTime() + randomMilliseconds);
        date.setTime(calendar.getTime().getTime());
        String formattedDate = sdf.format(date);
        return  formattedDate;
    }
}
