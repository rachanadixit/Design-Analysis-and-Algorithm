/* 123B5F138 RACHANA DIXIT
Movie Recommendation System Optimization
A popular OTT platform, StreamFlix, offers personalized recommendations by sorting movies
based on user preferences, such as IMDB rating, release year, or watch time popularity.
However, during peak hours, sorting large datasets slows down the system.
As a backend engineer, you must:
● Implement Quicksort to efficiently sort movies based on various user-selected
parameters.
● Handle large datasets containing of movies while maintaining fast response times*/




import java.util.*;
import java.time.Duration;
import java.time.Instant;

class Movie {
    String title;
    float rating;
    int releaseYear;
    int popularity;

    Movie(String title, float rating, int releaseYear, int popularity) {
        this.title = title;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.popularity = popularity;
    }

    void display() {
        System.out.println(title + " | Rating: " + rating +
                " | Year: " + releaseYear +
                " | Popularity: " + popularity);
    }
}

public class MovieSorting {
    public static void quickSort(List<Movie> movies, int low, int high, Comparator<Movie> comparator) {
        if (low < high) {
            int pivotIndex = partition(movies, low, high, comparator);
            quickSort(movies, low, pivotIndex - 1, comparator);
            quickSort(movies, pivotIndex + 1, high, comparator);
        }
    }

    private static int partition(List<Movie> movies, int low, int high, Comparator<Movie> comparator) {
        Movie pivot = movies.get(high);
        int i = low;
        for (int j = low; j < high; j++) {
            if (comparator.compare(movies.get(j), pivot) < 0) {
                Collections.swap(movies, i, j);
                i++;
            }
        }
        Collections.swap(movies, i, high);
        return i;
    }

    private static Comparator<Movie> compareByRating = Comparator.comparingDouble(m -> m.rating);
    private static Comparator<Movie> compareByYear = Comparator.comparingInt(m -> m.releaseYear);
    private static Comparator<Movie> compareByPopularity = Comparator.comparingInt(m -> m.popularity);

    public static List<Movie> generateMovies(int n) {
        List<Movie> movies = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            String title = "Movie " + (i + 1);
            float rating = (rand.nextInt(90) + 10) / 10.0f; 
            int releaseYear = rand.nextInt(45) + 1980; 
            int popularity = rand.nextInt(1_000_000) + 1000; 
            movies.add(new Movie(title, rating, releaseYear, popularity));
        }
        return movies;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numMovies = 100000; 
        List<Movie> movies = generateMovies(numMovies);

        System.out.print("Sort movies by (rating/year/popularity): ");
        String sortBy = sc.nextLine().trim().toLowerCase();

        Comparator<Movie> comparator;
        switch (sortBy) {
            case "year":
                comparator = compareByYear;
                break;
            case "popularity":
                comparator = compareByPopularity;
                break;
            case "rating":
            default:
                comparator = compareByRating;
                break;
        }

        System.out.println("\nSorting " + numMovies + " movies by " + sortBy + "...");
        Instant start = Instant.now();
        quickSort(movies, 0, movies.size() - 1, comparator);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        System.out.println("\nTop 10 movies by " + sortBy + ":");
        for (int i = numMovies - 1; i >= numMovies - 10; i--) {
            movies.get(i).display();
        }

        System.out.printf("\nSorted %,d movies in %.3f seconds.%n",
            numMovies, duration.toMillis() / 1000.0);
        sc.close();
    }

}
