JDBC assignments for classes at poznan university of technology

Zadanie 1
Napisz program, który wyświetla następujące informacje:
Zatrudniono X pracowników, w tym:
X1 w zespole Y1,
X2 w zespole Y2,

Zadanie 2
Napisz program, który wykonuje zapytanie odnajdujące wszystkich pracowników
zatrudnionych na etacie ASYSTENT i sortuje ich malejąco według pensji, a następnie wyświetla
asystenta, który zarabia najmniej, trzeciego najmniej zarabiającego asystenta i
przedostatniego asystenta w rankingu najmniej zarabiających asystentów (użyj do tego celu
kursorów przewijanych).

Zadanie 3
Dane są następujące tablice opisujące zmiany personalne:
int [] zwolnienia={150, 200, 230};
String [] zatrudnienia={"Kandefer", "Rygiel", "Boczar"};
Tablica zwolnienia zawiera identyfikatory pracowników, których należy zwolnić, a tablica
zatrudnienia – nazwiska pracowników, których należy zatrudnić.
Napisz program, który wykona w bazie danych zmiany opisane w tablicach. W celu
generowania kluczy dla nowych pracowników utwórz sekwencję (niekoniecznie z poziomu
własnego programu).

Zadanie 4
Napisz program wykonujący następujące czynności:
- Wyłącz automatyczne zatwierdzanie transakcji.
- Wyświetl wszystkie etaty.
- Wstaw nowy etat do tabeli ETATY.
- Ponownie wyświetl wszystkie etaty.
- Wycofaj transakcję.
- Ponownie wyświetl wszystkie etaty.
- Wstaw nowy etat do tabeli ETATY.
- Zatwierdź transakcję.
- Ponownie wyświetl wszystkie etaty.

Zadanie 5
Dane są następujące tablice opisujące nowych pracowników:
String [] nazwiska={"Woźniak", "Dąbrowski", "Kozłowski"};
int [] place={1300, 1700, 1500};
String []etaty={"ASYSTENT", "PROFESOR", "ADIUNKT"};
Kolejne pozycje tych tablic opisują różne atrybuty nowych pracowników. Wstaw nowych
pracowników do relacji PRACOWNICY wykorzystując mechanizm przygotowanych zapytań.
Ponownie wyświetl wszystkie etaty.

Zadanie 6
Spróbuj wstawić po 2000 pracowników: (1) wykonując sekwencyjnie polecenia wstawienia
rekordu do relacji oraz (2) łącząc wszystkie polecenia w jedno zadanie wsadowe. Całe zadanie
wykonaj w ramach jednej transakcji. Wykonaj pomiary czasu potrzebnego do wykonania
jednego i drugiego wstawiania.
Podpowiedź: przybliżony czas (w nanosekundach) można zmierzyć za pomocą statycznej
metody nanoTime klasy System:
long start = System.nanoTime();


Zadanie 7
Zaimplementuj operację zmiany wielkości liter (docelowo wielka pierwsza litera a pozostałe
litery małe) w nazwisku danego pracownika w postaci funkcji składowanej w bazie danych.
Funkcja ma mieć dwa parametry:
- identyfikator pracownika, który będzie podlegał zmianom (parametr wejściowy)
- nazwisko pracownika po dokonaniu zmian (parametr wyjściowy).
Wartością zwrotną funkcji ma być wartość 1 w przypadku pomyślnej zmiany oraz 0 w sytuacji,
gdy przekazany do funkcji identyfikator pracownika nie jest poprawny. Napisz program, który
wykorzysta zbudowaną przez Ciebie funkcję.
