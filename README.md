
# Transfer App 7
  
Link do frontu: [[https://github.com/szpila127/transfer-app-7front](https://github.com/szpila127/transfer-app-7front)]

Aplikacja jest prostym symulatorem banku.
Główne funkcjonalności:
 - podczas rejestracji użytkownika sprawdza poprawność maila, aby nie tworzyć kont "duchów" - Email Validator API
 - stale monitoruje aktualne kursy walut [EUR, USD, GBP] - NBP Web API
 - przy przelewach bankowych sprawdza dostępne środki na koncie, z którego ma być wykonany przelew (przelicza wg aktualnego kursu waluty), jeśli środków jest wystarczająco, trafiają one na wskazane konto i są przeliczane na przypisaną do konta walutę.
 - dostępna jest możliwość natychmiastowego zwrotu przelewu
 - po utworzeniu użytkownika lub konta aplikacja wysyła informację na maila administratora - done przypisane w *application.properties*
 - działa na bazie **MySQL** - dane do użytkownika i bazy w *application.properties*
 - startuje lokalnie na porcie **8080** z adresem **API [/v1/ta7](/v1/ta7)**
 - scheduler wysyła maile codziennie o godz. 20:00 z informacją o ilości używkowników i ilości kont
 - podczas korzystania z aplikacji są tworzone zdarzenia i zapisywane do bazy danych, w celach informacyjnych

