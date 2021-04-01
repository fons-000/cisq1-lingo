# CISQ1: Lingo Trainer

[![Java CI](https://github.com/fons-000/cisq1-lingo/actions/workflows/build.yml/badge.svg)](https://github.com/fons-000/cisq1-lingo/actions/workflows/build.yml)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fons-000_cisq1-lingo&metric=coverage)](https://sonarcloud.io/dashboard?id=fons-000_cisq1-lingo)

[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=fons-000_cisq1-lingo&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=fons-000_cisq1-lingo)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=fons-000_cisq1-lingo&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=fons-000_cisq1-lingo)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fons-000_cisq1-lingo&metric=security_rating)](https://sonarcloud.io/dashboard?id=fons-000_cisq1-lingo)

# Vulnerability Analysis

## A1:2017 Injection

### Description
Injectie aanvallen zijn aanvallen van users die "vijandelijke" data sturen naar de ontvangende kant van de applicatie/de interpreter.

Deze "vijandelijke data" wordt vaak in SQL, LDAP, XPath of NoSQl queries gevonden.

### Risk
[comment]: <> (Assessment of risk. Discussion of authentication and authorization.)
Het injecteren van vijandelijke data kan voor dataverlies, ongeldige data, verspreiding van data,
verlies van beheer of het verlies van toegang tot datasources betekenen.

Het toevoegen van authentication en authorization, kan ervoor zorgen dat sommige functionaliteiten al afgeschermd zijn van invoer.
In geval van injection, maakt het weinig uit welke functionaliteit het is, als er maar een datasource interpreter naar luistert.
Authenticatie of authorisatie is hier niet **de** oplossing voor.

### Counter-measures
[comment]: <> (Counter measures tasks.)
Het scheiden van injectie en commands & queries.
- Zo kan je bijvoorbeeld een veilige API gebruiken. 
- Je kan
een ORM gebruiken zoals Hibernate, voor het inserten van data/objecten.
- Als je direct tegen een data interpreter gaat praten, zorg er dan voor
dat query's gesanitized zijn. Denk aan escaping, filtering en het valideren tegen een whitelist.

## A7:2017 Cross-Site Scripting (XSS)

### Description
Cross-Site Scripting (XSS) zorgt ervoor dat een code script remote
uitgevoerd wordt in het slachtoffers browser.

### Risk
[comment]: <> (Assessment of risk. Discussion of authentication and authorization.)
Credentials en sessie-info kan gestolen worden.
Malware kan ook overgedragen worden op de slachtoffer's computer.

Authenticatie en authorisatie heeft geen invloed op het risico can cross-site scripting.
Als een gebruiker op een linkje of plaatje klikt van een andere gebruiker, kan hier een javascript achter zitten,
wat een javascript uitvoert.

### Counter-measures
[comment]: <> (Counter measures tasks.)
- Frameworks gebruiken dat automatische XSS escaped.
Denk aan Ruby on Rails of React JS.
Je moet de limitaties van elk framework's XSS protection en handlers opzoeken
per use case. 
- Escape zelf data van "sketchy" data in de HTML output (body, attribute, JavaScript, CSS or URL).
- Encodeer context-gevoelige data client side.
- Regel een Content Security Policy (CSP), die in-depth kijkt naar eventuele XSS content.

## A10:2017 Insufficient Logging & Monitoring

### Description
Het niet loggen en monitoren van gebruikers hun acties kan leiden tot succesvolle aanvallen in de applicatie.

### Risk
[comment]: <> (Assessment of risk. Discussion of authentication and authorization.)
* Weghalen van source code en content & het neerhalen van projecten als gevolg
* False log-ins of andere attacks van attackers die onopgemerkt blijven, wat schade aan kan richten.
* Malware die onopgemerkt in de code komt.

### Counter-measures
[comment]: <> (Counter measures tasks.)
- Authenticatie van personen is handig om te loggen. Zijn er transacties van dezelfde gebruiker in andere landen?
  Is de gebruiker 5 minuten later in-een-keer in China? Vreemd.
- Zorg ervoor dat authorisatie gegeven wordt aan niet-verdachte/voldoende geauthenticeerde gebruikers.
- Zorg ervoor dat acties in een goed formaat wordt gelogd.
- Transacties van belangrijke waarde, moeten een hoge audit trail hebben.
  Dan zou je kunnen denken aan two-way authentication, append-only database tabellen etc.
- Alert verdacht activiteit tijdig met monitoring.
- Stel een incident response of recovery plan op.
- Maak gebruik van commercial of open sourced applicaties die rekening houden met ModSecurity, log relaties en alerts.