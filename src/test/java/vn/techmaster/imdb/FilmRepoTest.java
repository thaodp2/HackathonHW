package vn.techmaster.imdb;

import java.util.List;
import java.util.Map;
import java.util.Comparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import vn.techmaster.imdb.model.Film;
import vn.techmaster.imdb.repository.FilmRepository;
import java.util.stream.Collectors;

@SpringBootTest
class FilmRepoTest {
	@Autowired private FilmRepository filmRepo;

	@Test
	public void getAll() {
		List<Film> filmList = filmRepo.getAll();
	}

	@Test
	public void getFilmByCountry() {
//		Map<String, List<Film>> filmByCountry = filmRepo.getFilmByCountry();
//		assertThat(filmByCountry).isSortedAccordingTo(Comparator.comparing(Map.Entry<String, Long>::getValue));
//		filmByCountry.str
		var country_film = filmRepo.getFilmByCountry();
		List<Film> films = filmRepo.getAll();

		List<String> country = films.stream()
				.map(Film::getCountry)
				.distinct()
				.peek(System.out::println)
				.toList();
		assertThat(country).containsAll(country_film.keySet());
	}
}
