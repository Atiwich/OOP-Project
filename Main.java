import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ============================================================
// ABSTRACT USER
// ============================================================

abstract class User {

    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public abstract String getRole();
}


// ============================================================
// CUSTOMER
// ============================================================

class Customer extends User {

    public Customer(String name, String email) {
        super(name, email);
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}


// ============================================================
// ADMIN
// ============================================================

class Admin extends User {

    public Admin(String name, String email) {
        super(name, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}


// ============================================================
// MOVIE
// ============================================================

class Movie {

    private String movieId;
    private String title;
    private String genre;
    private int duration;
    private double rating;

    public Movie(
            String movieId,
            String title,
            String genre,
            int duration,
            double rating) {

        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public double getRating() {
        return rating;
    }

    public void displayInfo() {

        System.out.println(
                movieId
                + " | "
                + title
                + " | "
                + genre
                + " | "
                + duration
                + " min"
                + " | Rating: "
                + rating
        );
    }
}


// ============================================================
// SEAT
// ============================================================

class Seat {

    private String seatNumber;
    private boolean reserved;

    public Seat(String seatNumber) {

        this.seatNumber = seatNumber;
        this.reserved = false;
    }

    public String getSeatNumber() {

        return seatNumber;
    }

    public boolean isReserved() {

        return reserved;
    }

    public boolean reserve() {

        if (reserved) {
            return false;
        }

        reserved = true;

        return true;
    }

    public void cancel() {

        reserved = false;
    }
}


// ============================================================
// SHOWTIME
// ============================================================

class ShowTime {

    private String showId;
    private Movie movie;
    private String date;
    private String time;

    private List<Seat> seats;

    public ShowTime(
            String showId,
            Movie movie,
            String date,
            String time) {

        this.showId = showId;
        this.movie = movie;
        this.date = date;
        this.time = time;

        seats = new ArrayList<>();

        // Create seats A1-A8
        // B1-B8
        // C1-C8
        // D1-D8

        for (char row = 'A'; row <= 'D'; row++) {

            for (int number = 1; number <= 8; number++) {

                seats.add(
                        new Seat(
                                row + String.valueOf(number)
                        )
                );
            }
        }
    }

    public String getShowId() {

        return showId;
    }

    public Movie getMovie() {

        return movie;
    }

    public String getDate() {

        return date;
    }

    public String getTime() {

        return time;
    }

    public Seat findSeat(String seatNumber) {

        for (Seat seat : seats) {

            if (seat.getSeatNumber()
                    .equalsIgnoreCase(seatNumber)) {

                return seat;
            }
        }

        return null;
    }

    public void displayShowtime() {

        System.out.println(
                showId
                + " | "
                + movie.getTitle()
                + " | "
                + date
                + " | "
                + time
        );
    }

    public void displaySeats() {

        System.out.println();
        System.out.println("========== SEAT MAP ==========");

        for (int i = 0; i < seats.size(); i++) {

            Seat seat = seats.get(i);

            if (seat.isReserved()) {

                System.out.print("[ X ] ");

            } else {

                System.out.print(
                        "[ "
                        + seat.getSeatNumber()
                        + " ] "
                );
            }

            if ((i + 1) % 8 == 0) {

                System.out.println();
            }
        }

        System.out.println();
        System.out.println("[ X ] = Reserved");
        System.out.println();
    }
}


// ============================================================
// PAYMENT
// ============================================================

class Payment {

    private String transactionId;
    private String paymentMethod;
    private double amount;
    private String status;

    public Payment(
            String transactionId,
            String paymentMethod,
            double amount) {

        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = "PAID";
    }

    public String getTransactionId() {

        return transactionId;
    }

    public String getPaymentMethod() {

        return paymentMethod;
    }

    public double getAmount() {

        return amount;
    }

    public String getStatus() {

        return status;
    }

    public void displayPayment() {

        System.out.println(
                "Transaction ID : "
                + transactionId
        );

        System.out.println(
                "Payment Method : "
                + paymentMethod
        );

        System.out.println(
                "Amount         : "
                + String.format("%.2f", amount)
                + " THB"
        );

        System.out.println(
                "Payment Status : "
                + status
        );
    }
}


// ============================================================
// BOOKING
// ============================================================

class Booking {

    private String bookingId;
    private Customer customer;
    private ShowTime showTime;

    private List<String> selectedSeats;

    private double totalPrice;

    private String status;

    private Payment payment;

    private static final double PRICE_PER_SEAT = 180.00;

    public Booking(
            String bookingId,
            Customer customer,
            ShowTime showTime,
            List<String> selectedSeats) {

        this.bookingId = bookingId;
        this.customer = customer;
        this.showTime = showTime;

        this.selectedSeats =
                new ArrayList<>(selectedSeats);

        this.totalPrice =
                selectedSeats.size()
                * PRICE_PER_SEAT;

        this.status = "Pending Payment";
    }

    public String getBookingId() {

        return bookingId;
    }

    public Customer getCustomer() {

        return customer;
    }

    public ShowTime getShowTime() {

        return showTime;
    }

    public List<String> getSelectedSeats() {

        return selectedSeats;
    }

    public double getTotalPrice() {

        return totalPrice;
    }

    public String getStatus() {

        return status;
    }

    public Payment getPayment() {

        return payment;
    }

    public void setPayment(Payment payment) {

        this.payment = payment;

        this.status = "Confirmed";
    }

    public void cancel() {

        for (String seatNumber : selectedSeats) {

            Seat seat =
                    showTime.findSeat(seatNumber);

            if (seat != null) {

                seat.cancel();
            }
        }

        status = "Cancelled";
    }

    public void displayBooking() {

        System.out.println();
        System.out.println("----------------------------------------");

        System.out.println(
                "Booking ID : "
                + bookingId
        );

        System.out.println(
                "Customer   : "
                + customer.getName()
        );

        System.out.println(
                "Movie      : "
                + showTime.getMovie().getTitle()
        );

        System.out.println(
                "Date       : "
                + showTime.getDate()
        );

        System.out.println(
                "Time       : "
                + showTime.getTime()
        );

        System.out.println(
                "Seats      : "
                + String.join(
                        ", ",
                        selectedSeats
                )
        );

        System.out.println(
                "Total      : "
                + String.format(
                        "%.2f",
                        totalPrice
                )
                + " THB"
        );

        System.out.println(
                "Status     : "
                + status
        );

        if (payment != null) {

            System.out.println(
                    "Payment    : "
                    + payment.getPaymentMethod()
            );

            System.out.println(
                    "Transaction: "
                    + payment.getTransactionId()
            );
        }

        System.out.println("----------------------------------------");
    }
}


// ============================================================
// CINEMA SYSTEM
// ============================================================

class CinemaSystem {

    private List<Movie> movies;
    private List<ShowTime> showTimes;
    private List<Booking> bookings;
    private List<Payment> payments;

    public CinemaSystem() {

        movies = new ArrayList<>();
        showTimes = new ArrayList<>();
        bookings = new ArrayList<>();
        payments = new ArrayList<>();

        createDemoData();
    }


    // ========================================================
    // MOVIE MANAGEMENT
    // ========================================================

    public void addMovie(Movie movie) {

        movies.add(movie);
    }

    public List<Movie> getMovies() {

        return movies;
    }

    public void displayMovies() {

        System.out.println();
        System.out.println("========== MOVIES ==========");

        if (movies.isEmpty()) {

            System.out.println(
                    "No movies available."
            );

            return;
        }

        for (Movie movie : movies) {

            movie.displayInfo();
        }
    }

    public void searchMovie(String keyword) {

        boolean found = false;

        System.out.println();
        System.out.println(
                "========== SEARCH RESULTS =========="
        );

        for (Movie movie : movies) {

            if (movie.getTitle()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                movie.displayInfo();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No movie found."
            );
        }
    }


    // ========================================================
    // SHOWTIME MANAGEMENT
    // ========================================================

    public void addShowTime(
            ShowTime showTime) {

        showTimes.add(showTime);
    }

    public void displayShowTimes(
            String movieId) {

        boolean found = false;

        System.out.println();
        System.out.println(
                "========== SHOWTIMES =========="
        );

        for (ShowTime show : showTimes) {

            if (show.getMovie()
                    .getMovieId()
                    .equalsIgnoreCase(movieId)) {

                show.displayShowtime();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No showtime found."
            );
        }
    }

    public ShowTime findShowTime(
            String showId) {

        for (ShowTime show : showTimes) {

            if (show.getShowId()
                    .equalsIgnoreCase(showId)) {

                return show;
            }
        }

        return null;
    }


    // ========================================================
    // BOOKING MANAGEMENT
    // ========================================================

    public Booking createBooking(
            Customer customer,
            String showId,
            List<String> seatNumbers) {

        ShowTime show =
                findShowTime(showId);

        if (show == null) {

            System.out.println(
                    "Showtime not found."
            );

            return null;
        }

        if (seatNumbers.isEmpty()) {

            System.out.println(
                    "Please select at least one seat."
            );

            return null;
        }

        // Check all seats first

        for (String seatNumber : seatNumbers) {

            Seat seat =
                    show.findSeat(seatNumber);

            if (seat == null) {

                System.out.println(
                        "Seat "
                        + seatNumber
                        + " does not exist."
                );

                return null;
            }

            if (seat.isReserved()) {

                System.out.println(
                        "Seat "
                        + seatNumber
                        + " is already reserved."
                );

                return null;
            }
        }

        // Reserve seats

        for (String seatNumber : seatNumbers) {

            Seat seat =
                    show.findSeat(seatNumber);

            seat.reserve();
        }

        String bookingId =
                "BK"
                + String.format(
                        "%03d",
                        bookings.size() + 1
                );

        Booking booking =
                new Booking(
                        bookingId,
                        customer,
                        show,
                        seatNumbers
                );

        bookings.add(booking);

        return booking;
    }


    public void displayBookings() {

        System.out.println();
        System.out.println(
                "========== MY BOOKINGS =========="
        );

        if (bookings.isEmpty()) {

            System.out.println(
                    "No bookings found."
            );

            return;
        }

        for (Booking booking : bookings) {

            booking.displayBooking();
        }
    }

    public Booking findBooking(
            String bookingId) {

        for (Booking booking : bookings) {

            if (booking.getBookingId()
                    .equalsIgnoreCase(bookingId)) {

                return booking;
            }
        }

        return null;
    }


    // ========================================================
    // PAYMENT
    // ========================================================

    public Payment processPayment(
            Booking booking,
            String paymentMethod) {

        String transactionId =
                "TXN"
                + String.format(
                        "%03d",
                        payments.size() + 1
                );

        Payment payment =
                new Payment(
                        transactionId,
                        paymentMethod,
                        booking.getTotalPrice()
                );

        payments.add(payment);

        booking.setPayment(payment);

        return payment;
    }


    public void displayPaymentHistory() {

        System.out.println();
        System.out.println(
                "========== PAYMENT HISTORY =========="
        );

        if (payments.isEmpty()) {

            System.out.println(
                    "No payment history."
            );

            return;
        }

        for (Payment payment : payments) {

            System.out.println();
            payment.displayPayment();
        }
    }


    // ========================================================
    // DEMO DATA
    // ========================================================

    private void createDemoData() {

        Movie movie1 =
                new Movie(
                        "M001",
                        "Interstellar",
                        "Sci-Fi",
                        169,
                        8.7
                );

        Movie movie2 =
                new Movie(
                        "M002",
                        "The Batman",
                        "Action",
                        176,
                        8.1
                );

        Movie movie3 =
                new Movie(
                        "M003",
                        "Inside Out 2",
                        "Animation",
                        96,
                        8.0
                );

        Movie movie4 =
                new Movie(
                        "M004",
                        "Your Name",
                        "Romance",
                        106,
                        8.8
                );

        addMovie(movie1);
        addMovie(movie2);
        addMovie(movie3);
        addMovie(movie4);

        int showNumber = 1;

        for (Movie movie : movies) {

            String[] times = {
                    "13:00",
                    "16:00",
                    "19:00"
            };

            for (String time : times) {

                ShowTime show =
                        new ShowTime(
                                String.format(
                                        "S%03d",
                                        showNumber
                                ),
                                movie,
                                "14/08/2026",
                                time
                        );

                addShowTime(show);

                showNumber++;
            }
        }
    }
}


// ============================================================
// MAIN
// ============================================================

public class Main {

    private static Scanner scanner =
            new Scanner(System.in);

    private static CinemaSystem system =
            new CinemaSystem();

    private static Customer customer =
            new Customer(
                    "Student",
                    "student@up.ac.th"
            );

    private static Admin admin =
            new Admin(
                    "Cinema Admin",
                    "admin@cinema.com"
            );


    // ========================================================
    // MAIN PROGRAM
    // ========================================================

    public static void main(String[] args) {

        showWelcome();

        boolean running = true;

        while (running) {

            showMenu();

            System.out.print(
                    "Select menu: "
            );

            String choice =
                    scanner.nextLine();

            switch (choice) {

                case "1":
                    displayMovies();
                    break;

                case "2":
                    searchMovie();
                    break;

                case "3":
                    displayShowtimes();
                    break;

                case "4":
                    booking();
                    break;

                case "5":
                    system.displayBookings();
                    break;

                case "6":
                    cancelBooking();
                    break;

                case "7":
                    addMovie();
                    break;

                case "8":
                    system.displayPaymentHistory();
                    break;

                case "9":
                    showReceipt();
                    break;

                case "0":

                    running = false;

                    System.out.println();
                    System.out.println(
                            "=========================================="
                    );

                    System.out.println(
                            "       Thank you for using CINEMAHUB!"
                    );

                    System.out.println(
                            "=========================================="
                    );

                    break;

                default:

                    System.out.println(
                            "\nInvalid menu. Please select 0-9."
                    );
            }
        }

        scanner.close();
    }


    // ========================================================
    // WELCOME
    // ========================================================

    private static void showWelcome() {

        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "              CINEMAHUB"
        );

        System.out.println(
                "       Cinema Ticket Booking System"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "User : "
                + customer.getName()
        );

        System.out.println(
                "Role : "
                + customer.getRole()
        );
    }


    // ========================================================
    // MENU
    // ========================================================

    private static void showMenu() {

        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "                    MENU"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "1. Display Movies"
        );

        System.out.println(
                "2. Search Movie"
        );

        System.out.println(
                "3. View Showtimes"
        );

        System.out.println(
                "4. Book Ticket"
        );

        System.out.println(
                "5. View My Bookings"
        );

        System.out.println(
                "6. Cancel Booking"
        );

        System.out.println(
                "7. Add Movie"
        );

        System.out.println(
                "8. Payment History"
        );

        System.out.println(
                "9. View Receipt"
        );

        System.out.println(
                "0. Exit"
        );

        System.out.println(
                "=========================================="
        );
    }


    // ========================================================
    // 1. DISPLAY MOVIES
    // ========================================================

    private static void displayMovies() {

        system.displayMovies();
    }


    // ========================================================
    // 2. SEARCH MOVIE
    // ========================================================

    private static void searchMovie() {

        System.out.print(
                "\nEnter movie title: "
        );

        String keyword =
                scanner.nextLine();

        system.searchMovie(keyword);
    }


    // ========================================================
    // 3. SHOWTIMES
    // ========================================================

    private static void displayShowtimes() {

        system.displayMovies();

        System.out.print(
                "\nEnter Movie ID: "
        );

        String movieId =
                scanner.nextLine();

        system.displayShowTimes(
                movieId
        );
    }


    // ========================================================
    // 4. BOOKING
    // ========================================================

    private static void booking() {

        system.displayMovies();

        System.out.print(
                "\nEnter Movie ID: "
        );

        String movieId =
                scanner.nextLine();

        system.displayShowTimes(
                movieId
        );

        System.out.print(
                "\nEnter Showtime ID: "
        );

        String showId =
                scanner.nextLine();

        ShowTime show =
                system.findShowTime(showId);

        if (show == null) {

            System.out.println(
                    "Showtime not found."
            );

            return;
        }

        show.displaySeats();

        System.out.print(
                "Enter seats "
                + "(Example: A1,A2,A3): "
        );

        String input =
                scanner.nextLine();

        String[] seats =
                input.split(",");

        List<String> selectedSeats =
                new ArrayList<>();

        for (String seat : seats) {

            String seatNumber =
                    seat.trim()
                            .toUpperCase();

            if (!selectedSeats
                    .contains(seatNumber)) {

                selectedSeats.add(
                        seatNumber
                );
            }
        }

        Booking booking =
                system.createBooking(
                        customer,
                        showId,
                        selectedSeats
                );

        if (booking == null) {

            return;
        }

        // ====================================================
        // BOOKING SUMMARY
        // ====================================================

        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "             BOOKING SUMMARY"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "Movie : "
                + show.getMovie().getTitle()
        );

        System.out.println(
                "Date  : "
                + show.getDate()
        );

        System.out.println(
                "Time  : "
                + show.getTime()
        );

        System.out.println(
                "Seats : "
                + String.join(
                        ", ",
                        selectedSeats
                )
        );

        System.out.println(
                "Total : "
                + String.format(
                        "%.2f",
                        booking.getTotalPrice()
                )
                + " THB"
        );

        System.out.println(
                "=========================================="
        );

        // Go to payment

        paymentMenu(booking);
    }


    // ========================================================
    // PAYMENT MENU
    // ========================================================

    private static void paymentMenu(
            Booking booking) {

        System.out.println();

        System.out.println(
                "========== PAYMENT =========="
        );

        System.out.println(
                "1. Cash"
        );

        System.out.println(
                "2. Credit / Debit Card"
        );

        System.out.println(
                "3. QR Payment"
        );

        System.out.println(
                "0. Cancel"
        );

        System.out.println(
                "=============================="
        );

        System.out.print(
                "Select payment method: "
        );

        String choice =
                scanner.nextLine();

        String paymentMethod;

        switch (choice) {

            case "1":
                paymentMethod = "Cash";
                break;

            case "2":
                paymentMethod =
                        "Credit / Debit Card";
                break;

            case "3":
                paymentMethod =
                        "QR Payment";
                break;

            case "0":

                booking.cancel();

                System.out.println(
                        "Booking cancelled."
                );

                return;

            default:

                System.out.println(
                        "Invalid payment method."
                );

                booking.cancel();

                return;
        }

        // QR PAYMENT

        if (paymentMethod.equals(
                "QR Payment")) {

            showQRCode(
                    booking.getTotalPrice()
            );
        }

        // Card payment

        if (paymentMethod.equals(
                "Credit / Debit Card")) {

            System.out.print(
                    "\nEnter card number: "
            );

            String cardNumber =
                    scanner.nextLine();

            if (cardNumber.length() < 4) {

                System.out.println(
                        "Invalid card number."
                );

                booking.cancel();

                return;
            }

            System.out.println(
                    "Card payment processing..."
            );
        }

        // Cash

        if (paymentMethod.equals("Cash")) {

            System.out.print(
                    "\nEnter cash amount: "
            );

            try {

                double cash =
                        Double.parseDouble(
                                scanner.nextLine()
                        );

                if (cash <
                        booking.getTotalPrice()) {

                    System.out.println(
                            "Insufficient cash."
                    );

                    booking.cancel();

                    return;
                }

                double change =
                        cash
                        - booking.getTotalPrice();

                System.out.println(
                        "Change : "
                        + String.format(
                                "%.2f",
                                change
                        )
                        + " THB"
                );

            } catch (Exception e) {

                System.out.println(
                        "Invalid amount."
                );

                booking.cancel();

                return;
            }
        }

        // Confirm payment

        System.out.println();

        System.out.println(
                "1. Confirm Payment"
        );

        System.out.println(
                "0. Cancel Payment"
        );

        System.out.print(
                "Select: "
        );

        String confirm =
                scanner.nextLine();

        if (!confirm.equals("1")) {

            booking.cancel();

            System.out.println(
                    "Payment cancelled."
            );

            return;
        }

        Payment payment =
                system.processPayment(
                        booking,
                        paymentMethod
                );

        System.out.println();

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "          PAYMENT SUCCESSFUL"
        );

        System.out.println(
                "=========================================="
        );

        payment.displayPayment();

        System.out.println();

        System.out.println(
                "Booking ID : "
                + booking.getBookingId()
        );

        System.out.println(
                "Status     : "
                + booking.getStatus()
        );

        System.out.println(
                "=========================================="
        );
    }


    // ========================================================
    // QR CODE
    // ========================================================

    private static void showQRCode(
            double amount) {

        System.out.println();

        System.out.println(
                "========== QR PAYMENT =========="
        );

        System.out.println(
                "Amount : "
                + String.format(
                        "%.2f",
                        amount
                )
                + " THB"
        );

        System.out.println();

        System.out.println(
                "+-----------------------+"
        );

        System.out.println(
                "| ##  #  ####  #  ##   |"
        );

        System.out.println(
                "| # # ## #  # ## # #   |"
        );

        System.out.println(
                "| ##  #  ####  #  ##   |"
        );

        System.out.println(
                "|   ##  ###  ##  #     |"
        );

        System.out.println(
                "| #  ##  # ##  ###  #  |"
        );

        System.out.println(
                "| ##  #  ####  #  ##   |"
        );

        System.out.println(
                "+-----------------------+"
        );

        System.out.println();

        System.out.println(
                "Please scan the QR code."
        );

        System.out.println(
                "This is a simulation."
        );
    }


    // ========================================================
    // 6. CANCEL BOOKING
    // ========================================================

    private static void cancelBooking() {

        system.displayBookings();

        System.out.print(
                "\nEnter Booking ID to cancel: "
        );

        String bookingId =
                scanner.nextLine();

        Booking booking =
                system.findBooking(
                        bookingId
                );

        if (booking == null) {

            System.out.println(
                    "Booking not found."
            );

            return;
        }

        if (booking.getStatus()
                .equals("Cancelled")) {

            System.out.println(
                    "This booking is already cancelled."
            );

            return;
        }

        booking.cancel();

        System.out.println(
                "Booking cancelled successfully."
        );
    }


    // ========================================================
    // 7. ADD MOVIE
    // ========================================================

    private static void addMovie() {

        System.out.println();
        System.out.println(
                "========== ADD MOVIE =========="
        );

        System.out.println(
                "Admin : "
                + admin.getName()
        );

        System.out.print(
                "Movie title: "
        );

        String title =
                scanner.nextLine();

        System.out.print(
                "Genre: "
        );

        String genre =
                scanner.nextLine();

        int duration;

        double rating;

        try {

            System.out.print(
                    "Duration (minutes): "
            );

            duration =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

            System.out.print(
                    "Rating: "
            );

            rating =
                    Double.parseDouble(
                            scanner.nextLine()
                    );

        } catch (Exception e) {

            System.out.println(
                    "Invalid number."
            );

            return;
        }

        String movieId =
                String.format(
                        "M%03d",
                        system.getMovies().size() + 1
                );

        Movie movie =
                new Movie(
                        movieId,
                        title,
                        genre,
                        duration,
                        rating
                );

        system.addMovie(movie);

        System.out.println();

        System.out.println(
                "Movie added successfully!"
        );

        movie.displayInfo();
    }


    // ========================================================
    // 9. RECEIPT
    // ========================================================

    private static void showReceipt() {

        system.displayBookings();

        System.out.print(
                "\nEnter Booking ID: "
        );

        String bookingId =
                scanner.nextLine();

        Booking booking =
                system.findBooking(
                        bookingId
                );

        if (booking == null) {

            System.out.println(
                    "Booking not found."
            );

            return;
        }

        if (booking.getPayment() == null) {

            System.out.println(
                    "Payment has not been completed."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "               CINEMAHUB"
        );

        System.out.println(
                "             PAYMENT RECEIPT"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "Booking ID : "
                + booking.getBookingId()
        );

        System.out.println(
                "Customer   : "
                + booking.getCustomer().getName()
        );

        System.out.println(
                "Movie      : "
                + booking.getShowTime()
                        .getMovie()
                        .getTitle()
        );

        System.out.println(
                "Date       : "
                + booking.getShowTime()
                        .getDate()
        );

        System.out.println(
                "Time       : "
                + booking.getShowTime()
                        .getTime()
        );

        System.out.println(
                "Seats      : "
                + String.join(
                        ", ",
                        booking.getSelectedSeats()
                )
        );

        System.out.println(
                "------------------------------------------"
        );

        System.out.println(
                "Total      : "
                + String.format(
                        "%.2f",
                        booking.getTotalPrice()
                )
                + " THB"
        );

        System.out.println(
                "Payment    : "
                + booking.getPayment()
                        .getPaymentMethod()
        );

        System.out.println(
                "Transaction: "
                + booking.getPayment()
                        .getTransactionId()
        );

        System.out.println(
                "Status     : "
                + booking.getPayment()
                        .getStatus()
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "        Thank you for using CINEMAHUB!"
        );

        System.out.println(
                "=========================================="
        );
    }
}