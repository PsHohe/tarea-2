INSTRUCCIONES:

Debes leer el enunciado presentado a continuación e implementar una solución en Java para resolverlo. La solución no es una aplicación real, sino una demostración.

Se usará la consola para mostrar el menú, con un input simple en que el user introduce las opciones que le piden.

La información se guardará simplemente en memoria.

ENUNCIADO:

El equipo de biblioteca de la Universidad Nacional Andrés Bello para la sede de Antonio Varas le ha solicitado a usted y a su equipo actualizar el sistema de biblioteca de dicha institución, para ello el establecimiento ha dispuesto la siguiente lista de requerimientos.
1. Requerimiento usuarios:
1.1. Cada usuario tiene los siguientes atributos
1.1.1. Nombre completo.
1.1.2. RUN.
1.1.3. Género.
1.1.4. Carrera.
1.1.5. Préstamo.
1.2. Crear y editar usuario
1.2.1. El RUN no puede repetirse.
1.2.2. Debe validar formato y dígito verificador del RUN.
1.2.3. Debe validar que el género del usuario sea M o F.
1.2.4. Préstamo corresponde a si el usuario tiene en su poder o no un libro, siendo cero no, e ISBN que sí.
1.3. Eliminar usuario, debe validar que exista.
1.4. Existen usuarios que son Docentes o Estudiantes. A los docentes adicionalmente se les registra la profesión con sus grados (magíster y/o doctor); y a los estudiantes la carrera que está estudiando.
2. Requerimiento Libro
2.1. Cada libro debe contener los siguientes atributos
2.1.1. ISBN (Código libro).
2.1.2. Título.
2.1.3. Autor.
2.1.4. Cantidad en biblioteca.
2.1.5. Cantidad disponible préstamo.
2.1.6. Imagen.
2.2. Crear Libro
2.2.1. El ISBN debe ser único.
2.2.2. Cantidad en biblioteca debe ser mayor a cero
2.2.3. Cantidad disponible debe ser mayor a cero y menor o igual a cantidad en biblioteca.
2.3. Eliminar Libro a través de ISBN, debe validar que exista.
3. Requerimiento Préstamo y Devolución
3.1. Préstamo
3.1.1. Debe ingresar ISBN del Libro a prestar.
3.1.2. El libro debe existir.
3.1.3. El libro debe tener al menos un ejemplar disponible.
3.1.4. Debe ingresar RUN usuario quien solicitó el libro.
3.1.5. El RUN debe existir.
3.1.6. El usuario debe estar habilitado para préstamo.
3.1.7. De cumplirse todos los puntos anteriores, el usuario debe quedar no disponible para nuevo préstamo a través del atributo préstamo, dejando el ISBN del libro a prestar.
3.1.8. De cumplirse todos los puntos anteriores, debe restar uno a la cantidad de ejemplares disponibles del libro.
3.1.9. Para el nuevo préstamo debe ingresar la fecha actual, de forma automática.
3.1.10. Debe ingresar la cantidad de días que se prestó el libro (si es docente el período máximo de préstamo es de 20 días y si es estudiante el período máximo es 10 días).
3.1.11. Debe ingresar la fecha que debe ser devuelto el libro de forma automática.
3.1.12. Finalmente generar la tarjeta de préstamo que será impresa y pegada en el libro que contenga los datos del préstamo (diseñe un formato ad-hoc).
3.2. Devolución
3.2.1. Debe ingresar ISBN del libro a devolver.
3.2.2. Debe validar que el libro exista.
3.2.3. Debe ingresar el RUN del usuario.
3.2.4. Debe validar que el usuario exista.
3.2.5. Debe validar que tanto el ISBN del libro a devolver como el atributo préstamo del usuario sean el mismo.
3.2.6. De cumplirse todo lo anterior debe dejar habilitado al usuario para que pueda solicitar un nuevo libro.
3.2.7. De cumplirse todo lo anterior debe sumar uno a la cantidad de ejemplares disponibles del libro.
3.2.8. Debe considerar que, si la devolución se entrega en un plazo mayor a la fecha que debió devolver el libro, al usuario se le cobrará una multa de $1.000 por cada día de retraso.
