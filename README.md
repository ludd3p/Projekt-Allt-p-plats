# Allt på plats

Allt på plats är en enkel applikation för serveringsställen som hjälper användaren med att hålla koll på olika 
varor som finns i lager, och recept som använder sig av varorna.  
Den tillhörande webapplikationen ger användaren information om lager- och orderstatus var hen än är.

## Beskrivning
Applikationen har ett antal funktioner för recepthantering, inventering och beställningar.
###Lager 
```
I applikationen kan användaren hantera ett virtuellt lager som representerar ett verkligt lager
genom att lägga till artiklar och dess volym.
```
### Recept
```
Användaren kan lägga till recept i applikationen, ett recept skapas av varor som finns inlagda i systemet. Användaren kan själv välja om den vill lägga till steg för steg instruktioner i receptet.  
Varje ingrediens i ett recept läggs till med den mängd som ska användas.  
När användaren registrerar att den är färdig med ett recept så dras mängderna av de använda varorna av från lagret.  
```
### Ordrar
```
Applikationen kan hantera ordrar och inköpslistor för flera leverantörer.  
När en vara hamnar under en specificerad volym läggs den automatiskt till på inköpslista för den leverantör som säljer varan.  
Användaren kan manuellt lägga till en vara på inköpslista.  
```

### Notifikationer
```
Applikationen har ett inbyggt system för notifikationer som varnar användaren när vissa händelser inträffar, till exempel när en varas volym är låg, när en högtid närmar sig eller när det är beställningsdag för en leverantör.
```


## Körning
Hämta den senaste versionen från https://github.com/ludd3p/Projekt-Allt-p-plats och starta applikationen genom att köra Main.  
  
För att öppna webapplikation hämta senaste version från https://github.com/HazemE1/APP_WEB

## Skapare
Ludvig Wedin Pettersson.  
Hazem Elkhalil.  
Qassem Aburas.  
Jonathan Engström.  
Alex Bergenholtz.  
