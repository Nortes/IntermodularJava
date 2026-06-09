# Análisis de dominio de la API REST del sistema de reservas

El proyecto consiste en un sistema de gestión de reservas de recursos. La funcionalidad principal permite que un usuario realice una reserva sobre un recurso concreto en una fecha y franja horaria determinada.

La entidad principal del sistema es `Reserva`. Esta entidad se relaciona con `Usuario`, ya que cada reserva pertenece a un usuario; con `Recurso`, ya que cada reserva se realiza sobre un recurso concreto; y con `Horario`, ya que la reserva ocupa una franja horaria. Además, la tabla `DisponibleEn` indica qué horarios están disponibles para cada recurso.

## Tablas implicadas

La tabla principal es `reserva`. Sus campos principales son:

- `id_reserva_local`: identificador local de la reserva dentro de un recurso.
- `id_recurso`: identificador del recurso reservado.
- `id_usuario`: identificador del usuario que realiza la reserva.
- `fecha`: fecha de la reserva.
- `hora_inicio`: hora de inicio.
- `hora_fin`: hora de finalización.
- `coste`: coste calculado de la reserva.
- `numero_plazas`: número de plazas solicitadas.
- `motivo`: motivo de la reserva.
- `observaciones`: información adicional.

La tabla `recurso` contiene la información del recurso que se puede reservar. Sus campos principales son `id_recurso`, `nombre`, `descripcion`, `ubicacion`, `capacidad` y `precioHora`.

La tabla `usuario` contiene los datos del usuario que realiza la reserva. Para la API de reservas solo es necesario exponer el identificador del usuario, no datos sensibles como la contraseña.

La tabla `horario` define las franjas horarias disponibles, incluyendo día de la semana, hora de inicio y hora de fin.

La tabla `disponibleen` relaciona cada recurso con los horarios en los que puede reservarse.

## Campos expuestos en la API

En las respuestas de la API se expondrán los datos necesarios para que el frontend pueda mostrar una reserva completa:

- `id`: identificador compuesto usado por el programa.
- `idReservaLocal`
- `idRecurso`
- `idUsuario`
- `fecha`
- `horaInicio`
- `horaFin`
- `coste`
- `numeroPlazas`
- `motivo`
- `observaciones`

En las peticiones para crear una reserva no se enviará el coste, ya que lo calcula el backend a partir del precio por hora del recurso y la duración de la reserva. Tampoco se enviará el identificador final de la reserva, ya que se genera al guardar la reserva.

## Validaciones y reglas de negocio

Para crear una reserva se deben aplicar varias validaciones:

- El recurso debe existir.
- El usuario debe existir.
- La hora de inicio debe ser anterior a la hora de fin.
- El recurso debe estar disponible en ese horario.
- No debe existir ya una reserva con el mismo identificador.
- El número de plazas debe ser mayor que 0.
- El coste se calcula automáticamente desde el backend.

El sistema ya comprueba la disponibilidad comparando el recurso reservado con la tabla `DisponibleEn` y validando que el horario de la reserva coincide con un horario permitido para ese recurso.
