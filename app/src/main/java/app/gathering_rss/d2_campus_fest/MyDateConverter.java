package app.gathering_rss.d2_campus_fest;

import com.tickaroo.tikxml.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateConverter implements TypeConverter<Date> {
    private SimpleDateFormat format =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    @Override
    public Date read(String value) throws Exception {
        return format.parse(value);
    }

    @Override
    public String write(Date value) {
        return format.format(value);
    }
}
