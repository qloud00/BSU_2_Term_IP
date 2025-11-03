import java.util.*;
import java.util.stream.Collectors;

public class Hotel {
    private String city;
    private String name;
    private int stars;
    private static List<Hotel> hotels = new ArrayList<>();

    public Hotel(String city, String name, int stars) {
        this.city = city;
        this.name = name;
        this.stars = stars;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    public static void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public static List<Hotel> getAllHotels() {
        return new ArrayList<>(hotels);
    }

    public static void clearAllHotels() {
        hotels.clear();
    }

    public static int getHotelsCount() {
        return hotels.size();
    }

    @Override
    public String toString() {
        return String.format("%-5s %d", name, stars);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hotel hotel = (Hotel) obj;
        return stars == hotel.stars &&
                city.equals(hotel.city) &&
                name.equals(hotel.name);
    }

    @Override
    public int hashCode() {
        return city.hashCode() + name.hashCode() + stars;
    }

    public static List<Hotel> sortByCityAndStars() {
        List<Hotel> sortedHotels = new ArrayList<>(hotels);
        sortedHotels.sort(
                Comparator.comparing(Hotel::getCity)
                        .thenComparing(Comparator.comparing(Hotel::getStars).reversed())
                        .thenComparing(Hotel::getName)
        );
        return sortedHotels;
    }

    public static List<Hotel> findHotelsByCity(String city) {
        return hotels.stream()
                .filter(hotel -> hotel.getCity().equalsIgnoreCase(city))
                .sorted(Comparator.comparing(Hotel::getStars).reversed()
                        .thenComparing(Hotel::getName))
                .collect(Collectors.toList());
    }

    public static Set<String> findCitiesByHotelName(String hotelName) {
        return hotels.stream()
                .filter(hotel -> hotel.getName().equalsIgnoreCase(hotelName))
                .map(Hotel::getCity)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public static Map<String, List<Hotel>> groupByCity() {
        return hotels.stream()
                .collect(Collectors.groupingBy(
                        Hotel::getCity,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    public static void displayAllHotels(List<Hotel> hotels) {
        List<Hotel> sortedHotels = sortByCityAndStars();

        String currentCity = "";
        for (Hotel hotel : sortedHotels) {
            if (!hotel.getCity().equals(currentCity)) {
                currentCity = hotel.getCity();
                System.out.println("\n--- " + currentCity + " ---");
            }
            System.out.println(hotel);
        }
    }

    public static void displayHotelsByCity(String city) {
        List<Hotel> cityHotels = findHotelsByCity(city);

        if (cityHotels.isEmpty()) {
            System.out.println("Отели в городе '" + city + "' не найдены.");
        } else {
            System.out.println("\nОтели в городе " + city);
            cityHotels.forEach(System.out::println);
        }
    }

    public static void displayCitiesByHotelName(String hotelName) {
        Set<String> cities = findCitiesByHotelName(hotelName);

        if (cities.isEmpty()) {
            System.out.println("Отель '" + hotelName + "' не найден ни в одном городе.");
        } else {
            System.out.println("\nГорода с отелем: " + hotelName);
            cities.forEach(System.out::println);
        }
    }
}