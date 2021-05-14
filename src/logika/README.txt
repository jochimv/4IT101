
Provedené změny:

1) změněný popis hlubokého lesu
2) přidána lokace řeka
3) test pro třídu hra včetně prostoru
4) nápověda přejmenována na pomoc
5) jde používat jak nápověda, tak pomoc - téhle změny jsem dosáhl úpravou metody zpracujPrikaz() ve třídě Hra, konkrétně pomocí jednoduchého if.
Když je první vložené slovo "nápověda", metoda slovo změní na "pomoc" pro další zpracování
6) na velikosti písmen nezáleží - scanner přečte input a celý ho změní na malá písmena pro další zpracování.
(jedná se o změnu ve tříde TextoveRozhrani metoda prectiString())
7) vítězství doplněno
8) zahrňte vítězství do testů - hotovo
9)seznam východů setříděn dle abeceny - tohle jsem udělal ve třídě Prostor nahrazením HashSetu za TreeSet a override metody compareTo()


Další změny, které jsem provedl, ačkoliv nebyly ani jako volitelné - dělal jsem je kvůli estetické stránce v konzoli:

1) seznam příkazů taktéž setříděn podle abecedy - mapa s příkazy je nyní TreeMap
2) východy jsou oddlěleny čárkou, a ne mezerou (pro lepší čitelnost)
3) při vítězství (neboli dosažení chaloupky) se již nevypisuje seznam východů
-> seznam východů se vypíše jen tehdy, když jméno aktuální lokace není chaloupka (úprava metody dlouhyPopis() v Prostor)

Celé jsem to upravoval v IntelliJ. K testování používám JUnit 5 místo JUnit 4, které se používá na cvičení, ačkoliv jediná změna
je v anotaci - @Before se změnilo na @BeforeEach.


Co se týče další expanze, chtěl bych vytvořit mnoho lokací, ve kterých se bude muset plnit nějaký úkol. (hra ve hře)
Jestli to stihnu, chtěl bych vytvořit i uživatelské rozhraní.