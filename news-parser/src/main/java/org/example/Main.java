package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        Document doc = Jsoup.parse(readFile("data\\code.html"));
        Document doc = getDoc("https://www.e1.ru/text/");
        Elements elements = doc.select("article.OPHIx");

        elements.forEach(e -> {
            System.out.println("======================");
            String time = e.select("div.Hiu4B").text();
            System.out.println(time);
            String title = e.select("h2.h9Jmx").text();
            System.out.println(title);
            String text = e.select("div.TdYOd").text();
            System.out.println(text);
            String imgUrl = e.select("img").attr("src");
            System.out.println(imgUrl);
            downloadImageByUrl(imgUrl, title.substring(0,title.indexOf(" ", 5)));
            System.out.println();
        });
    }

    public static Document getDoc(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFile(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(builder::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void downloadImageByUrl(String url, String name) {

        try {

            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream fos = new FileOutputStream("data/" + name + url.substring(url.lastIndexOf(".")));
            byte[] data = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fos.write(data, 0, count);
                fos.flush();
            }
            in.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}