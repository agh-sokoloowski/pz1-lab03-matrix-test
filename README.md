# Laboratorium 3

Celem laboratorium jest pokazanie, jak zazwyczaj przeprowadza się testowanie kodu. Zwykle testowane są poszczególne fragmenty kodu, np. pojedyncze metody klasy. Te fragmenty nazywane są jednostkami, stąd termin testy jednostkowe (unit testing)

O testowaniu:

- [Opis ogólny bez szczegółów technicznych](https://kobietydokodu.pl/17-testy-jednostkowe/)
- [Przykłady, wyjaśnienia, testy w IntelliJ](http://www.samouczekprogramisty.pl/testy-jednostkowe-z-junit/)

Ogólne zasady:

- testy umieszczamy w odrębnych klasach, np. MatrixTest
- klasa z testami jest w tym samym pakiecie, co klasa testowana (czyli pakiety Matrix i MatrixTest są identyczne)
- testów nie mieszamy z kodem, klasy służące do testowania umieszczamy w odrębnym folderze, zazwyczaj test

W wolnych chwilach przeczytaj także o [Convention over configuration](https://en.wikipedia.org/wiki/Convention_over_configuration)

## Konfiguracja IntelliJ

Zanim naciśniesz <kbd>Ctrl</kbd><kbd>Shift</kbd><kbd>T</kbd> i zbudujesz pierwsze testy – musisz przeprowadzić niewielką zmianę w strukturze projektu. Należy dodać katalog `test` i oznaczyć go jako *content root*. W tym katalogu będą umieszczane klasy z testami. (W Netbeans to już jest przygotowane.)

1. Zaznacz węzeł z nazwą projektu i wybierz opcję: File → New → Directory i podaj nazwę test. Katalog powienien pojawić się na tym samym poziomie, co `src`.
2. Wybierz File → Project structure → Modules i oznacz katalog test jako `Tests`. Opis konfiguracji znajdziesz tu: [https://www.jetbrains.com/help/idea/configuring-content-roots.html](https://www.jetbrains.com/help/idea/configuring-content-roots.html)

## Dodaj klasę z testami

Umieść kursor w linii `public class Matrix {` i użyj <kbd>Alt</kbd><kbd>Enter</kbd> lub <kbd>Ctrl</kbd><kbd>Shift</kbd><kbd>T</kbd>, wybierz Create Test. W oknie, które się pojawi wybierz `Testing library: JUnit4` oraz zaznacz wszystkie widoczne metody. Procedura jest też opisana tu: [https://www.jetbrains.com/help/idea/creating-tests.html](https://www.jetbrains.com/help/idea/creating-tests.html).

Oczekiwany efekt: w strukturze projektu pojawi się plik `MatrixTest` umieszczony w katalogu `test` i takim samym pakiecie, jak klasa `Matrix`. Klasa `MatrixTest` będzie zawierała szkielet pustych metod o nazwach odpowiadających metodom klasy `Matrix`.

**Zanim przejdziesz dalej:** Zmień nazwę metody `toString()` w `MatrixTest` na `testToString()`.

## Dodaj bibliotekę JUnit

- Uruchom `MatrixTest` (np. klikając prawym klawiszem i wybierając `Run MatrixTest`)
- Testy raczej nie wykonają się - brakuje w projekcie biblioteki JUnit.
- W linii `import static org.junit.Assert.*;` nazwa `junit` wyświetlana jest na czerwono. Umieść kursor na `junit`, naciśnij <kbd>Alt</kbd><kbd>Enter</kbd> i wybierz `Add JUnit4 to classpath`. Następnie wybierz skąd ma pochodzić biblioteka (wewnętrzna dystrybucja). JUnit4 powinno pojawić się w strukturze projektu (external libraries).
  - Lepiej wybrać `More actions...` i `Add JUnit5.8.1 to classpath`, po czym zatwierdzić przyciskiem `OK`. JUnit5 pozwala na więcej, w tym łatwiejsze testowanie wyjątków. Lab został wykonany z użyciem JUnit 5.
- Uruchom ponownie `MatrixTest`. Wszystkie testy powinny przejść pomyślnie.

To było dość skomplikowane. Osobiście wolę Netbeans, ponieważ projekty są od razu skonfigurowane do tworzenia testów. Generowane są też testy dla konstruktora i metody na ogół nazywają się `testXXXX()`.

## Jak działa testowanie

1. Biblioteka JUnit odnajduje wszystkie funkcje oznaczone, jako `@Test`. To są tak zwane *adnotacje*, czyli dodatkowe informacje dołączone do kodu, które nie są bezpośrednio wykorzystywane przez kompilator, ale przez zewnętrzne narzędzia i biblioteki, które je potrafią rozpoznać i zinterpretować.
2. Następnie uruchamiane są wszystkie funkcje oznaczone jako `@Test`. Jeśli funkcja nie spowoduje generacji wyjątku - test zakońcozny jest sukcesem, jeśli zostanie wygenerowany wyjątek, test kończy się porażką.

Oczywiście, puste funkcje nie generują wyjątków, więc kończą się sukcesem.

Biblioteka JUnit definiuje szereg funkcji `assertXYZ()`, które testują warunki i w razie ich niespełnienia generują wyjątki. Wyjątek może być też wygenerowany bez warunku: `fail()`.

Dodaj na początku import: `import static org.junit.Assert.*;`

Wprowadź przykładowe asercje do kolejnych funkcji i sprawdź ich działanie.

```java
fail("To nie działa");
assertTrue(1 > 2);
assertEquals(1, 1);
assertNotEquals(2.0 * 2.0, 4.0);
assertEquals(1.0, 1.1, 0.1); // testowanie równości wartości double z dokładnością 0.1

double[][] first = {{1, 2}, {3}};
double[][] second = {{1, 2}, {4}};
assertArrayEquals(first[0], second[0], .1);
assertArrayEquals(first[1], second[1], .1);
```

## Napisz testy

Pisanie kodu testującego może być całkiem twórczym zajęciem.

- Nigdy nie wprowadzaj danych z klawiatury
- Używaj macierzy niewielkich rozmiarów typu 2x3, 3x4, itp. lub losuj rozmiary i zawartość macierzy
- Jeżeli spodziewasz się jakiegoś wyniku result, np. `[[1,2], [3,4]]` to utwórz drugą macierz z wartościami do porównania, odejmij rezultat i wyznacz normę - która oczywiście powinna mieć niewielką wartość.

```java
Matrix expected = new Matrix(new double[][]{{1, 2}, {3, 4}});
Matrix diff = expected.sub(result);
double err = diff.frobenius();
assertEquals(err, 0, 1e-5);
```
| Metoda                       | Propozycja                                                                                                                          |
| ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| `Matrix(int rows, int cols)` | sprawdź rozmiary macierzy                                                                                                           |
| `Matrix(double[][] d)`       | wywołaj `asArray()` i sprawdź długości wierszy oraz czy brakujące wcześniej wartości są zerowe                                          |
| `double[][] asArray()`       | utwórz obiekt Matrix podając prostokątną tablicę d i sprawdź, poszczególne wiersze są sobie równe                                   |
| `get`/`set`                  | sprawdź czy odczytujesz prawidłowe wartości                                                                                         |
| `toString()`                 | Policz przecinki i nawiasy                                                                                                          |
| `reshape()`                  | spróbuj przechwycić wyjątek                                                                                                         |
| `sub()`                      | dedykowane testy lub odejmij identyczne macierze i oblicz normę frobeniusa                                                          |
| `mul()`                      | dedykowane testy lub pomnóż przez -1 i dodaj?                                                                                       |
| `add()`                      | chyba podobnie                                                                                                                      |
| `div()`                      | dedykowane testy lub podziel macierz przez samą siebie, oblicz normę frobeniusa, sprawdź czy jest równa iloczynowi wierszy i kolumn |
| `dot()`                      | dedykowany test wykonany wcześniej na kartce papieru?                                                                               |
| `eye()`                      | norma frobeniusa równa n                                                                                                            |
| odwracanie                   | Czy wektor pomnożony przez macierz i jej odwrotność wraca do poprzedniej wartości, czy A-1*A = I                                    |

### Test `toString()`

```java
String s = "[[1.0,2.3,4.56], [12.3,  45, 21.8]]";
s = s.replaceAll("(\\[|\\]|\\s)+", "");
String[] t = s.split("(,)+");
for (String x : t){
    System.out.println(String.format("\'%s\'", x));
}

double[] d = new double[t.length];
for (int i = 0; i < t.length; i++) {
    d[i] = Double.parseDouble(t[i]);
}

double[][] arr = new double[1][];
arr[0] = d;

for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr[i].length; j++) {
        System.out.println(arr[i][j]);
    }
}
```
