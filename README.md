# FCC - File Change Checker
Program FCC jest projektem zaliczeniowym na przedmiot Bezpieczeństwo sieci i systemów operacyjnych.
Realizuje on zadanie sprawdzania czy plik zmienił się od ostatniego sprawdzenia.

## Jak działa program
- program należy skompilować lub uruchomić w IDE, a sam program uruchamia się w terminalu,
- należy wybrać opcje 1 oraz wskazać ścieżkę do pliku,
- jeśli plik nie był wcześniej sprawdzany to lokalnie zostanie zapisany plik database.json przechowujący hashe sprawdzanych plików wg ścieżki dostępowej,
- jeśli plik był wcześniej sprawdzany to wykona się porównanie hashy plików wg SHA-256 - program zwróci adekwatny komunikat,
- czynność można powtarzać wybierając ponownie opcję 1, każdorazowo podając ścieżkę do sprawdzanego pliku (program działa w pętli),
- wybór opcji 0 spowoduje zakończenie programu.

## Struktura projektu
Program podzielony jest na trzy główne pliki:
- `Main.java` - w tym pliku wykonuje się wywołanie menu, obsługa błędów i główna pętla programu,
- `HashCalc.java` - w tym pliku znajduje się samodzielnie napisana implementacja hashowania SHA-256,
- `DatabaseHandler.java` - w tym pliku znajduje się obsługa bazy hashy.

_Copyleft - All Rights Reversed._
