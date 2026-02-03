package org.example.service;

import org.example.model.Zodiac;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class ParserGoroskop {
    private final static String URL = "https://obltv.ru/news/goroskop-na-28-yanvarya-rakam-den-sozdaniya-uyuta-vesam-garmonizaciya-otnoshenij-30646";
    private final static String URLOven = "https://horo.mail.ru/prediction/aries/today/";
    private final static String URLTelec = "https://horo.mail.ru/prediction/taurus/today/";
    private final static String URLTwins = "https://horo.mail.ru/prediction/gemini/today/";
    private final static String URLLion = "https://horo.mail.ru/prediction/leo/today/";
    private final static String URLRak = "https://horo.mail.ru/prediction/cancer/today/";
    private final static String URLDeva = "https://horo.mail.ru/prediction/virgo/today/";
    private final static String URLVesy = "https://horo.mail.ru/prediction/libra/today/";
    private final static String URLScorpio = "https://horo.mail.ru/prediction/scorpio/today/";
    private final static String URLStrelec = "https://horo.mail.ru/prediction/sagittarius/today/";
    private final static String URLKozerog = "https://horo.mail.ru/prediction/capricorn/today/";
    private final static String URLVodoley = "https://horo.mail.ru/prediction/aquarius/today/";
    private final static String URLRyba = "https://horo.mail.ru/prediction/pisces/today/";
    private final static String[] URLS = {URLOven, URLTelec, URLTwins, URLLion, URLRak,
                                            URLDeva, URLVesy, URLScorpio, URLStrelec,
                                            URLKozerog, URLVodoley, URLRyba};

    private static HashMap<String, Zodiac> zodiacs = new HashMap<>();
    public static void parse(){
        for(String url : URLS){
            Zodiac zodiac = getZodiacData(url);
            zodiacs.put(zodiac.getName(), zodiac);
        }
    }

    public static Zodiac getZodiacData(String url){
        String name;
        String prediction;
        String date;

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10000)
                    .get();

            name = extractedName(url);
            prediction = extractPrediction(doc);
            date =extractDate(doc);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Zodiac(name, prediction, date);
    }

    private static String extractedName(String url){
        String[] parts = url.split("/");
        for (String part : parts) {
            if (part.equals("aries")) return "Овен";
            if (part.equals("taurus")) return "Телец";
            if (part.equals("gemini")) return "Близнецы";
            if (part.equals("cancer")) return "Рак";
            if (part.equals("leo")) return "Лев";
            if (part.equals("virgo")) return "Дева";
            if (part.equals("libra")) return "Весы";
            if (part.equals("scorpio")) return "Скорпион";
            if (part.equals("sagittarius")) return "Стрелец";
            if (part.equals("capricorn")) return "Козерог";
            if (part.equals("aquarius")) return "Водолей";
            if (part.equals("pisces")) return "Рыбы";
        }
        return "Неизвестный знак";
    }

    private static String extractPrediction(Document doc){
        Elements paragraphs = doc.select("p");
        for(Element p : paragraphs){
            String text = p.text().trim();
            if(text.length() > 100 && !text.contains("реклама") && !text.contains("©")){
                return text;
            }
        }
        return "Прогноз не найден";
    }

    private static String extractDate(Document doc){
        String title = doc.title();
        if(title.contains("сегодня") || title.contains("today")){
            return "Сегодня";
        }
        return "Неизвестная дата";
    }


}
