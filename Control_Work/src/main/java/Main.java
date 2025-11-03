import java.io.*;
import java.util.*;

public class Main {
    private static List<Hotel> hotels = new ArrayList<>();

    public static void main(String[] args) {
        readHotelsFromFile("hotel.txt");

        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Вывести все отели (сортировка по городам и звездам)");
        System.out.println("2. Найти отели по городу");
        System.out.println("3. Найти города по названию отеля");
        System.out.print("Выберите опцию: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: {
                Hotel.displayAllHotels(hotels);
                break;
            }
            case 2: {
                System.out.print("Введите название города: ");
                String city = scanner.nextLine().trim();
                Hotel.displayHotelsByCity(city);
                break;
            }
            case 3: {
                System.out.print("Введите название отеля: ");
                String hotelName = scanner.nextLine().trim();
                Hotel.displayCitiesByHotelName(hotelName);
                break;
            }
            default: {
                System.out.println("Выход из программы.");
                return;
            }
        }
    }

    private static void readHotelsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentCity = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (!line.matches(".*\\d.*")) {
                    currentCity = line;
                } else {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        StringBuilder hotelName = new StringBuilder();
                        for (int i = 0; i < parts.length - 1; i++) {
                            if (i > 0) hotelName.append(" ");
                            hotelName.append(parts[i]);
                        }

                        int stars = Integer.parseInt(parts[parts.length - 1]);
                        Hotel.addHotel(new Hotel(currentCity, hotelName.toString(), stars));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата данных в файле.");
        }
    }
}
