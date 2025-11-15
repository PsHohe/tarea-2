# Plan de Implementación - Sistema de Biblioteca UNAB

## Objetivo
Implementar un sistema de biblioteca en Java con menú de consola para gestionar usuarios, libros, préstamos y devoluciones.

---

## 1. Estructura de Clases POO

### 1.1 Clase Usuario (Abstracta)
**Ubicación**: `biblioteca/src/models/Usuario.java`

**Atributos**:
- `String nombreCompleto`
- `String run` (formato: 12345678-9)
- `char genero` ('M' o 'F')
- `String prestamo` ("0" si no tiene préstamo, ISBN si tiene préstamo activo)

**Métodos**:
- Constructor con validaciones
- Getters y setters
- `abstract int getPeriodoMaximoPrestamo()` - retorna días máximos según tipo usuario
- `boolean tienePrestamo()` - retorna true si prestamo != "0"
- `toString()` para mostrar información

**Validaciones a implementar**:
- RUN no puede repetirse en el sistema
- Validar formato de RUN (8-9 dígitos + guión + dígito verificador)
- Validar dígito verificador del RUN usando algoritmo módulo 11
- Género debe ser 'M' o 'F' exactamente

---

### 1.2 Clase Docente extends Usuario
**Ubicación**: `biblioteca/src/models/Docente.java`

**Atributos adicionales**:
- `String profesion`
- `ArrayList<String> grados` (puede contener "Magíster", "Doctor", o ambos)

**Métodos**:
- Constructor que llama a super()
- `getPeriodoMaximoPrestamo()` - retorna 20
- Getters y setters específicos
- `toString()` sobrescrito

---

### 1.3 Clase Estudiante extends Usuario
**Ubicación**: `biblioteca/src/models/Estudiante.java`

**Atributos adicionales**:
- `String carrera`

**Métodos**:
- Constructor que llama a super()
- `getPeriodoMaximoPrestamo()` - retorna 10
- Getters y setters específicos
- `toString()` sobrescrito

---

### 1.4 Clase Libro
**Ubicación**: `biblioteca/src/models/Libro.java`

**Atributos**:
- `String isbn` (único en el sistema)
- `String titulo`
- `String autor`
- `int cantidadBiblioteca`
- `int cantidadDisponible`
- `String imagen` (puede ser una ruta o descripción)

**Métodos**:
- Constructor con validaciones
- Getters y setters
- `boolean hayDisponible()` - retorna true si cantidadDisponible > 0
- `void prestar()` - reduce cantidadDisponible en 1
- `void devolver()` - aumenta cantidadDisponible en 1
- `toString()` para mostrar información

**Validaciones a implementar**:
- ISBN debe ser único
- cantidadBiblioteca > 0
- cantidadDisponible > 0 y <= cantidadBiblioteca

---

### 1.5 Clase Prestamo
**Ubicación**: `biblioteca/src/models/Prestamo.java`

**Atributos**:
- `String isbn`
- `String run`
- `LocalDate fechaPrestamo` (fecha actual automática)
- `int diasPrestados`
- `LocalDate fechaDevolucion` (calculada automáticamente)

**Métodos**:
- Constructor que calcula fechaDevolucion automáticamente
- `String generarTarjetaPrestamo()` - retorna String con formato de tarjeta
- `int calcularMulta(LocalDate fechaDevolucionReal)` - calcula multa $1000 por día
- Getters
- `toString()`

**Formato de Tarjeta de Préstamo**:
```
╔═══════════════════════════════════════╗
║    TARJETA DE PRÉSTAMO - BIBLIOTECA   ║
║           UNAB ANTONIO VARAS          ║
╠═══════════════════════════════════════╣
║ ISBN: [isbn]                          ║
║ RUN Usuario: [run]                    ║
║ Fecha Préstamo: [dd/MM/yyyy]          ║
║ Días Prestados: [dias]                ║
║ Fecha Devolución: [dd/MM/yyyy]        ║
╚═══════════════════════════════════════╝
```

---

### 1.6 Clase ValidadorRUN (Utilidad)
**Ubicación**: `biblioteca/src/utils/ValidadorRUN.java`

**Métodos estáticos**:
- `boolean validarFormato(String run)` - verifica formato XX.XXX.XXX-X o XXXXXXXX-X
- `boolean validarDigitoVerificador(String run)` - algoritmo módulo 11
- `String limpiarRUN(String run)` - elimina puntos y guiones para procesamiento

**Algoritmo Módulo 11 para dígito verificador**:
1. Limpiar RUN de puntos y guiones
2. Tomar dígitos del RUN (sin el verificador)
3. Multiplicar de derecha a izquierda por serie 2,3,4,5,6,7,2,3...
4. Sumar productos
5. Calcular: 11 - (suma % 11)
6. Si resultado es 11 → '0', si es 10 → 'K', sino → el número

---

### 1.7 Clase SistemaBiblioteca (Controlador)
**Ubicación**: `biblioteca/src/controllers/SistemaBiblioteca.java`

**Atributos**:
- `ArrayList<Usuario> usuarios`
- `ArrayList<Libro> libros`
- `ArrayList<Prestamo> prestamos`

**Métodos CRUD Usuarios**:
- `boolean crearUsuario(Usuario usuario)` - valida RUN único
- `boolean editarUsuario(String run, Usuario datosNuevos)`
- `boolean eliminarUsuario(String run)` - valida que exista
- `Usuario buscarUsuarioPorRUN(String run)`
- `ArrayList<Usuario> listarUsuarios()`

**Métodos CRUD Libros**:
- `boolean crearLibro(Libro libro)` - valida ISBN único
- `boolean eliminarLibro(String isbn)` - valida que exista
- `Libro buscarLibroPorISBN(String isbn)`
- `ArrayList<Libro> listarLibros()`

**Métodos de Préstamo**:
- `Prestamo realizarPrestamo(String isbn, String run, int diasPrestados)`
  - Validar: libro existe
  - Validar: libro tiene disponibilidad
  - Validar: usuario existe
  - Validar: usuario no tiene préstamo activo
  - Validar: días <= periodo máximo según tipo usuario
  - Actualizar: usuario.prestamo = isbn
  - Actualizar: libro.cantidadDisponible - 1
  - Crear y retornar objeto Prestamo

**Métodos de Devolución**:
- `int realizarDevolucion(String isbn, String run)`
  - Validar: libro existe
  - Validar: usuario existe
  - Validar: usuario.prestamo == isbn
  - Actualizar: usuario.prestamo = "0"
  - Actualizar: libro.cantidadDisponible + 1
  - Buscar préstamo en ArrayList
  - Calcular y retornar multa (0 si está a tiempo)

---

### 1.8 Clase MenuPrincipal
**Ubicación**: `biblioteca/src/views/MenuPrincipal.java` o integrar en Main

**Opciones del Menú**:
```
╔═════════════════════════════════════╗
║  SISTEMA BIBLIOTECA UNAB           ║
╠═════════════════════════════════════╣
║  1. Gestión de Usuarios            ║
║  2. Gestión de Libros              ║
║  3. Realizar Préstamo              ║
║  4. Realizar Devolución            ║
║  5. Listar Usuarios                ║
║  6. Listar Libros                  ║
║  0. Salir                          ║
╚═════════════════════════════════════╝
```

**Submenú Gestión de Usuarios**:
- 1.1 Crear Docente
- 1.2 Crear Estudiante
- 1.3 Editar Usuario
- 1.4 Eliminar Usuario
- 1.5 Volver

**Submenú Gestión de Libros**:
- 2.1 Crear Libro
- 2.2 Eliminar Libro
- 2.3 Volver

---

## 2. Flujo de Implementación

### Fase 1: Clases de Modelo Base
1. Implementar `ValidadorRUN.java` con métodos estáticos
2. Implementar clase abstracta `Usuario.java`
3. Implementar `Docente.java` extendiendo Usuario
4. Implementar `Estudiante.java` extendiendo Usuario
5. Implementar `Libro.java` con validaciones

### Fase 2: Clase de Préstamo
6. Implementar `Prestamo.java` con manejo de fechas LocalDate
7. Implementar método `generarTarjetaPrestamo()`
8. Implementar método `calcularMulta()`

### Fase 3: Controlador del Sistema
9. Implementar `SistemaBiblioteca.java`
10. Implementar métodos CRUD para usuarios
11. Implementar métodos CRUD para libros
12. Implementar lógica de préstamo con todas las validaciones
13. Implementar lógica de devolución con cálculo de multas

### Fase 4: Interfaz de Usuario
14. Implementar menú principal en consola
15. Implementar submenús
16. Implementar manejo de inputs con Scanner
17. Implementar manejo de excepciones y mensajes de error

### Fase 5: Clase Main y Demostración
18. Crear clase `Main.java` en `biblioteca/src/`
19. Instanciar SistemaBiblioteca
20. Pre-cargar datos de ejemplo (2-3 usuarios, 3-4 libros)
21. Iniciar menú principal
22. Documentar flujo completo en comentarios

---

## 3. Validaciones Críticas por Función

### Crear Usuario:
- ✓ RUN no repetido en sistema
- ✓ Formato RUN válido (regex: `^\d{7,8}-[\dkK]$`)
- ✓ Dígito verificador correcto (algoritmo módulo 11)
- ✓ Género es 'M' o 'F'
- ✓ Inicializar prestamo = "0"

### Editar Usuario:
- ✓ Usuario existe (búsqueda por RUN)
- ✓ Si se cambia RUN, validar que nuevo RUN no exista
- ✓ Mantener mismas validaciones que crear

### Eliminar Usuario:
- ✓ Usuario existe
- ✓ (Opcional pero recomendado) Usuario no tiene préstamo activo

### Crear Libro:
- ✓ ISBN no repetido
- ✓ cantidadBiblioteca > 0
- ✓ cantidadDisponible > 0
- ✓ cantidadDisponible <= cantidadBiblioteca

### Realizar Préstamo:
- ✓ ISBN existe en sistema
- ✓ Libro tiene cantidadDisponible > 0
- ✓ RUN existe en sistema
- ✓ Usuario no tiene préstamo activo (prestamo == "0")
- ✓ diasPrestados <= usuario.getPeriodoMaximoPrestamo()
- ✓ Actualizar usuario.prestamo = isbn
- ✓ Decrementar libro.cantidadDisponible
- ✓ Generar fechaDevolucion = fechaPrestamo + diasPrestados

### Realizar Devolución:
- ✓ ISBN existe en sistema
- ✓ RUN existe en sistema
- ✓ usuario.prestamo == isbn (coincidencia)
- ✓ Actualizar usuario.prestamo = "0"
- ✓ Incrementar libro.cantidadDisponible
- ✓ Calcular multa si LocalDate.now() > fechaDevolucion
- ✓ Multa = diasRetraso * 1000

---

## 4. Estructura de Archivos Esperada

```
biblioteca/
├── src/
│   ├── Main.java                          # Punto de entrada
│   ├── models/
│   │   ├── Usuario.java                   # Clase abstracta
│   │   ├── Docente.java                   # Herencia
│   │   ├── Estudiante.java                # Herencia
│   │   ├── Libro.java                     # Modelo libro
│   │   └── Prestamo.java                  # Modelo préstamo
│   ├── controllers/
│   │   └── SistemaBiblioteca.java         # Lógica negocio
│   ├── utils/
│   │   └── ValidadorRUN.java              # Utilidades validación
│   └── views/
│       └── MenuPrincipal.java             # Interfaz consola
└── build.xml                              # NetBeans (ya existe)
```

---

## 5. Consideraciones de Implementación

### Imports necesarios:
- `java.util.ArrayList` - para listas dinámicas
- `java.time.LocalDate` - para manejo de fechas
- `java.time.temporal.ChronoUnit` - para cálculo de días
- `java.util.Scanner` - para entrada de usuario
- `java.util.regex.Pattern` - para validación RUN

### Manejo de fechas:
- Usar `LocalDate.now()` para fecha actual
- Usar `fechaPrestamo.plusDays(diasPrestados)` para fecha devolución
- Usar `ChronoUnit.DAYS.between(fecha1, fecha2)` para calcular diferencia

### Buenas prácticas:
- Cada clase en su propio archivo
- Comentarios JavaDoc en métodos públicos
- Validaciones en constructores y setters
- Métodos privados para lógica interna
- Nombres descriptivos en español
- Indentación consistente (4 espacios)
- Manejo de excepciones con try-catch
- Mensajes de error descriptivos

### Datos de demostración (Main.java):
Crear al menos:
- 1 Docente (con Magíster)
- 2 Estudiantes (carreras diferentes)
- 4 Libros (con ISBNs válidos)

Demostrar flujo completo:
1. Listar usuarios y libros iniciales
2. Crear nuevo estudiante
3. Realizar préstamo válido
4. Intentar préstamo inválido (usuario ya tiene libro)
5. Realizar devolución a tiempo
6. Realizar préstamo con días excedidos
7. Realizar devolución con multa

---

## 6. Checklist de Verificación Final

- [ ] Todas las clases tienen comentarios JavaDoc
- [ ] Validador RUN implementado con algoritmo módulo 11
- [ ] Herencia Usuario → Docente/Estudiante funcional
- [ ] Polimorfismo en getPeriodoMaximoPrestamo()
- [ ] ArrayList para usuarios, libros y préstamos
- [ ] Validaciones de RUN único y ISBN único
- [ ] Cálculo automático de fechas
- [ ] Generación de tarjeta de préstamo con formato
- [ ] Cálculo de multas por días de retraso
- [ ] Menú de consola funcional
- [ ] Manejo de excepciones en inputs
- [ ] Datos de demostración pre-cargados
- [ ] Main.java demuestra todos los casos de uso
- [ ] Código indentado y bien organizado
- [ ] Sin código comentado innecesariamente

---

## 7. Notas para la IA Implementadora

- **NO crear archivos de persistencia**: Todo se mantiene en memoria (ArrayLists)
- **NO usar bases de datos**: Solo estructuras en memoria
- **NO crear interfaz gráfica**: Solo consola con Scanner
- **SÍ validar exhaustivamente**: Cada requerimiento del enunciado debe cumplirse
- **SÍ comentar el código**: Especialmente validaciones y lógica de negocio
- **SÍ usar herencia y polimorfismo**: Es evaluado por el profesor
- **SÍ crear Main demostrativo**: Debe mostrar todos los casos de uso

### Orden de implementación recomendado:
1. ValidadorRUN (independiente)
2. Usuario, Docente, Estudiante (jerarquía)
3. Libro (independiente)
4. Prestamo (usa LocalDate)
5. SistemaBiblioteca (usa todo lo anterior)
6. MenuPrincipal (usa SistemaBiblioteca)
7. Main (orquesta todo)

### Testing manual a realizar:
- Crear usuario con RUN inválido → debe rechazar
- Crear usuario con RUN duplicado → debe rechazar
- Crear libro con ISBN duplicado → debe rechazar
- Préstamo sin disponibilidad → debe rechazar
- Préstamo con usuario ocupado → debe rechazar
- Devolución con ISBN no coincidente → debe rechazar
- Cálculo de multa correcto → verificar matemática
- Periodo máximo Docente vs Estudiante → verificar 20 vs 10 días
