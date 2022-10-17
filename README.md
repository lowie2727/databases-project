# databases-project
Loopwedstrijd

# schema
![schema](/schema.svg)

# loper
Als eerste is er een entiteit loper. Het belangrijkste attribuut van een loper is de loperID. De ID zal voor elke loper een uniek nummer zijn. De voornaam en achternaam van de lopers worden ook bijgehouden. Tot slot zijn er nog een aantal attributen zoals de leeftijd, het gewicht, de lengte en het adres van de lopers.
De entiteit loper heeft een één-of-meer op één-of-meer relatie met de entiteit wedstrijd. We hebben voor deze relatie gekozen omdat een wedstrijd minstens één loper moet hebben, maar natuurlijk liever meer dan één. Een loper kan ook pas geregistreerd worden als het aan minstens één wedstrijd heeft meegedaan, maar de loper mag natuurlijk ook aan meerdere wedstrijden meedoen.

# wedstrijd
De entiteit wedstrijd heeft als attributen wedstrijdID, wedstrijdNaam, wedstrijdAfstand en datum. De wedstrijdID is uniek voor elke wedstrijd.
Een wedstrijd bestaat dan uit verschillende etappes. Vandaar de relatie van één op één-of-meer. Een wedstrijd kan dus bestaan uit één of meerdere etappes. Een bepaalde etappe kan behoren tot één of meerdere wedstrijden. Het kan bijvoorbeeld zijn dat twee wedstrijden ongeveer in dezelfde richting verlopen en ze dus eenzelfde etappe delen.

# etappe
De entiteit etappe heeft als attributen een etappeID, een wedstrijdID, een locatie en de afstand. De etappeID is een uniek nummer voor elke etappe. De wedstrijdID is de ID van de wedstrijd waartoe de etappe behoort. De locatie is de startlocatie van de etappe en de afstand bedraagt de afstand die moet worden afgelegd bij de etappe.
De entiteit etappe heeft een één op meer relatie met de entiteit etappetijden. Dit is een één op meer relatie omdat de etappetijd van een loper maar tot één etappe kan behoren en een etappe meerdere etappetijden heeft van verschillende lopers.

# etappetijden
De entiteit etappetijden heeft als attributen een etappeID, een loperID en een etappeTijd. De etappeID is de ID van de etappe waar de tijd bij hoort. De loperID is de ID van de loper die de etappe heeft afgelegd en de tijd is de afgelegde tijd van de etappe.

# vrijwilliger
De entiteit vrijwilliger heeft als attributen een vrijwilligerID, een voornaam, een achternaam en een functie. De vrijwilligerID is weer een uniek nummer voor elke vrijwilliger. De voor- en achternaam van de vrijwilliger worden ook bijgehouden. Tot slot wordt voor elke vrijwilliger zijn of haar functie bijgehouden.
Elke wedstrijd heeft dan ook een aantal vrijwilligers of werknemers. Hiervoor wordt een één-of-meer op veel relatie gebruikt. We gebruiken deze relatie omdat een wedstrijd een aantal vrijwilligers nodig heeft om de wedstrijd in goede banen te leiden. Een vrijwilliger is echter niet beperkt om maar aan één wedstrijd mee te helpen.

# wedstrijd klassement
De entiteit wedstrijd klassement heeft als attributen een loperID, een wedstrijdID, de tijd en de eindpositie. De loperID is de ID van een loper die heeft deelgenomen aan de wedstrijd. De wedstrijdID is de ID van de wedstrijd waar het klassement bij hoort. De tijd is de tijd waarin de loper de wedstrijd heeft afgelegd en de eindpositie is de positie waar de loper is geëindigd op het einde van de wedstrijd.
Er is dan ook nog een relatie tussen de entiteit loper en de entiteit wedstrijdklassement. Deze relatie is een één-of-meer op één relatie. Een loper kan maar één keer tot een klassement behoren en tot een klassement behoren één of meerdere lopers.
Tussen de entiteit loper en de entiteit etappetijden is dezelfde één-of-meer op één relatie als tussen loper en wedstrijdklassement.
Tot slot heeft de entiteit wedstrijd nog een één op één relatie met de entiteit wedstrijdklassement. Dit is een één op één relatie omdat een wedstrijd maar één klassement kan hebben en een klassement maar tot één wedstrijd kan horen.

# algemeen klassement
De entiteit algemeen klassement heeft als attributen een algemeenKlassementID, een voor -en achternaam, woonplaats en prijzengeld. De algemeenKlassementID is een uniek nummer, waarbij nummer 1 de loper is die het hoogste staat in het klassement (en dus het meeste prijzengeld krijgt), het laatste nummer de loper is dat het laagste staat in het klassement.
De loperID gaat telkens met één omhoog bij een nieuwe loper, dus er is een relatie mogelijk tussen het algemeen klassement en loper. Waarbij elke loper 1 keer in het algemeen klassement staat en het algemeen klassement één of meerdere lopers bevat.
