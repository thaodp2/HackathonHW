package vn.techmaster.imdb.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import vn.techmaster.imdb.model.Film;

@Repository
public class FilmRepository implements IFilmRepo{
  private List<Film> films;

  public FilmRepository(@Value("${datafile}") String datafile) {
    try {
      File file = ResourceUtils.getFile("classpath:static/" + datafile);
      ObjectMapper mapper = new ObjectMapper(); // Dùng để ánh xạ cột trong CSV với từng trường trong POJO
      films = Arrays.asList(mapper.readValue(file, Film[].class));
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public List<Film> getAll() {
    return films;
  }

  @Override
  public Map<String, List<Film>> getFilmByCountry() {
   Map<String, List<Film>> listMapFilms = films.stream().distinct().collect(Collectors.groupingBy(Film::getCountry));
    return listMapFilms;
  }

  @Override
  public Entry<String, Long> getcountryMakeMostFilms() {
    var countYear = films.stream().collect(Collectors.groupingBy(Film::getTitle, Collectors.counting()));
//countYear.stream().max(Entry.<String, Long>comparingByValue().reversed())
//    countYear.sorted(Entry<String, Long>comparingByValue().reversed());
    return null;
  }

  @Override
  public Entry<Integer, Integer> yearMakeMostFilms() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getAllGeneres() {
//   List<String> listFilms= films.stream().map(Film::getGeneres);

//    List<String>listFilms = films.stream().collect(Collectors.groupingBy(Film::getGeneres));
     var listGeneres = films.stream().flatMap(f -> f.getGeneres().stream()).distinct().collect(Collectors.toList());
//    List<String>listGeneres  = films.stream().map(f->f.getGeneres()).flatMap(f->f.stream().collect(Collectors.toSet())).toList();
    return listGeneres;

//    return null;
  }

  @Override
  public List<Film> getFilmsMadeByCountryFromYearToYear(String country, int fromYear, int toYear) {
    List<Film> filmList = films.stream().filter(f->(f.getCountry().contains(country) && (f.getYear()>=fromYear))&&(f.getYear()<=toYear)).toList();
    return filmList;
  }

  @Override
  public Map<String, List<Film>> categorizeFilmByGenere() {
//    Map<String, List<Film>> filmList = films.stream().flatMap(Collectors.groupingBy((AbstractMap.SimpleEntry::getKey,
//            Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())));
//    Map<String, List<Film>> filmList = films.stream().collect(Collectors.groupingBy(f -> (f.getGeneres().stream()).distinct().collect(Collectors.toList())));
     return null;
  }

  @Override
  public List<Film> top5HighMarginFilms() {
//    var filmsRating = films.stream().sorted(Comparator.comparing(Film::getRating).reversed()).limit(5).toList();
    var filmsRating = films.stream().sorted((f1, f2) -> (f2.getRevenue() - f2.getCost()) - (f1.getRevenue() - f1.getCost()))
            .limit(5).collect(Collectors.toList());;
    return filmsRating;
  }

  @Override
  public List<Film> top5HighMarginFilmsIn1990to2000() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double ratioBetweenGenere(String genreX, String genreY) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<Film> top5FilmsHighRatingButLowMargin() {
    var filmsRating = films.stream().sorted(Comparator.comparing(Film::getRating).reversed()).limit(5).toList();
    return filmsRating.stream().sorted((f1,f2) -> (f2.getRevenue()-f2.getCost())-(f1.getRevenue())- f1.getCost()).limit(5).toList();
  }

}
