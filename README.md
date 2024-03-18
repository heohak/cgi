# Filmide veebirakendus
See dokumentatsioon kirjeldab minu filmide veebirakendust, mis koosneb kahest osast: back-end,
mis on loodud kasutades Spring Booti, ja front-end, mis on arendatud Reactiga. Andmebaasiks on kasutatud H2 Databasei.


### Kokkuvõte
Proovitöö peale kulus umbes 3-4 päeva. Filme saab filtreerida žanri, vanusepiirangu, kellaaja (algus alates) ja keele järgi.
Filmide valiku juures on võimalik ka filmide soovitusi näha. Soovituse võtab ta Useri vaatamisajaloost (userId on hardcoded 1 peale hetkel) ja otsib, mis žanri on kõige rohkem vaadatud ja soovitab
selle järgi filme kasutajale.Peale filmi valimist saad valida, mitu istekohta on vaja. Peale seda avaneb istekohtade vaade. Kinosaal on 10x12 suurusega ja algselt on suvalised istmed kinni (10% kogu istmetest).
Istmete soovitus töötab nii, et hakkab keskmisest reast järjest istmeid soovitama ja kui need on läbi vaadatud siis liigub äärmistesse ridadesse. Paremat lahendust ei osanud välja mõelda ja see oli
ka proovitöö kõige raskem osa.




### Autor:
- Henry Ohak