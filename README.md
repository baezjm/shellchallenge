# shellchallenge

Resolución iterada challenge shell.

Patrones utilizados:
- Command: Para crear cada comando (ls, mkdir, cd, pwd, touch).
- Composite: Para la estructura del File System.
- Factory: En este caso lo hice con un enum, para crear los distintos comandos.

Extras
- Se agregó la funcionalidad extra para ls -r.
- Se agregó la funcionalidad extra cd y ls con full path.
- Se agregó un test con mockito para ejmplo de utilización de test unitarios.
- Manejo de un archivo state.txt (la extensión no importa mucho) para guardar el estado del file system.

# ¿Cómo ejecutar la aplicación?
- Importar cómo proyecto maven en intellij
- Run de la clase Main.java
