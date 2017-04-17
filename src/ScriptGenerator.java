import com.github.javafaker.Faker;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScriptGenerator {

    /**
     * PK consecutive
     */
    private static int user_seq_site = 28;
    private static int event_seq_site = 4;
    private static int site_seq_site = 5;
    private static int artist_seq_artist = 19;

    /**
     * Customizable number query results
     */
    private static int num_sites = 100;
    private static int num_artists = 100;

    /**
     * Calculated
     */
    private static int user_seq_artist = user_seq_site + num_sites + 1;

    /**
     * Main method that generates de SQL queries.
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            PrintWriter siteWriter = new PrintWriter("sites_script.sql", "UTF-8");
            generateSiteQuery(siteWriter);
            siteWriter.close();
            PrintWriter artistWriter = new PrintWriter("artists_script.sql", "UTF-8");
            generateArtistQuery(artistWriter);
            artistWriter.close();
        } catch (IOException e) {
        }
    }


    /**
     * Generate the SQL queries for sites.
     *
     * @param printWriter printWriter to write.
     */
    private static void generateSiteQuery(PrintWriter printWriter) {

        Faker faker = new Faker(new Locale("es-MX"));
        for (int i = 0; i < num_sites; i++) {
            //User
            String name = faker.name().firstName() + "." + faker.name().lastName();

            //Site
            String siteName = faker.company().name();
            String phoneNumber = faker.phoneNumber().phoneNumber();
            String address = faker.address().fullAddress();
            int maxAttendance = faker.number().numberBetween(100, 1001);

            //Event
            String eventName = faker.team().name();
            String eventTitle = faker.commerce().department();
            String eventDescription = faker.company().catchPhrase();
            String req = faker.music().instrument();
            int duration = faker.number().numberBetween(1, 9);
            int price = faker.number().numberBetween(50000, 500000);
            int gender = faker.number().numberBetween(1, 4);
            int sbGender1 = faker.number().numberBetween(1, 8);
            int sbGender2 = faker.number().numberBetween(8, 14);
            Date dateInit = faker.date().between(new Date(), new Date(1525409460000L));
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(dateInit); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, 3); // adds one hour
            String dateFinal = dt.format(cal.getTime());

            //Queries
            printWriter.println("INSERT INTO public.auth_user (id, password, last_login, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined) " + "VALUES (" + (user_seq_site + 1 + i) + ",'pbkdf2_sha256$24000$2Pi6CjMjgA7D$NtEcGcuFbiuWTJSVGl+oHo2pvK6zzJTJTAd6sfQD8j4=', '2017-03-10 19:53:46.474341', true, '" + name + "', '', '', '" + name + "@contact.com', true, " + "true, '2017-03-10 19:50:52.412073');");
            printWriter.println("INSERT INTO public.dplanapp_site (id, site_name, phone, address, max_attendance, user_id, photo) " + "VALUES (" + (site_seq_site + 1 + i) + ", '" + siteName + "', " + phoneNumber + ", '" + address + "', " + maxAttendance + ", " + (user_seq_site + 1 + i) + ", 'profilePictures/eyesFB2.0.png');");
            printWriter.println("INSERT INTO public.dplanapp_event (id, name, title_event, description, technical_req, duration, max_attendance, price, date_event, date_ini, date_end, status, " + "gender_id,sub_gender_id,winner_id, site_id) " + "VALUES(" + (event_seq_site + 1 + i) + ", '" + eventName + "', '" + eventTitle + "', '" + eventDescription + "', '" + req + "', " + duration + ", " + maxAttendance + ", " + price + ", '" + dt.format(dateInit) + "', " + "'" + dt.format(dateInit) + "', '" + dateFinal + "', 'O', " + gender + ", " + (gender == 1 ? sbGender1 : sbGender2) + ", NULL, " + (site_seq_site + 1 + i) + ");");
        }
    }

    /**
     * Generate the SQL queries for artists.
     *
     * @param printWriter printWriter to write.
     */
    private static void generateArtistQuery(PrintWriter printWriter) {

        for (int i = 0; i < num_artists; i++) {
            Faker faker = new Faker(new Locale("es-MX"));
            //User
            String name = faker.name().firstName() + "." + faker.name().lastName();

            //Artist
            String artistName = faker.artist().name();
            String phoneNumber = faker.phoneNumber().phoneNumber();
            int gender = faker.number().numberBetween(1, 4);
            int sbGender1 = faker.number().numberBetween(1, 8);
            int sbGender2 = faker.number().numberBetween(8, 14);
            String biography = faker.lorem().fixedString(100);

            //Queries
            printWriter.println("INSERT INTO public.auth_user (id, password, last_login, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined) " + "VALUES (" + (user_seq_artist + i) + ",'pbkdf2_sha256$24000$2Pi6CjMjgA7D$NtEcGcuFbiuWTJSVGl+oHo2pvK6zzJTJTAd6sfQD8j4=', '2017-03-10 19:53:46.474341', true, '" + name + "', '', '', '" + name + "@contact" + ".com', true, true, '2017-03-10 19:50:52.412073');");
            printWriter.println("INSERT INTO public.dplanapp_artist (id, artistic_name, phone, photo, demo, user_id, gender_id, sub_gender_id, biography) VALUES (" + (artist_seq_artist + i) + ", '" + artistName + "', " + phoneNumber + ", 'profilePictures/github.png', 'https://www.youtube.com/watch?v=JGwWNGJdvx8', " + (user_seq_artist + i) + ", " + gender + ", " + (gender == 1 ? sbGender1 : sbGender2) + ", '" + biography + "');");
        }
    }


}
