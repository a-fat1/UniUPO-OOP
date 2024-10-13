# UniUPO - Programmazione ad oggetti
Questa repository contiene l'applicazione per la parte di "Programmazione ad oggetti", che appartiene al corso di "Paradigmi di programmazione".
Java viene utilizzato per quella parte specifica del corso, e di conseguenza il progetto è realizzato con lo stesso linguaggio.
Questo progetto è strutturato utilizzando Gradle, che viene utilizzato (solo) per eseguire i test.
Per questo motivo, il plugin del build.gradle è `id 'java'` al posto di `id 'application'`.
Inoltre, rootProject.name nel settings.gradle è `ListsManager`, e quel nome deve corrispondere alla cartella del progetto.