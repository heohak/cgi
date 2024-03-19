package org.example.cgi.repo;

import org.example.cgi.model.Film;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;

public interface FilmsFilters {

    public static Specification<Film> hasGenre(String genre) {
        return (root, query, criteriaBuilder) -> {
            if (genre == null || genre.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("genre")),
                    genre.toLowerCase()
            );
        };
    }

    public static Specification<Film> hasAgeRestriction(Integer ageRestriction) {
        return (root, query, criteriaBuilder) -> {
            if (ageRestriction == null || ageRestriction < 0) {
                return null;
            }
            return criteriaBuilder.equal(root.get("ageRestriction"), ageRestriction);
        };
    }

    public static Specification<Film> hasLanguage(String language) {
        return (root, query, criteriaBuilder) -> {
            if (language == null || language.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("language")),
                    language.toLowerCase()
            );
        };
    }

    public static Specification<Film> hasStartTime(LocalTime startTime) {
        return (root, query, criteriaBuilder) -> {
            if (startTime == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime);
        };
    }
}
